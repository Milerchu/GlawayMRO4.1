package com.glaway.sddq.config.soft.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 软件配置Jpo
 * 
 * @author  public2175
 * @version  [版本号, 2018-6-7]
 * @since  [产品/模块版本]
 */
public class SoftConfig extends StatusJpo implements IStatusJpo, FixedLoggers{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public void changestatus(String newstatus, String memo)
        throws MroException
    {
        if (!newstatus.equals(""))
        {
            String oldstatus = getString("STATUS");
            setValue("STATUS", newstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            IJpoSet ahstatusset = getJpoSet("SOFTSTATUSHISTORY");
            IJpo ahstatusnew = ahstatusset.addJpo();
            ahstatusnew.setValue("SOFTCONFIGNUM", getString("SOFTCONFIGNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("siteid", getString("siteid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("orgid", getString("orgid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("STATUS", oldstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("newstatus", newstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("REMARK", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setFlag(GWConstant.S_READONLY, true);
        }
        else
        {
            throw new AppException("custinfo", "cannotnull");
        }
    }
}
