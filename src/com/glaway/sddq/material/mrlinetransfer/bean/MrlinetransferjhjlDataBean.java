package com.glaway.sddq.material.mrlinetransfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <计划经理处置DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MrlinetransferjhjlDataBean extends DataBean {
	/**
	 * 删除方法，判断是否可以删除
	 * 
	 * @throws MroException
	 */
	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		String status = this.page.getAppBean().getJpo().getString("status");
		if (!status.equalsIgnoreCase(ItemUtil.STATUS_JHJLSP)
				&& !status.equalsIgnoreCase(ItemUtil.STATUS_JHJLXG)) {
			throw new MroException("mrlinetransjhjl", "nodelete");
		}
		super.delete();
	}

	/**
	 * 新增方法，初始化数据值
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		int i = super.addrow();
		this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY, true);
		this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_READONLY, true);
		this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY, true);
		// this.getJpo().setFieldFlag("TRANSTYPE", GWConstant.S_READONLY, true);
		this.getJpo().setFieldFlag("itemnum", GWConstant.S_READONLY, false);
		this.getJpo().setFieldFlag("itemnum", GWConstant.S_REQUIRED, true);
		this.getJpo().setValue("selecttype", "新增",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		return i;
	}

	/**
	 * 编辑数据方法，控制页面字段只读必填
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String selecttype = this.getJpo().getString("selecttype");
		String TRANSTYPE = this.getJpo().getString("TRANSTYPE");
		if (!selecttype.isEmpty()) {
			if (selecttype.equalsIgnoreCase("新增")) {
				this.getJpo().setFieldFlag("itemnum", GWConstant.S_READONLY,
						false);
				this.getJpo().setFieldFlag("itemnum", GWConstant.S_REQUIRED,
						true);
			}
			if (selecttype.equalsIgnoreCase("匹配")) {
				this.getJpo().setFieldFlag("itemnum", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("itemnum", GWConstant.S_READONLY,
						true);
			}
			if (TRANSTYPE.isEmpty()) {
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setFieldFlag("TRANSFERQTY",
						GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("JHQTY", GWConstant.S_REQUIRED,
						false);
				this.getJpo()
						.setFieldFlag("JHQTY", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("SAPPONUM", GWConstant.S_READONLY,
						true);
			} else {
				if (TRANSTYPE.equalsIgnoreCase("下达计划")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_REQUIRED,
							true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
				}
				if (TRANSTYPE.equalsIgnoreCase("中心库调拨")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY,
							true);
				}
				if (TRANSTYPE.equalsIgnoreCase("中心库调拨后下达计划")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_REQUIRED,
							true);
				}
				if (TRANSTYPE.equalsIgnoreCase("现场调拨")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY,
							true);
				}
				if (TRANSTYPE.equalsIgnoreCase("计划交付后发货")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY,
							true);
				}
				if (TRANSTYPE.equalsIgnoreCase("返修后发运")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_REQUIRED, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY,
							true);
				}
				if (TRANSTYPE.equalsIgnoreCase("退回申请人")) {
					this.getJpo().setFieldFlag("STOREROOM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("TRANSFERQTY",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("SAPPONUM",
							GWConstant.S_READONLY, true);
					this.getJpo().setFieldFlag("JHQTY", GWConstant.S_READONLY,
							true);
					this.getJpo().setFieldFlag("REMARK", GWConstant.S_REQUIRED,
							true);
				}
			}
		}
		return super.editrow();
	}

	/**
	 * 编辑确认按钮方法，编辑确认后保存数据
	 * 
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.addEditRowCallBackOk();
		this.page.getAppBean().SAVE();
	}

	/**
	 * <选择处置项按钮方法，判断是否可以选择处置项按钮 >
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void selectmrlinetransfer() throws MroException, IOException {
		IJpoSet mrlinetransferset = this.getJpoSet();
		if (!mrlinetransferset.isEmpty()) {
			for (int i = 0; i < mrlinetransferset.count(); i++) {
				IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
				String TRANSTYPE = mrlinetransfer.getString("TRANSTYPE");
				if (TRANSTYPE.isEmpty()) {
					throw new MroException("请先将下列物料处置完毕再选择处置项");
				}
			}
		}

	}
}
