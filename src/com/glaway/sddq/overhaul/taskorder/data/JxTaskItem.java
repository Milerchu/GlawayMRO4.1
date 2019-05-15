package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修工单任务项Jpo
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-31]
 * @since  [产品/模块版本]
 */
public class JxTaskItem extends StatusJpo implements IStatusJpo, FixedLoggers
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        //        if (getString("STATUS").equals("已确认"))
        //        {
        //            this.getJpoSet("JXTASKSWAPHISTORY").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKRECORD").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTESTRECORD").setFlag(GWConstant.S_READONLY, true);
        //        }
        //        if (getString("STATUS").equals("待确认"))
        //        {
        //            this.getJpoSet("JXTASKSWAPHISTORY").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKRECORD").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTESTRECORD").setFlag(GWConstant.S_READONLY, true);
        //        }
        //        if (getString("STATUS").equals("已完成"))
        //        {
        //            this.getJpoSet("JXTASKSWAPHISTORY").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTASKRECORD").setFlag(GWConstant.S_READONLY, true);
        //            this.getJpoSet("JXTESTRECORD").setFlag(GWConstant.S_READONLY, true);
        //        }
    }
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
        if (this.getJpoSet("JXTASKSWAPHISTORY") != null)
        {
            this.getJpoSet("JXTASKSWAPHISTORY").deleteAll(flag);
        }
        if (this.getJpoSet("JXTASKLOSSPART") != null)
        {
            this.getJpoSet("JXTASKLOSSPART").deleteAll(flag);
        }
        if (this.getJpoSet("JXTASKTOOLS") != null)
        {
            this.getJpoSet("JXTASKTOOLS").deleteAll(flag);
        }
        if (this.getJpoSet("JXTASKEXECPERSON") != null)
        {
            this.getJpoSet("JXTASKEXECPERSON").deleteAll(flag);
        }
        if (this.getJpoSet("JXTASKRECORD") != null)
        {
            this.getJpoSet("JXTASKRECORD").deleteAll(flag);
        }
        if (this.getJpoSet("JXTESTRECORD") != null)
        {
            this.getJpoSet("JXTESTRECORD").deleteAll(flag);
        }
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
        if (this.getJpoSet("JXTASKSWAPHISTORY") != null)
        {
            this.getJpoSet("JXTASKSWAPHISTORY").undeleteAll();
        }
        if (this.getJpoSet("JXTASKLOSSPART") != null)
        {
            this.getJpoSet("JXTASKLOSSPART").undeleteAll();
        }
        if (this.getJpoSet("JXTASKTOOLS") != null)
        {
            this.getJpoSet("JXTASKTOOLS").undeleteAll();
        }
        if (this.getJpoSet("JXTASKEXECPERSON") != null)
        {
            this.getJpoSet("JXTASKEXECPERSON").undeleteAll();
        }
        if (this.getJpoSet("JXTASKRECORD") != null)
        {
            this.getJpoSet("JXTASKRECORD").undeleteAll();
        }
        if (this.getJpoSet("JXTESTRECORD") != null)
        {
            this.getJpoSet("JXTESTRECORD").undeleteAll();
        }
    }
    
    @Override
    public void changeStatus(String status, String memo)
        throws MroException
    {
        if (!status.equals(""))
        {
            setValue("STATUS", status, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            setValue("REMARK", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        else
        {
            throw new AppException("jxtaskitem", "cannotnull");
        }
    }
    
    /**
     * 
     * 复制任务项的同时复制检查记录
     * @param oldTask
     * @param productJpo
     * @throws MroException [参数说明]
     *
     */
    public void copyTask(IJpo oldTask, IJpo productJpo)
            throws MroException
        {
    		
    	 String jxtasknum = productJpo.getString("JXTASKNUM");
    	 String assetnum = productJpo.getString("ASSETNUM");
    	 
    	 String oldTaskNum = oldTask.getString("TASKNUM");
         String newTaskNum = this.getString("TASKNUM");
    	 
         String[] fromAttrs = {"JPNUM", "DESCRIPTION", "SEQ", "REMARK", "PLANTASKTIME"};
         String[] toAttrs = {"JPNUM", "DESCRIPTION", "SEQ", "REMARK", "TASKTIME"};
         
         setValue(oldTask, fromAttrs, toAttrs, GWConstant.P_NOVALIDATION);
         setValue("jxtasknum", jxtasknum, GWConstant.P_NOVALIDATION);
         setValue("assetnum", assetnum, GWConstant.P_NOVALIDATION);

            // 拷贝相关的检查项
        IJpoSet jobtaskrecordSet =
            oldTask.getJpoSet("$JOBTASKRECORDTMP", "JOBTASKRECORD", "TASKNUM='" + oldTaskNum + "'");
        String[] taskrecordattrs = {"DESCRIPTION", "CHECKITEM", "PROJECTSTEPS"};
        IJpoSet newJobTaskrecordSet = getJpoSet("JXTASKRECORD", "JXTASKRECORD", "1=0");
        
        for (int i = 0; i < jobtaskrecordSet.count(); i++)
        {
            IJpo newJpo = newJobTaskrecordSet.addJpo();
            newJpo.setValue(jobtaskrecordSet.getJpo(i), taskrecordattrs, taskrecordattrs, GWConstant.P_NOVALIDATION);
            newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("JXTASKNUM", jxtasknum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("assetnum", assetnum, GWConstant.P_NOVALIDATION);
        }
            
        //拷贝相关的试验项目
        IJpoSet jobtestrecordSet =
            oldTask.getJpoSet("$JOBTESTRECORDTMP", "JOBTESTRECORD", "TASKNUM='" + oldTaskNum + "'");
        String[] testrecordSetattrs = {"DESCRIPTION", "TESTCONTENT", "TESTPROJECT"};
        IJpoSet newJobTestrecordSet = getJpoSet("JXTESTRECORD", "JXTESTRECORD", "1=0");
        
        for (int i = 0; i < jobtestrecordSet.count(); i++)
        {
            IJpo newJpo = newJobTestrecordSet.addJpo();
            newJpo.setValue(jobtestrecordSet.getJpo(i),
                testrecordSetattrs,
                testrecordSetattrs,
                GWConstant.P_NOVALIDATION);
            newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("JXTASKNUM", jxtasknum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("assetnum", assetnum, GWConstant.P_NOVALIDATION);
            
            //拷贝试验项目关联的检测项目 
            String oldjobtestrecordnum = jobtestrecordSet.getJpo(i).getString("JOBTESTRECORDNUM");
            String jxtestnum = newJpo.getString("JXTESTNUM");
            
            IJpoSet jobtestrecordingSet =
            		jobtestrecordSet.getJpo(i).getJpoSet("$JOBTESTRECORDINGTMP_"+i, "JOBTESTRECORDING", "TASKNUM='" + oldjobtestrecordnum + "'");
            
            String[] testrecordSeFromAttrs = {"DESCRIPTION", "TESTLOCATION", "STANDARDVALUE"};
            String[] testrecordSeToAttrs = {"DESCRIPTION", "LOC", "STANDARDVALUE"};

            IJpoSet newJobTestrecordingSet = newJpo.getJpoSet("JXTESTRECORDITEM_"+i, "JXTESTRECORDITEM", "1=0");
            
            for (int j = 0; j < jobtestrecordingSet.count(); j++)
            {
                IJpo newJobJpo = newJobTestrecordingSet.addJpo();
                newJobJpo.setValue(jobtestrecordingSet.getJpo(j),
                		testrecordSeFromAttrs,
                		testrecordSeToAttrs,
                    GWConstant.P_NOVALIDATION);
                newJobJpo.setValue("jxtestnum", jxtestnum, GWConstant.P_NOVALIDATION);
            }
        }
    }
}
