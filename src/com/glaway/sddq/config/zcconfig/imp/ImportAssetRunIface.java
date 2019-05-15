package com.glaway.sddq.config.zcconfig.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.httpclient.util.DateUtil;
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
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ExcelUtil;

/**
 * @ClassName ImportAssetRunIface
 * @Description 走行公里导入
 * @author public2175
 * @Date 2018-9-5 下午6:19:16
 * @version 1.0.0
 */
public class ImportAssetRunIface extends ImpObjectIface {

    public HashMap<String, String> addJpo(Workbook workbook, IJpo relationmbo,String userName) throws SQLException {

        HashMap<String, String> returnInfo = new HashMap<String, String>();
        returnInfo.put("errorMessage", "");
        returnInfo.put("successMessage", "");
        returnInfo.put("dealMessage", "");

        int sheetnum = workbook.getNumberOfSheets();
        for (int index = 0; index <= sheetnum - 1; index++) {

            Sheet sheet = workbook.getSheetAt(index);
            int rowcount = sheet.getLastRowNum();

            String errormsg = null;
            String successmsg = "";
            IJpoSet attrmboset = null;
            try {
                attrmboset = relationmbo.getJpoSet("IMPATTRIBUTE");
                attrmboset.setOrderBy("to_number(EXCELCOLNUM)");
                attrmboset.reset();

                long st = System.currentTimeMillis();

                HashMap<String, Object> excelAttr = new HashMap<String, Object>();
                for (int j = 0; j < attrmboset.count(); j++) {
                    IJpo mr1 = attrmboset.getJpo(j);
                    if (mr1.isNull("EXCELCOLNUM")) {
                        throw new MroException("system", "colnumnull");
                    }
                    excelAttr.put(mr1.getString("ATTRIBUTENAME").toUpperCase(),
                            Integer.valueOf(Integer.parseInt(mr1.getString("EXCELCOLNUM"))));
                }

                Connection con = DBManager.getDBManager().getConnection();
                HashMap<String, String> asAttrMap = this.getAssetInsertAttrMap();
                HashMap<Object, Object> ancestorMap = new HashMap<Object, Object>();
                HashMap<String, String> acsvalues = new HashMap<String, String>();
                ArrayList<String> ancestorSql = new ArrayList<String>();
                try {
                    boolean flag = false;
                    StringBuffer errorMsg = new StringBuffer();
                    for (int i = 1; i <= rowcount; i++) {
                    	ancestorSql.clear();
                        Row row = sheet.getRow(i);
                        try {
                            if (row == null) {
                                errormsg = "数据行" + i + "为空";
                                break;
                            }
                            Cell cell = row.getCell(0);
                            if ((cell != null) && !ExcelUtil.getStringValue(cell).equals("")) {
                                setAcsvalues(acsvalues, relationmbo,userName);// 对字段数据进行初始化
                                Cell cmodelCell = row.getCell(0);// 车型
                                String cmodel = ExcelUtil.getStringValue(cmodelCell);
                                Cell carnoCell = row.getCell(1);// 车号
                                String carno = ExcelUtil.getStringValue(carnoCell);
                                Cell dateCell = row.getCell(2);// 统计日期
                                String countdate = ExcelUtil.getStringValue(dateCell);
                                Cell runRilCell = row.getCell(3);// 累计走行公里数
                                String runRil = ExcelUtil.getStringValue(runRilCell);

                                // 根据车型车号，去系统中查找数据，判断是否存在
                                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
                                        "cmodel = '" + cmodel + "' and assetlevel = 'ASSET' and carno='" + carno + "'");

                                if (jposet != null && jposet.count() > 0) {
                                    String assetnum = jposet.getJpo(0).getString("ancestor");
                                    long assetRunRil = jposet.getJpo(0).getLong("RUNKILOMETRE");
                                    acsvalues.put("ASSETNUM", assetnum);
                                    acsvalues.put("RUNKILOMETRE", runRil);
                                    if(assetRunRil==0){
                                    	acsvalues.put("KILOMETRE",runRil);
                                    }else{
                                    	acsvalues.put("KILOMETRE", String.valueOf(Long.valueOf(runRil) - assetRunRil));
                                    }  
                                    acsvalues.put("COUNTDATE", countdate);
                                    String insertAssetJz = getInsertSql(asAttrMap, acsvalues, "ASSETRUN");
                                    ancestorSql.add(insertAssetJz);
                                    DBManager.getDBManager().executeBatch(con, ancestorSql);
                                  
                        				
                        			int repairkilometer = jposet.getJpo().getInt("REPAIRKILOMETER");
                        			IJpoSet assetrunSet = MroServer.getMroServer()
                        					.getSysJpoSet("ASSETRUN");
                        			assetrunSet.setQueryWhere("ASSETNUM='"+assetnum+"'");
                        			assetrunSet.setOrderBy("RUNKILOMETRE desc");
                        			assetrunSet.reset();
                        			if(assetrunSet.count()>0){
                        				int runkilometre = assetrunSet.getJpo().getInt("RUNKILOMETRE");
                            			int kilometre = assetrunSet.getJpo().getInt("KILOMETRE");
                            			jposet.getJpo().setValue("RUNKILOMETRE", runkilometre,
                            					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            			jposet.getJpo().setValue("KILOMETRE", kilometre,
                            					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            			jposet.getJpo().setValue("TJDATE", countdate,
                            					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            			int runkilometres = jposet.getJpo().getInt("RUNKILOMETRE");
                            			int repairafterkilometer = runkilometres - repairkilometer;
                            			jposet.getJpo().setValue("REPAIRAFTERKILOMETER", repairafterkilometer, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            			jposet.save();
                        			}                                    
                                } else {
                                    flag = true;
                                    errorMsg.append("在系统中无法匹配到第" + i + "行的车型车号数据");
                                    break;
                                }
                            }
                        }
                        // }
                        catch (Exception e) {
                            e.printStackTrace();
                            returnInfo.put("errorMessage", e.getMessage());
                            return returnInfo;
                        }
                    }
                    if (flag) {
                        returnInfo.put("dealMessage", errorMsg.toString());
                        return returnInfo;
                    }
                } catch (RuntimeException localRuntimeException) {
                } catch (Throwable localThrowable) {
                } finally {
                    if (con != null) {
                        con.close();
                    }
                }

                long mid = System.currentTimeMillis();

                long cl = mid - st;

                System.out.println("***************************************输入行历时" + cl
                        + " ms******************************************");

                if (errormsg == null) {
                    System.out
                            .println("**************************************************保存前************************************************************");

                    System.out
                            .println("**************************************************保存后************************************************************");

                    long end = System.currentTimeMillis();

                    long p = end - st;

                    System.out.println("***************************************保存历时" + p + " ms" + "保存sheet页: " + index
                            + "******************************************");

                    if (successmsg.startsWith(",")) {
                        successmsg = successmsg.substring(1);
                    }

                    System.out
                            .println("**************************************************成功,记录日志前************************************************************");

                    // addLog(this.ifacename, this.objectname, 1, "导入文件成功,select * from " +
                    // this.objectname + " where " + this.objectname + "ID in ('" +
                    // successmsg + "')");

                    System.out
                            .println("**************************************************成功,记录日志后************************************************************");
                    // throw new MroException("", "数据导入成功");
                    returnInfo.put("successMessage", "数据导入成功");
                } else {
                    System.out
                            .println("**************************************************错误,记录日志前************************************************************");
                    // addLog(this.ifacename, this.objectname, -1, errormsg);
                    System.out
                            .println("**************************************************错误,记录日志后************************************************************");
                }
            } catch (Exception e2) {
                System.out
                        .println("**************************************************异常,记录日志前************************************************************");
                e2.printStackTrace();
                errormsg = e2.getMessage();
                // addLog(this.ifacename, this.objectname, -1, errormsg);
                returnInfo.put("errorMessage", errormsg);
                System.out
                        .println("**************************************************异常,记录日志后************************************************************");
            }
        }
        return returnInfo;
    }

    public HashMap<String, String> getAssetInsertAttrMap() {
        HashMap<String, String> attrMap = new HashMap<String, String>();
        attrMap.put("ASSETRUNID", "BIGINT");
        attrMap.put("ASSETRUNNUM", "UPPER");
        attrMap.put("ASSETNUM", "UPPER");
        attrMap.put("SITEID", "UPPER");
        attrMap.put("ORGID", "UPPER");
        attrMap.put("COUNTDATE", "DATE");
        attrMap.put("KILOMETRE", "BIGINT");
        attrMap.put("RUNKILOMETRE", "BIGINT");
        attrMap.put("DESCRIPTION", "ALN");
        attrMap.put("CHECKBY", "ALN");
        attrMap.put("CHECKTIME", "DATE");

        return attrMap;
    }

    public void setAcsvalues(HashMap<String, String> acsvalues, IJpo relationmbo,String userName) {
        acsvalues.put("ASSETRUNID", "assetrunseq.nextval");
        acsvalues.put("ASSETNUM", "null");
        acsvalues.put("ASSETRUNNUM", "null");
        acsvalues.put("CHECKBY", userName);
        acsvalues.put("CHECKTIME",DateUtil.formatDate(MroServer.getMroServer().getDate(),"yyyy-MM-dd"));
        acsvalues.put("DESCRIPTION", "null");
        acsvalues.put("RUNKILOMETRE", "null");
        acsvalues.put("KILOMETRE", "null");
        acsvalues.put("COUNTDATE", "null");
        acsvalues.put("SITEID", "ELEC");
        acsvalues.put("ORGID", "CRRC");
    }

}
