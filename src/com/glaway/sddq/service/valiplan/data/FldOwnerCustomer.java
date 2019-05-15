package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 验证计划-验证产品范围-机务段OwnerCustomer字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月23日]
 * @since [产品/模块版本]
 */
public class FldOwnerCustomer extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 3001560182383146076L;

	@Override
	public void action() throws MroException {
		super.action();
		String stations = this.getValue();
		IJpoSet jpoSet = MroServer.getMroServer().getSysJpoSet("SYS_DEPT");
		jpoSet.setUserWhere(" deptnum ='" + stations + "' ");
		if (!jpoSet.isEmpty()) {
			IJpo jpo = jpoSet.getJpo();
			if (jpo != null) {
				String parent = jpo.getString("parent");
				this.getJpo().setValue("office", parent);
			}
		}
	}

}
