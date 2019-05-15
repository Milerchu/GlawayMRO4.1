package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaway.mro.app.system.doclinks.bean.AddDoclinkBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;


public class ImpJobTestRecordIngBean extends AddDoclinkBean {

	public int uploadfile() throws MroException, IOException {
		try {

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
				impData(tmpFile, fileName);
				tmpFile.delete();
			}
			dialogclose();
		} catch (Exception e) {
			mroSession.getRequest().getSession()
					.setAttribute("isSuccess", "uploadfileSuccess");
			dialogclose();
			throw new MroException(e.getMessage());
		}
		mroSession.getRequest().getSession()
				.setAttribute("isSuccess", "uploadfileSuccess");
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
	public void impData(File tmpFile, String filename) throws Exception {
		Workbook workbook = null;

		try {
			FileInputStream fis = new FileInputStream(tmpFile);

			if (filename.toLowerCase().endsWith(".xlsx")) {
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
			impExcelnr(workbook, "JOBTESTRECORDING", 1);
		}
	}

	private void impExcelnr(Workbook workbook, String table, int hang)
			throws Exception {
		IJpo jpo = this.getAppBean().getJpo();
		IJpoSet jobtestrecordingSet = jpo
				.getJpoSet("$jobtestrecording", table, "1=2");
		IJpoSet ijpoSet = jpo.getJpoSet("$IMPATTRIBUTE", "IMPATTRIBUTE",
				"ifacename='JOBTESTRECORDINGIMP'");
		ijpoSet.setOrderBy("EXCELCOLNUM asc");
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int zs = 0; zs < numberOfSheets; zs++) {
			Sheet sheet = workbook.getSheetAt(zs);
			int lastRowNum = sheet.getLastRowNum();
			int rsRows = sheet.getLastRowNum() + 1;
			// String name = sheet.getSheetName();
			// int cloum = sheet.getPhysicalNumberOfRows();// 总列数
			for (int i = hang; (i < rsRows) && (lastRowNum > 0); ++i) {// 从第几行开始
				IJpo jobtestrecording = jobtestrecordingSet.addJpo();
				Row row = sheet.getRow(i);
				for (int j = 0; j < ijpoSet.count(); j++) {
					try {
						IJpo ijpo = ijpoSet.getJpo(j);
						int index = ijpo.getInt("EXCELCOLNUM");// 序号
						Cell cell = row.getCell(index);// 获取每一行各列数据
						String attributename = ijpo.getString("attributename");
						String type = type(table, attributename, jpo);

						jobtestrecording.setValue(attributename,// 给字段赋值并区分类型
								getStringByCell(cell));
//						if (type.equals("ALN") || type.equals("UPPER")
//								|| type.equals("LOWER")) {
//							jobtestrecording.setValue(attributename,// 给字段赋值并区分类型
//									getStringByCell(cell));
//						} else if (type.equals("BIGINT")
//								|| type.equals("FLOAT")
//								|| type.equals("SMALLINT")
//								|| type.equals("DECIMAL")) {
//							jobtestrecording.setValue(attributename,
//									getNumberValue(cell));
//						} else if (type.equals("INTEGER")) {
//							jobtestrecording.setValue(attributename,
//									cell.get);
//						} else if (type.equals("YORN")) {
//							jobtestrecording.setValue(attributename,
//									getBoolean(cell));
//						} else if (type.equals("DATE")
//								|| type.equals("DATETIME")) {
//							jobtestrecording.setValue(attributename,
//									getDateValue(cell));
//						} else {
//							throw new MroException("", "导入失败"
//									+ sheet.getSheetName() + "第" + i + "行"
//									+ ",第" + j + "列 填写有误，请核查后重新导入");
//						}
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
	
	public String type(String table, String attributename, IJpo jpo)
			throws MroException {
		IJpoSet ijpoSet = jpo.getJpoSet("$SYS_FIELDCFG", "SYS_FIELDCFG",
				"OBJECTNAME='" + table + "' and FIELDNAME ='" + attributename
						+ "' ");
		IJpo jpos = ijpoSet.getJpo(0);
		String type = jpos.getString("FIELDTYPE");
		return type;

	}

}
