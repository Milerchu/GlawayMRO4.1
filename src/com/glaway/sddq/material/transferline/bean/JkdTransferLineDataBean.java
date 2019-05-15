package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <功能描述> 缴库单接收，新增，删除校验
 * 
 * @author 20167088
 * @version [版本号, 2018-8-7]
 * @since [产品/模块版本]
 */
public class JkdTransferLineDataBean extends DataBean {
	/**
	 * 
	 * <功能描述> 接收状态不可重复接收 非库管员不可接受
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void status() throws MroException {
		String status = this.getJpo().getString("status");
		String itemnum = this.getJpo().getString("itemnum");//原图号
		String sqn = this.getJpo().getString("sqn");//原序列号
		String newitemnum = this.getJpo().getString("newitemnum");//返修后图号
		String newsqn = this.getJpo().getString("newsqn");//返修后序列号
		String loginid=getUserInfo().getLoginID().toUpperCase();
		if (status.equalsIgnoreCase("已接收")){
			throw new MroException("transferline", "receive");
		}else{
			IJpoSet persongroupset=MroServer.getMroServer().getJpoSet("SYS_PERSONGROUPTEAM", MroServer.getMroServer().getSystemUserServer());
			persongroupset.setUserWhere("persongroup='JKJS' and resppartygroup='"+loginid	+"'");
			persongroupset.reset();
			if(persongroupset.isEmpty()){
				throw new MroException("非缴库负责人不能接收");
			}else{
				if(!newitemnum.isEmpty()){
					if(!itemnum.equalsIgnoreCase(newitemnum)){
						throw new MroException("图号有变更，请点击‘变更图号’按钮进行接收！");
					}else{
						if(!newsqn.isEmpty()){
							if(!sqn.equalsIgnoreCase(newsqn)){
								throw new MroException("序列号有变更，请点击‘变更序列号’按钮进行接收！");
							}
						}
					}
				}else{
					if(!newsqn.isEmpty()){
						if(!sqn.equalsIgnoreCase(newsqn)){
							throw new MroException("序列号有变更，请点击‘变更序列号’按钮进行接收！");
						}
					}
				}

			}
		}

	}
	/**
	 * 
	 * <变更图号按钮功能>
	 * @throws MroException [参数说明]
	 *
	 */
	public void changeitemnum() throws MroException {
		String status = this.getJpo().getString("status");
		String itemnum = this.getJpo().getString("itemnum");//原图号
		String newitemnum = this.getJpo().getString("newitemnum");//返修后图号
		String sqn = this.getJpo().getString("sqn");//原序列号
		String newsqn = this.getJpo().getString("newsqn");//返修后序列号
		String loginid=getUserInfo().getLoginID().toUpperCase();
		if (status.equalsIgnoreCase("已接收")){
			throw new MroException("transferline", "receive");
		}else{
			IJpoSet persongroupset=MroServer.getMroServer().getJpoSet("SYS_PERSONGROUPTEAM", MroServer.getMroServer().getSystemUserServer());
			persongroupset.setUserWhere("persongroup='JKJS' and resppartygroup='"+loginid	+"'");
			persongroupset.reset();
			if(persongroupset.isEmpty()){
				throw new MroException("非缴库负责人不能接收");
			}else{
				if(itemnum.equalsIgnoreCase(newitemnum)){
					if(sqn.equalsIgnoreCase(newsqn)){
						throw new MroException("图号未变更，请点击‘接收’按钮进行接收");
					}else{
						throw new MroException("序列号有变更，请点击‘变更序列号’按钮进行接收！");
					}
				}else{
					IJpoSet itemset=MroServer.getMroServer().getJpoSet("sys_item", MroServer.getMroServer().getSystemUserServer());
					itemset.setUserWhere("ITEMNUM in(select ALTITEMNUM from SYS_ALTITEM where(sys_altitemid in(select s.sys_altitemid from sys_altitem s  left join (select * from sys_altitem) t on s.REPLACE = t.REPLACE and (s.parent = t.parent or (s.parent is null and t.parent is null)) where t.altitemnum ='"+itemnum+"'))) and itemnum in ('"+newitemnum+"')");
					if(itemset.isEmpty()){
						throw new MroException("变更的图号与原图号在系统中不存在替换关系，请联系相关人员创建替换关系！");
					}
				}

			}
		}	

	}
	/**
	 * 
	 * <变更序列号按钮功能>
	 * @throws MroException [参数说明]
	 *
	 */
	public void changesqn() throws MroException {
		String status = this.getJpo().getString("status");
		String itemnum = this.getJpo().getString("itemnum");//原图号
		String newitemnum = this.getJpo().getString("newitemnum");//返修后图号
		String sqn = this.getJpo().getString("sqn");//原序列号
		String newsqn = this.getJpo().getString("newsqn");//返修后序列号
		String loginid=getUserInfo().getLoginID().toUpperCase();
		if (status.equalsIgnoreCase("已接收")){
			throw new MroException("transferline", "receive");
		}else{
			IJpoSet persongroupset=MroServer.getMroServer().getJpoSet("SYS_PERSONGROUPTEAM", MroServer.getMroServer().getSystemUserServer());
			persongroupset.setUserWhere("persongroup='JKJS' and resppartygroup='"+loginid	+"'");
			persongroupset.reset();
			if(persongroupset.isEmpty()){
				throw new MroException("非缴库负责人不能接收");
			}else{
				if(!itemnum.equalsIgnoreCase(newitemnum)){
					throw new MroException("图号有变更，请点击‘变更图号’按钮进行接收！");
				}else{
					if(sqn.equalsIgnoreCase(newsqn)){
						throw new MroException("序列号未变更，请点击‘接收’按钮进行接收");
					}
				}

			}
		}	

	}
	/**
	 * 删除校验 接收状态无法删除 非制单人不可删除
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		if (this.getString("status").equals("已接收"))
			throw new MroException("已接收的物料不可删除");
		if (this.getAppBean().getJpo().getString("KEEPER").equals(getUserInfo().getLoginID()))
			throw new MroException("接收人不可删除");
		return super.toggledeleterow();

	}

	/**
	 * 总单据-已经收无法新增 非制单人不可删除
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		if (this.getAppBean().getJpo().getString("status").equals("已接收"))
			throw new MroException("已接收单据不可新增行");
		if (!getUserInfo().getLoginID().equalsIgnoreCase("90000106"))
			throw new MroException("不可新增");
		if(this.getAppBean().getJpo().getString("RECEIVESTOREROOM").isEmpty()){
			throw new MroException("请先选择发出库房");
		}
		if(this.getAppBean().getJpo().getString("ISSUESTOREROOM").isEmpty()){
			throw new MroException("请先选择接收库房");
		}

		return super.addrow();
	}
	/**
	 * 编辑确认方法，确认编辑后如果是新增保存记录
	 * 
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.addEditRowCallBackOk();
		if (this.getJpo().isNew()) {
			this.page.getAppBean().SAVE();
		}

	}
}
