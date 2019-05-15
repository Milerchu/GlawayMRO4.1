package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <装箱单发运人、核对人、装箱人字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldCreatePerson extends JpoField {
	/**
	 * 如果是装箱单程序，则根据登陆人过滤相同不能人员
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("sys_person");
		String CREATEBY = this.getJpo().getString("CREATEBY");
		String type = this.getJpo().getString("type");
		String listSql = "";
		if (type.equalsIgnoreCase("ZXD")) {
			listSql = listSql
					+ "DEPARTMENT in (select DEPARTMENT from sys_person where personid='"
					+ CREATEBY + "')";
		} else {
			listSql = listSql + "1=1";
		}
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
