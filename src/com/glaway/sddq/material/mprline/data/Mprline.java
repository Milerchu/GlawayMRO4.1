package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <领料单行表JPo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class Mprline extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化页面控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (this.getAppName() != null) {
			if (this.getAppName().equalsIgnoreCase("JSITEMREQ")) {
				String itemnum = this.getString("itemnum");
				IJpoSet itemset = MroServer.getMroServer().getJpoSet(
						"sys_item",
						MroServer.getMroServer().getSystemUserServer());
				itemset.setQueryWhere("itemnum='" + itemnum + "'");
				itemset.reset();
				if (!itemset.isEmpty()) {

					String type = ItemUtil.getItemInfo(itemnum);
					if (ItemUtil.SQN_ITEM.equals(type)) {
						this.setFieldFlag("sqn", GWConstant.S_READONLY, false);
						this.setFieldFlag("sqn", GWConstant.S_REQUIRED, true);
						this.setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,
								false);
						this.setValueNull("LOTNUM", 112L);
						this.setFieldFlag("qty", 7L, true);
						if (this.getAppName().equals("GZZXD")) {
							this.setFieldFlag("LOTNUM", GWConstant.S_READONLY,
									true);
						}
					} else {
						if (ItemUtil.LOT_I_ITEM.equals(type)) {
							this.setFieldFlag("LOTNUM", GWConstant.S_READONLY,
									false);
							this.setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,
									true);
							this.setFieldFlag("sqn", GWConstant.S_REQUIRED,
									false);
							this.setFieldFlag("sqn", GWConstant.S_READONLY,
									true);
							this.setFieldFlag("qty", 128L, true);

						} else {
							this.setFieldFlag("sqn", GWConstant.S_REQUIRED,
									false);
							this.setFieldFlag("sqn", GWConstant.S_READONLY,
									true);
							this.setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,
									false);
							this.setFieldFlag("LOTNUM", GWConstant.S_READONLY,
									true);
							this.setFieldFlag("qty", 128L, true);

						}
					}
				}

				if (this.getString("status").equals("已接收")) {
					String[] attr = { "ITEMNUM", "SQN", "LOTNUM", "QTY", "MEMO" };
					this.setFieldFlag(attr, 7L, true);
				} 
//				else {
//					String personid = this.getUserInfo().getLoginID();
//					personid = personid.toUpperCase();
//					if (!this.getParent().getString("APPLICANTBY")
//							.equalsIgnoreCase(personid)) {
//						String[] attr = { "ITEMNUM", "SQN", "LOTNUM", "QTY",
//								"MEMO" };
//						this.setFieldFlag(attr, 7L, true);
//					}
//				}
			}
		}

	}

}
