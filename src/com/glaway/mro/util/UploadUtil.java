package com.glaway.mro.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysPropCache;
import com.glaway.sddq.tools.NetDiskUtil;

/**
 * 
 * 附件上传处理类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2016-5-23]
 * @since [GlawayMro4.0/附件]
 */
public class UploadUtil implements FixedLoggers {
	private final String SECRET_KEY = "GW_MRO_F07egs10L";// DES密钥

	String uploadFileName = null;

	String uploadFileNameFull = null;

	String encoding = "UTF-8";

	String username = null;

	String password = null;

	String remoteIp = null;

	MroServer mroServer = null;

	SysPropCache sysPropCache = null;

	String directoryName = null;

	boolean needToDeleteFile = false;

	boolean isRemote = false;

	boolean isEncrypt = false;// 加密文件

	boolean isEncryptName = false;// 加密文件名

	boolean isOverWrite = false;

	boolean isCheckExtName = true;

	String allowedFileExtensions = null;

	HttpServletRequest request = null;

	long maxSize = 10485760L;

	long maxSizeMB = 10L;

	public UploadUtil() {
		mroServer = MroServer.getMroServer();
		sysPropCache = mroServer.getSysPropCache();
		String remote = sysPropCache.getPropValue("mro.doc.remote");
		String checkext = sysPropCache
				.getPropValue("mro.doc.checkFileExtensions");
		String encrypt = MroServer.getMroServer().getSysProp("mro.doc.encrypt");
		String encryptName = MroServer.getMroServer().getSysProp(
				"mro.doc.encryptName");
		if (StringUtil.isStrNotEmpty(remote)) {
			isRemote = Boolean.valueOf(remote);
		}
		if (StringUtil.isStrNotEmpty(checkext)) {
			isCheckExtName = Boolean.valueOf(checkext);
		}
		if (isCheckExtName) {
			allowedFileExtensions = MroServer.getMroServer().getSysProp(
					"mro.doc.allowedFileExtensions");
		}
		if (StringUtil.isStrNotEmpty(encrypt)) {
			isEncrypt = Boolean.valueOf(encrypt);
		}
		if (StringUtil.isStrNotEmpty(encryptName)) {
			isEncryptName = Boolean.valueOf(encryptName);
		}

		if (isRemote) {
			username = sysPropCache.getPropValue("mro.doc.remote.username");
			password = sysPropCache.getPropValue("mro.doc.remote.password");
			remoteIp = sysPropCache.getPropValue("mro.doc.remote.domain");
		}
		String maxMegs = MroServer.getMroServer().getSysProp(
				"mro.doc.maxfilesize");
		if (StringUtil.isNull(maxMegs)) {
			maxSize = 10485760;
			maxSizeMB = 10;
		} else {
			long maxMegsNumber = Integer.parseInt(maxMegs);
			maxSizeMB = maxMegsNumber;
			maxSize = (maxMegsNumber * 1024 * 1024);
		}
	}

	public UploadUtil(HttpServletRequest req) {
		this();
		request = req;
	}

	/**
	 * 
	 * 获得上传文件列表
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 *             [参数说明]
	 * 
	 */
	public List<FileItem> getFileItems() throws IOException,
			FileUploadException {
		request.setCharacterEncoding(getEncoding());
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(dfif);
		List<FileItem> items = upload.parseRequest(request);
		return items;
	}

	/**
	 * 
	 * 根据mro系统规则设置附件上传目录
	 * 
	 * @param doctype
	 *            文档类型
	 * 
	 */
	public void setMroDirectoryName(String doctype) {
		String docRootFolder = MroServer.getMroServer().getSysPropCache()
				.getPropValue("mro.doc.rootfolder");
		String dirName = docRootFolder;
		if (docRootFolder.endsWith("\\")) {
			dirName = dirName + doctype;
		} else {
			dirName = dirName + File.separator + doctype;
		}
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		int month = (Calendar.getInstance().get(Calendar.MONTH) + 1);
		String monthStr = month < 10 ? "0" + month : "" + month;
		this.directoryName = dirName + File.separator + year + monthStr;

	}

	/**
	 * 设置报表文件临时存储目录
	 * 
	 * @param doctype
	 */
	public void setTempUploadReportDirName(String doctype) {
		this.directoryName = doctype;
	}

	/**
	 * 
	 * 获得上传目录
	 * 
	 * @return 目录路径
	 * 
	 */
	public String getDirectoryName() {
		return directoryName;
	}

