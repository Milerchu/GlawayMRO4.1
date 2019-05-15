package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修范围jpo
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-17]
 * @since  [产品/模块版本]
 */
public class RepairScope extends Jpo implements IJpo
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @throws MroException
     */
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
      /*  if (this.getJpoSet("SCHEMEJOBBOOKTASK") != null)
        {
            this.getJpoSet("SCHEMEJOBBOOKTASK").deleteAll(flag);
        }*/
        if (this.getJpoSet("SCHEMEOVERHAULMATERIAL") != null)
        {
            this.getJpoSet("SCHEMEOVERHAULMATERIAL").deleteAll(flag);
        }
        if (this.getJpoSet("SCHEMEPERSONDEMAND") != null)
        {
            this.getJpoSet("SCHEMEPERSONDEMAND").deleteAll(flag);
        }
        if (this.getJpoSet("SCHEMETOOLREQUIREMENT") != null)
        {
            this.getJpoSet("SCHEMETOOLREQUIREMENT").deleteAll(flag);
        }
        if (this.getJpoSet("SCHEMEJOBTASKRECORD") != null)
        {
            this.getJpoSet("SCHEMEJOBTASKRECORD").deleteAll(flag);
        }
        if (this.getJpoSet("SCHEMEJOBTESTRECORD") != null)
        {
            this.getJpoSet("SCHEMEJOBTESTRECORD").deleteAll(flag);
        }
       
        IJpoSet jposet = this.getJpoSet("SCHEMEJOBTESTRECORD");
        for (int ind = 0; ind < jposet.count(); ind++)
        {
            IJpo jxjpo = jposet.getJpo(ind);
            if (jxjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll(flag);
            }
        }
  
        /*IJpoSet jposet = this.getJpoSet("SCHEMEJOBBOOKTASK");
        for (int ind = 0; ind < jposet.count(); ind++)
        {
            IJpo jxjpo = jposet.getJpo(ind);
            
            if (jxjpo.getJpoSet("SCHEMEJOBTASKRECORD") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTASKRECORD").deleteAll(flag);
            }
            if (jxjpo.getJpoSet("SCHEMEJOBTESTRECORD") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTESTRECORD").deleteAll(flag);
            }
            IJpoSet jbjposet = jxjpo.getJpoSet("SCHEMEJOBTESTRECORD");
            for (int jbindex = 0; jbindex < jbjposet.count(); jbindex++)
            {
                IJpo jbjpo = jbjposet.getJpo(jbindex);
                if (jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
                {
                    jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll(flag);
                }
            }
        }*/
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
      /*  if (this.getJpoSet("SCHEMEJOBBOOKTASK") != null)
        {
            this.getJpoSet("SCHEMEJOBBOOKTASK").undeleteAll();
        }*/
        if (this.getJpoSet("SCHEMEOVERHAULMATERIAL") != null)
        {
            this.getJpoSet("SCHEMEOVERHAULMATERIAL").undeleteAll();
        }
        if (this.getJpoSet("SCHEMEPERSONDEMAND") != null)
        {
            this.getJpoSet("SCHEMEPERSONDEMAND").undeleteAll();
        }
        if (this.getJpoSet("SCHEMETOOLREQUIREMENT") != null)
        {
            this.getJpoSet("SCHEMETOOLREQUIREMENT").undeleteAll();
        }
        if (this.getJpoSet("SCHEMEJOBTASKRECORD") != null)
        {
            this.getJpoSet("SCHEMEJOBTASKRECORD").undeleteAll();
        }
        if (this.getJpoSet("SCHEMEJOBTESTRECORD") != null)
        {
            this.getJpoSet("SCHEMEJOBTESTRECORD").undeleteAll();
        }  
       
        IJpoSet jposet = this.getJpoSet("SCHEMEJOBTESTRECORD");
        for (int ind = 0; ind < jposet.count(); ind++)
        {
            IJpo jxjpo = jposet.getJpo(ind);
            if (jxjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTESTRECORDING").undeleteAll();
            }
        }
        
       /* IJpoSet jposet = this.getJpoSet("SCHEMEJOBBOOKTASK");
        for (int ind = 0; ind < jposet.count(); ind++)
        {
            IJpo jxjpo = jposet.getJpo(ind);
            
            if (jxjpo.getJpoSet("SCHEMEJOBTASKRECORD") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTASKRECORD").undeleteAll();
            }
            if (jxjpo.getJpoSet("SCHEMEJOBTESTRECORD") != null)
            {
                jxjpo.getJpoSet("SCHEMEJOBTESTRECORD").undeleteAll();
            }
            IJpoSet jbjposet = jxjpo.getJpoSet("SCHEMEJOBTESTRECORD");
            for (int jbindex = 0; jbindex < jbjposet.count(); jbindex++)
            {
                IJpo jbjpo = jbjposet.getJpo(jbindex);
                if (jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
                {
                    jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING").undeleteAll();
                }
            }
        }*/
    }
    
    
    
