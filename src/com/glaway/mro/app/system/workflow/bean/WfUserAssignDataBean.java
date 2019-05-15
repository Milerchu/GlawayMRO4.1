package com.glaway.mro.app.system.workflow.bean;

import java.io.IOException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName: WfUserAssignDataBean
 * @Description: 绑定 重新分配任务窗口
 * @author hyhe
 * @date Mar 23, 2016 2:33:27 PM
 */
public class WfUserAssignDataBean extends DataBean
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
    public int dialogok()
        throws IOException, MroException
    {
        super.checkSave();
        String taskId = this.getDialog().getCreatorBean().getJpo().getString("ID_");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        taskService.setVariable(taskId, "newUserId", getJpo().getString("ASSIGNEE"));
        taskService.setAssignee(taskId, getJpo().getString("ASSIGNEE"));
        //历史记录
        getJpo().setValue("TASKID", taskId);
        getJpo().setValue("OLDUSERID", task.getAssignee());
        getJpo().setValue("PROCINSTID", task.getProcessInstanceId());
        getJpo().setValue("ASSIGNTIME", MroServer.getMroServer().getDate());
        getJpo().setValue("TYPE", "1");
        this.getDialog().getCreatorBean().resetAndReload();
        super.dialogok();
        showOperInfo("wfuser", "cxfp");
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
}