	/**
	 * 
	 * 设置附件上传目录
	 * 
	 * @param directoryName
	 *            上传目录路径
	 * 
	 */
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public HashSet<String[]> writeToDisk(IJpo jpo) throws Exception {
		HashSet<String[]> writeFileToDisk = writeFileToDisk(jpo);
		if (isRemote()) {
			return writeFileToRemoteDisk(writeFileToDisk);
		}
		return writeFileToDisk;
	}

	public HashSet<String[]> writeToDisk() throws Exception {
		HashSet<String[]> writeFileToDisk = writeFileToDisk(null);
		if (isRemote()) {
			return writeFileToRemoteDisk(writeFileToDisk);
		}
		return writeFileToDisk;
	}

	public HashSet<String[]> writeToDiskForImpdata() throws Exception {
		HashSet<String[]> hs = new HashSet<String[]>();
		String[] f = new String[5];
		// if (isRemote())
		// {
		// HashSet<SmbFile> remoteSet = writeFileToRemoteDisk();
		// for (SmbFile sf : remoteSet)
		// {
		// f[0] = sf.getName();
		// f[1] = sf.getPath() + File.separator + sf.getName();
		// f[2] = getDirectoryName();
		// f[3] = getFileExtensions(f[0]);
		// hs.add(f);
		// }
		// }
		// else
		// {
		HashSet<File> fileSet = writeFileToDiskForImpdata();
		for (File sf : fileSet) {
			f[0] = sf.getName();
			f[1] = sf.getPath() + File.separator + sf.getName();
			f[2] = getDirectoryName();
			f[3] = getFileExtensions(f[0]);
			f[4] = "";
			hs.add(f);
		}
		// }
		return hs;
	}

