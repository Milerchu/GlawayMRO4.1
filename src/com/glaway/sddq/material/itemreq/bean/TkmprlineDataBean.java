package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <功能描述> 退库单单行接收
 * 
 * @author 20167088
 * @version [版本号, 2018-8-4]
 * @since [产品/模块版本]
 */
public class TkmprlineDataBean extends DataBean {
	/**
	 * 
	 * <判断是否能接收>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void mprlineqty() throws MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String RECEIVEBY = this.page.getAppBean().getJpo()
				.getString("RECIVEBY");// 现场库管员
		String status = this.getJpo().getString("status");
		String itemnum = this.getJpo().getString("itemnum");
		if (personid.equalsIgnoreCase(RECEIVEBY)) {
			if (status.equalsIgnoreCase("已退库")) {
				throw new MroException("该物料已经退库");
			}
		} else {
			throw new MroException("非库管员不能退库");
		}
		if(itemnum.isEmpty()){
			throw new MroException("请先选择退库物料");
		}
	}
	/**
	 * 
	 * <判断是否能选择入库明细>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectrkline() throws MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String MPRSTOREROOM = this.page.getAppBean().getJpo().getString("MPRSTOREROOM");
		String status = this.page.getAppBean().getJpo().getString("status");
		if(status.equalsIgnoreCase("草稿")){
			if(MPRSTOREROOM.isEmpty()){
				throw new MroException("请先选择退库库房");
			}
		}else{
			throw new MroException("该状态不能选择入库明细");
		}

	}
	/**
	 * 删除校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		if(!this.getAppBean().getJpo().getString("RECIVEBY").equalsIgnoreCase(this.getAppBean().getJpo().getString("APPLICANTBY"))){
			if(this.getAppBean().getJpo().getString("RECIVEBY").equals(getUserInfo().getLoginID())){
				throw new MroException("接收人不可删除单据");
			}
		}
		if (this.getString("status").equals("已退库")) {
			throw new MroException("已退库的物料无法删除");
		} else {
			// TODO Auto-generated method stub
			return super.toggledeleterow();
		}
	}

//	/**
//	 * 判断是否能新增
//	 * 
//	 * @return
//	 * @throws MroException
//	 * @throws IOException
//	 */
//	@Override
//	public int addrow() throws MroException, IOException {
//		// TODO Auto-generated method stub
//		String APPLICANTBY = this.getAppBean().getJpo()
//				.getString("APPLICANTBY");
//		String RKTYPE = this.getAppBean().getJpo().getString("RKTYPE");
//		String RECIVEBY = this.getAppBean().getJpo().getString("RECIVEBY");
//		String loginid = getUserInfo().getLoginID();
//		loginid = loginid.toUpperCase();
//		String status = this.getAppBean().getJpo().getString("status");
//		String MPRSTOREROOM = this.getAppBean().getJpo()
//				.getString("MPRSTOREROOM");
//		if (status.equalsIgnoreCase("已退库")) {
//			throw new MroException("已退库单据无法新增");
//		} else {
//			if (loginid.equalsIgnoreCase(RECIVEBY)) {
//				if (!RECIVEBY.equalsIgnoreCase(APPLICANTBY)) {
//					throw new MroException("接收人不可增加单据");
//				}
//			} else {
//				if (MPRSTOREROOM.equalsIgnoreCase("")) {
//					throw new MroException("请先选择退库库房");
//				}
//			}
//		}
//		
//		 super.addrow();
//		if(RKTYPE.equalsIgnoreCase("拆借件入库")){
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_READONLY, false);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_READONLY, false);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, false);
//			
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_REQUIRED, true);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_REQUIRED, true);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, true);
//		}else{
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_REQUIRED, false);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_REQUIRED, false);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, false);
//			
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_READONLY, true);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_READONLY, true);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, true);
//		}
//		return GWConstant.ACCESS_SAMEMETHOD;
//	}
//
//	@Override
//	public int editrow() throws MroException, IOException {
//		// TODO Auto-generated method stub
//		String RKTYPE = this.getAppBean().getJpo().getString("RKTYPE");
//		if(RKTYPE.equalsIgnoreCase("拆借件入库")){
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_READONLY, false);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_READONLY, false);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, false);
//			
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_REQUIRED, true);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_REQUIRED, true);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, true);
//		}else{
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_REQUIRED, false);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_REQUIRED, false);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, false);
//			
//			this.getJpo().setFieldFlag("PARENTLOCATION", GWConstant.S_READONLY, true);
//			this.getJpo().setFieldFlag("PARENTITEMNUM", GWConstant.S_READONLY, true);
//			this.getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, true);
//		}
//		return super.editrow();
//	}

}
