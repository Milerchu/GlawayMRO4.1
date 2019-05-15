package com.glaway.sddq.overhaul.plan.data;

import java.util.Calendar;

import com.alibaba.druid.util.StringUtils;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修计划Jpo
 * 
 * @author hyhe
 * @version [版本号, 2017-10-19]
 * @since [产品/模块版本]
 */
public class RepairPlan extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		String appName = this.getAppName();
		if (this.isNew()) {
			if (!StringUtils.isEmpty(appName) && appName.equals("YEARPLAN")) {
				this.setValue("TYPE", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (!StringUtils.isEmpty(appName) && appName.equals("MONTHPLAN")) {
				this.setValue("TYPE", "2",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		if (!StringUtils.isEmpty(appName) && appName.equals("YEARPLAN")) {
			try {
				IJpoSet jposet = this.getJpoSet("YEARREPAIRPLANS");
				if (jposet != null && jposet.count() > 0) {
					IJpo jpo = jposet.getJpo(0);
					int one = jpo.getInt("ONE");
					int two = jpo.getInt("TWO");
					int three = jpo.getInt("THREE");
					int four = jpo.getInt("FOUR");
					int five = jpo.getInt("FIVE");
					int six = jpo.getInt("SIX");
					int seven = jpo.getInt("SEVEN");
					int eight = jpo.getInt("EIGHT");
					int nine = jpo.getInt("NINE");
					int ten = jpo.getInt("TEN");
					int eleven = jpo.getInt("ELEVEN");
					int twelve = jpo.getInt("TWELVE");
					int sum = one + two + three + four + five + six + seven
							+ eight + nine + ten + eleven + twelve;
					this.setValue("TOTALNUM", sum, GWConstant.P_NOUPDATE);
				} else {
					this.setValue("TOTALNUM", 0, GWConstant.P_NOUPDATE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (!this.getString("STATUS").equals("新增")
					&& !WfControlUtil.isCurUser(this)) {
				this.setNoFieldFlag(new String[] { "TYPE" },
						GWConstant.S_READONLY, true);
				if (this.getString("TYPE").equals("1")) {
					// this.getJpoSet("YEARREPAIRPLANS").setFlag(GWConstant.S_READONLY,
					// true);
					IJpoSet yearrepairplansset = this
							.getJpoSet("YEARREPAIRPLANS");
					yearrepairplansset.setFlag(GWConstant.S_READONLY, true);
//					if (!yearrepairplansset.isEmpty()) {
//						String[] readonlyatt = { "CMODEL", "REPAIRPROCESS",
//								"SCHEMENUM", "PROJECTORDERNO", "PROJECTNAME",
//								"TOTALAMOUNT" };
//						for (int i = 0; i < yearrepairplansset.count(); i++) {
//							yearrepairplansset.getJpo(i).setFieldFlag(
//									readonlyatt, GWConstant.S_READONLY, true);
//						}
//					}
				}
				if (this.getString("TYPE").equals("2")) {
					this.getJpoSet("REPAIRPLANSCOPE").setFlag(
							GWConstant.S_READONLY, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IJpo duplicate() throws MroException {
		IJpo newjp = super.duplicate();
		newjp.setValue("status", "新增", GWConstant.P_NOVALIDATION_AND_NOACTION);
		newjp.setValue("creatby", newjp.getUserInfo().getUserName(),
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		newjp.setValue("createdate", MroServer.getMroServer().getDate(),
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		//String[] attr = { "PLANNUM" };
		//String[] val = { newjp.getString("PLANNUM") };
		if (this.getString("type").equals("1")) {
			// 复制年度计划的每月的预测/计划数量
			IJpoSet yearRepairPlansSet = this.getJpoSet(
					"$YEARREPAIRPLANS",
					"YEARREPAIRPLANS",
					"PLANNUM='"
							+ StringUtil.getSafeSqlStr(getString("PLANNUM"))
							+ "'");

		     if (!yearRepairPlansSet.isEmpty() && yearRepairPlansSet.count() > 0)
		        {
		    	 IJpoSet newYearRepairPlansSet = newjp.getJpoSet(
							"YEARREPAIRPLANS", "YEARREPAIRPLANS", "1=0");
		        	for (int index = 0;index < yearRepairPlansSet.count();index++){
		        		IJpo yearRepairPlans = yearRepairPlansSet.getJpo(index);
		        		YearRepairPlans newyearrepairplans = (YearRepairPlans) newYearRepairPlansSet.addJpo();
		        		newyearrepairplans.copyTask(yearRepairPlans,newjp);
		        	}													
		        	/*for (int index = 0; index < yearRepairPlansSet.count(); index++) {
					IJpo yearrepair = yearRepairPlansSet.getJpo(index);

					String oldjpnum = yearRepairPlansSet.getJpo(index)
							.getString("JPNUM");
					String jpnum = newYearRepairPlansSet.getJpo(index)
							.getString("JPNUM");

					//String[] attrs = { "JPNUM" };
					//String[] vals = { jpnum };
					IJpoSet productset = yearrepair.getJpoSet("$PRODUCT",
							"PRODUCT", "jpnum='" + oldjpnum + "'");
					String[] product = { "REPAIRPROCESS", "ITEMNUM",
							"AMOUNT","PALNAMOUNT","MODELS.MODELDESC","ITEM.DESCRIPTION"};
					IJpoSet newproduct = getJpoSet("product", "product", "1=0");
					
					 * // 复制产品列表 if (!productset.isEmpty() && productset.count()
					 * > 0) {
					 * 
					 * IJpoSet newproductsetSet = newjp.getJpoSet("product",
					 * "product", "1=0"); newproductsetSet.duplicate(productset,
					 * GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					 * newproductsetSet.updateAll(attrs, vals); }
					 

					for (int i = 0; i < productset.count(); i++) {
						IJpo newJpo = newproduct.addJpo();
						newJpo.setValue(productset.getJpo(i), product, product,
								GWConstant.P_NOVALIDATION);
						newJpo.setValue("JPNUM", jpnum,
								GWConstant.P_NOVALIDATION);
					}

					IJpoSet stockaruslistset = yearrepair.getJpoSet(
							"$STOCKARUSLIST", "STOCKARUSLIST", "jpnum='"
									+ oldjpnum + "'");
					String[] stockaruslistr = { "REPAIRPROCESS", "ITEMNUM",
							"STOCKITEMNUM","AMOUNT","TOTALAMOUNT","ITEM.DESCRIPTION","MODELS.MODELDESC"
							,"JXOVER.ITEM.DESCRIPTION"};
					IJpoSet newstockaruslist = getJpoSet("stockaruslist",
							"stockaruslist", "1=0");
					// 复制备料清单列表
					for (int i = 0; i < stockaruslistset.count(); i++) {
						IJpo newJpo = newstockaruslist.addJpo();
						newJpo.setValue(stockaruslistset.getJpo(i), stockaruslistr,
								stockaruslistr, GWConstant.P_NOVALIDATION);
						newJpo.setValue("JPNUM", jpnum,
								GWConstant.P_NOVALIDATION);
					}
				}*/
			}
		}
		return newjp;
	}

	/**
	 * 
	 * 版本变更历史
	 * 
	 * @param newversion
	 * @param memo
	 * @param newjpo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changeVersion(String newversion, String memo, IJpo newjpo)
			throws MroException {
		if (!newversion.equals("")) {
			String oldversion = getString("version");
			IJpoSet versionset = getJpoSet("YEARPLANVERSIONHISTROY");
			IJpo versionnew = versionset.addJpo();
			versionnew.setValue("version", oldversion,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			versionnew.setValue("newversion", newversion,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			versionnew.setValue("REMARK", memo,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			versionnew.setValue("PLANNUM", getString("PLANNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			versionnew.setValue("NEWPLANNUM", newjpo.getString("PLANNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			versionnew.setFlag(GWConstant.S_READONLY, true);
		} else {
			throw new AppException("yearplanversionhistroy", "version");
		}
	}

	@Override
	public void delete(long flag) throws MroException {
		if (this.getJpoSet("YEARPLANVERSIONHISTROY") != null) {
			this.getJpoSet("YEARPLANVERSIONHISTROY").deleteAll(flag);
		}
		if (this.getJpoSet("YEARREPAIRPLANS") != null) {
			this.getJpoSet("YEARREPAIRPLANS").deleteAll(flag);
		}
		if (this.getJpoSet("REPAIRPLANSCOPE") != null) {
			this.getJpoSet("REPAIRPLANSCOPE").deleteAll(flag);
		}
		super.delete(flag);
	}

	@Override
	public void add() throws MroException {
		super.add();
		// 当前年份
		int year = Calendar.getInstance().get(Calendar.YEAR);
		// 当前月份
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String plannum = this.getString("plannum");
		String appName = this.getAppName();
		// 年度计划自动编号
		if ("YEARPLAN".equalsIgnoreCase(appName)) {
			String plannums = "JH" + "-" + year + "-" + plannum;
			this.setValue("PLANNUM", plannums);
		} else {
			// 月度计划自动编号
			String plannums = "JH" + "-" + year + "-" + month + "-" + plannum;
			this.setValue("PLANNUM", plannums);
		}
	}

	/*
	 * public IJpo dumplicateAdd() throws MroException { super.add(); // 当前年份
	 * //int year = Calendar.getInstance().get(Calendar.YEAR); // 当前月份 //int
	 * month = Calendar.getInstance().get(Calendar.MONTH) + 1; int plannum =
	 * this.getInt("plannum"); String appName = this.getAppName(); // 年度计划自动编号
	 * if ("YEARPLAN".equalsIgnoreCase(appName)) { int plannums = plannum+1;
	 * this.setValue("PLANNUM", plannums); } else { // 月度计划自动编号 int plannums =
	 * plannum+1; this.setValue("PLANNUM", plannums); } return this; }
	 */
}