	/**
	 * 
	 * 将上传的文件写入到硬盘
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public HashSet<String[]> writeFileToDisk(IJpo jpo) throws Exception {
		HashSet<String[]> fset = new HashSet<String[]>();
		List<FileItem> items = getFileItems();
		Iterator<FileItem> its = items.iterator();
		String dir = getDirectoryName();
		File fdir = new File(dir);
		boolean b = true;
		if (!fdir.exists()) {
			b = fdir.mkdirs();
		}
		if (b && fdir.exists()) {
			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				// 判断上传文件名是否为空
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				} else if (fileName.length() > 250) {
					throw new AppException("jspmessages", "filenametoolong");
				}
				if (!fi.isFormField()) {
					long length = fi.getSize();

					if ((length > this.maxSize) && (this.maxSize > 0)) {
						String[] params = { fi.getName(),
								Long.toString(this.maxSizeMB) };
						throw new AppException("jspmessages",
								"contentLengthExceedsLimit", params);
					}
					if (fileName.indexOf("\\") > -1) {
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
					}
					String extName = getFileExtensions(fileName);
					if (!checkAllowedFileExtensions(extName)) {
						String[] params = { extName };
						throw new AppException("jspmessages",
								"noAllowedFileExtensions", params);
					}
//					if (!isOverWrite()) {
//						fileName = getUniqueFileName(fileName);
//					}
					// 加密文件名
					String encryptFileName = fileName;
					if (isEncryptName()) {
						encryptFileName = encryptName(fileName);
					}

					File file = new File(dir, encryptFileName);
					// 写入应用服务器硬盘
					fi.write(file);

					// 写入网盘
					String netdisk = MroServer.getMroServer().getSysPropCache()
							.getPropValue("mro.doc.netdisk");
					if ("1".equals(netdisk) && jpo != null) {
						String[] dirs = dir.split("\\\\");
						String newdir = dirs[dirs.length - 2] + "/"
								+ dirs[dirs.length - 1];
						file = NetDiskUtil.uploadFile(newdir, file);
					}
					
					fileName = file.getName();
					String[] f = new String[5];
					f[0] = fileName;
					if (isEncrypt()) {
						encryptStream(encryptFileName);
					}
					if (isEncryptName()) {
						f[1] = file.getPath();
					} else {
						f[1] = getDirectoryName() + File.separator
								+ file.getName();
					}

					f[2] = getDirectoryName();
					f[3] = getFileExtensions(f[0]);
					f[4] = this.isEncryptName ? encryptFileName : "";
					fset.add(f);
					
					if ("1".equals(netdisk) && jpo != null) {
						// 删除应用服务器端的文件
						if (file.exists() && file.isFile()) {
							file.delete();
						}
					}
				}
			}
		}
		return fset;
	}

	/**
	 * 
	 * 上传导入模板
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public HashSet<File> writeFileToDiskForImpdata() throws Exception {
		HashSet<File> fset = new HashSet<File>();
		List<FileItem> items = getFileItems();
		Iterator<FileItem> its = items.iterator();
		String dir = getDirectoryName();
		File fdir = new File(dir);
		boolean b = true;
		if (!fdir.exists()) {
			b = fdir.mkdirs();
		}
		if (b && fdir.exists()) {
			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				// 判断上传文件名是否为空
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				}
				if (!fi.isFormField()) {
					long length = fi.getSize();

					if ((length > this.maxSize) && (this.maxSize > 0)) {
						String[] params = { fi.getName(),
								Long.toString(this.maxSizeMB) };
						throw new AppException("jspmessages",
								"contentLengthExceedsLimit", params);
					}
					if (fileName.indexOf("\\") > -1) {
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
					}
					String extName = getFileExtensions(fileName);
					if (!extName.equals("xlsx") && !extName.equals("xls")) {
						String[] params = { extName };
						throw new AppException("jspmessages",
								"noAllowedFileExtensions", params);
					}
					if (!isOverWrite()) {
						fileName = getUniqueFileName(fileName);
					}
					// if (isEncryptName())
					// {
					// fileName = encryptName(fileName);
					// }

					File file = new File(dir, fileName);
					fi.write(file);
					// // if (isEncrypt())
					// // {
					// encryptStream(fileName);
					// // }
					fset.add(file);
				}
			}
		}
		return fset;
	}

	public String encryptName(String fName) throws UnsupportedEncodingException {
		// String fileName = fName;
		// int fileNameLength = fileName.length();
		// char[] fileNameChars = new char[fileNameLength];
		// int j = 0;
		// for (int i = 0; i < fileNameLength; i++)
		// {
		// char secChar = SECRET_KEY.charAt(j);
		// if (j == SECRET_KEY.length() - 1)
		// {
		// j = 0;
		// }
		// fileNameChars[i] = (char)(fileName.charAt(i) ^ secChar);
		// j++;
		// }
		// fileName = new String(fileNameChars);
		// return fileName;
		return UUID.randomUUID().toString();
	}

	public HashSet<String[]> writeFileToRemoteDisk(
			HashSet<String[]> writeFileToDisk) throws IOException,
			AppException, FileUploadException {
		if (remoteIp == null || remoteIp.equals("")) {
			throw new AppException("doclink", "请设置共享目录的ip地址");
		}
		NtlmPasswordAuthentication mpa = null;
		try {
			mpa = new NtlmPasswordAuthentication(remoteIp, username, password);
		} catch (Exception e) {
			throw new AppException("remoteFile", "connectouttime");
		}
		String dir = getDirectoryName();
		String remoteIpAddress = sysPropCache
				.getPropValue("mro.doc.remote.ipaddress");

		dir = dir.replaceAll("\\\\", "/").replace(":", "");
		if (StringUtil.isNull(remoteIpAddress)) {
			throw new AppException("remoteFile", "errorconfig");
		}
		SmbFile remoteDir = new SmbFile("smb://" + remoteIpAddress + "/"
				+ dir.replaceAll("\\\\", "/").replace(":", ""), mpa);
		Boolean existsFlag = false;
		try {
			existsFlag = remoteDir.exists();
		} catch (Exception se) {
			if (se instanceof SmbAuthException) {
				throw new AppException("remoteFile", "errorconfig");
			} else {
				throw new AppException("remoteFile", "connectouttime");
			}
		}
		if (!existsFlag) {
			remoteDir.mkdirs();
		}
		if (remoteDir.exists()) {
			for (String[] s : writeFileToDisk) {
				String fileName = s[0];
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				}

				String outputFileName;
				if (isEncryptName()) {
					outputFileName = dir + "/" + s[4];
				} else {
					outputFileName = dir + "/" + s[0];
				}

				SmbFile file = new SmbFile("smb://" + remoteIpAddress + "/"
						+ outputFileName, mpa);
				SmbFileOutputStream fo = new SmbFileOutputStream(file);
				InputStream is = new FileInputStream(s[1]);
				byte[] bbuf = new byte[102400];
				int result;
				while ((result = is.read(bbuf, 0, bbuf.length)) != -1) {
					fo.write(bbuf, 0, result);
				}
				fo.flush();
				fo.close();
				is.close();
				// 删除旧文件
				if (isEncryptName()) {
					deleteFileOnDisk(getDirectoryName() + File.separator + s[4]);
				} else {
					deleteFileOnDisk(getDirectoryName() + File.separator + s[0]);
				}
			}
		}
		return writeFileToDisk;
	}

	/**
	 * 
	 * 加密文件
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 */
	public void encryptStream(String fileName) {
		try {
			String dir = getDirectoryName();
			String secFileName = fileName + ".sec";
			OutputStream out = null;
			InputStream is = null;
			CipherInputStream cis = null;
			File secFile = new File(dir, secFileName);
			File oldFile = new File(dir, fileName);
			is = new FileInputStream(oldFile);
			Cipher cipher = MroServer.getMroServer().getEncoder();
			out = new FileOutputStream(secFile);
			cis = new CipherInputStream(is, cipher);
			byte[] buffer = new byte[1024];
			int r = 0;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
			out.close();
			is.close();
			cis.close();
			deleteFileOnDisk(dir + "\\\\" + fileName);
			if (secFile.exists()) {
				boolean b = secFile.renameTo(oldFile);
				if (!b) {
					EXCEPTIONLOGGER.error("rename file '" + oldFile + "' fail");
				}
			}
		} catch (FileNotFoundException e) {
			FixedLoggers.EXCEPTIONLOGGER.error(e);
		} catch (IOException e) {
			FixedLoggers.EXCEPTIONLOGGER.error(e);
		}
	}

