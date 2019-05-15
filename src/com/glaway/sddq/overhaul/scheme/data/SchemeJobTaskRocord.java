package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName SchemeJobTaskRocord
 * @Description 检修方案中的检查项
 * @author public2175
 * @Date 2018-8-17 上午11:21:33
 * @version 1.0.0
 */
public class SchemeJobTaskRocord extends Jpo implements IJpo {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    public void copyTaskForUpVersion(IJpo jobtaskrecord, RepairScope repairScope) throws MroException {
        
        //检修范围标准作业指导书编号
        String sopnum = repairScope.getString("SOPNUM");
        //检修范围检修方案编号
        String schemenum = repairScope.getString("SCHEMENUM");     
        //检修范围任务项编号
        String tasknum = repairScope.getString("TASKNUM");
        
        String[] attrs = {"SEQ", "CHECKITEM","DESCRIPTION","PROJECTSTEPS"};
        setValue(jobtaskrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
        setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
        setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);
        setValue("tasknum", tasknum, GWConstant.P_NOVALIDATION);
        
        
    }
}
