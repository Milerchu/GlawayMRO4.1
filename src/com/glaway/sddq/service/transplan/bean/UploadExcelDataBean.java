package com.glaway.sddq.service.transplan.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaway.mro.app.imp.bean.data.ImpObjectIface;
import com.glaway.mro.app.system.doclinks.bean.AddDoclinkBean;
import com.glaway.mro.app.system.doclinks.data.SysDocinfo;
import com.glaway.mro.app.system.doclinks.data.SysDoclink;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;

public class UploadExcelDataBean extends AddDoclinkBean {
	@Override
	public int uploadfile() throws Exception {
		long doclinksid = 0l;
		if (dataBean != null) {
			getMroSession().setIframeResponse(true);
			checkSave();
			if (currJpo != null) {
				currJpo.getString("DOCTYPE");
				doclinksid = currJpo.getId();
				if (currJpo.isNull("DOCTYPE")) {
					String[] p = { "DOCTYPE" };
					throw new AppException("system", "null", p);
				}
				UploadUtil upload = new UploadUtil(getMroSession().getRequest());
				upload.setMroDirectoryName(getString("DOCTYPE"));
				String ip = getMroSession().getRequest().getRemoteAddr();
				if (currJpo instanceof SysDoclink) {
					HashSet<String[]> hs = upload.writeToDisk();
					for (String[] s : hs) {
						String content = Property.UPLOADFILE_LABLE + "\""
								+ s[0] + "\"";
						writeAuditLog(getAppName(), content,
								Property.UPLOADFILE_LABLE, true, "",
								currJpo.getName(), ip, false);
						if (dataBean.getParent() != null) {
							IJpo parent = dataBean.getParent().getJpo();
							((SysDoclink) currJpo).addDoclink(parent, s[0],
									s[2], s[0], "");
							dataBean.getParent().putDocliksMap(currJpo.getId(),
									"A");
							getAppBean().putDocliksMap(currJpo.getId(), "A");
						}
					}
					doclinkSet.save();
				}
			}
			dataBean.reset();
			if (dataBean.getJpo() != null) {
				dataBean.getJpo().setValue("CHANGEDATE", getSysDate());
				dataBean.getJpo().setValue("CHANGEBY",
						getUserInfo().getPersonId());
			}
			dialogclose();
		}
		String ownerTable = dataBean.getJpo().getString("OWNERTABLE");

		if (!StringUtil.isNull(ownerTable)) {
			IJpoSet doclinksSet = dataBean.getJpo().getJpoSet();
			doclinksSet.setOrderBy("createdate desc");
			doclinksSet.reset();
			IJpo doclinkJpo = doclinksSet.getJpo(0);
			IJpoSet docinfoSet = doclinkJpo.getJpoSet("SYS_DOCINFO");
			IJpo docinfojpo = null;
			String filename = null;
			if (!docinfoSet.isEmpty()) {
				docinfojpo = docinfoSet.getJpo(0);
				filename = docinfojpo.getString("URLNAME");
			}
			System.out.println(filename + doclinksid);
			impData(filename, doclinksid);
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	public String getStringValue(Cell cell) {
		String str = "";

		str = cell.getStringCellValue();

		return str;
	}

	public double getNumberValue(Cell cell) {
		double value = 0.00D;

		value = cell.getNumericCellValue();

		return value;
	}

	public Date getDateValue(Cell cell) {
		Date date = null;

		date = cell.getDateCellValue();
		return date;
	}

	private double getDoubleByCell(Cell cell) {
		double sl = 0.000000d;
		int lx = 0;
		try {
			lx = cell.getCellType();
		} catch (Exception e) {
			lx = 3;
		}
		switch (lx) {
		case 1:
			sl = Double.valueOf(cell.getStringCellValue()).doubleValue();
			break;
		case 0:
			sl = cell.getNumericCellValue();
			break;
		}
		return sl;
	}

	private Boolean getBoolean(Cell cell) {
		boolean boo;
		try {
			boo = cell.getBooleanCellValue();
		} catch (Exception e) {
			String b = cell.getStringCellValue();
			if (b.toUpperCase().equals("Y")) {
				boo = true;
			} else {
				boo = false;
			}
		}
		return boo;
	}

	private String getStringByCell(Cell cell) {
		int gys = 0;
		int num = 0;
		int lx = 0;
		String vendor = "";
		try {
			lx = cell.getCellType();
		} catch (Exception e) {
			lx = 3;
		}
		switch (lx) {
		case 1:
			vendor = cell.getRichStringCellValue().getString();
			num = 1;
			break;
		case 0:
			gys = (int) cell.getNumericCellValue();
			num = 2;
			break;
		case 3:
			vendor = "";
			break;
		case 2:
			vendor = String.valueOf(cell.getCellFormula());
			break;
		case 4:
			vendor = String.valueOf(cell.getBooleanCellValue());
			break;
		case 5:
			vendor = String.valueOf(cell.getErrorCellValue());
		}
		if (num == 2) {
			vendor = String.valueOf(gys);
		}
		return vendor;
	}

	/**
	 * 
	 * <功能描述> 导入excel公用方法，首先在IMPATTRIBUTE 增加字段且增加序号，然后excel模板需保持一致
	 * 该方法会自动区分字段类型然后读取excel数据然后写入数据库
	 * 
	 * @param workbook
	 *            poi对象
	 * @param table
	 *            目标表名
	 * @param hang
	 *            excel 从第几行开始读写
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	private void impExcelnr(Workbook workbook, String table, int hang) throws Exception {
		IJpo jpo = this.getAppBean().getJpo();
		IJpoSet transplanlistSet = jpo.getJpoSet("$transplanlist",
				table, "1=2");
		IJpoSet ijpoSet = jpo.getJpoSet("$IMPATTRIBUTE", "IMPATTRIBUTE",
				"ifacename='TRANSPLANLIST'");
		ijpoSet.setOrderBy("EXCELCOLNUM asc");
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int zs = 0; zs < numberOfSheets; zs++) {
			Sheet sheet = workbook.getSheetAt(zs);
			int lastRowNum = sheet.getLastRowNum();
			int rsRows = sheet.getLastRowNum() + 1;
			// String name = sheet.getSheetName();
			// int cloum = sheet.getPhysicalNumberOfRows();// 总列数
			for (int i = hang; (i < rsRows) && (lastRowNum > 0); ++i) {// 从第几行开始
				IJpo transplanlist = transplanlistSet.addJpo();
				Row row = sheet.getRow(i);
				for (int j = 0; j < ijpoSet.count(); j++) {
					try {
						IJpo ijpo = ijpoSet.getJpo(j);
						int index = ijpo.getInt("EXCELCOLNUM");// 序号
						Cell cell = row.getCell(index);// 获取每一行各列数据
						String attributename = ijpo.getString("attributename");
						String type = type(table, attributename, jpo);
						if (type.equals("ALN") || type.equals("UPPER")
								|| type.equals("LOWER")) {
							transplanlist.setValue(attributename,// 给字段赋值并区分类型
									getStringByCell(cell));
						} else if (type.equals("BIGINT")
								|| type.equals("FLOAT")
								|| type.equals("SMALLINT")
								|| type.equals("INTEGER")
								|| type.equals("DECIMAL")) {
							transplanlist.setValue(attributename,
									getNumberValue(cell));
						} else if (type.equals("YORN")) {
							transplanlist.setValue(attributename,
									getBoolean(cell));
						} else if (type.equals("DATE")
								|| type.equals("DATETIME")) {
							transplanlist.setValue(attributename,
									getDateValue(cell));
						} else {
							throw new MroException("", "导入失败"
									+ sheet.getSheetName() + "第" + i + "行"
									+ ",第" + j + "列 填写有误，请核查后重新导入");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new MroException("", "导入失败"
								+ sheet.getSheetName() + "第" + i + "行" + ",第"
								+ j + "列 填写有误，请核查后重新导入(注意数据类型)");
					}

				}
			}

		}
		this.getAppBean().SAVE();
		throw new MroException("", "导入成功");//
	}

	/**
	 * 
	 * <功能描述>
	 * 
	 * @param table
	 * @param attributename
	 * @param jpo
	 * @return字段数据类型
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String type(String table, String attributename, IJpo jpo)
			throws MroException {
		IJpoSet ijpoSet = jpo.getJpoSet("$SYS_FIELDCFG", "SYS_FIELDCFG",
				"OBJECTNAME='" + table + "' and FIELDNAME ='" + attributename
						+ "' ");
		IJpo jpos = ijpoSet.getJpo(0);
		String type = jpos.getString("FIELDTYPE");
		return type;

	}

	public void impData(String filename, long doclinksid) throws Exception {
		Workbook workbook = null;

		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);

			if (file.getName().toLowerCase().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else {
				workbook = new HSSFWorkbook(fis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(0);
		if (sheet == null) {
			return;
		} else {
			impExcelnr(workbook, "TRANSPLANLIST", 1);
		}
	}
}
