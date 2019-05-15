package com.glaway.sddq.material.mprline.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.Invtrans;

/**
 * 
 * <入库单AppBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class JsMprlineAppBean extends AppBean {
	/**
	 * 
	 * <接收方法，判断是否可以接收，接收后状态字段赋值>
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int RECEIVE() throws MroException, IOException {
		IJpo msr = this.getAppBean().getJpo();
		if (msr.getString("status").equals("已接收"))
			throw new MroException("", "单据不可重复接收");
		if (!this.getAppBean().getJpo().getString("RECIVEBY")
				.equals(getUserInfo().getLoginID()))
			throw new MroException("非接收人不可接收单据");
		if (msr.isNull("RECIVEBY"))
			throw new MroException("请填写，接收人");
		IJpoSet jpoSet = this.getAppBean().getJpo().getJpoSet("mprline");
		if (!jpoSet.isEmpty()) {
			for (int i = 0; i < jpoSet.count(); i++) {
				IJpo jpo = jpoSet.getJpo(i);
				if (jpo.getString("status").equals("已接收"))
					continue;
				String itemnum = jpo.getString("itemnum");// 物料编码
				String pc = jpo.getString("LOTNUM");// 批次
				String sqn = jpo.getString("sqn").toUpperCase();// 序列号
				String mprnum = jpo.getString("MPRNUM");// 入库单号
				String STOREROOM = this.getAppBean().getJpo()
						.getString("MPRSTOREROOM");// 库房
				boolean pcy = false;
				if (!pc.isEmpty() || !pc.equals("")) {// 是否批次号
					pcy = true;
				}
				boolean xly = false;
				if (!sqn.isEmpty() || !sqn.equals("")) {// 是否序列号
					xly = true;
				}
				double QTY = jpo.getDouble("QTY");// 接收数量

				Invtrans invtrans = new Invtrans();
				invtrans.recive(mprnum, "", itemnum, "", STOREROOM, QTY, jpo,
						"", xly, sqn, pcy, pc);
				jpo.setValue("status", "已接收");
			}

		}
		this.getAppBean().getJpo().setValue("status", "已接收");
		this.getAppBean().getJpo()
				.setValue("ENDTIME", MroServer.getMroServer().getDate());
		showMsgbox("提示", "接收成功");
		this.getAppBean().SAVE();

		return 0;

	}

	/**
	 * 删除校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int DELETE() throws MroException, IOException {
		if (this.getAppBean().getJpo().getString("RECIVEBY")
				.equals(getUserInfo().getLoginID()))
			throw new MroException("接收人不可删除单据");
		if (this.getAppName().equals("JSITEMREQ")
				&& this.getAppBean().getJpo().getString("status").equals("已接收"))
			throw new MroException("mprline", "delete");

		return super.DELETE();
	}

}
