package com.glaway.sddq.material.location.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库房管理修改序列号弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class AssetSqnChangeDataBean extends DataBean {
	/**
	 * 确认按钮变更序列号，校验是否重复
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {

		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			String newsqn = this.getJpo().getString("newsqn");
			String itemnum = parent.getString("itemnum");
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setUserWhere("itemnum='" + itemnum + "' and sqn='"
					+ newsqn + "'");
			if (assetset.isEmpty()) {
				parent.setValue("sqn", newsqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				// 调用增加变更历史方法
				ADDHISTOY();
				this.getAppBean().SAVE();
			} else {
				throw new MroException("asset", "havesqn");
			}
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * <增加序列号变更历史>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDHISTOY() throws MroException {
		IJpo parent = this.getJpo().getParent();
		String oldsqn = this.getJpo().getString("oldsqn");
		String newsqn = this.getJpo().getString("newsqn");
		String location = parent.getString("location");
		String itemnum = parent.getString("itemnum");
		Date changetime = MroServer.getMroServer().getDate();
		String changeby = this.page.getAppBean().getUserInfo().getLoginID();
		changeby = changeby.toUpperCase();
		String siteid = parent.getString("siteid");
		String orgid = parent.getString("orgid");
		IJpoSet assetsqnchangehistoryset = MroServer.getMroServer().getJpoSet(
				"assetsqnchangehistory",
				MroServer.getMroServer().getSystemUserServer());
		IJpo assetsqnchangehistory = assetsqnchangehistoryset.addJpo();
		assetsqnchangehistory.setValue("oldsqn", oldsqn,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("newsqn", newsqn,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("location", location,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("itemnum", itemnum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("changetime", changetime,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("changeby", changeby,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("siteid", siteid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistory.setValue("orgid", orgid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		assetsqnchangehistoryset.save();
	}
}
