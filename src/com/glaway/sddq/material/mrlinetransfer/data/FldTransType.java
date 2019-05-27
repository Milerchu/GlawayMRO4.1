package com.glaway.sddq.material.mrlinetransfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <配件申请项目经理选择处置方式字段类，如果为计划，则计划数量必填>
 * 
 * @author public2795
 * @version [版本号, 2018-9-3]
 * @since [产品/模块版本]
 */
public class FldTransType extends JpoField {
	/**
	 * 获取处置方式列表内容
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		String datatype = getJpo().getString("datatype");
		if (datatype.equalsIgnoreCase("1")) {// 库房级别-中心库
			double jhjlqty = 0.00;
			String thisitemnum = this.getJpo().getString("itemnum");
			IJpoSet thisjposet = this.getJpo().getJpoSet();
			for (int i = 0; i < thisjposet.count(); i++) {
				String itemnum = thisjposet.getJpo(i).getString("itemnum");
				String transtype = thisjposet.getJpo(i).getString("transtype");
				if (itemnum.equalsIgnoreCase(thisitemnum)
						&& transtype
								.equalsIgnoreCase(ItemUtil.TRANSTYPE_JHJLXT)) {
					jhjlqty += thisjposet.getJpo(i).getDouble("transferqty");
				}
			}
			if (jhjlqty > 0) {
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
						"domainid='LINETRANSTYPE'");
				domainSet.setUserWhere("VALUE in ('" + ItemUtil.TRANSTYPE_NBXT
						+ "')");// 过滤-处置方式为内部协调，计划经理协调
				domainSet.reset();
				return domainSet;
			} else {
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
						"domainid='LINETRANSTYPE'");
				domainSet.setUserWhere("VALUE in ('" + ItemUtil.TRANSTYPE_NBXT
						+ "','" + ItemUtil.TRANSTYPE_JHJLXT + "')");// 过滤-处置方式为内部协调，计划经理协调
				domainSet.reset();
				return domainSet;
			}

		}
		if (datatype.equalsIgnoreCase("2")) {
			double xjhqty = 0.00;
			double fxhfyqty = 0.00;
			String thisitemnum = this.getJpo().getString("itemnum");
			String selecttype = this.getJpo().getString("selecttype");
			IJpoSet thisjposet = this.getJpo().getJpoSet();
			for (int i = 0; i < thisjposet.count(); i++) {
				String itemnum = thisjposet.getJpo(i).getString("itemnum");
				String transtype = thisjposet.getJpo(i).getString("transtype");
				if (itemnum.equalsIgnoreCase(thisitemnum)
						&& transtype.equalsIgnoreCase(ItemUtil.TRANSTYPE_XDJH)) {
					xjhqty += thisjposet.getJpo(i).getDouble("transferqty");
				}
			}
			if (xjhqty > 0) {
				for (int i = 0; i < thisjposet.count(); i++) {
					String itemnum = thisjposet.getJpo(i).getString("itemnum");
					String transtype = thisjposet.getJpo(i).getString(
							"transtype");
					if (itemnum.equalsIgnoreCase(thisitemnum)
							&& transtype
									.equalsIgnoreCase(ItemUtil.TRANSTYPE_FXHFY)) {
						fxhfyqty += thisjposet.getJpo(i).getDouble(
								"transferqty");
					}
				}
				if (fxhfyqty > 0) {/* 无下计划，无返修后发运 */
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='LINETRANSTYPE'");
					if (selecttype.equalsIgnoreCase("匹配")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','退回申请人','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					if (selecttype.equalsIgnoreCase("新增")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					domainSet.reset();
					return domainSet;
				} else {/* 无下计划，有返修发运 */
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='LINETRANSTYPE'");
					if (selecttype.equalsIgnoreCase("匹配")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_FXHFY + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','退回申请人','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					if (selecttype.equalsIgnoreCase("新增")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_FXHFY + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					domainSet.reset();
					return domainSet;
				}
			} else {
				for (int i = 0; i < thisjposet.count(); i++) {
					String itemnum = thisjposet.getJpo(i).getString("itemnum");
					String transtype = thisjposet.getJpo(i).getString(
							"transtype");
					if (itemnum.equalsIgnoreCase(thisitemnum)
							&& transtype
									.equalsIgnoreCase(ItemUtil.TRANSTYPE_FXHFY)) {
						fxhfyqty += thisjposet.getJpo(i).getDouble(
								"transferqty");
					}
				}
				if (fxhfyqty > 0) {/* 有下计划，无返修发运 */
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='LINETRANSTYPE'");
					if (selecttype.equalsIgnoreCase("匹配")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_XDJH + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','退回申请人','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					if (selecttype.equalsIgnoreCase("新增")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_XDJH + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					domainSet.reset();
					return domainSet;
				} else {/* 有下计划，有返修发运 */
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='LINETRANSTYPE'");
					if (selecttype.equalsIgnoreCase("匹配")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_FXHFY + "','"
								+ ItemUtil.TRANSTYPE_XDJH + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','退回申请人','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					if (selecttype.equalsIgnoreCase("新增")) {
						domainSet.setUserWhere("VALUE in ('"
								+ ItemUtil.TRANSTYPE_XCDB + "','"
								+ ItemUtil.TRANSTYPE_FXHFY + "','"
								+ ItemUtil.TRANSTYPE_XDJH + "','"
								+ ItemUtil.TRANSTYPE_ZXKDB
								+ "','计划交付后发货','中心库调拨后下达计划')");// 过滤-处置方式为现场调拨，下达计划，返修后发运
					}
					domainSet.reset();
					return domainSet;
				}
			}
		} else {
			return super.getList();
		}

	}

	/**
	 * 触发控制页面字段赋值，只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String datatype = getJpo().getString("datatype");
		String transtype = this.getValue();
		double NOQTY = getJpo().getDouble("NOQTY");
		if (datatype.equalsIgnoreCase("1")) {
			if (transtype.equalsIgnoreCase(ItemUtil.TRANSTYPE_NBXT)) {// 处置方式为内部协调
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);
			}
			if (transtype.equalsIgnoreCase(ItemUtil.TRANSTYPE_JHJLXT)) {// 处置方式为计划经理协调
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, true);
			}
		}
		if (datatype.equalsIgnoreCase("2")) {
			if (transtype.equalsIgnoreCase("下达计划")) {
				this.getJpo().setFieldFlag("jhqty", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_REQUIRED, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (transtype.equalsIgnoreCase("中心库调拨")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			}
			if (transtype.equalsIgnoreCase("中心库调拨后下达计划")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_REQUIRED, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (transtype.equalsIgnoreCase("现场调拨")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (transtype.equalsIgnoreCase("计划交付后发货")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (transtype.equalsIgnoreCase("返修后发运")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (transtype.equalsIgnoreCase("退回申请人")) {
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("SAPPONUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("jhqty", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("jhqty", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("jhqty", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("STOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						true);

				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSFERQTY", NOQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("REMARK", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("REMARK", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
						true);
			}
		}
	}

	/**
	 * 无校验
	 * 
	 * @throws MroException
	 */
	@Override
	public void validate() throws MroException {
		// TODO Auto-generated method stub
		// super.validate();
	}

}
