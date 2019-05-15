package com.glaway.sddq.material.materborrowline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;

/**
 * 
 * <配件借用行列表databean类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-23]
 * @since [产品/模块版本]
 */
public class BorrowLineDataBean extends DataBean {
	/**
	 * 新增行方法，校验是否可以新增行
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String BORROWBY = this.getAppBean().getJpo().getString("BORROWBY");// 借用人
		String status = this.getAppBean().getJpo().getString("status");//状态
		String BORROWDATE = this.getAppBean().getJpo().getString("BORROWDATE");// 借用时间
		String PLANRETURNDATE = this.getAppBean().getJpo()
				.getString("PLANRETURNDATE");// 承诺归还时间
		String BORROWSTOREROOM = this.getAppBean().getJpo()
				.getString("BORROWSTOREROOM");// 借出库房
		String SEVICEAPPR = this.getAppBean().getJpo().getString("SEVICEAPPR");// 借出方责任人
		if (BORROWBY.isEmpty() || BORROWDATE.isEmpty()
				|| PLANRETURNDATE.isEmpty() || BORROWSTOREROOM.isEmpty()
				|| SEVICEAPPR.isEmpty()) {
			throw new MroException("请先补全借用基础信息在新建行");
		}else{
			if(!status.equalsIgnoreCase("新建")){
				throw new MroException("非新建状态下不能新建行");
			}
		}
		return super.addrow();
	}
/**
 * 删除校验，非新建状态不能删除行
 * @throws MroException
 */
	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		String status=this.getAppBean().getJpo().getString("status");// 状态
		if(!status.equalsIgnoreCase("新建")){
			throw new MroException("非新建状态下不能删除行");
		}
		super.delete();
		
	}
	/**
	 * 
	 * <配件借用归还物料按钮>
	 * @throws MroException [参数说明]
	 *
	 */
	public void BORROWRETURN() throws MroException {
		String createby=this.getAppBean().getJpo().getString("createby");//单据创建人
		String personid = this.page.getAppBean().getUserInfo().getLoginID();//当前登录人
		personid = personid.toUpperCase();
		String status=this.getAppBean().getJpo().getString("status");//单据状态
		if(!personid.equalsIgnoreCase(createby)){//判断登录人是否是单据创建人，非创建人不能归还
			throw new MroException("非创建人不能归还物料");
		}else{
			if(status.equalsIgnoreCase("新建")||status.equalsIgnoreCase("驳回")||status.equalsIgnoreCase("待审批")||status.equalsIgnoreCase("全部归还")||status.equalsIgnoreCase("关闭")){
				throw new MroException("该状态下不能归还");
			}else{
				String linestatus=this.getJpo().getString("status");//行状态
				if(linestatus.equalsIgnoreCase("已归还")){
					throw new MroException("该物料已经归还");
				}
			}
		}
	}
}
