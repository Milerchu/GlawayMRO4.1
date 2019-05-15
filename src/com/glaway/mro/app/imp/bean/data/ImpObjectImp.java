package com.glaway.mro.app.imp.bean.data;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;

import com.glaway.mro.jpo.IJpo;


public interface ImpObjectImp {
	public HashMap<String, String> addJpo(Workbook workbook, IJpo relationmbo) throws SQLException;
	
	public void addLog(String ifacename,String objectname,int type,String msg);
	
	public long getDoclinksid();
	
	public void setDoclinksid(long doclinksid) ;
	
	public String getObjectname();
	
	public void setObjectname(String objectname);
	
	public String getIfacename();
	
	public void setIfacename(String ifacename);
}
