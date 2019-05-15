package com.glaway.sddq.service.chasset.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 故障工单-串换记录表 databean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月26日]
 * @since [产品/模块版本]
 */
public class ChAssetDataBean extends DataBean {

	@Override
	public void toggleEditRow() throws MroException, IOException {
		super.toggleEditRow();
		if (getBoolean("isdone")) {// 串换已经完成，记录只读
			getJpo().setFlag(GWConstant.S_READONLY, true);
		} else {

			// 串换类型
			String chtype = getString("chtype");
			// 同车互换
			if (SddqConstant.SWAP_SAMETRAIN.equals(chtype)) {

				// 设置串换车号只读
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_READONLY, true);

			} else {
				// 两车互换
				// 设置串换车号只读取消
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_READONLY,
						false);

			}

			// 设置必填
			String[] requiredAttrs = { "BEFOREITEMNUM", "AFTERITEMNUM" };
			getJpo().setFieldFlag(requiredAttrs, GWConstant.S_REQUIRED, true);
		}
	}

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.addEditRowCallBackOk();
	}

	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		super.delete();
	}

	@Override
	public synchronized void undelete() throws MroException {
		// TODO Auto-generated method stub
		super.undelete();
	}

}
