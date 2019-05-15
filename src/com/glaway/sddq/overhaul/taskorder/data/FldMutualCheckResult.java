package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 互检员检查结果字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-18]
 * @since  [产品/模块版本]
 */
public class FldMutualCheckResult extends JpoField {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String personID=this.getJpo().getUserInfo().getPersonId();
		if(this.getJpo().getString("MUTUALCHECKRESULT").equals("Y")){
			this.getJpo().setValue("MUTUALEXAMINER", personID,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}else{
			this.getJpo().setValue("MUTUALEXAMINER", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}	
}
