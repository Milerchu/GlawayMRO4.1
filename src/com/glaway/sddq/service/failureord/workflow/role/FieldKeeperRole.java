package com.glaway.sddq.service.failureord.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障工单 现场库管员 定制角色类
 * 
 * @author public2796
 * @version [版本号, 2018年7月13日]
 * @since [产品/模块版本]
 */
public class FieldKeeperRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String arg1) throws MroException {
		String persons = "";
		// 所属站点
		// String whichstation = jpo.getString("WHICHSTATION");
		// 库房
		String location = "";
		// 获取服务工程师所在站点现场库库管员
		IJpoSet locationSet = MroServer.getMroServer().getJpoSet("locations",
				jpo.getUserServer());
		// 序列号件上下车记录
		IJpoSet exchangeSet = MroServer.getMroServer().getSysJpoSet(
				"EXCHANGERECORD",
				"FAILUREORDERNUM='" + jpo.getString("ordernum") + "'");
		if (exchangeSet != null && exchangeSet.count() > 0) {
			// 取入库库房
			location = exchangeSet.getJpo(0).getString("location");
		}
		// 如果未取到库房
		if (StringUtil.isStrEmpty(location)) {

			// 非序列号件上下车记录
			IJpoSet losspartSet = MroServer.getMroServer().getSysJpoSet(
					"JXTASKLOSSPART",
					"JXTASKNUM='" + jpo.getString("ordernum") + "' ");
			if (losspartSet != null && losspartSet.count() > 0) {
				// 取入库库房
				location = losspartSet.getJpo(0).getString("underloc");
			}
		}
		locationSet.setUserWhere("location='" + location + "'");
		// locationSet.setUserWhere("erploc='" + ItemUtil.ERPLOC_1020
		// + "' and storeroomlevel in('" + ItemUtil.STOREROOMLEVEL_XCZDK
		// + "','" + ItemUtil.STOREROOMLEVEL_XCK + "') and locationtype='"
		// + ItemUtil.LOCATIONTYPE_WX + "' and locsite='" + whichstation
		// + "'");
		locationSet.reset();
		if (!locationSet.isEmpty()) {
			persons = "'" + locationSet.getJpo(0).getString("keeper") + "'";
		} else {
			throw new MroException("", "库管员角色为空！");
		}

		// 返回人员jpoSet
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				jpo.getUserServer());
		personSet.setUserWhere("personid in(" + persons + ")");
		personSet.reset();
		return personSet;
	}

}
