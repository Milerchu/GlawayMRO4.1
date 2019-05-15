package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 缴库单选择送修单行dataBean
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-9]
 * @since [GlawayMro4.0/选择送修单行]
 */
public class SelectRepairlineBean extends DataBean {

	/**
	 * 非制单人不可增加行
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public int execute() throws MroException {
		IJpo transferJpo = getAppBean().getJpo();//
		if (!transferJpo.getString("CREATEBY").equals(
				getUserInfo().getLoginID()))
			throw new MroException("非制单人不可新增行");//
		if (transferJpo != null) {
			List<IJpo> list = this.getJpoSet().getSelections();
			IJpoSet tlJpoSet = transferJpo.getJpoSet("TRANSFERLINES");
			for (IJpo jpo : list) {
				int maxnum = (int) tlJpoSet.max("TRANSFERLINENUM");
				IJpo cjpo = jpo.duplicate();
				cjpo.setValue("TRANSFERLINENUM", maxnum + 10);
				cjpo.setValue("TRANSFERNUM",
						transferJpo.getString("TRANSFERNUM"));
				cjpo.setValue("YJSQTY", 0);
				cjpo.setValue("status", "未接收", 112L);
				cjpo.setValue("passqty", transferJpo.getDouble("ORDERQTY"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		try {
			this.getAppBean().SAVE();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	public boolean isRepeat(String transferlinenum, IJpoSet tlJpoSet)
			throws MroException {
		boolean r = false;
		for (int i = 0; i < tlJpoSet.count(); i++) {
			IJpo tlJpo = tlJpoSet.getJpo(i);
			if (StringUtil.isEqual(tlJpo.getString("TRANSFERLINENUM"),
					transferlinenum)) {
				return true;
			}
		}
		return r;
	}
}
