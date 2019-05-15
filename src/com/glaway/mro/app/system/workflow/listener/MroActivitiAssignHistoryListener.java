package com.glaway.mro.app.system.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 工作流历史记录监听器
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-6-2]
 * @since  [MRO4.0/工作流设计器]
 */
public class MroActivitiAssignHistoryListener implements TaskListener
{
    
    private static final long serialVersionUID = 5918552032523709762L;
    
    @Override
    public void notify(DelegateTask delegateTask)
    {
        
        if (delegateTask.getVariable("newUserId") != null)
        {
            delegateTask.removeVariable("newUserId");
        }
        else
        {
            IJpoSet jposet = null;
            try
            {
                jposet = MroServer.getMroServer().getSysJpoSet("ACT_HI_ASSIGN", "1=2");
                IJpo jpo = jposet.addJpo();
                jpo.setValue("TASKID", delegateTask.getId());
                jpo.setValue("ASSIGNEE", delegateTask.getAssignee());
                jpo.setValue("OLDUSERID", "");
                jpo.setValue("PROCINSTID", delegateTask.getProcessInstanceId());
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
    }
}
