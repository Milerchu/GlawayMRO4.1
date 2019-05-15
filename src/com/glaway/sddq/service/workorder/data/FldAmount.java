package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 耗损件记录表 amount字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-9]
 * @since [产品/模块版本]
 */
public class FldAmount extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 4718399549404143767L;

	@Override
	public void action() throws MroException {
		super.action();
		// 输入值
		int input = this.getInputMroType().asInt();
		if (input == 0) {
			throw new AppException("", "请输入正确的上车数量！");
		}
		// 库存
		int inventory = getJpo().getInt("INVENTORY");
		if (inventory != 0) {

			if (input > inventory) {
				throw new AppException("losspart", "notenoughinv");
			}
		}

		// 跟可上车数量比较
		String assetnum = this.getJpo().getString("DOWNASSETNUM");
		String itemnum = this.getJpo().getString("DOWNITEMNUM");
		String lotnum = this.getJpo().getString("DOWNLOTNUM");

		IJpoSet assetpSet = MroServer.getMroServer().getSysJpoSet("ASSETPART");
		String where = "assetnum='" + assetnum + "' and itemnum='" + itemnum
				+ "'";
		if (StringUtil.isStrNotEmpty(lotnum)) {
			where += " and lotnum='" + lotnum + "'";
		}
		assetpSet.setUserWhere(where);
		assetpSet.reset();
		if (assetpSet != null && assetpSet.count() > 0) {

			int canDownCount = assetpSet.getJpo(0).getField("QTY")
					.getIntValue();
			if (input > canDownCount) {
				throw new AppException("losspart", "outofqtyintrain");
			}

		}

	}
}
