package com.glaway.sddq.service.servplan.data;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 服务计划 Jpo类
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-2-2]
 * @since  [MRO4.0/模块版本]
 */
public class ServicePlan extends Jpo
{
    
    private static final long serialVersionUID = 4524804666756674223L;
    
    @Override
    public void init()
        throws MroException
    {
        //状态
        String status = getString("STATUS");
        //当前登录用户
        String loginid = getUserInfo().getLoginID();
        
        //服务计划基本信息
        String[] basicInfo =
            {"SERVPLANNAME", "PROJECTNUM", "MODELS", "PROJECTTARGET", "COMMUNICATIONPLAN", "PROJECTRANGE", "RISKPLAN",
                "REMARK", "MODELNUM"};
        
        //工作流发起后禁止修改的字段
        if (!"草稿".equals(status))
        {
            List<IJpoSet> jpoSets = new ArrayList<IJpoSet>();
            //里程碑子表
            IJpoSet milestoneSet = getJpoSet("MILESTONE");
            //项目团队管理子表
            IJpoSet prjmemberSet = getJpoSet("PROJECTTEAMEMBER");
            //培训计划子表
            IJpoSet trainplanSet = getJpoSet("TRAINPLAN");
            //工具清单
            IJpoSet toollistSet = getJpoSet("TOOLIST");
            //用户资料计划
            IJpoSet userinfoSet = getJpoSet("USERINFO");
            //备品备件计划
            IJpoSet sparepartsSet = getJpoSet("SPAREPARTSLIST");
            //团队人工工时预算
            IJpoSet teampeoplebudgetSet = getJpoSet("WORKINGHOURSBUDGET");
            //项目费用预算
            IJpoSet prjfeebudgetSet = getJpoSet("EXPENSEBUDGET");
            //三包配件预算
            IJpoSet partsbudgetSet = getJpoSet("REPLACEMENTBUDGET");
            //干系人
            IJpoSet relatePeopleSet = getJpoSet("RELATEDPEOPLEREG");
            
            //设置所有字段只读
            setFieldFlag(basicInfo, GWConstant.S_READONLY, true);
            
            //设置子表只读
            jpoSets.add(milestoneSet);
            jpoSets.add(prjmemberSet);
            jpoSets.add(trainplanSet);
            jpoSets.add(toollistSet);
            jpoSets.add(userinfoSet);
            jpoSets.add(sparepartsSet);
            /*jpoSets.add(teampeoplebudgetSet);
            jpoSets.add(prjfeebudgetSet);
            jpoSets.add(partsbudgetSet);*/
            jpoSets.add(relatePeopleSet);
            setJpoSetsFlag(jpoSets, true);
            
            //对具体状态下权限进行控制
            if ("策划中".equals(status) || "重新策划".equals(status))
            {
                String servplanNum = getString("SERVPLANNUM");
                String role = getPrjteamRole(servplanNum, loginid);
                
                if (StringUtil.isStrNotEmpty(role))
                {
                    if ("售后质量经理".equals(role))
                    {
                        //里程碑
                        milestoneSet.setFlag(GWConstant.S_READONLY, false);
                    }
                    else if ("售后技术经理".equals(role))
                    {
                        //备品备件清单
                        sparepartsSet.setFlag(GWConstant.S_READONLY, false);
                        //工具清单
                        toollistSet.setFlag(GWConstant.S_READONLY, false);
                        //用户资料
                        userinfoSet.setFlag(GWConstant.S_READONLY, false);
                    }
                    else if ("售后培训经理".equals(role))
                    {
                        //培训计划
                        trainplanSet.setFlag(GWConstant.S_READONLY, false);
                    }
                }
                
            }
            else if ("评审".equals(status))
            {
                
            }
            else
            {//已发布状态 禁止修改当前记录
                setFlag(GWConstant.S_READONLY, true);
            }
        }
        super.init();
    }
    
    /**
     * 
     * 设置jposets中各子表的只读状态与否
     * @param jpoSets
     * @param flag [参数说明]
     *
     */
    public void setJpoSetsFlag(List<IJpoSet> jpoSets, boolean flag)
    {
        for (IJpoSet jposet : jpoSets)
        {
            jposet.setFlag(GWConstant.S_READONLY, flag);
        }
    }
    
    /**
     * 
     * 获取当前登录用户在项目中的角色
     * @param loginid 登录id
     * @param servplanNum 服务计划编号
     * @return
     * @throws MroException [参数说明]
     *
     */
    public String getPrjteamRole(String servplanNum, String loginid)
        throws MroException
    {
        String role = "";
        String where = "servplannum='" + servplanNum + "' and personid = '" + loginid.toUpperCase() + "'";
        IJpoSet prjteam = MroServer.getMroServer().getSysJpoSet("PROJECTTEAMEMBER", where);
        if (!prjteam.isEmpty())
        {
            IJpo jpo = prjteam.getJpo(0);
            role = jpo.getString("PROJECTROLE");
        }
        return role;
    }
}
