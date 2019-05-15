package com.glaway.sddq.overhaul.jobbook.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
 * 标准作业指导书jpo
 * 
 * @author hyhe
 * @version [版本号, 2017-11-3]
 * @since [产品/模块版本]
 */
public class JobBook extends StatusJpo implements IStatusJpo, FixedLoggers
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        if (!this.isNew())
        {
            setFieldFlag("JPNUM", GWConstant.S_READONLY, true);
        }
        if (getString("STATUS").equals("已发布"))
        {
            setFieldFlag("JPNUM", GWConstant.S_READONLY, true);
            setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
            setFieldFlag("ITEMNUM", GWConstant.S_READONLY, true);
            setFieldFlag("REPAIRPROCESS", GWConstant.S_READONLY, true);
            setFieldFlag("FILENUM", GWConstant.S_READONLY, true);
            setFieldFlag("FILENAME", GWConstant.S_READONLY, true);
            setFieldFlag("VERSION", GWConstant.S_READONLY, true);
            setFieldFlag("JXCODE", GWConstant.S_READONLY, true);
            this.getJpoSet("JOBSCOPE").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("JOBBOOKTASK").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("OVERHAULMATERIAL").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("PERSONDEMAND").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("TOOLREQUIREMENT").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("JOBTASKRECORD").setFlag(GWConstant.S_READONLY, true);
            this.getJpoSet("JOBTESTRECORD").setFlag(GWConstant.S_READONLY, true);
        }
    }
    
    /**
     * 
     * 变更状态
     * @param newstatus
     * @param memo
     * @throws MroException [参数说明]
     *
     */
    public void changestatus(String newstatus, String memo)
        throws MroException
    {
        if (!newstatus.equals(""))
        {
            String oldstatus = getString("STATUS");
            setValue("STATUS", newstatus);
            IJpoSet ahstatusset = getJpoSet("JOBPLANSTATUSHISTORY");
            IJpo ahstatusnew = ahstatusset.addJpo();
            ahstatusnew.setValue("JPNUM", getString("JPNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
    
    @Override
    public IJpo duplicate()
        throws MroException
    {
        IJpo newjp = super.duplicate();
        Date createdate = MroServer.getMroServer().getDate();
        newjp.setValue("status", "草稿", GWConstant.P_NOVALIDATION_AND_NOACTION);
        newjp.setValue("CREATEDATE",createdate, GWConstant.P_NOVALIDATION_AND_NOACTION);
        String[] attrs = {"jpnum"};
        String[] vals = {newjp.getString("JPNUM")};
        
        // 标准作业指导书任务项
        //IJpoSet jobtaskSet =
            //getJpoSet("$JOBBOOKTASK", "JOBBOOKTASK", "JPNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        // 适用车型范围
        IJpoSet jobScopeSet =
            getJpoSet("$JOBSCOPE", "JOBSCOPE", "JPNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        jobScopeSet.setOrderBy(" JOBSCOPEID  ");
        // 检修物料清单
        IJpoSet overHaulMaterialSet =
            getJpoSet("$OVERHAULMATERIAL",
                "OVERHAULMATERIAL",
                "TASKNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        
        // 人员需求
        IJpoSet persondmandSet =
            getJpoSet("$PERSONDEMAND", "PERSONDEMAND", "TASKNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        
        // 工具需求
        IJpoSet toolsSet =
            getJpoSet("$TOOLREQUIREMENT", "TOOLREQUIREMENT", "TASKNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM"))
                + "'");
        //检查项
        IJpoSet jobtaskrecordSet =
                getJpoSet("$JOBTASKRECORD", "JOBTASKRECORD", "JPNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        //试验项目
        IJpoSet jobtestrecordSet =
            getJpoSet("$JOBTESTRECORD", "JOBTESTRECORD", "JPNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        //复制检查项
        if (!jobtaskrecordSet.isEmpty())
        {
            IJpoSet newjobtaskrecordSet = newjp.getJpoSet("JOBTASKRECORD", "JOBTASKRECORD", "1=0");
            for (int index = 0; index < jobtaskrecordSet.count(); index++)
            {
            	IJpo jobtaskrecord = jobtaskrecordSet.getJpo(index);
            	JobTaskRecord newjobtastrecord = (JobTaskRecord)newjobtaskrecordSet.addJpo();
            	newjobtastrecord.jcopyTask(jobtaskrecord, newjp);
            }
        }
        //复制试验项目
          if (!jobtestrecordSet.isEmpty())
          {               
              IJpoSet newjobtestrecordSet = newjp.getJpoSet("JOBTESTRECORD", "JOBTESTRECORD", "1=0");            
              for (int index = 0; index < jobtestrecordSet.count(); index++)
              {
                  IJpo jobtestrecord = jobtestrecordSet.getJpo(index);                 
                  JobTestRecord newjobtestrecord = (JobTestRecord)newjobtestrecordSet.addJpo();
                  newjobtestrecord.jcopyTask(jobtestrecord, newjp);
              }
            
          }
        
        
     /*   if (jobtaskSet.count() > 0)
        {
            IJpoSet newjobtaskSet = newjp.getJpoSet("JOBBOOKTASK", "JOBBOOKTASK", "1=0");
        	for (int index = 0;index < jobtaskSet.count();index++){
        		IJpo jpotask = jobtaskSet.getJpo(index);
        		JobBookTask newJpoTask = (JobBookTask) newjobtaskSet.addJpo();
        		newJpoTask.copyTask(jpotask,newjp);
        	}
        }*/
        
        if (!jobScopeSet.isEmpty())
        {
            IJpoSet newjobScopeSet = newjp.getJpoSet("JOBSCOPE", "JOBSCOPE", "1=0");
            newjobScopeSet.duplicate(jobScopeSet, GWConstant.P_NOVALIDATION);
            newjobScopeSet.updateAll(attrs, vals);
        }
        
        //检修对象
        IJpoSet jobproductscopeJpoSet =
            getJpoSet("$JOBPRODUCTSCOPE", "JOBPRODUCTSCOPE", "JPNUM='" + StringUtil.getSafeSqlStr(getString("JPNUM")) + "'");
        jobproductscopeJpoSet.setOrderBy(" JOBPRODUCTSCOPEID  ");

        if(jobproductscopeJpoSet != null && jobproductscopeJpoSet.count() > 0){
        	IJpoSet newJobproductscopeJpoSet = newjp.getJpoSet("JOBPRODUCTSCOPE", "JOBPRODUCTSCOPE", "1=0");
        	newJobproductscopeJpoSet.duplicate(jobproductscopeJpoSet, GWConstant.P_NOVALIDATION);
        	newJobproductscopeJpoSet.updateAll(attrs, vals);
        }
        
        String[] attrs2 = {"TASKNUM"};
        String[] vals2 = {newjp.getString("JPNUM")};
        if (!overHaulMaterialSet.isEmpty())
        {
            IJpoSet newoverHaulMaterialSet = newjp.getJpoSet("OVERHAULMATERIAL", "OVERHAULMATERIAL", "1=0");
            newoverHaulMaterialSet.duplicate(overHaulMaterialSet, GWConstant.P_NOVALIDATION);
            newoverHaulMaterialSet.updateAll(attrs2, vals2);
        }
        if (!persondmandSet.isEmpty())
        {
            IJpoSet newPersondmandSet = newjp.getJpoSet("PERSONDEMAND", "PERSONDEMAND", "1=0");
            newPersondmandSet.duplicate(persondmandSet, GWConstant.P_NOVALIDATION);
            newPersondmandSet.updateAll(attrs2, vals2);
        }
        if (!toolsSet.isEmpty())
        {
            IJpoSet newtoolSet = newjp.getJpoSet("TOOLREQUIREMENT", "TOOLREQUIREMENT", "1=0");
            newtoolSet.duplicate(toolsSet, GWConstant.P_NOVALIDATION);
            newtoolSet.updateAll(attrs2, vals2);
        }
        return newjp;
    }
    
    
    
    
    /**
     * 
     * 复制任务项
     * @param srcTask
     * @throws MroException [参数说明]
     *
     */
    public  void copyTask(IJpo newTask,IJpo oldTask) throws MroException{
    	
//      newjobtaskSet.duplicate(jobtaskSet, GWConstant.P_NOVALIDATION);
//      newjobtaskSet.updateAll(attrs, vals);
//      for (int index = 0; index < newjobtaskSet.count(); index++)
//      {
//          IJpo jpo = newjobtaskSet.getJpo(index);
//          String jobtasknum = jpo.getString("TASKNUM");
//          String oldtasknum = jobtaskSet.getJpo(index).getString("TASKNUM");
//          //检查项
//          IJpoSet jobtaskrecordSet =
//              jpo.getJpoSet("$JOBTASKRECORDTMP_"+index, "JOBTASKRECORD", "TASKNUM='" + oldtasknum + "'");
//          
//          String[] attrss = {"tasknum","jpnum"};
//          String[] valss = {jobtasknum,newjp.getString("JPNUM")};
//          if (!jobtaskrecordSet.isEmpty())
//          {
//              IJpoSet newjobtaskrecordSet = jpo.getJpoSet("JOBTASKRECORD", "JOBTASKRECORD", "1=0");
//              newjobtaskrecordSet.duplicate(jobtaskrecordSet, GWConstant.P_NOVALIDATION);
//              newjobtaskrecordSet.updateAll(attrss, valss);
//          }
//          
//          //试验项目
//          IJpoSet jobtestrecordSet =
//              jpo.getJpoSet("$JOBTESTRECORDTMP", "JOBTESTRECORD", "TASKNUM='" + oldtasknum + "'");
//          if (!jobtestrecordSet.isEmpty())
//          {
//          	jobtestrecordSet.setOrderBy("TASKNUM desc");
//          	jobtestrecordSet.reset();
//               
//              IJpoSet newjobtestrecordSet = jpo.getJpoSet("JOBTESTRECORD", "JOBTESTRECORD", "1=0");
//              newjobtestrecordSet.duplicate(jobtestrecordSet, GWConstant.P_NOVALIDATION);
//              newjobtestrecordSet.updateAll(attrss, valss);
//              
//              for (int jobindex = 0; jobindex < newjobtestrecordSet.count(); jobindex++)
//              {
//                  IJpo jobjpo = newjobtestrecordSet.getJpo(jobindex);
//                  String jobschemenum = jobjpo.getString("TASKNUM");
//                  String oldschemenum = jobtestrecordSet.getJpo(jobindex).getString("TASKNUM");
//                  //检测项目
//                  IJpoSet jobtestrecordingSet =
//                      jobjpo.getJpoSet("$JOBTESTRECORDING_" + jobindex+"_"+index, "JOBTESTRECORDING", "TASKNUM='"
//                          + oldschemenum + "'");
//                  String[] jobattr = {"TASKNUM"};
//                  String[] jobval = {jobschemenum};
//                  if (!jobtestrecordingSet.isEmpty())
//                  {
//                      IJpoSet newjobtestrecordingSet =
//                          jobjpo.getJpoSet("JOBTESTRECORDINGTMP_" + jobindex+"_"+index, "JOBTESTRECORDING", "1=0");
//                      newjobtestrecordingSet.duplicate(jobtestrecordingSet, GWConstant.P_NOVALIDATION);
//                      newjobtestrecordingSet.updateAll(jobattr, jobval);
//                  }
//              }
//          }
//      }
    }
    
    @Override
    public void delete()
        throws MroException
    {
        
        if (getString("STATUS").equals("作废"))
        {
            IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("REPAIRSCOPE");
            jposet.setUserWhere("SOPNUM='" + this.getString("JPNUM") + "'");
            if (jposet != null && jposet.count() > 0)
            {
                throw new AppException("jobbook", "isForSche");
            }
            else
            {
                if (this.getJpoSet("JOBPLANSTATUSHISTORY") != null)
                {
                    this.getJpoSet("JOBPLANSTATUSHISTORY").deleteAll();
                }
                if (this.getJpoSet("JOBSCOPE") != null)
                {
                    this.getJpoSet("JOBSCOPE").deleteAll();
                }
              /*  if (this.getJpoSet("JOBBOOKTASK") != null)
                {
                    this.getJpoSet("JOBBOOKTASK").deleteAll();
                }*/
                if (this.getJpoSet("OVERHAULMATERIAL") != null)
                {
                    this.getJpoSet("OVERHAULMATERIAL").deleteAll();
                }
                if (this.getJpoSet("PERSONDEMAND") != null)
                {
                    this.getJpoSet("PERSONDEMAND").deleteAll();
                }
                if (this.getJpoSet("TOOLREQUIREMENT") != null)
                {
                    this.getJpoSet("TOOLREQUIREMENT").deleteAll();
                }
                if (this.getJpoSet("JOBVERSIONHISTORY") != null)
                {
                    this.getJpoSet("JOBVERSIONHISTORY").deleteAll();
                }
                if (this.getJpoSet("JOBTASKRECORD")!=null)
                {
                	this.getJpoSet("JOBTASKRECORD").deleteAll();
                }                 	
                if (this.getJpoSet("JOBTESTRECORD")!=null)
                {
                	this.getJpoSet("JOBTESTRECORD").deleteAll();
                	
                 }    
                IJpoSet jobtestrecordSet = this.getJpoSet("JOBTESTRECORD");
                for (int index = 0; index < jobtestrecordSet.count(); index++)
                {
                	  IJpo jobtestrecord = jobtestrecordSet.getJpo(index);
                	  if (jobtestrecord.getJpoSet("JOBTESTRECORDING") != null)
                      {
                		  jobtestrecord.getJpoSet("JOBTESTRECORDING").deleteAll();
                      }
                	  
                }
                super.delete();
            }
        }
        else if (getString("STATUS").equals("已发布"))
        {
            throw new AppException("jobbook", "cannotnull");
        }
        else
        {
            if (this.getJpoSet("JOBPLANSTATUSHISTORY") != null)
            {
                this.getJpoSet("JOBPLANSTATUSHISTORY").deleteAll();
            }
            if (this.getJpoSet("JOBSCOPE") != null)
            {
                this.getJpoSet("JOBSCOPE").deleteAll();
            }
        /*    if (this.getJpoSet("JOBBOOKTASK") != null)
            {
                this.getJpoSet("JOBBOOKTASK").deleteAll();
            }*/
            if (this.getJpoSet("OVERHAULMATERIAL") != null)
            {
                this.getJpoSet("OVERHAULMATERIAL").deleteAll();
            }
            if (this.getJpoSet("PERSONDEMAND") != null)
            {
                this.getJpoSet("PERSONDEMAND").deleteAll();
            }
            if (this.getJpoSet("TOOLREQUIREMENT") != null)
            {
                this.getJpoSet("TOOLREQUIREMENT").deleteAll();
            }
            if (this.getJpoSet("JOBVERSIONHISTORY") != null)
            {
                this.getJpoSet("JOBVERSIONHISTORY").deleteAll();
            }
            if (this.getJpoSet("JOBTASKRECORD")!=null)
            {
            	this.getJpoSet("JOBTASKRECORD").deleteAll();
            }
              	
            if (this.getJpoSet("JOBTESTRECORD")!=null)
            {
            	this.getJpoSet("JOBTESTRECORD").deleteAll();
            }
            IJpoSet jobtestrecordSet = this.getJpoSet("JOBTESTRECORD");
            for (int index = 0; index < jobtestrecordSet.count(); index++)
            {
            	  IJpo jobtestrecord = jobtestrecordSet.getJpo(index);
            	  if (jobtestrecord.getJpoSet("JOBTESTRECORDING") != null)
                  {
            		  jobtestrecord.getJpoSet("JOBTESTRECORDING").deleteAll();
                  }
            }
           /* IJpoSet jobjposet = this.getJpoSet("JOBBOOKTASK");
            for (int index = 0; index < jobjposet.count(); index++)
            {
                IJpo jpo = jobjposet.getJpo(index);
                
                if (jpo.getJpoSet("JOBTASKRECORD") != null)
                {
                    jpo.getJpoSet("JOBTASKRECORD").deleteAll();
                }
                if (jpo.getJpoSet("JOBTESTRECORD") != null)
                {
                    jpo.getJpoSet("JOBTESTRECORD").deleteAll();
                }
                IJpoSet jbjposet = jpo.getJpoSet("JOBTESTRECORD");
                for (int ind = 0; ind < jbjposet.count(); ind++)
                {
                    IJpo jbjpo = jbjposet.getJpo(ind);
                    if (jbjpo.getJpoSet("JOBTESTRECORDING") != null)
                    {
                        jbjpo.getJpoSet("JOBTESTRECORDING").deleteAll();
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
            String oldversion = getString("VERSION");
            IJpoSet versionset = getJpoSet("JOBVERSIONHISTORY");
            IJpo versionnew = versionset.addJpo();
            versionnew.setValue("VERSION", oldversion, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("NEWVERSION", newversion, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("REMARK", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("JPNUM", getString("JPNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("NEWJPNUM", newJpo.getString("JPNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setValue("CHANGEDATE",
                MroServer.getMroServer().getDate(),
                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            versionnew.setFlag(GWConstant.S_READONLY, true);
        }
        else
        {
            throw new AppException("jobversionhistory", "version");
        }
    }
    
    /**
     * 
     * 获取车型范围集合
     * @return
     * @throws MroException [参数说明]
     *
     */
    public Set<String> getCmodels()
        throws MroException
    {
        Set<String> cmodels = new HashSet<String>();
        if (this.getJpoSet("JOBSCOPE") != null)
        {
            IJpoSet jposet = this.getJpoSet("JOBSCOPE");
            for (int index = 0; index < jposet.count(); index++)
            {
                cmodels.add(jposet.getJpo(index).getString("cmodel"));
            }
        }
        return cmodels;
    }
}
