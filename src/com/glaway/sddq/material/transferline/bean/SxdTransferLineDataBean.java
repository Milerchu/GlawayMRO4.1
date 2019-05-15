package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * 
 * <送修单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class SxdTransferLineDataBean extends DataBean {
	/**
	 * 
	 * <接收按钮，判断是否可以接收>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void status() throws MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");// 现场库管员
		String status = this.getJpo().getString("status");
		if (personid.equalsIgnoreCase(ENDBY)) {
			if (status.equalsIgnoreCase("已接收")) {
				throw new MroException("transferline", "receive");
			}
			if (!this.getJpo().getParent().getString("status")
					.equalsIgnoreCase("在途")) {
				throw new MroException("transferline", "statusreceive");
			}
		} else {
			throw new MroException("transferline", "endbyreceive");
		}

	}

	/**
	 * 删除方法，判断是否可以删除行
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		String status = this.page.getAppBean().getJpo().getString("status");
		if (!personid.equalsIgnoreCase(CREATEBY)) {
			throw new MroException("transferline", "nodelete");
		} else {
			if (!status.equalsIgnoreCase("未处理")) {
				throw new MroException("transferline", "statusnodelete");
			}
		}
		return super.toggledeleterow();
	}

	/**
	 * 选择装箱单按钮方法，判断是否可以选择装箱单 <功能描述>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectzxdline() throws IOException, MroException {

		String SXTYPE = this.page.getAppBean().getJpo().getString("SXTYPE");
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String RECEIVESTOREROOM = this.page.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		if (SXTYPE.isEmpty()) {
			throw new MroException("transferline", "nosxtype");
		}
		if (ISSUESTOREROOM.isEmpty()) {
			throw new MroException("transferline", "sxnostoreroom");
		}
		if (RECEIVESTOREROOM.isEmpty()) {
			throw new MroException("请先选择接收库房");
		}
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		if (!this.page.getAppBean().getJpo().getString("status")
				.equalsIgnoreCase("未处理")) {
			throw new MroException("transferline", "statusselectzxdline");
		} else {
			if (!personid.equalsIgnoreCase(CREATEBY)) {
				throw new MroException("transferline", "createbyselectzxdline");
			}
		}
		IJpoSet transferlineset = this.page.getAppBean().getJpo()
				.getJpoSet("transferline");
		for (int i = 0; i < transferlineset.count(); i++) {
			String zxdlineid = transferlineset.getJpo(0).getString("zxdlineid");
			if (zxdlineid.isEmpty()) {
				throw new MroException("transferline", "noselectzxdline");
			}
		}
	}

	/**
	 * 选择入库单按钮方法，判断是否可以选择入库单 <功能描述>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectmprline() throws IOException, MroException {

		String SXTYPE = this.page.getAppBean().getJpo().getString("SXTYPE");
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		if (SXTYPE.isEmpty()) {
			throw new MroException("transferline", "nosxtype");
		}
		if (ISSUESTOREROOM.isEmpty()) {
			throw new MroException("transferline", "sxnostoreroom");
		}
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		if (!this.page.getAppBean().getJpo().getString("status")
				.equalsIgnoreCase("未处理")) {
			throw new MroException("transferline", "statusselectmprline");
		} else {
			if (!personid.equalsIgnoreCase(CREATEBY)) {
				throw new MroException("transferline", "createbyselectmprline");
			}
		}
		IJpoSet transferlineset = this.page.getAppBean().getJpo()
				.getJpoSet("transferline");
		for (int i = 0; i < transferlineset.count(); i++) {
			String mprlineid = transferlineset.getJpo(0).getString("mprlineid");
			if (mprlineid.isEmpty()) {
				throw new MroException("transferline", "noselectmprline");
			}
		}
	}

	@Override
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		return super.editrow();

	}

}
