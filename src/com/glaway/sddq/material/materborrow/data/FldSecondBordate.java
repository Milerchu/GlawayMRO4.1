package com.glaway.sddq.material.materborrow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件借用续借表续借日期字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-25]
 * @since  [产品/模块版本]
 */
public class FldSecondBordate extends JpoField {
	/**
	 * 初始续借日期
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		java.util.Date SECONDBORDATE = this.getJpo().getUserServer().getDate();
		this.getJpo().setValue("SECONDBORDATE", SECONDBORDATE,
				GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
	}
}
