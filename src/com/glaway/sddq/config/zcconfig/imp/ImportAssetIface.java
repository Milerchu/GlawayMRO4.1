package com.glaway.sddq.config.zcconfig.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaway.mro.app.imp.bean.data.ImpObjectIface;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.ExcelUtil;

/**
 * 
 * 现场配置导入接口
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-22]
 * @since  [产品/模块版本]
 */
public class ImportAssetIface extends ImpObjectIface
{
    public HashMap<String, String> addJpo(Workbook workbook, IJpo relationmbo)
        throws SQLException
    {
        
        HashMap<String, String> returnInfo = new HashMap<String, String>();
        returnInfo.put("errorMessage", "");
        returnInfo.put("successMessage", "");
        
        int sheetnum = workbook.getNumberOfSheets();
        for (int index = 0; index <= sheetnum - 1; index++)
        {
            
            Sheet sheet = workbook.getSheetAt(index);
            int rowcount = sheet.getLastRowNum();
            
            String errormsg = null;
            String successmsg = "";
            IJpoSet attrmboset = null;
            try
            {
                attrmboset = relationmbo.getJpoSet("IMPATTRIBUTE");
                attrmboset.setOrderBy("to_number(EXCELCOLNUM)");
                attrmboset.reset();
                
                long st = System.currentTimeMillis();
                
                HashMap<String, Object> excelAttr = new HashMap<String, Object>();
                for (int j = 0; j < attrmboset.count(); j++)
                {
                    IJpo mr1 = attrmboset.getJpo(j);
                    if (mr1.isNull("EXCELCOLNUM"))
                    {
                        throw new MroException("system", "colnumnull");
                    }
                    excelAttr.put(mr1.getString("ATTRIBUTENAME").toUpperCase(),
                        Integer.valueOf(Integer.parseInt(mr1.getString("EXCELCOLNUM"))));
                }
                
                Connection con = DBManager.getDBManager().getConnection();
//                HashMap<Object, Object> asAttrMap = this.getAssetInsertAttrMap();
                HashMap<String, String> asAttrMap = this.getAssetInsertAttrMap();
                HashMap<Object, Object> ancestorMap = new HashMap<Object, Object>();
//                HashMap<Object, Object> acsvalues = new HashMap<Object, Object>();
                HashMap acsvalues = new HashMap();
                ArrayList<String> ancestorSql = new ArrayList<String>();
                try
                {
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
                            if ((cell != null) && !ExcelUtil.getStringValue(cell).equals(""))
                            {
                                setAcsvalues(acsvalues, relationmbo);//对字段数据进行初始化
                                Set<String> set = excelAttr.keySet();
                                for (String key : set)
                                {
                                    
                                    String maxtype = (String)asAttrMap.get(key);
                                    if(maxtype == null){
                                        System.out.println(key);
                                    }
                                    cell = row.getCell(((Integer)excelAttr.get(key)).intValue());
                                    if (cell != null)
                                    {
                                        if(key != null && key.toUpperCase().equals("ASSETLEVEL") && ExcelUtil.getStringValue(cell).equals("ASSET")){
                                            Cell ancestorcell =  row.getCell(((Integer)excelAttr.get("ANCESTOR")).intValue());
                                            String ancestor = ExcelUtil.getStringValue(ancestorcell);
//                                            Cell cmodelCell =  row.getCell(((Integer)excelAttr.get("CMODEL")).intValue());
//                                            String cmodel = ExcelUtil.getStringValue(cmodelCell);
//                                            Cell carnoCell =  row.getCell(((Integer)excelAttr.get("CARNO")).intValue());
//                                            String carno = ExcelUtil.getStringValue(carnoCell);
                                            //去数据库中校验该ancestor是否存在
                                            IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET", "ancestor = '"+ancestor+"' and assetlevel = 'ASSET'");
                                            if(jposet != null && jposet.count() > 0){
                                                String delAssetSqlString = "delete from asset where ancestor = '"+ancestor+"'";
                                                String delAssettreeSqlString = "delete from assettree where assetnum in (select assetnum from assettree where ancestor='"+ancestor+"')";
                                                ancestorSql.add(delAssetSqlString);
                                                ancestorSql.add(delAssettreeSqlString);
                                            }
                                        }
                                        acsvalues.put(key.toUpperCase(), ExcelUtil.getStringValue(cell));
                                    }
                                }
                                if (((String)acsvalues.get("SECRETLEVELDES")).equals("非密"))
                                    acsvalues.put("SECRETLEVEL", "0");
                                else if (((String)acsvalues.get("SECRETLEVELDES")).equals("秘密"))
                                    acsvalues.put("SECRETLEVEL", "1");
                                else if (((String)acsvalues.get("SECRETLEVELDES")).equals("机密"))
                                    acsvalues.put("SECRETLEVEL", "2");
                                else if (((String)acsvalues.get("SECRETLEVELDES")).equals("绝密"))
                                {
                                    acsvalues.put("SECRETLEVEL", "3");
                                }
                                acsvalues.put("TYPE", "2");
                                if (!((String)acsvalues.get("PARENT")).equals("null"))
                                {
                                    String parent = (String)acsvalues.get("PARENT");
                                    if (!parent.equals(""))
                                    {
                                        ancestorMap.put((String)acsvalues.get("ASSETNUM"), parent);
                                    }
                                    else
                                    {
                                        ancestorMap.put((String)acsvalues.get("ASSETNUM"), null);
                                    }
                                }
                                else
                                {
                                    ancestorMap.put((String)acsvalues.get("ASSETNUM"), null);
                                }
                                String insertAssetJz = getInsertSql(asAttrMap, acsvalues, "ASSET");
                               // System.out.println("---insert-" + i + "-" + insertAssetJz);
                                
                                ancestorSql.add(insertAssetJz);
                                ancestorSql =
                                    getInsertAncestorSql("ASSETTREE",
                                        ancestorSql,
                                        ancestorMap,
                                        (String)acsvalues.get("ASSETNUM"),
                                        (String)acsvalues.get("ASSETNUM"),
                                        (String)acsvalues.get("ANCESTOR"),
                                        0);
                            }
                        }
                        //   	          }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            returnInfo.put("errorMessage", e.getMessage());
                            return returnInfo;
                        }
                    }
                    DBManager.getDBManager().executeBatch(con, ancestorSql);
                }
                catch (RuntimeException localRuntimeException)
                {
                }
                catch (Throwable localThrowable)
                {
                }
                finally
                {
                    if (con != null)
                    {
                        con.close();
                    }
                }
                