/*    @Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		
		IJpo repairscheme =this.getParent();
		String status =repairscheme.getString("STATUS");
		if(!status.equals("草稿")){
	           this.getJpoSet("SCHEMEOVERHAULMATERIAL").setFlag(GWConstant.S_READONLY, true);
	           this.getJpoSet("SCHEMEPERSONDEMAND").setFlag(GWConstant.S_READONLY, true);
	           this.getJpoSet("SCHEMETOOLREQUIREMENT").setFlag(GWConstant.S_READONLY, true);
	           this.getJpoSet("SCHEMEJOBTASKRECORD").setFlag(GWConstant.S_READONLY, true);
	           this.getJpoSet("SCHEMEJOBTESTRECORD").setFlag(GWConstant.S_READONLY, true);
		}		 
	}*/

	public void jxcopyTask(IJpo repairScope, IJpo newjp)
        throws MroException
    {
        String oldRepairScopeTaskNum = repairScope.getString("TASKNUM");//检修范围TASKNUM
        String schemenum = newjp.getString("SCHEMENUM");//新的SCHEMENUM
        String[] attrs = {"AMOUNT", "REPAIRSITE", "REPAIRMODE", "REMARK", "ITEMNUM", "SOPNUM"};
        setValue(repairScope, attrs, attrs, GWConstant.P_NOVALIDATION);
        setValue("SCHEMENUM", schemenum, GWConstant.P_NOVALIDATION);
        
        // 标准作业指导书任务项
   /*     IJpoSet jobtaskSet =
            repairScope.getJpoSet("$JOBBOOKTASK", "SCHEMEJOBBOOKTASK", "TASKNUM='" + oldRepairScopeTaskNum + "'");
        
        if (jobtaskSet.count() > 0)
        {
            IJpoSet newjobtaskSet = getJpoSet("SCHEMEJOBBOOKTASK", "SCHEMEJOBBOOKTASK", "1=0");
            for (int j = 0; j < jobtaskSet.count(); j++)
            {
                IJpo jpotask = jobtaskSet.getJpo(j);
                SchemeJobBookTask newJpoTaskn = (SchemeJobBookTask)newjobtaskSet.addJpo();
                newJpoTaskn.copyTaskForUpVersion(jpotask, this);
            }
        }*/
        
        //检查项
        IJpoSet jobtaskrecordSet =
                repairScope.getJpoSet("$JOBTASKRECORD", "SCHEMEJOBTASKRECORD", "SCHEMENUM='" + repairScope.getString("SCHEMENUM")
                    + "' and TASKNUM='" + oldRepairScopeTaskNum + "'");
        
        if (jobtaskrecordSet != null && jobtaskrecordSet.count() > 0)
        {
            IJpoSet newjobtaskrecordSet = getJpoSet("JOBTASKRECORD", "SCHEMEJOBTASKRECORD", "1=0");
            for(int index=0;index <jobtaskrecordSet.count();index++){
                IJpo jobtaskrecord = jobtaskrecordSet.getJpo(index);
                SchemeJobTaskRocord newschemeJobTaskrecord = (SchemeJobTaskRocord)newjobtaskrecordSet.addJpo();
                newschemeJobTaskrecord.copyTaskForUpVersion(jobtaskrecord,this);
            }
        }
        
        IJpoSet jobtestrecordSet =
                repairScope.getJpoSet("$JOBTESTRECORD", "SCHEMEJOBTESTRECORD", "TASKNUM='" + oldRepairScopeTaskNum + "'");
            
            if (jobtestrecordSet.count() > 0)
            {
                IJpoSet newjobtestrecordSet = getJpoSet("SCHEMEJOBTESTRECORD", "SCHEMEJOBTESTRECORD", "1=0");
                for (int j = 0; j < jobtestrecordSet.count(); j++)
                {
                    IJpo jobtestrecord = jobtestrecordSet.getJpo(j);
                    SchemeJobTestRecord newJpoTaskn = (SchemeJobTestRecord)newjobtestrecordSet.addJpo();
                    newJpoTaskn.copyTaskForUpVersion(jobtestrecord, this);
                }
            }
    }
}
