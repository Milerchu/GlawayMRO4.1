package com.glaway.sddq.material.tooltrans.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * <工具交接详细信息Databean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class TooltransBean extends DataBean {
	/**
	 * 过滤库管员名下工具
	 * 
	 * @throws MroException
	 */
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		IJpo mr = getAppBean().getJpo();
		String oldkeeper = mr.getString("OLDKEEPER");
		jpoSet.setUserWhere("KEEPER='" + oldkeeper + "'");
		jpoSet.reset();
	}

	/**
	 * 确认赋值数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	public int execute() throws IOException, MroException {

		IJpoSet tooltrset = getAppBean().getJpo().getJpoSet("DEVICETRANS");

		List<IJpo> list = getAppBean().getDataBean("selectmstmp_select_table")
				.getJpoSet().getSelections();

		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo tooltr = list.get(i);
				IJpo tooltrans = tooltrset.addJpo();

				tooltrans.setValue("TOOLNUM", tooltr.getString("TOOLNUM"));
				tooltrans.setValue("ITEMNUM", tooltr.getString("ITEMNUM"));
				tooltrans.setValue("TRANSQTY", tooltr.getString("QTY"));
				tooltrans.setValue("ASSETNUM", tooltr.getString("ASSETNUM"));
				tooltrans.setValue("ASSETNUM", tooltr.getString("ASSETNUM"));
				tooltrans.setValue("OLDKEEPER", tooltr.getString("KEEPER"));
			}
		}

		return super.execute();

	}

}
