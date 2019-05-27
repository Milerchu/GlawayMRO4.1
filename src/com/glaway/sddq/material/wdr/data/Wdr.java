package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;
/**
 * 
 * <处置管理主类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-18]
 * @since  [产品/模块版本]
 */
public class Wdr extends Jpo {
/**
 * 处置管理初始化
 * @throws MroException
 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (!this.isNew()) {
			String personid = this.getUserInfo().getLoginID();
			personid = personid.toUpperCase();
			String createby=this.getString("createby");
			if(personid.equalsIgnoreCase(createby)){//登录人是创建人
				String status=this.getString("status");
				if(!status.equalsIgnoreCase("草稿")){
					this.setFieldFlag("DEALTYPE", GWConstant.S_READONLY, true);
					this.setFieldFlag("LOCATION", GWConstant.S_READONLY, true);
					this.setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
				}else{
					IJpoSet docset=this.getJpoSet("DOCLINKS");
					if(!docset.isEmpty()){
						this.setFieldFlag("DEALTYPE", GWConstant.S_READONLY, true);
						this.setFieldFlag("LOCATION", GWConstant.S_READONLY, true);
						this.setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
					}
				}
			}else{
				this.setFlag(GWConstant.S_READONLY, true);
			}
		}
	}

	@Override
	public void add() throws MroException {
		// TODO Auto-generated method stub
		super.add();
		if (toBeAdded()) {
			setValue("STATUS", "草稿");
			String loginid = getUserServer().getUserInfo().getLoginID();
			loginid = loginid.toUpperCase();
			String WDRNUM = this.getString("WDRNUM");
			IJpoSet deptset = MroServer.getMroServer().getJpoSet(
					"SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ loginid + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String deptnum = deptset.getJpo(0).getString("deptnum");
				String retrun = WorkorderUtil
						.getAbbreviationByDeptnum(deptnum);
				if (retrun.equalsIgnoreCase("PJGL")) {
					WDRNUM = "ZX-" + "CZGL" + "-" + WDRNUM;
				} else {
					WDRNUM = retrun + "-" + "CZGL" + "-" + WDRNUM;
					setValue("DEALTYPE", "再利用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.setFieldFlag("DEALTYPE", GWConstant.S_READONLY, true);
				}
			} else {
				WDRNUM = "ZX-" + "CZGL" + "-" + WDRNUM;
			}
			this.setValue("WDRNUM", WDRNUM,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
	/**
	 * 级联删除相关数据
	 * 
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		String status=this.getString("status");
		String createby=this.getString("createby");
		String loginid = getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		if(status.equalsIgnoreCase("草稿")&&loginid.equalsIgnoreCase(createby)){
			getJpoSet("WDRLINESELECT").deleteAll(
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			getJpoSet("WDRLINE").deleteAll();
			super.delete(flag);	
		}else{
			throw new MroException("非创建人、非草稿状态下不能删除");
		}
		
	}
}
