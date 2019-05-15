/*
 * 文 件 名:  GlobalEventListeners.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  全局事件处理器
 * 修 改 人:  hyhe
 * 修改时间:  2016-7-22
 */
package com.glaway.mro.app.system.workflow.listener;

import java.io.IOException;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 全局监听器（流程启动、流程结束、以及人员分配）
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-10-28]
 * @since  [MRO4.0/工作流管理]
 */
public class GlobalEventListeners implements ActivitiEventListener
{
    
    /**
     * @return
     */
    @Override
    public boolean isFailOnException()
    {
        return false;
    }
    
    /**
     * @param event
     */
    @Override
    public void onEvent(ActivitiEvent event)
    {
        switch (event.getType())
        {
            case TASK_ASSIGNED://人员分配时进行历史记录
                ActivitiEntityEventImpl entityEvent = (ActivitiEntityEventImpl)event;
                TaskEntity taskEntity = (TaskEntity)entityEvent.getEntity();
                if (taskEntity.getVariable("newUserId") != null)
                {
                    taskEntity.removeVariable("newUserId");
                }
                else
                {
                    IJpoSet jposet = null;
                    try
                    {
                        jposet = MroServer.getMroServer().getSysJpoSet("ACT_HI_ASSIGN", "1=2");
                        IJpo jpo = jposet.addJpo();
                        jpo.setValue("TASKID", taskEntity.getId());
                        jpo.setValue("ASSIGNEE", taskEntity.getAssignee());
                        jpo.setValue("OLDUSERID", "");
                        jpo.setValue("PROCINSTID", taskEntity.getProcessInstanceId());
                        jpo.setValue("TYPE", "0");
                        jposet.save();
                    }
                    catch (Exception e)
                    {
                        FixedLoggers.EXCEPTIONLOGGER.error(e);
                        if (jposet != null)
                        {
                            jposet.destroy();
                        }
                    }
                }
                break;
            case ACTIVITY_STARTED://工作流启动时记录
                ActivitiActivityEventImpl activityEvent = (ActivitiActivityEventImpl)event;
                if (activityEvent.getActivityType().equals("startEvent"))
                {
                    try
                    {
                        WfControlUtil.addHistoryLog(activityEvent.getProcessInstanceId(),
                            Authentication.getAuthenticatedUserId(),
                            WFConstant.ACTIVITISTART);
                    }
                    catch (IOException e)
                    {
                        FixedLoggers.EXCEPTIONLOGGER.error(e);
                    }
                    catch (MroException e)
                    {
                        FixedLoggers.EXCEPTIONLOGGER.error(e);
                    }
                }
                break;
            case PROCESS_COMPLETED://流程结束时记录
                try
                {
                    WfControlUtil.addHistoryLog(event.getProcessInstanceId(),
                        Authentication.getAuthenticatedUserId(),
                        WFConstant.ACTIVITIEND);
                    //流程结束后，更新 ACT_APP_INFO表中的数据的状态
                    WfControlUtil.updateActAppInfo(event.getProcessInstanceId(), WFConstant.NONE);
                }
                catch (Exception e)
                {
                    FixedLoggers.EXCEPTIONLOGGER.error(e);
                }
                break;
        }
    }
}
