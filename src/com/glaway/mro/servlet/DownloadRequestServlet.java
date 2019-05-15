package com.glaway.mro.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysPropCache;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;
import com.glaway.sddq.tools.NetDiskUtil;

public class DownloadRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 8086176079319551232L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("isDownLoadSuccess", "");
		if (session.getAttribute("OPENURLSTR") != null) {
			String urlname = (String) session.getAttribute("OPENURLSTR");
			String encryptName = (String) session.getAttribute("encryptName");
			session.removeAttribute("OPENURLSTR");
			session.removeAttribute("encryptName");
			downloadFile(req, resp, urlname, encryptName);
		} else {
			SXSSFWorkbook workbook = null;
			try {
				String filename = (String) session.getAttribute("FILENAME");
				String[] titles = (String[]) session.getAttribute("TITLES");
				String[] attributes = (String[]) session
						.getAttribute("ATTRIBUTES");
				@SuppressWarnings("unchecked")
				List<List<String>> data = (List<List<String>>) session
						.getAttribute("DATA");
				// HandleRequestUtil hu = new HandleRequestUtil();
				// if(hu.checkIsIntranet(req)){
				// session.setAttribute("isDownLoadSuccess", "DownLoadSuccess");
				// throw new AppException("system", "cannotdownload");
				// }else{
				workbook = downloadTable(data, titles, attributes);

				// 输出流下载
				ServletOutputStream out = resp.getOutputStream();
				resp.reset();
				resp.addHeader("Content-Disposition", "attachment;filename="
						+ filename + ".xlsx");
				resp.setContentType("application/octet-stream");
				resp.flushBuffer();
				workbook.write(out);
				// }

			} catch (Exception e) {
				FixedLoggers.EXCEPTIONLOGGER.error(e);
			} finally {
				if (workbook != null) {
					workbook.close();
				}
				session.removeAttribute("downloading");

				session.removeAttribute("APPNAME");
				session.removeAttribute("TITLES");
				session.removeAttribute("ATTRIBUTES");
				session.removeAttribute("DATA");

			}
		}
		session.setAttribute("isDownLoadSuccess", "DownLoadSuccess");
	}

	private void downloadFile(HttpServletRequest req, HttpServletResponse resp,
			String url, String uuid) throws RemoteException,
			MalformedURLException, SmbException, UnknownHostException,
			FileNotFoundException, IOException, UnsupportedEncodingException {

		String urlname = url;
		BufferedInputStream fis = null;
		InputStream in = null;
		int length = 0;
		boolean fileExist = false;
		urlname = urlname.replaceAll("\\\\", "\\/");
		String path = urlname;
		String filename = path.substring(path.lastIndexOf("/") + 1);
		if (!StringUtil.isStrEmpty(uuid)) {
			int lastIndexOf = path.lastIndexOf("/");
			path = path.substring(0, lastIndexOf + 1) + uuid;
		}
		UploadUtil uf = new UploadUtil();
		SysPropCache spc = MroServer.getMroServer().getSysPropCache();
		String remote = spc.getPropValue("mro.doc.remote");
		String encrypt = spc.getPropValue("mro.doc.encrypt");
		String netdiskFlag = spc.getPropValue("mro.doc.netdisk");
		boolean isRemote = Boolean.valueOf(remote);
		boolean isEncrypt = Boolean.valueOf(encrypt);

		if (isRemote) {
			String username = spc.getPropValue("mro.doc.remote.username");
			String password = spc.getPropValue("mro.doc.remote.password");
			String remoteIp = spc.getPropValue("mro.doc.remote.domain");
			NtlmPasswordAuthentication mpa = new NtlmPasswordAuthentication(
					remoteIp, username, password);
			SmbFile remoteFile = new SmbFile("smb://"
					+ spc.getPropValue("mro.doc.remote.ipaddress") + "/"
					+ path.replace(":", ""), mpa);
			if (remoteFile.exists()) {
				in = new SmbFileInputStream(remoteFile);
				fis = new BufferedInputStream(in);
				// length = fis.available();
				length = remoteFile.getContentLength();
				fileExist = true;
			}
		} else {
			if ("1".equals(netdiskFlag)) {// 网盘模式

				String[] paths = path.split("/");
				// 网盘文件路径
				String netdiskPath = paths[paths.length - 3] + "/"
						+ paths[paths.length - 2] + "/" + filename;
				String rootPath = MroServer.getMroServer().getSysPropCache()
						.getPropValue("mro.doc.rootfolder");
				// 下载文件夹不存在则创建
				String downPath = rootPath + "DOWNLOADS";
				File dFile = new File(downPath);
				if (!dFile.exists() && !dFile.isDirectory()) {
					dFile.mkdir();
				}
				String savePath = rootPath.replaceAll("\\\\", "\\/")
						+ "DOWNLOADS/" + filename;
				try {
					String downloadPath = MroServer.getMroServer()
							.getSysPropCache()
							.getPropValue("mro.doc.rootfolder")
							+ "DOWNLOADS\\" + filename;
					// 调用网盘下载
					NetDiskUtil.Osdownload(netdiskPath, downloadPath);
					path = savePath;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			File file = new File(path);
			if (file.exists()) {
				in = new FileInputStream(file);
				fis = new BufferedInputStream(in);
				length = fis.available();
				fileExist = true;
			}
		}
		if (fileExist) {
			if (req.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				filename = URLEncoder.encode(filename, "UTF-8");
				filename = filename.replaceAll("\\+", "%20");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			}
			byte[] buffer = new byte[length];
			fis.read(buffer);
			if (isEncrypt) {
				buffer = uf.decryptStream(buffer);
				length = buffer.length;
			}
			resp.reset();
			ServletOutputStream out = resp.getOutputStream();
			resp.addHeader("Content-Disposition", "attachment;filename=\""
					+ filename + "\"");
			resp.addHeader("Content-Length", String.valueOf(length));
			resp.setContentType("application/x-msdownload");
			resp.flushBuffer();
			out.write(buffer);

			fis.close();
			in.close();
			out.close();
		} else {
			if (fis != null) {
				fis.close();
			}
			resp.reset();
			resp.setContentType("text/html");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write("未找到文件“" + filename + "”");
		}

		// 网盘管理开启时需删除下载的临时文件
		if ("1".equals(netdiskFlag)) {
			// 删除临时文件夹中的文件
			String deletePath = path.replaceAll("\\/", "\\\\");
			File deletefile = new File(deletePath);
			if (deletefile.exists() && deletefile.isFile()) {
				// 删除文件
				deletefile.delete();
			}
		}

	}

	/**
	 * 
	 * 列表上的下载功能
	 * 
	 * @deprecated 使用新的方法
	 * 
	 * @param req
	 *            请求
	 * @param resp
	 *            response
	 * @param jpoSet
	 *            要导出的数据集合
	 * @param attributes
	 *            要导出的属性数组
	 * @param filename
	 *            下载的文件名，不包括扩展名
	 * @throws IOException
	 *             [参数说明]
	 * @throws MroException
	 * 
	 */
	@Deprecated
	private void downloadTable(HttpServletRequest req,
			HttpServletResponse resp, IJpoSet jpoSet, String[] titles,
			String[] attributes, String filename) throws IOException,
			MroException {
		if (jpoSet != null) {
			SXSSFWorkbook wb2 = new SXSSFWorkbook();
			SXSSFSheet sheet2 = wb2.createSheet("new sheet");
			Font font = wb2.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 生成一个单元格样式
			CellStyle cs = wb2.createCellStyle();
			cs.setFont(font);
			// 设置边框
			cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
			Row row = sheet2.createRow(0);
			Cell cell = null;
			int colnum = -1;
			for (int j = 0; j < attributes.length; j++) {
				if (StringUtil.isStrNotEmpty(attributes[j])) {
					colnum++;
					cell = row.createCell(colnum);
					cell.setCellStyle(cs);
					cell.setCellValue(titles[j]);
				}
			}
			// 生成一个单元格样式
			CellStyle cs1 = wb2.createCellStyle();
			// 设置边框
			cs1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cs1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cs1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cs1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			for (int i = 0; i < jpoSet.count(); i++) {
				colnum = -1;
				IJpo jpo = jpoSet.getJpo(i);

				if (jpo != null) {
					row = sheet2.createRow(i + 1);
					for (int j = 0; j < attributes.length; j++) {
						if (StringUtil.isStrNotEmpty(attributes[j])) {
							colnum++;
							cell = row.createCell(colnum);
							cell.setCellStyle(cs1);
							String val = jpo.getLocalString(attributes[j]);
							if (val == null) {
								val = "";
							}
							cell.setCellValue(val);
						}
					}
				}
			}
			// 输出流下载
			ServletOutputStream out = resp.getOutputStream();
			try {
				resp.reset();
				resp.addHeader("Content-Disposition", "attachment;filename="
						+ filename + ".xlsx");
				resp.setContentType("application/octet-stream");
				resp.flushBuffer();
				wb2.write(out);
			} catch (Exception e) {
				FixedLoggers.EXCEPTIONLOGGER.error(e);
			} finally {
				req.getSession().removeAttribute("downloading");
				try {
					wb2.dispose();
					wb2.close();
				} catch (IOException e) {
					FixedLoggers.EXCEPTIONLOGGER.error(e);
				}
				if (jpoSet != null) {
					jpoSet.destroy();
				}
			}
		}
	}

	private SXSSFWorkbook downloadTable(List<List<String>> data,
			String[] titles, String[] attributes) throws IOException,
			MroException {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		SXSSFSheet sheet2 = wb.createSheet("new sheet");
		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 生成一个单元格样式
		CellStyle cs = wb.createCellStyle();
		cs.setFont(font);
		// 设置边框
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		Row row = sheet2.createRow(0);
		Cell cell = null;
		int colnum = -1;
		for (int j = 0; j < attributes.length; j++) {
			if (StringUtil.isStrNotEmpty(attributes[j])) {
				colnum++;
				cell = row.createCell(colnum);
				cell.setCellStyle(cs);
				cell.setCellValue(titles[j]);
			}
		}
		// 生成一个单元格样式
		CellStyle cs1 = wb.createCellStyle();
		// 设置边框
		cs1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		for (int i = 0; i < data.size(); i++) {
			colnum = 0;
			List<String> rowData = data.get(i);
			row = sheet2.createRow(i + 1);
			for (int j = 0; j < attributes.length; j++) {
				if (StringUtil.isStrNotEmpty(attributes[j])) {
					cell = row.createCell(colnum++);
					cell.setCellStyle(cs1);
					cell.setCellValue(rowData.get(j));
				}
			}
		}
		return wb;
	}
}
