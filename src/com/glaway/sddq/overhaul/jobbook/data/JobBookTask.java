package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;

/**
 * 
 * 标准作业指导书任务项 Jpo
 * 
 * @author bchen
 * @version [版本号, 2018-5-2]
 * @since [产品/模块版本]
 */
public class JobBookTask extends Jpo implements IJpo
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
        if (this.getJpoSet("JOBTASKRECORD") != null)
        {
            this.getJpoSet("JOBTASKRECORD").deleteAll();
        }
        if (this.getJpoSet("JOBTESTRECORD") != null)
        {
            this.getJpoSet("JOBTESTRECORD").deleteAll();
        }
        IJpoSet jposet = this.getJpoSet("JOBTESTRECORD");
        for (int index = 0; index < jposet.count(); index++)
        {
            IJpo jbjpo = jposet.getJpo(index);
            if (jbjpo.getJpoSet("JOBTESTRECORDING") != null)
            {
                jbjpo.getJpoSet("JOBTESTRECORDING").deleteAll();
            }
        }
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
        if (this.getJpoSet("JOBTASKRECORD") != null)
        {
            this.getJpoSet("JOBTASKRECORD").undeleteAll();
        }
        if (this.getJpoSet("JOBTESTRECORD") != null)
        {
            this.getJpoSet("JOBTESTRECORD").undeleteAll();
        }
        IJpoSet jposet = this.getJpoSet("JOBTESTRECORD");
        for (int index = 0; index < jposet.count(); index++)
        {
            IJpo jbjpo = jposet.getJpo(index);
            if (jbjpo.getJpoSet("JOBTESTRECORDING") != null)
            {
                jbjpo.getJpoSet("JOBTESTRECORDING").undeleteAll();
            }
        }
    }

    
    /*public void copyTask(IJpo oldTask, IJpo newJobBookJpo)
        throws MroException
    {
        
        String oldTaskNum = oldTask.getString("TASKNUM");
        String newTaskNum = this.getString("TASKNUM");
        String jpnum = newJobBookJpo.getString("jpnum");
        String[] attrs = {"DESCRIPTION", "REMARK", "SEQ", "TASKTIME"};
        setValue(oldTask, attrs, attrs, GWConstant.P_NOVALIDATION);
        setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);
        
        // 拷贝相关的检查项
        IJpoSet jobtaskrecordSet =
            oldTask.getJpoSet("$JOBTASKRECORDTMP", "JOBTASKRECORD", "TASKNUM='" + oldTaskNum + "'");
        String[] taskrecordattrs = {"DESCRIPTION", "CHECKITEM", "PROJECTSTEPS"};
        IJpoSet newJobTaskrecordSet = getJpoSet("JOBTASKRECORD", "JOBTASKRECORD", "1=0");
        
        for (int i = 0; i < jobtaskrecordSet.count(); i++)
        {
            IJpo newJpo = newJobTaskrecordSet.addJpo();
            newJpo.setValue(jobtaskrecordSet.getJpo(i), taskrecordattrs, taskrecordattrs, GWConstant.P_NOVALIDATION);
            newJpo.setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
        }
        
        //拷贝相关的试验项目
        IJpoSet jobtestrecordSet =
            oldTask.getJpoSet("$JOBTESTRECORDTMP", "JOBTESTRECORD", "TASKNUM='" + oldTaskNum + "'");
        String[] testrecordSetattrs = {"DESCRIPTION", "TESTCONTENT", "TESTPROJECT"};
        IJpoSet newJobTestrecordSet = getJpoSet("JOBTESTRECORD", "JOBTESTRECORD", "1=0");
        
        for (int i = 0; i < jobtestrecordSet.count(); i++)
        {
            IJpo newJpo = newJobTestrecordSet.addJpo();
            newJpo.setValue(jobtestrecordSet.getJpo(i),
                testrecordSetattrs,
                testrecordSetattrs,
                GWConstant.P_NOVALIDATION);
            newJpo.setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
            
            //拷贝试验项目关联的检测项目 
            String oldjobtestrecordnum = jobtestrecordSet.getJpo(i).getString("JOBTESTRECORDNUM");
            String newjobtestrecordnum = newJpo.getString("JOBTESTRECORDNUM");
            
            IJpoSet jobtestrecordingSet =
            		jobtestrecordSet.getJpo(i).getJpoSet("$JOBTESTRECORDINGTMP_"+i, "JOBTESTRECORDING", "TASKNUM='" + oldjobtestrecordnum + "'");
            String[] testrecordSetattrss = {"DESCRIPTION", "LOC", "STANDARDVALUE"};
            IJpoSet newJobTestrecordingSet = newJpo.getJpoSet("JOBTESTRECORDING_"+i, "JOBTESTRECORDING", "1=0");
            
            for (int j = 0; j < jobtestrecordingSet.count(); j++)
            {
                IJpo newJobJpo = newJobTestrecordingSet.addJpo();
                newJobJpo.setValue(jobtestrecordingSet.getJpo(j),
                    testrecordSetattrss,
                    testrecordSetattrss,
                    GWConstant.P_NOVALIDATION);
                newJobJpo.setValue("TASKNUM", newjobtestrecordnum, GWConstant.P_NOVALIDATION);
                newJobJpo.setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);
            }
        }
    }*/
}