	/**
	 * 
	 * 解密文件
	 * 
	 * @param buffer
	 * @return [参数说明]
	 * 
	 */
	public byte[] decryptStream(byte[] buffer) {
		Cipher cipher = MroServer.getMroServer().getDecoder();
		byte[] outBuffer = null;
		try {
			outBuffer = cipher.doFinal(buffer);
		} catch (IllegalBlockSizeException e) {
			FixedLoggers.EXCEPTIONLOGGER.error(e);
		} catch (BadPaddingException e) {
			FixedLoggers.EXCEPTIONLOGGER.error(e);
		}
		return outBuffer;
	}

	/**
	 * 
	 * 获得唯一文件名
	 * 
	 * @param fileName
	 *            原始文件名
	 * @return 唯一文件名
	 * 
	 */
	public String getUniqueFileName(String fn) {
		String fileName = fn;
		String dir = getDirectoryName();
		String uniqueFile = dir + File.separator + fileName;
		File f = new File(uniqueFile);
		if (!f.exists()) {
			return fileName;
		}
		String extension = "";
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			extension = fileName.substring(index);
			fileName = fileName.substring(0, index);
		}

		String uniqueFileName = "";
		while (true) {
			long timeInMillis = System.currentTimeMillis();
			uniqueFileName = fileName + timeInMillis + extension;
			uniqueFile = dir + File.separator + uniqueFileName;
			f = new File(uniqueFile);
			if (!(f.exists())) {
				break;
			}
		}
		return uniqueFileName;
	}

	/**
	 * 
	 * 删除文件
	 * 
	 * @param fileName
	 *            带路径的文件名
	 * 
	 */
	public void deleteFileOnDisk(String fileName) {
		File f = new File(fileName);
		if (!(f.exists())) {
			return;
		}
		boolean deletedf = f.delete();
		if (deletedf) {
			return;
		}
	}

	/**
	 * 判断是否加密文件，true 加密，false 不加密
	 */
	public boolean isEncrypt() {
		return isEncrypt;
	}

	/**
	 * 
	 * 设置是否加密
	 * 
	 * @param isEncrypt
	 *            true 加密，false 不加密
	 * 
	 */
	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	/**
	 * 
	 * 是否覆盖同名文件
	 * 
	 * @return true 覆盖，false 不覆盖
	 * 
	 */
	public boolean isOverWrite() {
		String overWrite = MroServer.getMroServer().getSysPropCache()
				.getPropValue("mro.doc.overwrite");
		if (StringUtil.isEqualIgnoreCase(overWrite, "true")) {
			return true;
		}
		return isOverWrite;
	}

	/**
	 * 
	 * 设置是否覆盖同名文件
	 * 
	 * @param isOverWrite
	 *            true 覆盖，false 不覆盖
	 * 
	 */
	public void setOverWrite(boolean isOverWrite) {
		this.isOverWrite = isOverWrite;
	}

	/**
	 * 
	 * 是否使用了远程文档服务器
	 * 
	 * @return [参数说明]
	 * 
	 */
	public boolean isRemote() {
		return isRemote;
	}

	/**
	 * 
	 * 获得当前使用的字符集
	 * 
	 * @return 当前使用的字符集
	 * 
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 
	 * 设置当前使用的字符集
	 * 
	 * @param encoding
	 *            字符集
	 * 
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 
	 * 检查文件扩展名是否符合要求
	 * 
	 * @param extName
	 *            扩展名
	 * @return true 符合要求，false 不符合要求
	 * 
	 */
	public boolean checkAllowedFileExtensions(String extName) {
		if (isCheckExtName) {
			String extNames[] = allowedFileExtensions.split(",");
			for (String name : extNames) {
				if (StringUtil.isEqualIgnoreCase(extName, name)) {
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 获得文件扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件的扩展名
	 * 
	 */
	public String getFileExtensions(String fileName) {
		String extName = "";
		if (StringUtil.isStrNotEmpty(fileName)) {
			extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return extName;
	}

	public boolean isEncryptName() {
		return isEncryptName;
	}

	public void setEncryptName(boolean isEncryptName) {
		this.isEncryptName = isEncryptName;
	}

	public File writeTempFileToDisk() throws Exception {
		File file = null;
		file = writeReportFileToDisk();
		return file;
	}

	public File writeReportFileToDisk() throws Exception {
		File tempFile = null;
		List<FileItem> items = getFileItems();
		Iterator<FileItem> its = items.iterator();
		String dir = getDirectoryName();
		File fdir = new File(dir);
		boolean b = true;
		if (!fdir.exists()) {
			b = fdir.mkdirs();
		}
		if (b && fdir.exists()) {
			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				// 判断上传文件名是否为空
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				}
				// 文件名特殊字符限制
				hasSpecialCharts(fileName);

				if (fi.getSize() > 104857600) { // 报表文件大于100M限制
					throw new AppException("jspmessages", "tooBigfile");
				}
				String extName = getFileExtensions(fileName);
				if (!(extName.equalsIgnoreCase("frm")
						|| extName.equalsIgnoreCase("cpt") || extName
							.equalsIgnoreCase("rptdesign"))) {
					throw new AppException("jspmessages", "notreportfile");
				}
				if (!fi.isFormField()) {
					File file = new File(dir, fileName);
					fi.write(file);
					tempFile = file;
				}
			}
		}
		return tempFile;
	}

	public boolean writeAppXmlFileToDisk() throws Exception {
		boolean tempFile = true;
		List<FileItem> items = getFileItems();
		Iterator<FileItem> its = items.iterator();
		String dir = getDirectoryName();
		File fdir = new File(dir);
		boolean b = true;
		if (!fdir.exists()) {
			b = fdir.mkdirs();
		}
		if (b && fdir.exists()) {
			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				// 判断上传文件名是否为空
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				}
				String extName = getFileExtensions(fileName);
				if (!extName.equalsIgnoreCase("xml")) {
					tempFile = false;
				}
			}
		}
		return tempFile;
	}

	public File getAppXmlFileToDisk() throws Exception {
		File tempFile = null;
		List<FileItem> items = getFileItems();
		Iterator<FileItem> its = items.iterator();
		String dir = getDirectoryName();
		File fdir = new File(dir);
		boolean b = true;
		if (!fdir.exists()) {
			b = fdir.mkdirs();
		}
		if (b && fdir.exists()) {
			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				if (StringUtil.isStrEmpty(fileName)) {
					// mroServer.getMroSession(request).setIframeResponse(true);
					throw new AppException("jspmessages", "nouploadfile");
				}
				String extName = getFileExtensions(fileName);
				if (!extName.equalsIgnoreCase("xml")) {
					// mroServer.getMroSession(request).setIframeResponse(true);
					throw new MroException("designer", "importnoxml");
				}
				if (!fi.isFormField()) {
					File file = new File(dir, fileName);
					fi.write(file);
					tempFile = file;
				}
			}
		}
		return tempFile;

	}

	public void hasSpecialCharts(String fileName) throws MroException {
		String specialCharts = "[`~!@#$%^&*()\"（）+=|':;',\\[\\]<>/?~！@#￥……-【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(specialCharts.trim());
		Matcher m = p.matcher(fileName);
		if (m.find()) {
			throw new MroException("reportmanage", "specialfilename");
		}

	}

}
