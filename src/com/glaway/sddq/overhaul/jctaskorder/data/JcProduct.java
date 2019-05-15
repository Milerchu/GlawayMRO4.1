package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;
/**
 * 
 * 交车产品列表JPO
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-8]
 * @since  [产品/模块版本]
 */
public class JcProduct extends StatusJpo implements IStatusJpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String status = this.getString("STATUS");
		if (SddqConstant.JC_T_STATUS.equals(status)) 
		{
			setFieldFlag("CARNO", GWConstant.S_READONLY, true);
            setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
            setFieldFlag("CARRIAGENUM", GWConstant.S_READONLY, true);
            this.getJpoSet("JXTESTRECORD").setFlag( GWConstant.S_READONLY, true);
            //this.getJpoSet("EXCHANGERECORD").setFlag( GWConstant.S_READONLY, true);
		}

	}

}
