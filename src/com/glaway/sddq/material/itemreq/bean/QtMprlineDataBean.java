package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
/**
 * 
 * <客户料退库领料单行绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-12]
 * @since  [产品/模块版本]
 */
public class QtMprlineDataBean extends DataBean {
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
		if (personid.equalsIgnoreCase(RECEIVEBY)) {
			if (status.equalsIgnoreCase("已领用")) {
				throw new MroException("该物料已经领出");
			}
		} else {
			throw new MroException("非库管员不能确定领料");
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
	public void selectjktransferline() throws MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String MPRSTOREROOM = this.page.getAppBean().getJpo().getString("MPRSTOREROOM");
		String status = this.page.getAppBean().getJpo().getString("status");
		if(status.equalsIgnoreCase("草稿")){
			if(this.getAppBean().getJpo().getString("iscreate").equalsIgnoreCase("是")){
				throw new MroException("系统自动创建不能选择");
			}else{
				if(MPRSTOREROOM.isEmpty()){
					throw new MroException("请先选择领料库房");
				}
			}
		}else{
			throw new MroException("该状态不能选择领料明细");
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
		if (this.getString("status").equals("已领用")) {
			throw new MroException("已领用的物料无法删除");
		} else {
			if(this.getAppBean().getJpo().getString("iscreate").equalsIgnoreCase("是")){
				throw new MroException("系统自动创建无法删除");
			}else{
				// TODO Auto-generated method stub
				return super.toggledeleterow();
			}
			
		}
	}
}
