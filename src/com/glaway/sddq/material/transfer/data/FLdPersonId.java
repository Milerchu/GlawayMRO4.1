package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <接收开箱人，接收核对人字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FLdPersonId extends JpoField {
	/**
	 * 装箱单根据接收人部门过滤人员信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("sys_person");
		String RECEIVEBY = this.getJpo().getString("RECEIVEBY");
		String type = this.getJpo().getString("type");
		String listSql = "";
		if (type.equalsIgnoreCase("ZXD")) {
			listSql = listSql
					+ "DEPARTMENT in (select DEPARTMENT from sys_person where personid='"
					+ RECEIVEBY + "')";
		} else {
			listSql = listSql + "1=1";
		}
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
