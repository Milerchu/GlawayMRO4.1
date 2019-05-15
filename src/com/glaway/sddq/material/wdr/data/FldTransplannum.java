package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <处置管理改造计划编号字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-4-11]
 * @since [产品/模块版本]
 */
public class FldTransplannum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "transplannum" };
		setLookupMap(thisAttrs, srcAttrs);

	}

	/**
	 * 获取改造计划信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("transplan");
		String listSql = "";
		listSql = "STATUS='已完成' and plantype!='验证'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		String transplannum=this.getValue();
		IJpoSet TRANSNOTICEset = MroServer.getMroServer().getJpoSet(
				"TRANSNOTICE", MroServer.getMroServer().getSystemUserServer());
		TRANSNOTICEset.setUserWhere("TRANSNOTICENUM in (select TRANSNOTICENUM from TRANSPLAN where transplannum='"+transplannum+"')");
		if(!TRANSNOTICEset.isEmpty()){
			String TRANSWORKORDERNUM=TRANSNOTICEset.getJpo(0).getString("TRANSWORKORDERNUM");
			if(TRANSWORKORDERNUM.isEmpty()){
				throw new MroException("改造计划关联改造通知单工作令号为空");
			}else{
				this.getJpo().setValue("WORKORDERNUM", TRANSWORKORDERNUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}else{
			throw new MroException("改造计划未关联改造通知单");
		}
		super.action();
	}
	
}
