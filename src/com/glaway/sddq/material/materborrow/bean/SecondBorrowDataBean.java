package com.glaway.sddq.material.materborrow.bean;

import java.io.IOException;
import java.sql.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件借用续借窗口功能类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-25]
 * @since  [产品/模块版本]
 */
public class SecondBorrowDataBean extends DataBean {
	/**
	 * 确认按钮赋值到续借时间，续借承诺归还时间
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	public int execute() throws MroException, IOException {
		java.util.Date SECONDBORDATE=this.getJpo().getDate("SECONDBORDATE");//续借日期
		java.util.Date SECONDPLANRETURNDATE=this.getJpo().getDate("SECONDPLANRETURNDATE");//续借承诺归还时间
		this.getParent().getJpo().setValue("SECONDBORDATE", SECONDBORDATE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getParent().getJpo().setValue("SECONDPLANRETURNDATE", SECONDPLANRETURNDATE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getAppBean().SAVE();

		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
