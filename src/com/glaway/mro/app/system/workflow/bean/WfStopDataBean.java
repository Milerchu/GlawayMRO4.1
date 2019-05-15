/*
 * 文 件 名:  WfStopDataBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  流程停止DataBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-6-24
 */
package com.glaway.mro.app.system.workflow.bean;

import java.io.IOException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 流程停止DataBean
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-6-24]
 * @since  [MRO4.0/4.0]
 */
public class WfStopDataBean extends DataBean
{
    
    @Override
    public void buildJpoSet()
        throws MroException
    {
        super.buildJpoSet();
        this.jpoSet.setUserWhere("1=2");
        this.jpoSet.reset();
        this.jpoSet.addJpo();
    }
    
    @Override
    public int execute()
        throws MroException, IOException
    {
        try
        {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            RuntimeService runtimeService = processEngine.getRuntimeService();
            String procInstId = getJpo().getParent().getString("PROCINSTID");
            runtimeService.suspendProcessInstanceById(procInstId);// 挂起流程实例
            // 删除运行实例记录
            IJpoSet executionSet = getMroSession().getUserServer().getJpoSet("ACT_RU_EXECUTION", "PROC_INST_ID_='" + procInstId + "'");
            executionSet.deleteAll();
            executionSet.save();
            
            String memo = getJpo().getString("MEMO");
            if (memo == null || memo.trim().equals(""))
            {
                memo = WFConstant.ACTIVITISTOP;
            }
            if (this.getJpo() != null && this.getJpo().getParent() != null)
            {
                //更新状态
                this.getJpo().getParent().setValue("STATUS", WFConstant.STOP, GWConstant.P_NOVALIDATION_AND_NOACTION);
            }
            //            WfControlUtil.updateActAppInfo(procInstId, WFConstant.STOP);
            WfControlUtil.addHistoryLog(procInstId, this.getUserInfo().getPersonId(), memo);
            showOperInfo("workflow", "isStop");
        }
        catch (Exception e)
        {
            EXCEPTIONLOGGER.error(e);
        }
        this.reloadSelfAndSubs();
        return super.execute();
    }
    
    // 工作流管理中停止工作流有錯是因為缺少如下代碼 
    @Override 
    public void dialogclose() throws IOException, MroException { 
       getDialog().getCreatorBean().refixPos(); 
       super.dialogclose(); 
    }
}
