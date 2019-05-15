package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.TransLineInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransLineStoroomCommon;

/**
 * 
 * <送修单接收弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class SxTransferlineStatusDataBean extends DataBean {
	/**
	 * 确认接收按钮
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
			String transfernum = parent.getString("transfernum");
			String transferlineid = parent.getString("transferlineid");
			if (statusjsqty > statuswjsqty) {
				throw new MroException("transferline", "qty");
			} else {
				// 调用公共入库方法
				TransLineStoroomCommon.in_storoom(parent, statusjsqty);
				// 调用公共入库记录方法
				TransLineInvtranscommon.in_invtrans(parent, statusjsqty);
				parent.setValue("YJSQTY", newyjsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (statuswjsqty == statusjsqty) {
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet transferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					transferlineset.setQueryWhere("transfernum='" + transfernum
							+ "' and status!='已接收' and transferlineid!='"
							+ transferlineid + "'");
					if (transferlineset.isEmpty()) {
						parent.getParent().setValue("status", "已接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
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