                long mid = System.currentTimeMillis();
                
                long cl = mid - st;
                
                System.out.println("***************************************输入行历时" + cl
                    + " ms******************************************");
                
                if (errormsg == null)
                {
                    System.out.println("**************************************************保存前************************************************************");
                    
                    System.out.println("**************************************************保存后************************************************************");
                    
                    long end = System.currentTimeMillis();
                    
                    long p = end - st;
                    
                    System.out.println("***************************************保存历时" + p + " ms" + "保存sheet页: " + index
                        + "******************************************");
                    
                    if (successmsg.startsWith(","))
                    {
                        successmsg = successmsg.substring(1);
                    }
                    
                    System.out.println("**************************************************成功,记录日志前************************************************************");
                    
                    //	        addLog(this.ifacename, this.objectname, 1, "导入文件成功,select * from " + 
                    //	          this.objectname + " where " + this.objectname + "ID in ('" + 
                    //	          successmsg + "')");
                    
                    System.out.println("**************************************************成功,记录日志后************************************************************");
                    //	        throw new MroException("", "数据导入成功");
                    returnInfo.put("successMessage", "数据导入成功");
                }
                else
                {
                    System.out.println("**************************************************错误,记录日志前************************************************************");
                    //	        addLog(this.ifacename, this.objectname, -1, errormsg);
                    System.out.println("**************************************************错误,记录日志后************************************************************");
                }
            }
            catch (Exception e2)
            {
                System.out.println("**************************************************异常,记录日志前************************************************************");
                e2.printStackTrace();
                errormsg = e2.getMessage();
                //	      addLog(this.ifacename, this.objectname, -1, errormsg);
                returnInfo.put("errorMessage", errormsg);
                System.out.println("**************************************************异常,记录日志后************************************************************");
            }
        }
        return returnInfo;
    }
    
    public void setAcsvalues(HashMap<Object, Object> acsvalues, IJpo relationmbo)
    {
        acsvalues.put("ASSETID", "ASSETSEQ.NEXTVAL");
        acsvalues.put("ASSETNUM", "null");
        acsvalues.put("ASSETLEVEL", "SYSTEM");
        acsvalues.put("PARENT", "null");
        acsvalues.put("ANCESTOR", "null");
        acsvalues.put("TYPE", "2");
        acsvalues.put("DESCRIPTION", "null");
        acsvalues.put("ITEMNUM", "null");
        acsvalues.put("SQN", "null");
        acsvalues.put("LINENUM", "null");
        acsvalues.put("RNUM", "null");
        acsvalues.put("CONFIGNUM", "null");
        acsvalues.put("MODEL", "null");
        acsvalues.put("PRODUCTCODE", "null");
        acsvalues.put("CUSTNUM", "null");
        //        acsvalues.put("CREATEBY", relationmbo.getUserInfo().getUserName());
        //        acsvalues.put("CHANGEBY", relationmbo.getUserInfo().getUserName());
        //        acsvalues.put("CHANGEDATE", "sysdate");
        //        acsvalues.put("CREATEDATE", "sysdate");
        acsvalues.put("VERSION", "null");
        acsvalues.put("STATUS", "正常");
        acsvalues.put("SECRETLEVEL", "0");
        acsvalues.put("SECRETLEVELDES", "非密");
        acsvalues.put("SITEID", "ELEC");
        acsvalues.put("ORGID", "CRRC");
        acsvalues.put("LANGCODE", "ZH");
    }
    
    
    public HashMap<String, String> getAssetInsertAttrMap()
    {
        HashMap<String, String> attrMap = new HashMap<String, String>();
        
        attrMap.put("ASSETID", "BIGINT");
        attrMap.put("ASSETLEVEL", "UPPER");
        attrMap.put("ASSETNUM", "UPPER");
        attrMap.put("PARENT", "UPPER");
        attrMap.put("ANCESTOR", "UPPER");
        attrMap.put("TYPE", "ALN");
        attrMap.put("DESCRIPTION", "ALN");
        attrMap.put("ITEMNUM", "UPPER");
        attrMap.put("SQN", "ALN");
        attrMap.put("LINENUM", "ALN");
        attrMap.put("RNUM", "ALN");
        attrMap.put("CONFIGNUM", "ALN");
        attrMap.put("MODEL", "ALN");
        attrMap.put("PRODUCTCODE", "ALN");
        attrMap.put("CUSTNUM", "UPPER");
        attrMap.put("VERSION", "ALN");
        attrMap.put("STATUS", "ALN");
        attrMap.put("SECRETLEVEL", "INTEGER");
        attrMap.put("SECRETLEVELDES", "UPPER");
        attrMap.put("LANGCODE", "UPPER");
        attrMap.put("SITEID", "UPPER");
        attrMap.put("ORGID", "UPPER");
        
        attrMap.put("CMODEL", "ALN");
        attrMap.put("CARNO", "ALN");
        attrMap.put("OWNERCUSTOMER", "ALN");
        attrMap.put("OVERHAULER", "ALN");
        attrMap.put("MAKER", "ALN");
        attrMap.put("ONLINETIME", "DATE");
        attrMap.put("RELEASEDATE", "DATE");
        attrMap.put("WARRANTYEXPDATE", "DATE");
        attrMap.put("UPDATETIME", "DATE");
        attrMap.put("RUNKILOMETRE", "DECIMAL");
        attrMap.put("REMARK", "ALN");
        return attrMap;
    }
    
    public ArrayList<String> getInsertAncestorSql(String tbname, ArrayList<String> sqlList,
        HashMap<Object, Object> ancestorMap, String assetnum, String parent, String assetancestor, int i)
    {
        if (ancestorMap.containsKey(parent))
        {
            String ancestor = (String)ancestorMap.get(parent);
            String insertSql = "insert into " + tbname.toUpperCase() + "(";
            insertSql = insertSql + "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
            insertSql =
                insertSql + " values (ASSETTREESEQ.NEXTVAL,'" + assetnum + "','" + parent + "','ELEC','CRRC'," + i
                    + ")";
            sqlList.add(insertSql);
            if (ancestor != null)
            {
                sqlList = getInsertAncestorSql(tbname, sqlList, ancestorMap, assetnum, ancestor, assetancestor, i + 1);
            }
        }
        else
        {
            if ((parent.equals(assetancestor)))
            {
                String insertSql = "insert into " + tbname.toUpperCase() + "(";
                insertSql = insertSql + "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
                insertSql =
                    insertSql + " values (ASSETTREESEQ.NEXTVAL,'" + assetnum + "','" + parent + "','ELEC','CRRC'," + i
                        + ")";
                sqlList.add(insertSql);
            }
        }
        return sqlList;
    }
    
}
