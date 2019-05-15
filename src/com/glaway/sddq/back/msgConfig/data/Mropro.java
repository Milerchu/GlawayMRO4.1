package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * 系统问题记录Jpo
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-27]
 * @since  [产品/模块版本]
 */
public class Mropro extends StatusJpo implements IStatusJpo, FixedLoggers {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if(!this.isNew()){
			String status = this.getString("status");
			if(StringUtil.isEqualIgnoreCase(status, "关闭")||
					StringUtil.isEqualIgnoreCase(status, "延期")){
				this.setFlag(GWConstant.S_READONLY, true);
			}
		}
	}
	
	 public void changestatus(String newstatus, String memo)
		        throws MroException
		    {
		        if (!newstatus.equals(""))
		        {
		            String oldstatus = getString("STATUS");
		            setValue("STATUS", newstatus);
		            IJpoSet ahstatusset = getJpoSet("MROPROSTATUSHISTORY");
		            IJpo ahstatusnew = ahstatusset.addJpo();
		            ahstatusnew.setValue("PRONUM", getString("PRONUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            ahstatusnew.setValue("siteid", getString("siteid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            ahstatusnew.setValue("orgid", getString("orgid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            ahstatusnew.setValue("STATUS", oldstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            ahstatusnew.setValue("newstatus", newstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            ahstatusnew.setValue("REMARK", memo, 11L);
		            ahstatusnew.setFlag(7L, true);
		        }
		        else
		        {
		            throw new AppException("custinfo", "cannotnull");
		        }
		    }
}
