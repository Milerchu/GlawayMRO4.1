package com.glaway.mro.app.imp.bean.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;


public class ImpObjectIface implements ImpObjectImp {
	String objectname;
	String ifacename;
	long doclinksid;

	@Override
	public HashMap<String, String> addJpo(Workbook workbook, IJpo relationmbo) throws SQLException {
		
		HashMap<String, String> returnInfo = new HashMap<String, String>();
        returnInfo.put("errorMessage", "");
        returnInfo.put("successMessage", "");
        String errormsg = null;
        String successmsg = "";
        Connection con = null;
        
        con = DBManager.getDBManager().getConnection();
		
        try
        {
            long st = System.currentTimeMillis();
            int sheetnum = workbook.getNumberOfSheets();
            
            Statement stm = con.createStatement();
            int successcount=0;
            for (int index = 0; index <= sheetnum-1; index++)
            {
                Sheet sheet = workbook.getSheetAt(index);
                int rowcount = sheet.getLastRowNum();
                
                IJpoSet attrmboset = null;
                
                attrmboset = relationmbo.getJpoSet("IMPATTRIBUTE");
                //	    attrmboset.setWhere("ISEXP=1");
                attrmboset.setOrderBy("to_number(EXCELCOLNUM)");
                attrmboset.reset();
                
                HashMap<String, Object> excelAttr = new HashMap<String, Object>();
                if(attrmboset.count()<=0){
                	throw new AppException("imp", "attrerror");
                }
                for (int j = 0; j < attrmboset.count(); j++)
                {
                    IJpo mr1 = attrmboset.getJpo(j);
                    if (mr1.isNull("EXCELCOLNUM"))
                    {
                        throw new MroException("system", "colnumnull");
                    }
                    excelAttr.put(mr1.getString("ATTRIBUTENAME"), Integer.valueOf(Integer.parseInt(mr1.getString("EXCELCOLNUM"))));
                }
                
                HashMap<String, String> fieldAttrMap = this.getInsertAttrMap(relationmbo.getString("objectname"),relationmbo.getString("ifacename"));
//                
                List<String> list = new ArrayList<String>();
                
                for (int i = 1; i <= rowcount; i++)
                {
                    Row row = sheet.getRow(i);
                    try
                    {
                        if (row == null)
                        {
                            errormsg = "数据行" + i + "为空";
                            break;
                        }
                        Cell cell = row.getCell(0);
                        if ((cell != null) && (!cell.getStringCellValue().trim().equals("")))
                        {
                            HashMap acsvalues = new HashMap();
                            acsvalues.put(relationmbo.getString("objectname")+"ID", relationmbo.getString("objectname")+"SEQ.NEXTVAL");
                            
                            IJpoSet tablecofig = MroServer.getMroServer().getSysJpoSet("SYS_TABLECFG");
                            tablecofig.setQueryWhere("objectname = '"+relationmbo.getString("objectname")+"'");
                            tablecofig.reset();
                            if("地点".equals(tablecofig.getJpo().getString("SITEORGTYPE"))){
                            	acsvalues.put("ORGID", "CETC");
                                
                                acsvalues.put("SITEID", "TF");
                            }
                            
                            Iterator<Map.Entry<String, Object>> it = excelAttr.entrySet().iterator();
                            while (it.hasNext())
                            {
                                Map.Entry<String, Object> entry = it.next();
                                String key = entry.getKey();
                                String maxtype = (String)fieldAttrMap.get(key);
                                cell = row.getCell(((Integer)entry.getValue()).intValue());
                                if (cell != null)
                                {
                                	if(!StringUtil.isNull(maxtype)){
                                		if ((maxtype.equals("YORN")) || (maxtype.equals("ALN")) || (maxtype.equals("GL")) || (maxtype.equals("UPPER"))
    	                                        || (maxtype.equals("LOWER")))
    	                                    {
    	                                        cell.setCellType(Cell.CELL_TYPE_STRING);
    	                                        acsvalues.put(key.toUpperCase(), cell.getStringCellValue());
    	                                    }
    	                                    else if ((maxtype.equals("BIGINT")) || (maxtype.equals("DECIMAL"))
    	                                        || (maxtype.equals("FLOAT")) || (maxtype.equals("INTEGER")) || (maxtype.equals("SMALLINT"))
    	                                        || (maxtype.equals("AMOUNT")) || (maxtype.equals("DURATION")))
    	                                    {
    	                                        acsvalues.put(key.toUpperCase(), cell.getNumericCellValue());
    	                                    }
    	                                    else if ((maxtype.equals("TIME")) || (maxtype.equals("DATE")) || (maxtype.equals("DATETIME")))
    	                                    {
    	                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	                                        cell.setCellType(Cell.CELL_TYPE_STRING);
    	                                        if (cell.getStringCellValue() != null)
    	                                        {
    	                                            String vas = cell.getStringCellValue();
    	                                            acsvalues.put(key.toUpperCase(), vas);
    	                                        }
    	                                    }
                                	}else{
                                        addLog(this.ifacename, this.objectname, -1, "'"+key+"'字段不存在");
                                        returnInfo.put("errorMessage", "'"+key+"'字段不存在");
                                        return returnInfo;
                                	}
                                }
                                
                            }
                            
                            String insertAssetJz = getInsertSql(fieldAttrMap, acsvalues, relationmbo.getString("objectname"));
                            list.add(insertAssetJz);
                        }
                        
                    }
                    catch (Exception e)
                    {
                        FixedLoggers.EXCEPTIONLOGGER.error(e);
                        addLog(this.ifacename, this.objectname, -1, e.getMessage());
                        returnInfo.put("errorMessage", e.getMessage());
                        return returnInfo;
                    }
                }
                String[] sqlArray = new String[list.size()];
                sqlArray = list.toArray(sqlArray);
                try
                {
                    
                    DBManager.getDBManager().executeBatchNoCommit(con, sqlArray);
                }
                catch (Exception e)
                {
                    FixedLoggers.EXCEPTIONLOGGER.error(e);
//                    addLog(this.ifacename, this.objectname, -1, e.getMessage());
//                    returnInfo.put("errorMessage", e.getMessage());
                    throw new MroException("", e.getMessage());
                }
                successcount=successcount+list.size();
            }
            
            con.commit();
            
            if (errormsg == null)
            {
                if (successmsg.startsWith(","))
                {
                    successmsg = successmsg.substring(1);
                }
                
                addLog(this.ifacename, this.objectname, 1, "导入文件成功,成功导入" + this.objectname + "表数据"+successcount+"条。");
                
                returnInfo.put("successMessage", "数据导入成功。");
            }
            else
            {
                addLog(this.ifacename, this.objectname, -1, errormsg);
            }
        }
        catch (SQLException e)
        {
            con.rollback();
            errormsg = e.getMessage();
            FixedLoggers.EXCEPTIONLOGGER.error(e);
            addLog(this.ifacename, this.objectname, -1, errormsg);
            returnInfo.put("errorMessage", errormsg);
        }
        catch (Exception e2)
        {
            FixedLoggers.EXCEPTIONLOGGER.error(e2);
            errormsg = e2.getMessage();
            addLog(this.ifacename, this.objectname, -1, errormsg);
            returnInfo.put("errorMessage", errormsg);
        }
        finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    FixedLoggers.EXCEPTIONLOGGER.error(e);
                }
            }
            
        }
        
        return returnInfo;
	}



	public String getErrorMsg(int i, Exception e) {
		String errormsg = null;
		if (e.getMessage() != null) {
			errormsg = "导入文件的第" + i + "行时出错，报错信息：" + e.getMessage();
		} else {
			StackTraceElement[] trace = e.getStackTrace();
			String error = "";
			for (int j = 0; j < (trace.length > 5 ? 5 : trace.length); j++) {
				error = error + "\n \tat " + trace[j];
			}
			errormsg = "导入文件的第" + i + "行时出错，报错信息：" + e + error;
		}
		return errormsg;
	}

	
	public void addLog(String ifacename, String objectname, int type, String msg)
    {
        IJpoSet jposet;
        try
        {
            jposet = MroServer.getMroServer().getJpoSet("IMPLOG", MroServer.getMroServer().getSystemUserServer());
            IJpo mr = jposet.addJpo();
            mr.setValue("IFACENAME", ifacename);
            mr.setValue("OBJECTNAME", objectname);
            mr.setValue("TYPE", type);
            if (msg != null && !msg.equals(""))
            {
                //增加长度校验--begin
                if (msg.length() > 1000)
                {
                    msg = msg.substring(0, 1000) + "...";
                }
                //增加长度校验--end
                mr.setValue("DESCRIPTION_LONGDESCRIPTION", msg);
            }
            mr.setValue("LOGTIME", MroServer.getMroServer().getDate());
            mr.setValue("DOCLINKSID", getDoclinksid());
            mr.setValue("urlname", getUrlName(getDoclinksid()));
            jposet.save();
        }
        catch (MroException e)
        {
            FixedLoggers.EXCEPTIONLOGGER.error(e);
        }
    }

	@Override
	public long getDoclinksid() {
		return doclinksid;
	}
	
	@Override
	public void setDoclinksid(long doclinksid) {
		this.doclinksid = doclinksid;
	}

	public String getAttributeName(String attributeName) {
		int index = attributeName.indexOf('.');
		if (index != -1)
			return attributeName.substring(index + 1);
		else
			return attributeName;
	}

	@Override
	public String getIfacename() {
		return ifacename;
	}

	@Override
	public String getObjectname() {
		return objectname;
	}

	@Override
	public void setIfacename(String ifacename) {
		this.ifacename = ifacename;
	}

	@Override
	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	
	
