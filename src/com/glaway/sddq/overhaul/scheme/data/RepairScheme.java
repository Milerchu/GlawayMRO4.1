package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修方案jpo
 * 
 * @author hyhe
 * @version [版本号, 2017-10-19]
 * @since [产品/模块版本]
 */
public class RepairScheme extends StatusJpo implements IStatusJpo, FixedLoggers
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        if (getString("STATUS").equals("已发布"))
        {
            setFieldFlag("SCHEMENUM", GWConstant.S_READONLY, true);
            setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
            setFieldFlag("VERSION", GWConstant.S_READONLY, true);
            setFieldFlag("JXNUM", GWConstant.S_READONLY, true);
            setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
            setFieldFlag("REPAIRPROCESS", GWConstant.S_READONLY, true);
            setFieldFlag("PERIOD", GWConstant.S_READONLY, true);
            this.getJpoSet("SCHEMENUM").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("REPAIRSCOPE").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("JCJOBTESTRECORD").setFlag(GWConstant.S_READONLY, true);
        }
    }
    
    /**
     * 
     * 变更状态
     * 
     * @param newstatus
     * @param memo
     * @throws MroException
     *             [参数说明]
     * 
     */
    public void changestatus(String newstatus, String memo)
        throws MroException
    {
        if (!newstatus.equals(""))
        {
            String oldstatus = getString("STATUS");
            setValue("STATUS", newstatus);
            IJpoSet ahstatusset = getJpoSet("REPAIRSCHEMESTATUSHISTORY");
            IJpo ahstatusnew = ahstatusset.addJpo();
            ahstatusnew.setValue("SCHEMENUM", getString("SCHEMENUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
    
    @Override
    public void delete()
        throws MroException
    {
        if (getString("STATUS").equals("作废"))
        {
            IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("REPAIRPLANS");
            jposet.setUserWhere("SCHEMENUM='" + this.getString("SCHEMENUM") + "'");
            if (jposet != null && jposet.count() > 0)
            {
                throw new AppException("REPAIRPLANS", "cannotnull");
            }
            else
            {
                if (this.getJpoSet("REPAIRSCHEMESTATUSHISTORY") != null)
                {
                    this.getJpoSet("REPAIRSCHEMESTATUSHISTORY").deleteAll();
                }
                if (this.getJpoSet("REPAIRSCHEMEVERSIONHISTROY") != null)
                {
                    this.getJpoSet("REPAIRSCHEMEVERSIONHISTROY").deleteAll();
                }
                if (this.getJpoSet("REPAIRSCOPE") != null)
                {
                    IJpoSet repairJpoSet = this.getJpoSet("REPAIRSCOPE");
                    for (int index = 0; index < repairJpoSet.count(); index++)
                    {
                        //repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBBOOKTASK").deleteAll();
                        repairJpoSet.getJpo(index).getJpoSet("SCHEMEOVERHAULMATERIAL").deleteAll();
                        repairJpoSet.getJpo(index).getJpoSet("SCHEMEPERSONDEMAND").deleteAll();
                        repairJpoSet.getJpo(index).getJpoSet("SCHEMETOOLREQUIREMENT").deleteAll();
                        repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTASKRECORD").deleteAll();
                        if (repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD") != null)
                        {
                            IJpoSet schemejobtestrecordSet = repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD");
                            for (int i = 0; i < schemejobtestrecordSet.count(); i++)
                            {
                            	schemejobtestrecordSet.getJpo(i).getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
                            }
                            repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD").deleteAll();
                        }
                    }
                    this.getJpoSet("REPAIRSCOPE").deleteAll();
                }
                if (this.getJpoSet("SCHEMENUM") != null)
                {
                    this.getJpoSet("SCHEMENUM").deleteAll();
                }
            
                if (this.getJpoSet("JCJOBTESTRECORD") != null)
                {
                    IJpoSet jcjobtestrecordset = this.getJpoSet("JCJOBTESTRECORD");
                    for (int index = 0; index < jcjobtestrecordset.count(); index++)
                    {
                        jcjobtestrecordset.getJpo(index).getJpoSet("JCJOBTESTRECORDING").deleteAll();
                    }
                    this.getJpoSet("JCJOBTESTRECORD").deleteAll();
                }
                super.delete();
            }
        }
        else if (getString("STATUS").equals("已发布"))
        {
            throw new AppException("REPAIRPLANS", "cannotnull1");
        }
        else
        {
            if (this.getJpoSet("REPAIRSCHEMESTATUSHISTORY") != null)
            {
                this.getJpoSet("REPAIRSCHEMESTATUSHISTORY").deleteAll();
            }
            if (this.getJpoSet("REPAIRSCHEMEVERSIONHISTROY") != null)
            {
                this.getJpoSet("REPAIRSCHEMEVERSIONHISTROY").deleteAll();
            }
            if (this.getJpoSet("REPAIRSCOPE") != null)
            {
                IJpoSet repairJpoSet = this.getJpoSet("REPAIRSCOPE");
                for (int index = 0; index < repairJpoSet.count(); index++)
                {
                    //repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBBOOKTASK").deleteAll();
                    repairJpoSet.getJpo(index).getJpoSet("SCHEMEOVERHAULMATERIAL").deleteAll();
                    repairJpoSet.getJpo(index).getJpoSet("SCHEMEPERSONDEMAND").deleteAll();
                    repairJpoSet.getJpo(index).getJpoSet("SCHEMETOOLREQUIREMENT").deleteAll();
                    repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTASKRECORD").deleteAll();
                    if (repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD") != null)
                    {
                        IJpoSet schemejobtestrecordSet = repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD");
                        for (int i = 0; i < schemejobtestrecordSet.count(); i++)
                        {
                        	schemejobtestrecordSet.getJpo(i).getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
                        }
                        repairJpoSet.getJpo(index).getJpoSet("SCHEMEJOBTESTRECORD").deleteAll();
                    }
                }
                this.getJpoSet("REPAIRSCOPE").deleteAll();
            }
            if (this.getJpoSet("SCHEMENUM") != null)
            {
                this.getJpoSet("SCHEMENUM").deleteAll();
            }
            if (this.getJpoSet("JCJOBTESTRECORD") != null)
            {
                IJpoSet jcjobtestrecordset = this.getJpoSet("JCJOBTESTRECORD");
                for (int index = 0; index < jcjobtestrecordset.count(); index++)
                {
                    jcjobtestrecordset.getJpo(index).getJpoSet("JCJOBTESTRECORDING").deleteAll();
                }
                this.getJpoSet("JCJOBTESTRECORD").deleteAll();
            }
            //任务项存在删除
           /* IJpoSet jobjposet = this.getJpoSet("REPAIRSCOPE");
            for (int index = 0; index < jobjposet.count(); index++)
            {
                IJpo jpo = jobjposet.getJpo(index);
                
                if (jpo.getJpoSet("SCHEMEJOBBOOKTASK") != null)
                {
                    jpo.getJpoSet("SCHEMEJOBBOOKTASK").deleteAll();
                }
                IJpoSet jposet = jpo.getJpoSet("SCHEMEJOBBOOKTASK");
                for (int ind = 0; ind < jposet.count(); ind++)
                {
                    IJpo jxjpo = jposet.getJpo(ind);
                    
                    if (jxjpo.getJpoSet("SCHEMEJOBTASKRECORD") != null)
                    {
                        jxjpo.getJpoSet("SCHEMEJOBTASKRECORD").deleteAll();
                    }
                    if (jxjpo.getJpoSet("SCHEMEJOBTESTRECORD") != null)
                    {
                        jxjpo.getJpoSet("SCHEMEJOBTESTRECORD").deleteAll();
                    }
                    IJpoSet jbjposet = jxjpo.getJpoSet("SCHEMEJOBTESTRECORD");
                    for (int jbindex = 0; jbindex < jbjposet.count(); jbindex++)
                    {
                        IJpo jbjpo = jbjposet.getJpo(jbindex);
                        if (jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
                        {
                            jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
                        }
                    }
                }
            }*/
            super.delete();
        }
    }
    
    /**
     * 
     * 版本变更历史  
     * @param newversion
     * @param memo
     * @param newJpo
     * @throws MroException [参数说明]
     *
     */
    public void changeVersion(String newversion, String memo, IJpo newJpo)
        throws MroException
    {
        if (!newversion.equals(""))
        {
            String oldversion = getString("version");
            IJpoSet versionset = getJpoSet("REPAIRSCHEMEVERSIONHISTROY");
            IJpo versionnew = versionset.addJpo();
            versionnew.setValue("version", oldversion, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("newversion", newversion, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("remark", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("schemenum", getString("SCHEMENUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("newschemenum", newJpo.getString("SCHEMENUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        else
        {
            throw new AppException("repairversionhistroy", "version");
        }
    }
    
    @Override
    public IJpo duplicate()
        throws MroException
    {
        String curSchemeNum = getString("SCHEMENUM");
        IJpo newjp = super.duplicate();
        newjp.setValue("status", "草稿", GWConstant.P_NOVALIDATION_AND_NOACTION);
        String[] attr = {"schemenum"};
        String[] val = {newjp.getString("SCHEMENUM")};
        String newSchemeNum = newjp.getString("SCHEMENUM");
        //复制承修单位
        IJpoSet factorySet =
            this.getJpoSet("$CHENGXIUUNIT",
                "CHENGXIUUNIT",
                "SCHEMENUM='" + StringUtil.getSafeSqlStr(getString("SCHEMENUM")) + "'");
        
        if (!factorySet.isEmpty())
        {
            IJpoSet newfactorySet = newjp.getJpoSet("CHENGXIUUNIT", "CHENGXIUUNIT", "1=0");
            newfactorySet.duplicate(factorySet, GWConstant.P_NOVALIDATION);
            newfactorySet.updateAll(attr, val);
        }
        //复制交车检查项 
        IJpoSet jcjobtestrecordset =
            this.getJpoSet("$JCJOBTESTRECORD",
                "JCJOBTESTRECORD",
                "SCHEMENUM='" + StringUtil.getSafeSqlStr(getString("SCHEMENUM")) + "'");
        if (!jcjobtestrecordset.isEmpty())
        {
            IJpoSet newjcjobtestrecordset = newjp.getJpoSet("JCJOBTESTRECORDTMP", "JCJOBTESTRECORD", "1=0");
            for (int index = 0; index < jcjobtestrecordset.count(); index++)
            {
                IJpo jcjobtestrecord = jcjobtestrecordset.getJpo(index);
                JcJobTestRecord newjcjobtestrecord = (JcJobTestRecord)newjcjobtestrecordset.addJpo();
                newjcjobtestrecord.jccopyTask(jcjobtestrecord, newjp);
            }
        }
        
        //复制检修范围
        IJpoSet repairScopeSet =
            this.getJpoSet("$REPAIRSCOPE",
                "REPAIRSCOPE",
                "SCHEMENUM='" + StringUtil.getSafeSqlStr(getString("SCHEMENUM")) + "'");
        if (!repairScopeSet.isEmpty())
        {
            IJpoSet newrepairScopeSet = newjp.getJpoSet("REPAIRSCOPETMP", "REPAIRSCOPE", "1=0");
            for (int index = 0; index < repairScopeSet.count(); index++)
            {
                IJpo repairScope = repairScopeSet.getJpo(index);
                RepairScope newRepairScope = (RepairScope)newrepairScopeSet.addJpo();
                newRepairScope.jxcopyTask(repairScope, newjp);
                String oldtasknum = repairScopeSet.getJpo(index).getString("TASKNUM");
                String tasknum = newRepairScope.getString("TASKNUM");
                // 检修物料清单
                IJpoSet overHaulMaterialSet =
                    this.getJpoSet("$OVERHAULMATERIAL", "SCHEMEOVERHAULMATERIAL", "SCHEMENUM='" + curSchemeNum
                        + "' and TASKNUM='" + oldtasknum + "'");
                
                // 人员需求
                IJpoSet persondmandSet =
                    repairScope.getJpoSet("$PERSONDEMAND", "SCHEMEPERSONDEMAND", "SCHEMENUM='" + curSchemeNum
                        + "' and TASKNUM='" + oldtasknum + "'");
                
                // 工具需求
                IJpoSet toolsSet =
                    repairScope.getJpoSet("$TOOLREQUIREMENT", "SCHEMETOOLREQUIREMENT", "SCHEMENUM='" + curSchemeNum
                        + "' and TASKNUM='" + oldtasknum + "'");
                
                
                String[] attrs = {"tasknum", "schemenum"};
                String[] vals = {tasknum, newSchemeNum};
                
                //                if (jobtaskSet.count() > 0)
                //                {
                //                    IJpoSet newjobtaskSet = newjp.getJpoSet("SCHEMEJOBBOOKTASK", "SCHEMEJOBBOOKTASK", "1=0");
                //                    for (int j = 0; j < jobtaskSet.count(); j++)
                //                    {
                //                        IJpo jpotask = jobtaskSet.getJpo(j);
                //                        SchemeJobBookTask newJpoTaskn = (SchemeJobBookTask)newjobtaskSet.addJpo();
                //                        newJpoTaskn.copyTask(jpotask, newjp);
                //                    }
                //                }
                if (!overHaulMaterialSet.isEmpty())
                {
                    IJpoSet newoverHaulMaterialSet =
                        repairScope.getJpoSet("OVERHAULMATERIAL", "SCHEMEOVERHAULMATERIAL", "1=0");
                    newoverHaulMaterialSet.duplicate(overHaulMaterialSet, GWConstant.P_NOVALIDATION);
                    newoverHaulMaterialSet.updateAll(attrs, vals);
                }
                if (!persondmandSet.isEmpty())
                {
                    IJpoSet newPersondmandSet = repairScope.getJpoSet("PERSONDEMAND", "SCHEMEPERSONDEMAND", "1=0");
                    newPersondmandSet.duplicate(persondmandSet, GWConstant.P_NOVALIDATION);
                    newPersondmandSet.updateAll(attrs, vals);
                }
                if (!toolsSet.isEmpty())
                {
                    IJpoSet newtoolSet = repairScope.getJpoSet("TOOLREQUIREMENT", "SCHEMETOOLREQUIREMENT", "1=0");
                    newtoolSet.duplicate(toolsSet, GWConstant.P_NOVALIDATION);
                    newtoolSet.updateAll(attrs, vals);
                }
            }
        }
        return newjp;
    }
}
