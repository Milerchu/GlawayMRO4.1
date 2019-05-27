package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <装箱单发运方式字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldCourierCompany extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "VALUE" };
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 根据移动类型过滤发运方式内容
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		String transfermovetype = this.getJpo().getString("transfermovetype");
		if (!transfermovetype.equalsIgnoreCase("")) {
			if (transfermovetype
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)
					|| transfermovetype
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
						"domainid='XCOURIERCOMPANY'");
				domainSet.reset();
			} else {
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
						"domainid='COURIERCOMPANY'");
				domainSet.reset();
			}
			return domainSet;
		} else {
			return super.getList();
		}

	}
}