//	public HashMap<String, String> getInsertAttrMap(String tablename) throws MroException
//    {
//		HashMap attrMap = new HashMap();
//		
//		IJpoSet filedconfig = MroServer.getMroServer().getSysJpoSet("SYS_FIELDCFG");
//		filedconfig.setQueryWhere("objectname = '"+tablename+"' and ispersistent=1");
//		filedconfig.reset();
//		for(int i=0;i<filedconfig.count();i++){
//			IJpo fieldcfg = filedconfig.getJpo(i);
//			attrMap.put(fieldcfg.getString("fieldname"), fieldcfg.getString("fieldtype"));
//		}
//        return attrMap;
//    }
	
	public HashMap<String, String> getInsertAttrMap(String tablename,String ifacename) throws MroException
    {
        HashMap attrMap = new HashMap();
        
        IJpoSet impattributeset = MroServer.getMroServer().getJpoSet("impattribute", MroServer.getMroServer().getSystemUserServer());
        impattributeset.setQueryWhere("OBJECTNAME = '"+tablename+"' and ifacename = '"+ifacename+"'");
        impattributeset.reset();
        
        IJpoSet fieldjposet = MroServer.getMroServer().getJpoSet("Sys_Fieldcfg", MroServer.getMroServer().getSystemUserServer());
        
        for(int i=0;i<impattributeset.count();i++){
        	IJpo impattributesetjpo = impattributeset.getJpo(i);
        	fieldjposet.setQueryWhere("OBJECTNAME = '"+tablename+"' AND fieldname = '"+impattributesetjpo.getString("ATTRIBUTENAME")+"'");
        	fieldjposet.reset();
        	attrMap.put(impattributesetjpo.getString("ATTRIBUTENAME"),fieldjposet.getJpo().getString("FIELDTYPE"));
        }
//        attrMap.put("LANGCODE", "UPPER");
//        attrMap.put("SITEID", "UPPER");
//        attrMap.put("ORGID", "UPPER");
//        
//        attrMap.put("ASSETID", "BIGINT");
//        attrMap.put("ASSETNUM", "UPPER");
//        attrMap.put("DESCRIPTION", "ALN");
//        attrMap.put("DRAWNO", "ALN");
//        attrMap.put("PARENT", "UPPER");
//        attrMap.put("ASSETLEVEL", "UPPER");
//        attrMap.put("MODEL", "ALN");
//        attrMap.put("ITEMNUM", "UPPER");
//        attrMap.put("CUSTNUM", "UPPER");
//        attrMap.put("LOCSITE", "ALN");
//        attrMap.put("OUTDATE", "DATE");
//        attrMap.put("WARRANTYEXPDATE", "DATE");
//        attrMap.put("QAPERIOD", "UPPER");
//        attrMap.put("STATUS", "ALN");
//        attrMap.put("ANCESTOR", "UPPER");
//        attrMap.put("SECRETLEVEL", "INTEGER");
//        attrMap.put("SECRETLEVELDES", "UPPER");
//        attrMap.put("CONTRANUM", "UPPER");
        return attrMap;
    }
	
	
	public String getInsertSql(HashMap<String, String> attrMap, HashMap<String, String> hm, String tbname)
    {
        String insertsql = "insert into " + tbname.toUpperCase() + " ( ";
        String valuesql = "";
        String colsql = "";
        Iterator<Map.Entry<String, String>> it = attrMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = String.valueOf(hm.get(key));
            String maxtype = entry.getValue();
            colsql += key.toUpperCase() + ",";
            //System.out.println("----------" + key + "----------" + tbname);
            if (value == null || value.equals("null"))
            {
                valuesql += "null" + ",";
            }
            else
            {
                if (maxtype.equals("ALN") || maxtype.equals("GL"))
                {
                    valuesql += "'" + value + "',";
                }
                else if (maxtype.equals("UPPER"))
                {
                    valuesql += "'" + value.toUpperCase() + "',";
                }
                else if (maxtype.equals("LOWER"))
                {
                    valuesql += "'" + value.toLowerCase() + "',";
                }
                else if (maxtype.equals("YORN") || maxtype.equals("BIGINT") || maxtype.equals("DECIMAL") || maxtype.equals("FLOAT")
                    || maxtype.equals("INTEGER") || maxtype.equals("SMALLINT") || maxtype.equals("AMOUNT") || maxtype.equals("DURATION"))
                {
                    valuesql += value + ",";
                }
                else if (maxtype.equals("TIME"))
                {
                    if (value.equalsIgnoreCase("sysdate"))
                    {
                        valuesql += value + ",";
                    }
                    else
                    {
                        valuesql += "to_date('" + value + "','hh:mi:ss'),";
                    }
                }
                else if (maxtype.equals("DATE"))
                {
                    if (value.equalsIgnoreCase("sysdate"))
                    {
                        valuesql += value + ",";
                    }
                    else
                    {
                        valuesql += "to_date('" + value + "','yyyy-mm-dd'),";
                    }
                }
                else if (maxtype.equals("DATETIME"))
                {
                    if (value.equalsIgnoreCase("sysdate"))
                    {
                        valuesql += value + ",";
                    }
                    else
                    {
                        valuesql += "to_date('" + value + "','yyyy-mm-dd hh24:mi:ss'),";
                    }
                }
            }
        }
        colsql = colsql.substring(0, colsql.length() - 1);
        valuesql = valuesql.substring(0, valuesql.length() - 1);
        insertsql = insertsql + colsql + ") values (" + valuesql + ")";
        return insertsql;
    }

	/**
     * 获取指定附件id的urlname
     * @param doclinkid
     * @return
     * @throws MroException
     */
    private String getUrlName(long doclinkid)
        throws MroException
    {
        IJpoSet linkSet =
            MroServer.getMroServer().getJpoSet("SYS_DOCLINKS", MroServer.getMroServer().getSystemUserServer());
        linkSet.setUserWhere("sys_doclinksid ='" + doclinkid + "'");
        linkSet.reset();
        if (linkSet.count() > 0)
        {
            IJpoSet infoSet = linkSet.getJpo(0).getJpoSet("SYS_DOCINFO");
            if (infoSet.count() > 0)
            {
                return infoSet.getJpo(0).getString("urlname");
            }
        }
        return "";
    }
}
