package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <故障件返修字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldFrepairnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "frepairnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("frepair");
		String listSql = "";
		listSql = "status!='已完成' ";
		IJpoSet thisSet = this.getJpo().getJpoSet();
		String frepairnums = null;
		if (!thisSet.isEmpty()) {
			for (int i = 0; i < thisSet.count(); i++) {
				if (thisSet.getJpo(i) == null) {
					continue;
				}
				String frepairnum = thisSet.getJpo(i).getString(
						this.getFieldName());
				if (!"".equals(frepairnum)) {
					if (frepairnums == null)
						frepairnums = "'"
								+ StringUtil.getSafeSqlStr(frepairnum) + "'";
					else
						frepairnums = frepairnums + ",'"
								+ StringUtil.getSafeSqlStr(frepairnum) + "'";
				}
			}
		}
		if (frepairnums != null) {
			listSql = listSql + "and  frepairnum not in (" + frepairnums + ")";
		}

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}

	/**
	 * 触发赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String frepairnum = this.getValue();
		IJpoSet frepairset = MroServer.getMroServer().getJpoSet("frepair",
				MroServer.getMroServer().getSystemUserServer());
		frepairset.setQueryWhere("frepairnum='" + frepairnum + "'");
		frepairset.reset();
		String sqn = frepairset.getJpo(0).getString("sqn");
		String itemnum = frepairset.getJpo(0).getString("itemnum");
		String assetnum = frepairset.getJpo(0).getString("assetnum");
		IJpoSet EXCHANGERECORDset = MroServer.getMroServer().getJpoSet(
				"EXCHANGERECORD",
				MroServer.getMroServer().getSystemUserServer());
		EXCHANGERECORDset
				.setQueryWhere("taskordernum in (select ORDERNUM from workorder where status='处理中') and sqn='"
						+ sqn + "'");
		EXCHANGERECORDset.reset();
		String EXCHANGERECORDID = EXCHANGERECORDset.getJpo(0).getString(
				"EXCHANGERECORDID");
		String taskordernum = EXCHANGERECORDset.getJpo(0).getString(
				"taskordernum");
		String REPAIRPROCESS = EXCHANGERECORDset.getJpo(0).getString(
				"REPAIRPROCESS");
		String FAILURENUM = EXCHANGERECORDset.getJpo(0).getString("FAILURENUM");
		this.getJpo().setValue("TASKNUM", taskordernum);
		this.getJpo().setValue("assetnum", assetnum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("itemnum", itemnum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("sqn", sqn,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("REPAIRPROCESS", REPAIRPROCESS,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("FAILURENUM", FAILURENUM,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("EXCHANGERECORDID", EXCHANGERECORDID,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("ORDERQTY", 1,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("ORDERQTY", GWConstant.S_READONLY, true);
		frepairset.getJpo(0).setValue("transfernum",
				this.getJpo().getString("transfernum"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		frepairset.save();

	}

}
