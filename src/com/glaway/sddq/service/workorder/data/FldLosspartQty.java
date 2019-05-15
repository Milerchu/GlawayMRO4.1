package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 耗损件记录 qty字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class FldLosspartQty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		IJpo parent = getJpo().getParent();
		if (parent != null) {
			if (getJpo() != null) {
				setValue(getOnCarCount(), GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}

	/**
	 * 计算可下车数量
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private int getOnCarCount() throws MroException {
		String downItem = getJpo().getString("DOWNITEMNUM");
		String orderNum = getJpo().getString("JXTASKNUM");
		if (downItem.isEmpty()) {
			return 0;
		}
		// 根据当前物料、当前工单计算出还能上车的数量
		IJpoSet thisSet = MroServer.getMroServer().getSysJpoSet(
				"JXTASKLOSSPART");
		thisSet.setQueryWhere("DOWNITEMNUM='" + downItem + "' and JXTASKNUM='"
				+ orderNum + "'");
		thisSet.reset();
		int readyDownCount = 0;
		for (int i = 0; i < thisSet.count(); i++) {
			int oneCount = thisSet.getJpo(i).getInt("AMOUNT");
			readyDownCount += oneCount;
		}
		int onCarCount = getJpo().getInt("ASSETPART.QTY");
		return onCarCount - readyDownCount;
	}
}
