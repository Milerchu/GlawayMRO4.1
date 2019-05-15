package com.glaway.mro.app.system.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.identity.Authentication;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;

/**
 * 
 * @ClassName: MroActivitiEndListener
 * @Description: 将流程结束记录添加至历史记录表
 * @author hyhe
 * @date Mar 29, 2016 3:05:53 PM
 */
public class MroActivitiEndListener implements ExecutionListener
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void notify(DelegateExecution execution)
        throws Exception
    {
        WfControlUtil.addHistoryLog(execution.getProcessInstanceId(),
            Authentication.getAuthenticatedUserId(),
            WFConstant.ACTIVITIEND);
        //流程结束后，更新 ACT_APP_INFO表中的数据的状态
        WfControlUtil.updateActAppInfo(execution.getProcessInstanceId(), WFConstant.NONE);
    }
}