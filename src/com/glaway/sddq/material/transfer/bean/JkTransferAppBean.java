package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.Invtrans;

/**
 * 
 * <缴库单AppBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class JkTransferAppBean extends AppBean {
	/**
	 * 
	 * <接收校验 1、非制单人不可接收 2、非未处理状态不可接收 3、行单据没完全接收不可接收>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void RECEIVE() throws MroException, IOException {
		IJpo transferJpo = this.getAppBean().getJpo();
		String status = transferJpo.getString("status");
		if (!transferJpo.getString("KEEPER").equals(getUserInfo().getLoginID()))
			throw new MroException("transferline", "keeper");
		if (status.equalsIgnoreCase("未处理")) {
			String rloc = this.getAppBean().getString("ISSUESTOREROOM");// 接收库房
			IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
			transferlineset.setUserWhere("status!='已接收'");
			transferlineset.reset();
			for (int i = 0; (transferlineset.getJpo(i) != null); i++) {
				Invtrans invtrans = new Invtrans();
				IJpo transferline = transferlineset.getJpo(i);
				String itemnum = transferline.getString("itemnum");//
				boolean xlh = false;
				if (!transferline.getString("sqn").isEmpty()
						&& (!transferline.getString("sqn").equals(""))) {
					xlh = true;
				}
				boolean pch = false;
				if (!transferline.getString("LOTNUM").isEmpty()
						|| transferline.getString("LOTNUM").length() > 0) {

					pch = true;

				}
				double qty = transferline.getDouble("ORDERQTY")
						- transferline.getDouble("YJSQTY");
				invtrans.recive(
						this.getAppBean().getJpo().getString("TRANSFERNUM"),
						"", itemnum, "", rloc, qty, this.getAppBean().getJpo(),
						transferline.getString("assetnum"), xlh,
						transferline.getString("sqn"), pch,
						transferline.getString("LOTNUM"));
				transferline.setValue("status", "已接收");
			}
			transferJpo.setValue("status", "已接收");
			transferJpo.setValue("RECEIVEDATE", MroServer.getMroServer()
					.getDate());
			showMsgbox("提示", "接收成功");
			this.getAppBean().SAVE();

		} else {
			throw new MroException("transferline", "noreceive");
		}
	}

	/**
	 * @param 删除校验
	 *            ： 1、非制单人不可删除 2、行数据已经被接收不可删除
	 * @param---2018-08-07关
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int DELETE() throws MroException, IOException {
		IJpo transferJpo = getJpo();
		if (transferJpo.getString("KEEPER").equals(getUserInfo().getLoginID()))
			throw new MroException("transferline", "createby");
		IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
		transferlineset.setUserWhere("status='已接收'");
		transferlineset.reset();
		if (transferlineset.count() > 0) {
			throw new MroException("transferline", "deletes");
		}
		return super.DELETE();
	}

}
