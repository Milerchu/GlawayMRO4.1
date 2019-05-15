package com.glaway.sddq.overhaul.jobbook.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaway.mro.app.imp.bean.data.ImpObjectImp;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.ExcelUtil;

public class ImpToolItemnum implements ImpObjectImp{


	String objectname;
	String ifacename;
	long doclinksid;

	public HashMap<String, String> addJpo(Workbook workbook, IJpo relationjpo)
			throws SQLException {
		HashMap<String, String> returnInfo = new HashMap<String, String>();
		returnInfo.put("errorMessage", "");
		returnInfo.put("successMessage", "");

		int sheetnum = workbook.getNumberOfSheets(); // 工作表数量
		for (int index = 0; index < sheetnum; index++) {
			Sheet sheet = workbook.getSheetAt(index);
			int rowcount = sheet.getLastRowNum();

			String errormsg = null;
			String successmsg = "";
			IJpoSet attrjposet = null;

			try {
				attrjposet = relationjpo.getJpoSet("IMPATTRIBUTE");
				attrjposet.setOrderBy("to_number(EXCELCOLNUM)");
				attrjposet.reset();

				long st = System.currentTimeMillis();

				HashMap<String, Object> excelAttr = new HashMap<String, Object>();
				for (int j = 0; j < attrjposet.count(); j++) {
					IJpo mr1 = attrjposet.getJpo(j);
					if (mr1.isNull("EXCELCOLNUM")) {
						throw new MroException("system", "colnumnull");
					}
					excelAttr.put(mr1.getString("ATTRIBUTENAME").toUpperCase(),
							Integer.valueOf(Integer.parseInt(mr1
									.getString("EXCELCOLNUM"))));
				}

				Connection con = DBManager.getDBManager().getConnection();
				HashMap<String, String> asAttrMap = getAssetInsertAttrMap();// 获取字段属性
				// ArrayList<HashMap> tableList = new ArrayList<HashMap>();
				HashMap<String, String> acsvalues = new HashMap<String, String>();
				ArrayList<String> jxclList = new ArrayList<String>();
				try {
					try {
						for (int i = 1; i <= rowcount; i++) {
							Row row = sheet.getRow(i);
							if (row == null) {
								errormsg = "数据行" + i + "为空";
								break;
							}

							Cell cell = row.getCell(0);
							if ((cell != null)
									&& (!ExcelUtil.getStringValue(cell).equals(
											""))) {
								setAcsvalues(acsvalues, relationjpo);// 对字段数据进行初始化
								Set<String> set = excelAttr.keySet();
								for (String key : set) {
									cell = row.getCell(((Integer) excelAttr
											.get(key)).intValue());
									acsvalues.put(key.toUpperCase(),
											ExcelUtil.getStringValue(cell));
									Cell tasknumcell = row
											.getCell(((Integer) excelAttr
													.get("TASKNUM")).intValue());
									String tasknum = ExcelUtil
											.getStringValue(tasknumcell);
									acsvalues.put(key.toUpperCase(),
											ExcelUtil.getStringValue(cell));
									IJpoSet jposet = MroServer.getMroServer()
											.getSysJpoSet(
													"JOBBOOK",
													"JPNUM = '" + tasknum
															+ "'");
									if (jposet.count() < 1) {
										throw new AppException("jobbook",
												"atypism");
									}
								}
							}

							// TODO acsvalues转为sql
							String insertJxc = getInsertSql(asAttrMap,
									acsvalues, "TOOLREQUIREMENT");
							jxclList.add(insertJxc);
						}

						DBManager.getDBManager().executeBatch(con, jxclList);
					} catch (Exception e) {
						e.printStackTrace();
						returnInfo.put("errorMessage", e.getMessage());
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

				System.out
						.println("***************************************输入行历时"
								+ cl
								+ " ms******************************************");

				if (errormsg == null) {
					System.out
							.println("**************************************************保存前************************************************************");

					System.out
							.println("**************************************************保存后************************************************************");

					long end = System.currentTimeMillis();

					long p = end - st;

					System.out
							.println("***************************************保存历时"
									+ p
									+ " ms"
									+ "保存sheet页: "
									+ index
									+ "******************************************");

					if (successmsg.startsWith(",")) {
						successmsg = successmsg.substring(1);
					}

					System.out
							.println("**************************************************成功,记录日志前************************************************************");

					// addLog(this.ifacename, this.objectname, 1,
					// "导入文件成功,select * from " +
					// this.objectname + " where " + this.objectname +
					// "ID in ('" +
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

	private String getInsertSql(HashMap<String, String> attrMap,
			HashMap<String, String> hm, String tbname) {
		String insertsql = "insert into " + tbname.toUpperCase() + " ( ";
		String valuesql = "";
		String colsql = "";
		Iterator<Map.Entry<String, String>> it = attrMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = String.valueOf(hm.get(key));
			String maxtype = entry.getValue();
			colsql += key.toUpperCase() + ",";
			// System.out.println("----------" + key + "----------" + tbname);
			if (value == null || value.equals("null")) {
				valuesql += "null" + ",";
			} else {
				if (maxtype.equals("ALN") || maxtype.equals("GL")) {
					valuesql += "'" + value + "',";
				} else if (maxtype.equals("UPPER")) {
					valuesql += "'" + value.toUpperCase() + "',";
				} else if (maxtype.equals("LOWER")) {
					valuesql += "'" + value.toLowerCase() + "',";
				} else if (maxtype.equals("YORN") || maxtype.equals("BIGINT")
						|| maxtype.equals("DECIMAL") || maxtype.equals("FLOAT")
						|| maxtype.equals("INTEGER")
						|| maxtype.equals("SMALLINT")
						|| maxtype.equals("AMOUNT")
						|| maxtype.equals("DURATION")) {
					valuesql += value + ",";
				} else if (maxtype.equals("TIME")) {
					if (value.equalsIgnoreCase("sysdate")) {
						valuesql += value + ",";
					} else {
						valuesql += "to_date('" + value + "','hh:mi:ss'),";
					}
				} else if (maxtype.equals("DATE")) {
					if (value.equalsIgnoreCase("sysdate")) {
						valuesql += value + ",";
					} else {
						valuesql += "to_date('" + value + "','yyyy-mm-dd'),";
					}
				} else if (maxtype.equals("DATETIME")) {
					if (value.equalsIgnoreCase("sysdate")) {
						valuesql += value + ",";
					} else {
						valuesql += "to_date('" + value
								+ "','yyyy-mm-dd hh24:mi:ss'),";
					}
				}
			}
		}
		colsql = colsql.substring(0, colsql.length() - 1);
		valuesql = valuesql.substring(0, valuesql.length() - 1);
		insertsql = insertsql + colsql + ") values (" + valuesql + ")";
		return insertsql;

	}

	public HashMap<String, String> getAssetInsertAttrMap() {
		HashMap<String, String> attrMap = new HashMap<String, String>();

		attrMap.put("TOOLREQUIREMENTID", "BIGINT");
		attrMap.put("TASKNUM", "ALN");
		attrMap.put("ITEMNUM", "ALN");
		attrMap.put("AMOUNT", "FLOAT");
		attrMap.put("SITEID", "UPPER");
		attrMap.put("ORGID", "UPPER");
		return attrMap;
	}

	public void setAcsvalues(HashMap<String, String> acsvalues, IJpo relationmbo) {
		acsvalues.put("TOOLREQUIREMENTID", "TOOLREQUIREMENTSEQ.NEXTVAL");
		acsvalues.put("TASKNUM", "null");
		acsvalues.put("ITEMNUM", "null");
		acsvalues.put("AMOUNT", "null");
		acsvalues.put("SITEID", "ELEC");
		acsvalues.put("ORGID", "CRRC");
	}

	@Override
	public void addLog(String ifacename, String objectname, int type, String msg) {

		IJpoSet jposet;
		try {
			jposet = MroServer.getMroServer().getJpoSet("IMPLOG",
					MroServer.getMroServer().getSystemUserServer());
			IJpo mr = jposet.addJpo();
			mr.setValue("IFACENAME", ifacename);
			mr.setValue("OBJECTNAME", objectname);
			mr.setValue("TYPE", type);
			if (msg != null && !msg.equals("")) {
				// 增加长度校验--begin
				if (msg.length() > 1000) {
					msg = msg.substring(0, 1000) + "...";
				}
				// 增加长度校验--end
				mr.setValue("DESCRIPTION_LONGDESCRIPTION", msg);
			}
			mr.setValue("LOGTIME", MroServer.getMroServer().getDate());
			mr.setValue("DOCLINKSID", getDoclinksid());
			// mr.setValue("urlname", getUrlName(getDoclinksid()));
			jposet.save();
		} catch (MroException e) {
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
}
