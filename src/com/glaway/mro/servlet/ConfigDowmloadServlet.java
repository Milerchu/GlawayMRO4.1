package com.glaway.mro.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Date;

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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysPropCache;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;
import com.glaway.sddq.tools.NetDiskUtil;

/**
 * 
 * 配置数据导出Servlet
 * 
 * @author ygao
 * @version [版本号, 2017-10-18]
 * @since [产品/模块版本]
 */
public class ConfigDowmloadServlet extends HttpServlet {
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

		if (session.getAttribute("assetset") != null) {

			try {
				downloadTable(req, resp);
			} catch (MroException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	 * 通过poi创建excell <功能描述>
	 * 
	 * @param data
	 * @param titles
	 * @param attributes
	 * @return
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private SXSSFWorkbook downloadTable(HttpServletRequest req,
			HttpServletResponse resp) throws IOException,
			MroException {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		SXSSFSheet sheet = wb.createSheet("配置数据");
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
		
		sheet.createFreezePane(0, 1, 1, 1);
		int num = 0;

		IJpoSet assetset = (IJpoSet) req.getSession().getAttribute("assetset");
		/*
		 * String projectnum = (String) req.getAttribute("PmNO"); IJpoSet
		 * assetset = MroServer.getMroServer().getJpoSet("asset",
		 * MroServer.getMroServer().getSystemUserServer());
		 * assetset.setUserWhere("PROJECTNUM='" + projectnum + "'");
		 * assetset.reset();
		 */
		// 3 遍历车辆信息
		for (int i = 0; i < assetset.count(); i++) {
		IJpo asset = assetset.getJpo(i);
			// 查询具体车辆的配置 写入文件（获取项目号和车号，并写入文件）
			String projectnum = asset.getString("PROJECTNUM");// 项目号
			String carno = asset.getString("CARNO");// 车号
			// 先将当前车辆信息写入excell ,然后获取所有车厢数据
		// if (isAsset(asset)) {
			// 如果是整车行 写入项目 和车号
		if (asset != null) {

				// SXSSFRow row = sheet.createRow(0);// 创建行，从0开始

				if (num == 0) {

					SXSSFRow row1 = sheet.createRow(num++);// 创建行，从0开始

					row1.createCell(0).setCellValue("项目");
					row1.createCell(1).setCellValue("车号");
					row1.createCell(2).setCellValue("车厢");
					row1.createCell(3).setCellValue("图号");
					row1.createCell(4).setCellValue("名称");
					row1.createCell(5).setCellValue("上级图号");
					row1.createCell(6).setCellValue("上级名称");
					row1.createCell(7).setCellValue("序列号");
					row1.createCell(8).setCellValue("位置号");
					row1.createCell(9).setCellValue("位置号描述");
				}

				SXSSFRow row2 = sheet.createRow(num++);
				row2.createCell(0).setCellValue(projectnum);// 项目
				row2.createCell(1).setCellValue(carno);// 车号
				row2.createCell(2).setCellValue("");
				row2.createCell(3).setCellValue("");
				row2.createCell(4).setCellValue("");
				row2.createCell(5).setCellValue("");
				row2.createCell(6).setCellValue("");
				row2.createCell(7).setCellValue("");
				row2.createCell(8).setCellValue("");
				row2.createCell(9).setCellValue("");

		}

		// }
			// 获取车厢并遍历车厢
		IJpoSet CarAssetset = asset.getJpoSet("CHILDREN");
			// 遍历车厢数据
		for (int j = 0; j < CarAssetset.count(); j++) {
				// 车厢
			IJpo carAsset = CarAssetset.getJpo(j);
			String carassetnum = carAsset.getString("assetnum");
				String carriagenum = carAsset.getString("CARRIAGENUM");// 车厢号
			// if (isCar(carAsset)) {
				// 如果是 车厢 写入车厢 其他忽略
			if (carAsset != null) {
					SXSSFRow row2 = sheet.createRow(num++);// 创建行，从0开始
					SXSSFCell cell2 = row2.createCell(0);
					cell2.setCellValue("");// 项目
					row2.createCell(1).setCellValue("");// 车号
					row2.createCell(2).setCellValue(carriagenum);// 车厢号
				row2.createCell(3).setCellValue("");
				row2.createCell(4).setCellValue("");
				row2.createCell(5).setCellValue("");
				row2.createCell(6).setCellValue("");
				row2.createCell(7).setCellValue("");
				row2.createCell(8).setCellValue("");
				row2.createCell(9).setCellValue("");
			}
			// }
				// 获取车厢下的所有配置
			IJpoSet lineAssetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			lineAssetset
					.setUserWhere("assetnum in (SELECT  a.assetnum FROM ASSET a  start with a.assetnum='"
							+ carassetnum
							+ "' connect by a.parent = prior a.assetnum) and assetnum not in ('"
							+ carassetnum + "')");
			// IJpoSet lineAssetset = carAsset.getJpoSet("THISANDCHILDREN");
				// 遍历车厢下的所有配置
			for (int n = 0; n < lineAssetset.count(); n++) {
					// 车厢下配置jpo
				IJpo assetLine = lineAssetset.getJpo(n);
					String itemnum = assetLine.getString("ITEMNUM");// 物料编码
					String description = assetLine
							.getString("ITEM.DESCRIPTION");// 物料描述
					String parent = assetLine.getString("PARENT");// 父级
					// 父级描述
					String sqn = assetLine.getString("SQN");// 序列号
					String loc = assetLine.getString("LOC");// 位置号
					String locdesc = assetLine.getString("LOCDESC");// 位置号描述
				// if (isSystem(assetLine)) {
					// 如果是配置 节点 写入配置数据 忽略 项目 车号 车厢
				if (assetLine != null) {
						SXSSFRow row3 = sheet.createRow(num++);// 创建行，从0开始
						SXSSFCell cell3 = row3.createCell(0);
						cell3.setCellValue("");// 项目
						row3.createCell(1).setCellValue("");// 车号
						row3.createCell(2).setCellValue("");// 车厢号
						row3.createCell(3).setCellValue(itemnum);// 物料编码
						row3.createCell(4).setCellValue(description);// 描述
						row3.createCell(5).setCellValue(parent);// 父级
						row3.createCell(6).setCellValue("");// 父级描述
						row3.createCell(7).setCellValue(sqn);// 序列号
						row3.createCell(8).setCellValue(loc);// 位置号
						row3.createCell(9).setCellValue(locdesc);// 位置号描述
				}
				// }
			}
			}
		}

		/*
		 * IJpoSet bomoutlineset = (IJpoSet) req.getSession().getAttribute(
		 * "bomoutlineset");
		 */
		String PmNO = (String) req.getSession().getAttribute("PmNO");
		IJpoSet bomoutlineset = MroServer.getMroServer().getJpoSet(
				"bomoutline", MroServer.getMroServer().getSystemUserServer());
		bomoutlineset.setUserWhere("projectnum='" + PmNO + "'");
		bomoutlineset.reset();
		String person = (String) req.getSession().getAttribute("person");
		Date actiondate = MroServer.getMroServer().getDate();

		IJpo boLine = bomoutlineset.addJpo();
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		boLine.setValue("PROJECTNUM", PmNO); // 项目、人员、时间、状态...
		boLine.setValue("ACTIONPERSON", person);
		boLine.setValue("ACTIONDATE", actiondate);
		boLine.setValue("STATUS", "已完成");
		boLine.setValue("ORGID", "CRRC");
		boLine.setValue("SITEID", "ELEC");
		bomoutlineset.save();

		// File file = new File("CONFIG.xlsx");
		String fileName = new String("CONFIG.xlsx".getBytes("UTF-8"),
				"iso-8859-1");

		resp.reset();
		resp.addHeader("Content-Disposition", "attachment; fileName="
				+ new String(fileName.getBytes("gb2312")));
		resp.setContentType("application/octet-stream; charset=UTF-8");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-control", "no-cache");
		OutputStream stream = resp.getOutputStream();
		BufferedOutputStream bufferedoutput = new BufferedOutputStream(stream);
		wb.write(bufferedoutput);
		// bufferedoutput.flush();
		bufferedoutput.close();
		return wb;
	}
}
