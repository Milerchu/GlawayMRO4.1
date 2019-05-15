package com.glaway.sddq.overhaul.plan.bean;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 月度计划的检修车辆列表DataBean
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-23]
 * @since  [产品/模块版本]
 */
public class PlanCarDataBean extends DataBean
{
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        if (this.getJpo().isNew())
        {
            String carno = this.getJpo().getString("WAGONNUM");
            String cmodel = this.getJpo().getString("CMODEL");
            String repairprocess = this.getJpo().getString("repairprocess");
            String ancestor = this.getJpo().getString("ancestor");
            String plannum = this.getJpo().getString("PLANNUM");
            IJpoSet cmodelJpoSet = this.getAppBean().getJpo().getJpoSet("MONTHYEARPLAN");
            cmodelJpoSet.setUserWhere("cmodel='"+cmodel+"' and repairprocess='"+repairprocess+"'");
            cmodelJpoSet.reset();
            String itemnumStr = "";
            if (!cmodelJpoSet.isEmpty())
            {
            	IJpo jpo = cmodelJpoSet.getJpo(0);
            	IJpoSet repairScopeJpoSet = jpo.getJpoSet("REPAIRSCOPE"); 
                for (int index = 0; index < repairScopeJpoSet.count(); index++)
                {
                    itemnumStr = itemnumStr + "'" + repairScopeJpoSet.getJpo(index).getString("ITEMNUM") + "',";
                }
            }
            if (StringUtils.isNotEmpty(itemnumStr) && itemnumStr.endsWith(","))
            {
                itemnumStr = itemnumStr.substring(0, itemnumStr.length() - 1);
                
                IJpoSet assetJpoSet =
                    MroServer.getMroServer().getSysJpoSet("ASSET",
                        "CMODEL = '" + cmodel + "' and carno='" + carno
                            + "' and assetlevel != 'ASSET' and type = '2' and itemnum in(" + itemnumStr + ")");
                if (!assetJpoSet.isEmpty())
                {
                    IJpoSet assetBjJposet = this.getJpo().getJpoSet("SINGLEOVERHAUL");
                    for (int index = 0; index < assetJpoSet.count(); index++)
                    {
                        IJpo jpo = assetBjJposet.addJpo(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("SQN",
                            assetJpoSet.getJpo(index).getString("SQN"),
                            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("ASSETNUM",
                            assetJpoSet.getJpo(index).getString("ASSETNUM"),
                            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("CMODEL", cmodel, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("WAGONNUM", carno, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("PLANNUM", plannum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("ITEMNUM", assetJpoSet.getJpo(index).getString("ITEMNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("ancestor", ancestor, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        jpo.setValue("repairprocess", repairprocess, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                    assetBjJposet.save();
                }
            }
            this.getAppBean().SAVE();
        }
    }
    
	/**
	 * 
	 * 导出
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void exportDemo() throws MroException, IOException {

//		HttpServletRequest request = getMroSession().getRequest();
//		String path = request.getScheme() + "://" + request.getServerName()
//				+ ":" + request.getServerPort() + request.getContextPath()
//				+ "/";
//		
//		if (!path.endsWith("/")) {
//			path += "/";
//		}
//		String filenamepath = path+"";
//		HttpSession session = request.getSession();
//		session.setAttribute("downloadDemo", filenamepath);
//		path += "downloadattachments";
//		openurl(path);
//		HttpServletResponse resp = this.getMroSession().getResponse();
//		HttpServletRequest req = this.getMroSession().getRequest();
//		HttpSession session = this.getMroSession().getHttpSession();
//		String path = req.getSession().getServletContext().getRealPath("/");  
//		// String link = "http://localhost:58000/gwmro/page/filedemo/abc.xlsx";
//		// if(link != null){
//		// mroSession.returnReponse("", "openUrl", "<url>" + link + "</url>");
//		// }
//
////		// 输出流下载
//		String path1 = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"//"+req.getContextPath()+"/";
//		File file = new File(path1+"/page/filedemo/abc.xlsx");
////		InputStream in = new FileInputStream(file);
//		ServletOutputStream out = resp.getOutputStream();
////		SXSSFWorkbook workbook = new SXSSFWorkbook();
////		
//		SXSSFWorkbook wb = new SXSSFWorkbook();
//		SXSSFSheet sheet2 = wb.createSheet("new sheet");
//		Font font = wb.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 生成一个单元格样式
//		CellStyle cs = wb.createCellStyle();
//		cs.setFont(font);
//		// 设置边框
//		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		Row row = sheet2.createRow(0);
//		Cell cell = null;
//		int colnum = -1;
//		// 生成一个单元格样式
//		CellStyle cs1 = wb.createCellStyle();
//		// 设置边框
//		cs1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cs1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cs1.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cs1.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		
//		// 输出流下载
//		resp.reset();
//		resp.addHeader("Content-Disposition", "attachment;filename="
//				+"abc.xlsx");
//		resp.setContentType("application/octet-stream");
//		resp.flushBuffer();
//		wb.write(out);
//		
//		if (wb != null) {
//			wb.close();
//		}
//		session.removeAttribute("downloading");
//
//		session.removeAttribute("APPNAME");
//		session.removeAttribute("TITLES");
//		session.removeAttribute("ATTRIBUTES");
//		session.removeAttribute("DATA");
//		session.setAttribute("isDownLoadSuccess", "DownLoadSuccess");
//		try {
//			resp.reset();
//			resp.addHeader("Content-Disposition", "attachment;filename="
//					+ file.getAbsolutePath());
//			resp.setContentType("application/octet-stream");
//			resp.flushBuffer();
//			byte[] b = new byte[100];
//			int len;
//			try {
//				while ((len = in.read(b)) > 0) {
//					out.write(b, 0, len);
//				}
//				in.close();
//			} catch (Exception e) {
//				FixedLoggers.EXCEPTIONLOGGER.error(e);
//			}
//		} finally {
//			in.close();
//		}
		
//		BufferedInputStream fis = null;
//		InputStream in = null;
//		File file = new File(path+"/page/filedemo/abc.xlsx");
//		int length = 0;
//		boolean fileExist = false;
//		if (file.exists()) {
//			in = new FileInputStream(file);
//			fis = new BufferedInputStream(in);
//			length = fis.available();
//			fileExist = true;
//		}
//		byte[] buffer = new byte[100];
//		UploadUtil uf = new UploadUtil();
//		fis.read(buffer);
//			length = 100;
//		resp.reset();
//		ServletOutputStream out = resp.getOutputStream();
//		resp.addHeader("Content-Disposition", "attachment;filename=\""
//				+ file.getAbsolutePath() + "\"");
//		resp.addHeader("Content-Length", String.valueOf(length));
//		resp.setContentType("application/x-msdownload");
//		resp.flushBuffer();
//		out.write(buffer);
//
//		fis.close();
//		in.close();
//		out.close();
		
	}
    
    
}
