package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 车型每月的预测/计划数量jpo
 * 
 * @author bchen
 * @version [版本号, 2018-5-9]
 * @since [产品/模块版本]
 */
public class YearRepairPlans extends StatusJpo implements IStatusJpo,
		FixedLoggers {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 删除关联子项
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		
		String plannum = this.getString("PLANNUM");
		IJpoSet repairplansSet = MroServer.getMroServer().getSysJpoSet(
				"REPAIRPLANS", "PLANNUM='" + plannum + "'");
		IJpo repairplans = repairplansSet.getJpo();
		String status = repairplans.getString("STATUS");
		super.delete(flag);
		if (!status.equals("新增")) {
			throw new AppException("repairplans", "notdelete");
		} else {
			if (this.getJpoSet("PRODUCT") != null) {
				this.getJpoSet("PRODUCT").deleteAll();
			}
			if (this.getJpoSet("STOCKARUSLIST") != null) {
				this.getJpoSet("STOCKARUSLIST").deleteAll();
			}
		}
	}
	
	/**
	 * 取消关联删除
	 * @throws MroException
	 */
	@Override
	public void undelete() throws MroException {
		super.undelete();
		if (this.getJpoSet("PRODUCT") != null) {
			this.getJpoSet("PRODUCT").undeleteAll();
		}
		if (this.getJpoSet("STOCKARUSLIST") != null) {
			this.getJpoSet("STOCKARUSLIST").undeleteAll();
		}
	}
	
	/**
	 * 
	 * 复制
	 * @param oldyearRepairPlans
	 * @param newyearRepairPlans
	 * @throws MroException [参数说明]
	 *
	 */
	public void copyTask(IJpo oldyearRepairPlans, IJpo newyearRepairPlans)
			throws MroException {
		String oldjpnum = oldyearRepairPlans.getString("JPNUM");
		String newjpnum = this.getString("JPNUM");
		String plannum = newyearRepairPlans.getString("PLANNUM");
		String[] attrs = { "CMODEL", "REPAIRPROCESS", "SCHEMENUM",
				"PROJECTORDERNO", "PROJECTNAME", "ONE", "TWO", "THREE", "FOUR",
				"FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN",
				"TWELVE", "TOTALAMOUNT", "PROJECTNUM", "PLANAMOUNT", "JXBASE" };
		setValue(oldyearRepairPlans, attrs, attrs,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		setValue("PLANNUM", plannum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		// 复制产品列表
		IJpoSet productset = oldyearRepairPlans.getJpoSet("$PRODUCT",
				"PRODUCT", "jpnum='" + oldjpnum + "'");
		String[] product = { "REPAIRPROCESS", "ITEMNUM", "AMOUNT",
				"PALNAMOUNT", "CMODEL", "ITEM.DESCRIPTION", "PLANNUM",
				"JXCODE", "JXDESC", "PRODUCTCODE", "PRODUCTDESC" };
		IJpoSet newproduct = getJpoSet("product", "product", "1=0");

		for (int i = 0; i < productset.count(); i++) {
			IJpo newJpo = newproduct.addJpo();
			newJpo.setValue(productset.getJpo(i), product, product,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			newJpo.setValue("JPNUM", newjpnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			newJpo.setValue("YEARPLANNUM", plannum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}

		// 复制备料清单列表
		IJpoSet stockaruslistset = oldyearRepairPlans.getJpoSet(
				"$STOCKARUSLIST", "STOCKARUSLIST", "jpnum='" + oldjpnum + "'");
		String[] stockaruslistr = { "REPAIRPROCESS", "ITEMNUM", "STOCKITEMNUM",
				"AMOUNT", "TOTALAMOUNT", "ITEM.DESCRIPTION", "CMODEL",
				"JXOVER.ITEM.DESCRIPTION", "REMARK", "SCHEMEAMOUNT", "PLANNUM",
				"JXCODE", "JXDESC" };
		IJpoSet newstockaruslist = getJpoSet("stockaruslist", "stockaruslist",
				"1=0");
		for (int i = 0; i < stockaruslistset.count(); i++) {
			IJpo newJpo = newstockaruslist.addJpo();
			newJpo.setValue(stockaruslistset.getJpo(i), stockaruslistr,
					stockaruslistr, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			newJpo.setValue("JPNUM", newjpnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			newJpo.setValue("YEARPLANNUM", plannum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
}
