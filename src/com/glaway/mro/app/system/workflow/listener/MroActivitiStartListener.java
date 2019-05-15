package com.glaway.mro.app.system.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.identity.Authentication;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;

/**
 * 
 * @ClassName: MroActivitiStartListener
 * @Description: 将流程启动记录添加至历史记录表
 * @author hyhe
 * @date Mar 29, 2016 2:54:12 PM
 */
public class MroActivitiStartListener implements ExecutionListener
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void notify(DelegateExecution execution)
        throws Exception
    {
        WfControlUtil.addHistoryLog(execution.getProcessInstanceId(),
            Authentication.getAuthenticatedUserId(),
            WFConstant.ACTIVITISTART);
    }
    
}
