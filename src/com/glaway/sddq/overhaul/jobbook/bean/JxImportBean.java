package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaway.mro.app.system.doclinks.bean.AddDoclinkBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.overhaul.jobbook.imp.ImpItemnum;

public class JxImportBean extends AddDoclinkBean {
	public int uploadfile() throws MroException, IOException {
        try{
            
            HttpServletRequest request = getMroSession().getRequest();
            getMroSession().setIframeResponse(true);
    		request.setCharacterEncoding("UTF-8");
    		DiskFileItemFactory dfif = new DiskFileItemFactory();
    		ServletFileUpload upload = new ServletFileUpload(dfif);
    		List<FileItem> items = upload.parseRequest(request);

    		Iterator<FileItem> its = items.iterator();

			while (its.hasNext()) {
				FileItem fi = its.next();
				String fileName = fi.getName();
				// 判断上传文件名是否为空
				if (StringUtil.isStrEmpty(fileName)) {
					throw new AppException("jspmessages", "nouploadfile");
				}
		

				if (fi.getSize() > 104857600) { // 报表文件大于100M限制
					throw new AppException("jspmessages", "tooBigfile");
				}
				File tmpFile = new File("./tmpFile");
				fi.write(tmpFile);
				impData(tmpFile,fileName);
				tmpFile.delete();				
			}
			dialogclose();
        }catch(Exception e){
        	mroSession.getRequest().getSession()
			.setAttribute("isSuccess", "uploadfileSuccess");
        	dialogclose();
        	throw new MroException(e.getMessage());
        }
        mroSession.getRequest().getSession()
		.setAttribute("isSuccess", "uploadfileSuccess");
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
		    
		    public void impData(File tmpFile,String filename)
		        throws IOException, MroException, SQLException, InstantiationException, IllegalAccessException,
		        ClassNotFoundException
		    {
		        Workbook workbook = null;
		        FileInputStream fis = null;
		        try
		        {
		        	fis = new FileInputStream(tmpFile);
		        	
		            if (filename.toLowerCase().endsWith(".xlsx"))
		            {
		                workbook = new XSSFWorkbook(fis);
		            }
		            else
		            {
		                workbook = new HSSFWorkbook(fis);
		            }
			        
			        String impclass = "com.glaway.sddq.overhaul.jobbook.imp.ImpItemnum";
			        
			        Sheet sheet = workbook.getSheetAt(0);
			        if (sheet == null)
			        {
			            return;
			        }
			        
			        ImpItemnum impjpo = new ImpItemnum();
			        if (impclass != null)
			        {
			            impjpo = (ImpItemnum)Class.forName(impclass).newInstance();
			        }
			        impjpo.setIfacename("OVERHAULMATERIALIMP");
			        impjpo.setObjectname("OVERHAULMATERIAL");
			        IJpoSet imprltset = MroServer.getMroServer().getSysJpoSet("imprelation");
			        imprltset.setUserWhere("IFACENAME = 'OVERHAULMATERIALIMP' AND OBJECTNAME='OVERHAULMATERIAL'");
			        imprltset.reset();
			        IJpo imprltjpo = imprltset.getJpo(0);
			        
			        HashMap<String, String> returninfo = impjpo.addJpo(workbook, imprltjpo);
			        String successinfo = returninfo.get("successMessage");
			        String errorInfo = returninfo.get("errorMessage");
			        if (!"".equals(successinfo))
			        {
			            throw new MroException("", successinfo);
			        }
			        else if (!"".equals(errorInfo))
			        {
			            throw new MroException(errorInfo);
			        }
		} catch (IOException e){
		        	e.printStackTrace();
		        }finally{
		        	fis.close();
		        }
		    }
}
