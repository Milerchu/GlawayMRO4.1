package com.glaway.sddq.material.toolapplication.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Button;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具设备申请行Databean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class TrlineDataBean extends DataBean {
	/**
	 * 初始化控制页面显隐
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {

		if (getJpo() != null) {
			IJpo parent2 = this.getJpo().getParent();
			String createby = parent2.getString("CREATEBY");
			String status = parent2.getString("STATUS");

			if (status.equals("待审批")
					&& (!createby.equalsIgnoreCase(this.getUserInfo()
							.getUserName()))) {
				IJpoSet trlineTmp = getJpoSet();
				for (int j = 0; j < trlineTmp.count(); j++) {
					IJpo trline = trlineTmp.getJpo(j);
					trline.setFieldFlag(new String[] { "ITEMNUM", "USE",
							"UNITPRICE", "REQQTY", "MEMO" },
							GWConstant.S_READONLY, true);
					// trline.setFlag(GWConstant.S_READONLY, true);
				}

				try {
					Button b1 = ((Button) this.getPage().getControlByXmlId(
							"1522225697076"));
					if (b1 != null) {
						// this.getPage().getControlByXmlId("1522225697076").hide();
						b1.setDisable(true);
						b1.loadData();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		super.initialize();
	}

	/**
	 * 删除行方法
	 * 
	 * @throws MroException
	 */
	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		if (getJpo() != null) {
			IJpo parent2 = this.getJpo().getParent();
			String createby = parent2.getString("CREATEBY");
			String status = parent2.getString("STATUS");
			if (status.equals("待审批")
					&& (!createby.equalsIgnoreCase(this.getUserInfo()
							.getUserName()))) {
				// showOperInfo("", "当前记录不可删除");
				throw new AppException("提示", "当前记录不可删除");
			} else {
				super.delete();
			}
		}
	}
}
