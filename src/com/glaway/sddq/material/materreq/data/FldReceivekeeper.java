package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <配件申请接收人字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldReceivekeeper extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		return super.getList();
	}

	/**
	 * 获取接收人电话赋值到收货人联系电话
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		super.action();
		String personid = this.getValue();
		IJpoSet personSet = MroServer.getMroServer().getSysJpoSet("SYS_PERSON");
		personSet.setUserWhere(" personid ='" + personid + "' ");
		personSet.reset();
		if (!personSet.isEmpty()) {
			String phone = personSet.getJpo().getString("PRIMARYPHONE");
			this.getJpo().setValue("RECEIVEPHONE", phone);
		}
	}
}
