package com.glaway.sddq.service.servplan.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <服务计划 里程碑模板 dialog bean>
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-4-26]
 * @since  [MRO4.0/模块版本]
 */
public class SelectMileStoneTmpDialog extends DataBean
{
    @Override
    public int execute()
        throws IOException, MroException
    {
        DataBean msdataBean = this.page.getAppBean().getDataBean("1508391589093");
        IJpoSet selectSets = this.page.getAppBean().getDataBean("selectmstmp_select_table").getJpoSet();
        
        IJpoSet milestoneset = MroServer.getMroServer().getJpoSet("MILESTONE", mroSession.getUserServer());
        
        String servplannum = getAppBean().getJpo().getString("servplannum");
        List<IJpo> list = selectSets.getSelections();
        if (!list.isEmpty())
        {
            for (int i = 0; i < list.size(); i++)
            {
                IJpo mstmp = list.get(i);
                IJpo milestone = milestoneset.addJpo();
                milestone.setValue("STAGENUM", mstmp.getString("STAGENUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                milestone.setValue("STAGENAME", mstmp.getString("STAGENAME"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                milestone.setValue("MILESTONENUM",
                    mstmp.getString("MILESTONENUM"),
                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                milestone.setValue("MILESTONENAME",
                    mstmp.getString("MILESTONENAME"),
                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                milestone.setValue("RESPROLE", mstmp.getString("RESPROLE"));
                milestone.setValue("MILESTONETIME", mstmp.getString("MILESTONETIME"));
                milestone.setValue("ISEDITED", mstmp.getString("ISEDITED"));
                milestone.setValue("SERVPLANNUM", servplannum);
                milestone.setValue("ORGID", "CRRC");
                milestone.setValue("SITEID", "ELEC");
                
            }
        }
        milestoneset.save();
        msdataBean.getTable().loadBaseCtrlData();
        //milestoneset.destroy();
        return super.execute();
    }
}
