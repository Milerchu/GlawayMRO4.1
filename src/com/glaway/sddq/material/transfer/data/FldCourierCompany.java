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
						"domainid='COURIERCOMPANY'");
				domainSet
						.setUserWhere(domainSet.getUserWhere()
								+ " and VALUE in ('顺丰','圆通','EMS','中通','德邦','景赋','超音速','金诚')");
				domainSet.reset();
			} else {
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
						"domainid='COURIERCOMPANY'");
				domainSet
						.setUserWhere(domainSet.getUserWhere()
								+ " and VALUE in ('恒安达','自提','顺丰','景赋','南华','飞茂','巨邦','锦海捷亚','电机修造','弘毅','中铁','超音速','金诚')");// 过滤-库房类型为常规
				domainSet.reset();
			}
			return domainSet;
		} else {
			return super.getList();
		}

	}
}
