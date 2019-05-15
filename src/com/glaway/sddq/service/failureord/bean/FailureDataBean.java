package com.glaway.sddq.service.failureord.bean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.glaway.mro.app.imp.bean.ImpIfaceDataBean;
import com.glaway.mro.app.system.doclinks.bean.AddDoclinkBean;
import com.glaway.mro.app.system.doclinks.data.SysDocinfo;
import com.glaway.mro.app.system.doclinks.data.SysDoclink;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;
import com.glaway.sddq.tools.BigDataFTPUploader;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.WorkorderUtil;
import com.glaway.sddq.tools.ZipHelper;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * 
 * 故障数据包 databean
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-8]
 * @since [MRO4.1/故障工单]
 */
public class FailureDataBean extends AddDoclinkBean {

	long maxSize = 10485760L;
	long maxSizeMB = 10L;
	String directoryName = null;

	@Override
	public void initialize() throws MroException {
		super.initialize();
		if (currJpo != null) {
			if (getCount() > 0) {
				throw new MroException("", "已上传过故障数据包！");
			}
		}
	}

	/**
	 * 重写附件上传功能，修改上传后文件名
	 * 
	 * @return
	 * @throws Exception
	 */
	public int uploadfile() throws MroException {

		mroSession.getRequest().getSession().setAttribute("isSuccess", "");
		boolean importflag = false;
		long doclinksid = 0l;
		String[] ftpStr = null;
		try {
			if (dataBean != null) {
				getMroSession().setIframeResponse(true);
				checkSave();
				if (currJpo != null) {
					currJpo.getString("DOCTYPE");
					doclinksid = currJpo.getId();
					if (currJpo.isNull("DOCTYPE")) {
						String[] p = { "DOCTYPE" };
						throw new AppException("system", "null", p);
					}
					// 设置文件夹为故障文件夹
					currJpo.setValue("DOCTYPE", "FAILURE");

					UploadUtil upload = new UploadUtil(getMroSession()
							.getRequest());
					// upload.setMroDirectoryName(getString("DOCTYPE"));
					setMroDirectoryName("FAILURE");
					// upload.getFileItems();
					String ip = getMroSession().getRequest().getRemoteAddr();
					if (currJpo instanceof SysDoclink) {
						ftpStr = writeFileToDisk(dataBean.getParent().getJpo());

						String content = Property.UPLOADFILE_LABLE + "\""
								+ ftpStr[0] + "\"";
						writeAuditLog(getAppName(), content,
								Property.UPLOADFILE_LABLE, true, "",
								currJpo.getName(), ip, false);
						if (dataBean.getParent() != null) {
							IJpo parent = dataBean.getParent().getJpo();

							/* 重命名文件 */
							String[] newFileInfo = renameFile(parent,
									ftpStr[0], ftpStr[1]);
							// s[0] = newFileInfo[0];
							ftpStr[1] = newFileInfo[1];
							// s[5]是压缩包文件内文件列表
							currJpo.setValue("FILELIST", ftpStr[5]);

							((SysDoclink) currJpo).addDoclink(parent,
									ftpStr[0], ftpStr[2], ftpStr[0], ftpStr[4]);
							dataBean.getParent().putDocliksMap(currJpo.getId(),
									"A");
							getAppBean().putDocliksMap(currJpo.getId(), "A");
							// parent.getJpoSet("FAILURELIB").save();
						}
						doclinkSet.save();
					}
					if (currJpo instanceof SysDocinfo) {
						if (!currJpo.isNull("DOCUMENT")) {
							HashSet<String[]> hs = null;
							hs = upload.writeToDisk(currJpo);
							for (String[] s : hs) {
								String content = Property.UPLOADFILE_LABLE
										+ "\"" + s[0] + "\"";
								writeAuditLog(getAppName(), content,
										Property.UPLOADFILE_LABLE, true, "",
										currJpo.getName(), ip, false);
								((SysDocinfo) currJpo).addDocinfo(s[0], s[2],
										s[0], s[4]);
								if (dataBean.getParent() != null) {
									dataBean.getParent().putDocliksMap(
											currJpo.getId(), "A");
								} else {
									getAppBean().putDocliksMap(currJpo.getId(),
											"A");
								}
							}
							doclinkSet.save();
						} else {
							String[] p = { getJpoSet().getFieldInfo("DOCUMENT")
									.getTitle() };
							throw new AppException("system", "null", p);
						}
					}
				}
				dataBean.reset();
				if (dataBean.getJpo() != null) {
					dataBean.getJpo().setValue("CHANGEDATE", getSysDate());
					dataBean.getJpo().setValue("CHANGEBY",
							getUserInfo().getPersonId());
				}
				dialogclose();
				getAppBean().save();
			}
		} catch (Exception e) {
			mroSession.getRequest().getSession()
					.setAttribute("isSuccess", "uploadfileSuccess");
			throw new MroException(e.getMessage());
		}
		mroSession.getRequest().getSession()
				.setAttribute("isSuccess", "uploadfileSuccess");
		if (importflag
				&& (currJpo != null ? (currJpo instanceof SysDoclink) : false)) {
			IJpoSet doclinksSet = dataBean.getJpo().getJpoSet();
			doclinksSet.setOrderBy("createdate desc");
			doclinksSet.reset();
			IJpo doclinkJpo = doclinksSet.getJpo(0);
			IJpoSet docinfoSet = doclinkJpo.getJpoSet("SYS_DOCINFO");
			IJpo docinfojpo = null;
			String filename = null;
			if (!docinfoSet.isEmpty()) {
				docinfojpo = docinfoSet.getJpo(0);
				filename = docinfojpo.getString("URLNAME");
			}
			ImpIfaceDataBean impdataBean = (ImpIfaceDataBean) this.page
					.getAppBean().getDataBean("1487057668704");
			try {
				impdataBean.impData(filename, doclinksid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isStrNotEmpty(ftpStr[6])) {
			showMsgbox("提示", ftpStr[6].trim());
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	public String[] writeToDisk(IJpo workorder) throws Exception {
		String[] writeFileToDisk = writeFileToDisk(workorder);

		return writeFileToDisk;
	}

	public String[] writeFileToDisk(IJpo workorder) throws MroException {
		String[] f = new String[7];
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
				String oldName = fi.getName();
				String oldPath = getDirectoryName() + "\\" + oldName;

				String[] fileNames = renameFile(workorder, oldName, oldPath);
				String fileName = fileNames[0];

				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				} else if (fileName.length() > 250) {
					throw new AppException("jspmeasages", "filenametoolong");
				}
				if (!fi.isFormField()) {
					long length = fi.getSize();

					if (fileName.indexOf("\\") > -1) {
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
					}
					String extName = getFileExtensions(fileName);
					// if(!checkAllowedFileExtensions(extName)){
					// String[] params = {extName};
					// throw new AppException("jspmessasges",
					// "noAllowedFileExtensions", params);
					// }
					// 后缀过滤压缩文件
					String[] canUploadSuffix = { "zip", "rar" };
					if (!StringUtil.isHaveStr(canUploadSuffix,
							extName.toLowerCase())) {
						throw new MroException("文件格式不正确，请选择正确的压缩包上传！");
					}
					if (!isOverWrite()) {
						fileName = getUniqueFileName(fileName);
					}
					f[0] = fileName;

					// 写入到硬盘的方法屏蔽掉
					// File file = new File(dir, fileName);
					// fi.write(file);

					// f[1] = getDirectoryName() + File.separator +
					// file.getName();

					/* 文件列表START */
					OutputStream os = null;
					InputStream input = null;
					BufferedOutputStream bof = null;
					try {
						HashSet<String> fileNameSet = new HashSet<String>();
						// 临时文件
						File tempFile = new File(fileName + "TempFile");
						os = new FileOutputStream(tempFile);
						bof = new BufferedOutputStream(os);

						// add by zh start
						if (length > 100 * 1024 * 1024) {// 100M以上文件采用分块上传

							input = fi.getInputStream();
							int temp = 0;
							byte[] data = new byte[512];
							while ((temp = input.read(data, 0, 512)) != -1) {
								bof.write(data);
							}

						} else {

							bof.write(fi.get());

						}
						// add by zh end

						// bof.write(fi.get());
						// zip格式的压缩包
						if ("zip".equals(extName.toLowerCase())) {
							ZipFile zf = new ZipFile(tempFile);
							zf.setFileNameCharset(ZipHelper
									.getEncoding(tempFile.getAbsolutePath()));

							// 获取压缩文件中文件名，仅获取一级目录的
							for (Object h : zf.getFileHeaders()) {
								net.lingala.zip4j.model.FileHeader header = (net.lingala.zip4j.model.FileHeader) h;
								String[] rarFileName = header.getFileName()
										.split("/");
								String subFileName = rarFileName[0];
								fileNameSet.add(subFileName);
							}
						} else if ("rar".equals(extName.toLowerCase())) {// rar格式压缩包
							Archive rarFile = new Archive(tempFile);
							for (FileHeader h : rarFile.getFileHeaders()) {
								String[] rarFileName = h.getFileNameW()
										.isEmpty() ? h.getFileNameString()
										.split("\\\\") : h.getFileNameW()
										.split("\\\\");
								String subFileName = rarFileName[0];
								fileNameSet.add(subFileName);
							}
							rarFile.close();
						}
						tempFile.delete();
						if (fileNameSet.size() < 1) {
							throw new MroException("故障数据包内无文件或文件读取失败！");
						}
						StringBuffer fileNameBuff = new StringBuffer();
						for (String oneFileName : fileNameSet) {
							fileNameBuff.append(oneFileName + ",");
						}
						String nameList = fileNameBuff.substring(0,
								fileNameBuff.toString().length() - 1);
						f[5] = nameList;
					} catch (Exception e1) {
						f[5] = "";
						f[6] = "上传故障数据包成功，但是文件列表获取失败，可能是压缩包损坏或者压缩包内文件为空";
						e1.printStackTrace();
						// throw new MroException(e1.getMessage());
					} finally {
						try {
							bof.close();
							os.close();
							if (input != null) {
								input.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
							// throw new MroException(e.getMessage());
						}
					}
					/* 文件列表END */

					// 上传大数据FTP
					// byte[] by = fi.get();
					f[2] = getDirectoryName();
					f[3] = getFileExtensions(f[0]);
					f[4] = fileName;

					String dict = "其他";
					if (fileNames[2].contains("机车")) {
						dict = "机车";
					} else if (fileNames[2].contains("动车")) {
						dict = "动车";
					} else if (fileNames[2].contains("城轨")) {
						dict = "城轨";
					}
					BigDataFTPUploader uploader = new BigDataFTPUploader();
					try {
						boolean flag = uploader.upload(dict, fileName, length,
								fi.getInputStream());

						// 更新上传状态
						IJpoSet failurelibSet = MroServer.getMroServer()
								.getSysJpoSet("FAILURELIB");
						failurelibSet.setQueryWhere("FAILUREORDERNUM='"
								+ workorder.getString("ORDERNUM") + "'");
						failurelibSet.reset();
						if (failurelibSet != null && failurelibSet.count() > 0) {
							IJpo failure = failurelibSet.getJpo(0);
							if (flag) {

								failure.setValue("FAULTDATAREC", "上传成功");
								failure.setValueNull("NODATAREASON",
										GWConstant.P_NOVALIDATION);
								// 向failurelib表记录大数据下载地址
								StringBuffer buf = new StringBuffer();
								// 配置文件中的地址
								buf.append(IFUtil
										.getIfServiceInfo("bigdata.downloadurl"));
								buf.append("?");
								buf.append("orderID="
										+ workorder.getString("ORDERNUM"));
								failure.setValue("FAULTDATAURL", buf.toString());
							} else {
								failure.setValue("FAULTDATAREC", "上传失败");
								failure.setValue("NODATAREASON", "数据上传失败",
										GWConstant.P_NOVALIDATION);
							}
							failurelibSet.save();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return f;
	}

	/**
	 * 
	 * 获得唯一文件名
	 * 
	 * @param fn
	 * @return [参数说明]
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
			long timeInMills = System.currentTimeMillis();
			uniqueFileName = fileName + timeInMills + extension;
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
	 * 是否覆盖同名文件
	 * 
	 * @return [参数说明]
	 * 
	 */
	public boolean isOverWrite() {
		return true;
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

	/**
	 * 
	 * 获得文件上传列表
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 *             [参数说明]
	 * 
	 */
	public List<FileItem> getFileItems() throws MroException {
		try {
			getMroSession().getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new MroException(e.getMessage());
		}
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(dfif);
		List<FileItem> items;
		try {
			items = upload.parseRequest(getMroSession().getRequest());
		} catch (FileUploadException e) {
			throw new MroException(e.getMessage());
		}
		return items;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	/**
	 * 
	 * 根据mro系统规则设置上传目录
	 * 
	 * @param doctype
	 *            [参数说明]
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
	 * 
	 * 根据当前工单信息重命名文件
	 * 
	 * @param workorder
	 *            故障工单jpo
	 * @param oldFileName
	 *            原文件名（含扩展名）
	 * @param oldPath
	 *            原文件绝对路径
	 * @return [参数说明]fileInfo[0] 新文件名， fileInfo(1) 新绝对路径
	 * @throws MroException
	 * @author zhuhao
	 * 
	 */
	private String[] renameFile(IJpo workorder, String oldFileName,
			String oldPath) throws MroException {
		String[] fileInfo = new String[3];

		// 车型
		String models = workorder.getString("MODELS.MODELCODE");
		// 车号
		String carnum = workorder.getString("CARNUM");
		// 故障记录jpo
		IJpo failurelib = workorder.getJpoSet("FAILURELIB").getJpo(0);
		if (failurelib == null) {
			throw new MroException("", "该工单故障记录为空，请核对信息！");
		}

		Date faulttime = failurelib.getDate("FAULTTIME");
		if (faulttime == null) {
			throw new MroException("故障发生时间为空，请检查！");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 故障时间
		String fttime = sdf.format(faulttime);
		// 车厢号或AB节
		String csnum = failurelib.getString("CARSECTIONNUM");
		csnum = (csnum != null) ? csnum : failurelib
				.getString("TMSDATA.ABCFLAG");
		csnum = (csnum != null) ? csnum : "NA";
		// 产品简称
		String productnickname = failurelib.getString("PRODUCTNICKNAME");
		// 故障代码
		String failurecode = failurelib.getString("FAILURECODE");
		if (WorkorderUtil.hasIllegalFileNameChar(failurecode)) {
			throw new AppException("", "故障代码中包含非法字符，无法重命名数据包上传，请检查！");
		}
		// 故障名称
		String faultname = failurelib.getString("FAILUREDESC");
		if (WorkorderUtil.hasIllegalFileNameChar(faultname)) {
			throw new AppException("", "故障名称中包含非法字符，无法重命名数据包上传，请检查！");
		}
		// 配属用户
		String psuser = workorder.getString("ASSET.OWNERCUSTOMER.CUSTNAME");
		if (WorkorderUtil.hasIllegalFileNameChar(psuser)) {
			throw new AppException("", "配属用户中包含非法字符，无法重命名数据包上传，请检查！");
		}
		// 工单编号
		String ordernum = workorder.getString("ORDERNUM");

		if (models.isEmpty() || carnum.isEmpty() || fttime.isEmpty()
				|| ordernum.isEmpty()) {
			throw new MroException("", "参数不满足分析故障条件，请完整填写");
		}
		// 新文件名
		String newFileName = models + "_" + carnum + "_" + fttime + "_" + csnum
				+ "_" + productnickname + "_" + failurecode + "_" + faultname
				+ "_" + psuser + "_" + ordernum;

		/* 处理旧文件名 */
		String[] oldName = oldFileName.split("\\.");
		fileInfo[0] = newFileName + "." + oldName[oldName.length - 1];

		/* 处理文件路径 */
		// oldPath = oldPath.substring(0, oldPath.lastIndexOf("\\") + 1);
		// 大数据下载地址
		String url = IFUtil.getIfServiceInfo("bigdata.downloadurl");
		fileInfo[1] = url + "?orderID=" + ordernum + "&fileName=" + fileInfo[0];

		// 车型管理-车型大类,上传FTP指定目录用
		String productline = workorder.getString("MODELS.PRODUCTLINE");
		fileInfo[2] = productline;
		return fileInfo;
	}
}
