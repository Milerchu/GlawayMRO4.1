package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <服务专改造领料单成本中心选择字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-4]
 * @since [产品/模块版本]
 */
public class FldCostcenter extends JpoField {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6714808953825812433L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "value" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String listSql = "";
		setListObject("SYS_ALNDOMAIN");
		listSql = "DOMAINID='COSTCENTER'";

		setListWhere(listSql);

		return super.getList();
	}
}
