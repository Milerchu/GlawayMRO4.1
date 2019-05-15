package com.glaway.sddq.config.csbom.imp;

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
 * 整车SBOM导入接口
 * 
 * @author  hzhu
 * @version  [1.0, 2017-12-25]
 * @since  [MRO/4.0]
 */
public class ImportAssetCSIface extends ImpObjectIface
{
    /**
     * 
     * <导入excel中的数据>
     * @param workbook excel workbook对象
     * @param relationjpo 导入对象jpo
     * @return
     *
     */
    @Override
    public HashMap<String, String> addJpo(Workbook workbook, IJpo relationjpo)
        throws SQLException
    {
        HashMap<String, String> returnInfo = new HashMap<String, String>();
        returnInfo.put("errorMessage", "");
        returnInfo.put("successMessage", "");
        
        int sheetnum = workbook.getNumberOfSheets(); //工作表数量
        for (int index = 0; index < sheetnum; index++)
        {
            Sheet sheet = workbook.getSheetAt(index);
            int rowcount = sheet.getLastRowNum();
            
            String errormsg = null;
            String successmsg = "";
            IJpoSet attrjposet = null;
            
            try
            {
                
                attrjposet = relationjpo.getJpoSet("IMPATTRIBUTE");
                attrjposet.setOrderBy("to_number(EXCELCOLNUM)");
                attrjposet.reset();
                
                long st = System.currentTimeMillis();
                
                HashMap<String, Object> excelAttr = new HashMap<String, Object>();
                for (int j = 0; j < attrjposet.count(); j++)
                {
                    IJpo mr1 = attrjposet.getJpo(j);
                    if (mr1.isNull("EXCELCOLNUM"))
                    {
                        throw new MroException("system", "colnumnull");
                    }
                    excelAttr.put(mr1.getString("ATTRIBUTENAME").toUpperCase(),
                        Integer.valueOf(Integer.parseInt(mr1.getString("EXCELCOLNUM"))));
                }
                
                Connection con = DBManager.getDBManager().getConnection();
                HashMap<String, String> asAttrMap = this.getAssetInsertAttrMap();//获取字段属性
                HashMap<Object, Object> ancestorMap = new HashMap<Object, Object>();
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
                            if ((cell != null) && (!ExcelUtil.getStringValue(cell).equals("")))
                            {
                                setAcsvalues(acsvalues, relationjpo);//对字段数据进行初始化
                                Set<String> set = excelAttr.keySet();
                                for (String key : set)
                                {
                                    String maxtype = (String)asAttrMap.get(key);
                                    cell = row.getCell(((Integer)excelAttr.get(key)).intValue());
                                    if (cell != null)
                                    {
                                        if (key != null && key.toUpperCase().equals("ASSETCSLEVEL")
                                            && ExcelUtil.getStringValue(cell).equals("ASSET"))
                                        {
                                            Cell ancestorcell =
                                                row.getCell(((Integer)excelAttr.get("ANCESTOR")).intValue());
                                            String ancestor = ExcelUtil.getStringValue(ancestorcell);
                                            //去数据库中校验该ancestor是否存在 
                                            IJpoSet jposet =
                                                MroServer.getMroServer().getSysJpoSet("ASSETCS",
                                                    "ancestor = '" + ancestor + "' and ASSETCSLEVEL = 'ASSET'");
                                            if (jposet != null && jposet.count() > 0)
                                            {
                                                String delAssetSqlString =
                                                    "delete from ASSETCS where ancestor = '" + ancestor + "'";
                                                String delAssettreeSqlString =
                                                    "delete from assetcstree where assetcsnum in (select assetcsnum from assetcstree where ancestor='"
                                                        + ancestor + "')";
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
                                if (!((String)acsvalues.get("PARENT")).equals("null"))
                                {
                                    String parent = (String)acsvalues.get("PARENT");
                                    if (!parent.equals(""))
                                    {
                                        ancestorMap.put((String)acsvalues.get("ASSETCSNUM"), parent);
                                    }
                                    else
                                    {
                                        ancestorMap.put((String)acsvalues.get("ASSETCSNUM"), null);
                                    }
                                }
                                else
                                {
                                    ancestorMap.put((String)acsvalues.get("ASSETCSNUM"), null);
                                }
                                String insertAssetJz = getInsertSql(asAttrMap, acsvalues, "ASSETCS");
                                System.out.println("---insert: " + i + "-" + insertAssetJz);
                                
                                ancestorSql.add(insertAssetJz);
                                ancestorSql =
                                    getInsertAncestorSql("ASSETCSTREE",
                                        ancestorSql,
                                        ancestorMap,
                                        (String)acsvalues.get("ASSETCSNUM"),
                                        (String)acsvalues.get("ASSETCSNUM"),
                                        (String)acsvalues.get("ANCESTOR"),
                                        0);
                                
                            }
                        }
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
                    
                    //          addLog(this.ifacename, this.objectname, 1, "导入文件成功,select * from " + 
                    //            this.objectname + " where " + this.objectname + "ID in ('" + 
                    //            successmsg + "')");
                    
                    System.out.println("**************************************************成功,记录日志后************************************************************");
                    //          throw new MroException("", "数据导入成功");
                    returnInfo.put("successMessage", "数据导入成功");
                }
                else
                {
                    System.out.println("**************************************************错误,记录日志前************************************************************");
                    //          addLog(this.ifacename, this.objectname, -1, errormsg);
                    System.out.println("**************************************************错误,记录日志后************************************************************");
                }
                
            }
            catch (Exception e2)
            {
                System.out.println("**************************************************异常,记录日志前************************************************************");
                e2.printStackTrace();
                errormsg = e2.getMessage();
                //        addLog(this.ifacename, this.objectname, -1, errormsg);
                returnInfo.put("errorMessage", errormsg);
                System.out.println("**************************************************异常,记录日志后************************************************************");
            }
            
        }
        return returnInfo;
    }
    
    public HashMap<String, String> getAssetInsertAttrMap()
    {
        HashMap<String, String> attrMap = new HashMap<String, String>();
        
        attrMap.put("ASSETCSID", "BIGINT");
        attrMap.put("ASSETCSLEVEL", "UPPER");
        attrMap.put("ASSETCSNUM", "UPPER");
        attrMap.put("PARENT", "UPPER");
        attrMap.put("ANCESTOR", "UPPER");
        attrMap.put("CMODEL", "ALN");
        attrMap.put("MAKER", "ALN");
        attrMap.put("DESCRIPTION", "ALN");
        attrMap.put("ITEMNUM", "UPPER");
        attrMap.put("LINENUM", "ALN");
        attrMap.put("LOC", "ALN");
        attrMap.put("LOCDESC", "ALN");
        attrMap.put("PARTCODE", "ALN");
        attrMap.put("TEMPLEVEL", "ALN");
        attrMap.put("RNUM", "ALN");
        attrMap.put("SOFTNAME", "ALN");
        attrMap.put("SOFTVERSION", "ALN");
        attrMap.put("SOFTUPDATE", "DATETIME");
        attrMap.put("CONFIGNUM", "ALN");
        attrMap.put("STATUS", "ALN");
        attrMap.put("SECRETLEVEL", "INTEGER");
        attrMap.put("SECRETLEVELDES", "UPPER");
        attrMap.put("LANGCODE", "UPPER");
        attrMap.put("SITEID", "UPPER");
        attrMap.put("ORGID", "UPPER");
        return attrMap;
    }
    
    public void setAcsvalues(HashMap<Object, Object> acsvalues, IJpo relationmbo)
    {
        acsvalues.put("ASSETCSID", "ASSETCSSEQ.NEXTVAL");
        acsvalues.put("ASSETCSLEVEL", "null");
        acsvalues.put("ASSETCSNUM", "null");
        acsvalues.put("PARENT", "null");
        acsvalues.put("ANCESTOR", "null");
        acsvalues.put("DESCRIPTION", "null");
        acsvalues.put("MAKER", "null");
        acsvalues.put("ITEMNUM", "null");
        acsvalues.put("LINENUM", "null");
        acsvalues.put("RNUM", "null");
        acsvalues.put("PARTCODE", "null");
        acsvalues.put("TEMPLEVEL", "null");
        acsvalues.put("LOC", "null");
        acsvalues.put("LOCDESC", "null");
        acsvalues.put("CONFIGNUM", "null");
        acsvalues.put("CMODEL", "null");
        acsvalues.put("SOFTNAME", "null");
        acsvalues.put("SOFTVERSION", "null");
        acsvalues.put("SOFTUPDATE", "null");
        //        acsvalues.put("CREATEBY", relationmbo.getUserInfo().getUserName());
        //        acsvalues.put("CHANGEBY", relationmbo.getUserInfo().getUserName());
        //        acsvalues.put("CHANGEDATE", "sysdate");
        //        acsvalues.put("CREATEDATE", "sysdate");
        acsvalues.put("STATUS", "新建");
        acsvalues.put("SECRETLEVEL", "0");
        acsvalues.put("SECRETLEVELDES", "非密");
        acsvalues.put("SITEID", "ELEC");
        acsvalues.put("ORGID", "CRRC");
        acsvalues.put("LANGCODE", "ZH");
    }
    
    /**
     * 
     * 获取树状结构insertsql
     *
     */
    public ArrayList<String> getInsertAncestorSql(String tbname, ArrayList<String> sqlList,
        HashMap<Object, Object> ancestorMap, String assetnum, String parent, String assetancestor, int i)
    {
        if (ancestorMap.containsKey(parent))
        {
            String ancestor = (String)ancestorMap.get(parent);
            String insertSql = "insert into " + tbname.toUpperCase() + "(";
            insertSql = insertSql + "ASSETCSTREEID,ASSETCSNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
            insertSql =
                insertSql + " values (ASSETCSTREESEQ.NEXTVAL,'" + assetnum + "','" + parent + "','ELEC','CRRC'," + i
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
                insertSql = insertSql + "ASSETCSTREEID,ASSETCSNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
                insertSql =
                    insertSql + " values (ASSETCSTREESEQ.NEXTVAL,'" + assetnum + "','" + parent + "','ELEC','CRRC',"
                        + i + ")";
                sqlList.add(insertSql);
            }
        }
        return sqlList;
    }
}
