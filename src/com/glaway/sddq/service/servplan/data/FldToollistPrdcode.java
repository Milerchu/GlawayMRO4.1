package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 服务计划-工具计划 物料编码 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-13]
 * @since [产品/模块版本]
 */
public class FldToollistPrdcode extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 7344955581704802355L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		setLookupMap(new String[] { "PRODUCTCODE" }, new String[] { "ITEMNUM" });
		super.init();
	}

	@Override
	public void action() throws com.glaway.mro.exception.MroException {
		super.action();
		MroType value = getInputMroType();
		IJpo sparelistjpo = getJpo();
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setQueryWhere("itemnum = '" + value.asString() + "'");
		itemset.reset();
		if (!itemset.isEmpty()) {
			IJpo item = itemset.getJpo();
			String desc = item.getString("description");
			String spec = item.getString("SPECIFICATION");
			sparelistjpo.setValue("PRODUCTDESC", desc);
			sparelistjpo.setValue("MODEL", spec);
		}
	}

	@Override
	public IJpoSet getList() throws MroException {
		// TODO Auto-generated method stub
		this.setListObject("sys_inventory");
		this.setListWhere("itemnum in (select itemnum from sys_item where status='活动' and itemtype='工具')");

		return super.getList();
	}
}
