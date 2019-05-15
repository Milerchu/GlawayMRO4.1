package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨单确认接收DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class TransferlineStatusDataBean extends DataBean {
	/**
	 * 确认接收变更单据状态
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			double statuswjsqty = this.getJpo().getDouble("wjsqty");
			double statusjsqty = this.getJpo().getDouble("jsqty");
			double YJSQTY = parent.getDouble("YJSQTY");
			double newyjsqty = YJSQTY + statusjsqty;
			if (statusjsqty > statuswjsqty) {
				throw new MroException("transferline", "qty");
			} else {
				parent.setValue("YJSQTY", newyjsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (statuswjsqty == statusjsqty) {
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					parent.setValue("status", "部分接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}

		}
		this.getAppBean().SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

}
