package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨单状态字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {
	/**
	 * 根据状态触发赋值编号到序列号表
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String status = this.getValue();
		String type = this.getJpo().getString("type");
		IJpoSet transferlineset = this.getJpo().getJpoSet("TRANSFERLINE");
		if (!transferlineset.isEmpty()) {
			if (status.equalsIgnoreCase("在途")) {// 设置周转件关联的送修单，装箱单，在故障件返修中显现
				for (int i = 0; i < transferlineset.count(); i++) {
					if (transferlineset.getJpo(i) == null) {
						continue;
					}
					IJpo transferline = transferlineset.getJpo(i);
					String assetnum = transferline.getString("assetnum");
					String transfernum = transferline.getString("transfernum");
					if (!assetnum.equalsIgnoreCase("")) {
						IJpoSet assetset = MroServer.getMroServer()
								.getSysJpoSet("asset");
						assetset.setUserWhere("assetnum='" + assetnum + "'");
						if (!assetset.isEmpty()) {
							IJpo asset = assetset.getJpo(0);
							if (type.equalsIgnoreCase("SX")) {
								asset.setValue("sxdtransfernum", transfernum,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (type.equalsIgnoreCase("ZXD")) {
								asset.setValue("zxdtransfernum", transfernum,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						assetset.save();

					}
				}
			}
		}
	}

}
