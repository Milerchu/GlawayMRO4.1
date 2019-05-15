/*
 * 文 件 名:  MroActivitiActionListener.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  该类是在activiti工作流中配置ACTION操作的监听器
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-12
 */
package com.glaway.mro.app.system.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import com.glaway.mro.app.system.action.data.Action;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 该类是在activiti工作流中配置ACTION操作的监听器
 * 
 * @author  hyhe
 * @version  [版本号, 2016-4-12]
 * @since  [产品/模块版本]
 */
public class MroActivitiActionListener implements ExecutionListener
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 在工作流执行线上配置的操作
     */
    private Expression actions;
    
    /**获取操作，并为当前JPO执行操作
     * @param arg0
     * @throws Exception
     */
    @Override
    public void notify(DelegateExecution execution)
        throws Exception
    {
    	
    	IJpo curJpo = (IJpo) execution.getVariable("curJpo");
    	
        String actionText = actions.getExpressionText();
        if (StringUtil.isStrNotEmpty(actionText))
        {
            String[] actionIds = actionText.split(",");
            for (int index = 0; index < actionIds.length; index++)
            {
                String actionid = actionIds[index];
                if(curJpo == null || curJpo.getJpoSet() == null){
                	curJpo = WfControlUtil.getJpoByProInstId(execution.getProcessInstanceId());
                	if(curJpo == null){
                		curJpo = WfControlUtil.getJpoByUid(execution);
                	}
                }
//                IJpo appjpo = WfControlUtil.getJpoByProInstId(execution.getProcessInstanceId());
//                if (appjpo == null)
//                {
//                    appjpo = WfControlUtil.getJpoByUid(execution);
//                }
//                if (appjpo != null)
//                {
                    IJpoSet actionJposet =
                        MroServer.getMroServer().getSysJpoSet("SYS_ACTION", "ACTION='" + StringUtil.getSafeSqlStr(actionid) + "'");
                    if (!actionJposet.isEmpty())
                    {
                        Action action = (Action)actionJposet.getJpo(0);
                        action.executeAction(curJpo);
                    }
//                }
            }
        }
    }
}
