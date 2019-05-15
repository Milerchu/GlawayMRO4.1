package com.glaway.sddq.material.mrline.bean;

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

/**
 * 
 * <配件申请行导入数据DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ImpMrlineDean extends AddDoclinkBean {
	/**
	 * 上传文件方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
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

	/**
	 * 
	 * <导入数据方法>
	 * 
	 * @param tmpFile
	 * @param filename
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
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
			impExcelnr(workbook, "MRLINE", 1);
		}
	}

	private void impExcelnr(Workbook workbook, String table, int hang)
			throws Exception {
		IJpo jpo = this.getAppBean().getJpo();
		String mrnum = jpo.getString("mrnum");
		String MODEL = jpo.getString("MODEL");
		String PROJECT = jpo.getString("PROJECT");
		IJpoSet mrlineSet = jpo.getJpoSet("$MATERIALLIST", table, "1=2");
		IJpoSet ijpoSet = jpo.getJpoSet("$IMPATTRIBUTE", "IMPATTRIBUTE",
				"ifacename='MRLINE'");
		ijpoSet.setOrderBy("EXCELCOLNUM asc");
		boolean flg = true;
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int zs = 0; zs < numberOfSheets; zs++) {
			Sheet sheet = workbook.getSheetAt(zs);
			int lastRowNum = sheet.getLastRowNum();
			int rsRows = sheet.getLastRowNum() + 1;
			for (int i = hang; (i < rsRows) && (lastRowNum > 0); ++i) {// 从第几行开始
				Row row = sheet.getRow(i);
				for (int j = 0; j < ijpoSet.count(); j++) {
					try {
						IJpo ijpo = ijpoSet.getJpo(j);
						int index = ijpo.getInt("EXCELCOLNUM");// 序号
						Cell cell = row.getCell(index);// 获取每一行各列数据

						String attributename = ijpo.getString("attributename");
						if ("ITEMNUM".equals(attributename)) {
							String itemnum = getStringByCell(cell);
							IJpoSet itemset = jpo.getJpoSet("$item",
									"sys_item", "itemnum='" + itemnum + "'");
							if (itemset.isEmpty()) {
								flg = false;
								throw new MroException("物料编码：" + itemnum
										+ "在系统中不存在,请核查");
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new MroException("", "导入失败"
								+ sheet.getSheetName() + "第" + i + "行" + ",第"
								+ j + "列 填写有误，请核查后重新导入(注意数据类型)"
								+ e.getMessage());
					}

				}
			}

		}
		if (flg) {
			for (int zs = 0; zs < numberOfSheets; zs++) {
				Sheet sheet = workbook.getSheetAt(zs);
				int lastRowNum = sheet.getLastRowNum();
				int rsRows = sheet.getLastRowNum() + 1;
				for (int i = hang; (i < rsRows) && (lastRowNum > 0); ++i) {// 从第几行开始
					IJpo mrline = mrlineSet.addJpo();
					mrline.setValue("mrnum", mrnum,
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					mrline.setValue("datetype", "导入",
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					mrline.setValue("MODEL", MODEL,
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					mrline.setValue("PROJECT", PROJECT,
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					Row row = sheet.getRow(i);
					for (int j = 0; j < ijpoSet.count(); j++) {
						IJpo ijpo = ijpoSet.getJpo(j);
						int index = ijpo.getInt("EXCELCOLNUM");// 序号
						Cell cell = row.getCell(index);// 获取每一行各列数据

						String attributename = ijpo.getString("attributename");
						if ("ISSOFT".equals(attributename)) {
							String ISSOFT = getStringByCell(cell);
							if (ISSOFT.isEmpty()
									|| ISSOFT.equalsIgnoreCase("N")) {
								mrline.setValue("ISSOFT", 0,
										GWConstant.P_NOVALIDATION_AND_NOACTION);
							}
							if (ISSOFT.equalsIgnoreCase("Y")) {
								mrline.setValue("ISSOFT", 1,
										GWConstant.P_NOVALIDATION_AND_NOACTION);
							}

						}
						mrline.setValue(
								attributename,// 给字段赋值并区分类型
								getStringByCell(cell),
								GWConstant.P_NOVALIDATION_AND_NOACTION);
					}
				}

			}
		}
		this.getAppBean().SAVE();
		throw new MroException("", "导入成功");//
	}

}
