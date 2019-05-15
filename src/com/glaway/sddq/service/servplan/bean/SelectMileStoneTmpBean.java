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
 * <选择里程碑模板>
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-18]
 * @since  [产品/模块版本]
 */
public class SelectMileStoneTmpBean extends DataBean
{
    
    @Override
    public void buildJpoSet()
        throws MroException
    {
        
        super.buildJpoSet();
        //已选择里程碑set
        IJpoSet milestoneSet = getAppBean().getDataBean("1508391589093").getJpoSet();
        String milestonenums = "";
        if (!milestoneSet.isEmpty())//不为空则过滤掉已选择的
        {
            //遍历已选择的里程碑
            for (int i = 0; i < milestoneSet.count(); i++)
            {
                IJpo msJpo = milestoneSet.getJpo(i);
                milestonenums += "'" + msJpo.getString("milestonenum") + "',";
            }
            
        }
        //过滤掉不可裁剪的里程碑
        IJpoSet mstmpSet = MroServer.getMroServer().getSysJpoSet("milestonetemp", "isedited=0");
        for (int j = 0; j < mstmpSet.count(); j++)
        {
            IJpo jpo = mstmpSet.getJpo(j);
            milestonenums += "'" + jpo.getString("milestonenum") + "',";
        }
        
        milestonenums = milestonenums.substring(0, milestonenums.length() - 1);
        
        //过滤弹出表格数据
        jpoSet.setUserWhere("milestonenum not in (" + milestonenums + ")");
        jpoSet.reset();
        //选中不可裁剪的里程碑模板
        /*for (int j = 0; j < jpoSet.count(); j++)
        {
            IJpo mstmp = jpoSet.getJpo(j);
            boolean isedited = mstmp.getBoolean("ISEDITED");
            if (!isedited)
            {
                mstmp.select();
            }
        }*/
        
    }
    
    @Override
    public int execute()
        throws IOException, MroException
    {
        //        DataBean msdataBean = getAppBean().getDataBean("1508391589093");
        
        // IJpoSet milestoneset = MroServer.getMroServer().getJpoSet("MILESTONE", mroSession.getUserServer());
        IJpoSet milestoneset = getAppBean().getJpo().getJpoSet("MILESTONE");
        
        String servplannum = getAppBean().getJpo().getString("SERVPLANNUM");
        List<IJpo> list = getAppBean().getDataBean("selectmstmp_select_table").getJpoSet().getSelections();
        //添加不可裁剪的里程碑
        IJpoSet milestoneSet = getAppBean().getDataBean("1508391589093").getJpoSet();
        if (milestoneSet.isEmpty())
        {
            IJpoSet milestoneTempSet = MroServer.getMroServer().getSysJpoSet("milestonetemp", " isedited=0 ");
            for (int i = 0; i < milestoneTempSet.count(); i++)
            {
                list.add(milestoneTempSet.getJpo(i));
            }
        }
        
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
                milestone.setValue("ORGID", getAppBean().getJpo().getString("ORGID"));
                milestone.setValue("SITEID", getAppBean().getJpo().getString("SITEID"));
            }
        }
        // milestoneset.save();
        // msdataBean.resetAndReload();
        //  milestoneset.destroy();
        return super.execute();
    }
    
}
