package com.glaway.sddq.material.item.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 物料表Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/物料]
 */
public class Item extends Jpo {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化只读控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (!this.isNew()) {
			String personid = this.getUserInfo().getLoginID();
			personid = personid.toUpperCase();
			IJpoSet GROUPUSER = MroServer.getMroServer().getSysJpoSet(
					"SYS_GROUPUSER");// 判断登陆人是不是在系统管理员组
			GROUPUSER.setUserWhere("groupname='SDDQDEFAULT' and userid='"
					+ personid + "'");
			if (this.getString("status").equalsIgnoreCase("活动")) {
				if (!GROUPUSER.isEmpty()) {
					this.setFieldFlag("DESCRIPTION", GWConstant.S_READONLY,
							true);// 物料描述
					this.setFieldFlag("SPECIFICATION", GWConstant.S_READONLY,
							true);// 规格型号
					this.setFieldFlag("ORDERUNIT", GWConstant.S_READONLY, true);// 基本计量单位
					this.setFieldFlag("ITEMTYPE", GWConstant.S_READONLY, true);// 物料类型
					this.setFieldFlag("ITEMGROUP", GWConstant.S_READONLY, true);// 物料组
					this.setFieldFlag("CLASSNUM", GWConstant.S_READONLY, true);// 物料分类
					this.setFieldFlag("CYCLE", GWConstant.S_READONLY, true);// 有效存储周期
					this.setFieldFlag("PRODUCTER", GWConstant.S_READONLY, true);// 品牌
					this.setFieldFlag("TOOL", GWConstant.S_READONLY, true);//
					this.setFieldFlag("ISCHECK", GWConstant.S_READONLY, true);
					this.setFieldFlag("ISIV", GWConstant.S_READONLY, true);// 是否虚拟件
					this.setFieldFlag("CHECKUNIT", GWConstant.S_READONLY, true);
					this.setFieldFlag("TECH", GWConstant.S_READONLY, true);
					this.setFieldFlag("LRUSRU", GWConstant.S_READONLY, true);// 可更换单元类别
				} else {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			} else {

				this.setFlag(GWConstant.S_READONLY, true);
			}

		}

	}

	/**
	 * 新增赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		setValue("status", "活动", GWConstant.P_NOVALIDATION);
		setValue("statusdate", MroServer.getMroServer().getDate(),
				GWConstant.P_NOVALIDATION);
	}

}
