package com.glaway.mro.app.system.workflow.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.pvm.PvmException;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * @ClassName: WfTaskFormBean
 * @Description: 工作流任务表单bean
 * @author hyhe
 * @date Mar 23, 2016 2:39:31 PM
 */
public class WfTaskFormBean extends DataBean
{
    
    @Override
    public void initialize()
        throws MroException
    {
        super.initialize();
        try
        {
            String taskId = WfControlUtil.hasTaskAuth(getAppBean());
            getJpo().setValue("taskId", taskId);
        }
        catch (Exception e)
        {
            EXCEPTIONLOGGER.error(e);
        }
    }
    
    @Override
    public int dialogok()
        throws IOException, MroException
    {
        try
        {
            super.checkSave();
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            FormService formService = processEngine.getFormService();
            IdentityService identityService = processEngine.getIdentityService();
            String userId = this.getUserInfo().getPersonId();
            userId = this.getUserInfo().getUserName();
            Map<String, String> formProperties = new HashMap<String, String>();
            formProperties.put("memo", getJpo().getString("MEMO"));
            formProperties.put(WFConstant.APPROVED, getJpo().getString("ACTIONID"));
            String taskId = getJpo().getString("taskId");
            
            identityService.setAuthenticatedUserId(userId);
            formService.submitTaskFormData(taskId, formProperties);
            String processInstanceId ="";
            IJpo actAppInfo = WfControlUtil.getActAppInfo(getAppBean());
            if (actAppInfo != null)
            {
                processInstanceId = actAppInfo.getString("PROCINSTID");
                WfControlUtil.updateHistoryLog(processInstanceId, taskId, getJpo().getString("MEMO"), userId);
            }
            // 故障工单中增加下一节点任务执行人记录  修改人  杨毅 2018-09-26
            String appName =this.getAppName();
            if(StringUtil.isEqualIgnoreCase(appName, "FAILUREORD")){
                setFailureActperson(processInstanceId);
            }
            
        }
        catch (Exception e)
        {
            if (e instanceof AppException)
            {
                throw (AppException)e;
            }
            else if (e instanceof PvmException)
            {
                String msg = e.getMessage();
                if (msg.indexOf(":") != -1)
                {
                    throw new MroException(msg.substring(msg.indexOf(":") + 1));
                }
                else
                {
                    throw new MroException(msg);
                }
            }
            else
            {
                EXCEPTIONLOGGER.error(e);
            }
        }
        
        return super.dialogok();
    }
    
    @Override
    public synchronized String[][] getDataList(String dataAttribute)
        throws MroException
    {
        if (dataAttribute.equals("ACTIONID"))
        {
            String[][] raidos = null;
            int arrayIndex = 0;
            String taskId;
            try
            {
                taskId = WfControlUtil.hasTaskAuth(getAppBean());
                Map<String, Object> properties = WfControlUtil.findTaskForm(taskId);
                if (properties.get(WFConstant.APPROVED) != null)
                {
                    Map<String, String> values = (Map<String, String>)properties.get(WFConstant.APPROVED);
                    if (values != null)
                    {
                        raidos = new String[values.size()][2];
                        for (Entry<String, String> enumEntry : values.entrySet())
                        {
                            raidos[arrayIndex][0] = enumEntry.getKey();
                            raidos[arrayIndex][1] = enumEntry.getValue();
                            arrayIndex++;
                        }
                        if (getJpo() != null && !raidos[0][0].isEmpty())
                        {
                            getJpo().setValue(dataAttribute, raidos[0][0]);
                        }
                    }
                    else
                    {
                        // 隐藏单选按钮标签
                        //getPage().getControlByXmlId("startWorkflowActionId").hide();
                        getPage().getControlByXmlId("startWorkflowActionId").setShowOrHideFlag(Property.HIDE_CTRL);
                    }
                }
                else
                {
                    // 隐藏单选按钮标签
                    //getPage().getControlByXmlId("startWorkflowActionId").hide();
                    getPage().getControlByXmlId("startWorkflowActionId").setShowOrHideFlag(Property.HIDE_CTRL);
                }
            }
            catch (Exception e)
            {
                EXCEPTIONLOGGER.error(e);
            }
            return raidos;
        }
        else
        {
            return super.getDataList(dataAttribute);
        }
    }
    /**
     *  故障工单中增加下一节点任务执行人记录  修改人  杨毅 2018-09-26
     * <功能描述>
     * @param processInstanceId
     * @throws MroException
     * @throws IOException [参数说明]
     *
     */
    private void setFailureActperson(String processInstanceId) throws MroException, IOException {
		IJpoSet taskSet = MroServer.getMroServer().getSysJpoSet("act_ru_task","proc_inst_id_ ='"+processInstanceId+"'");
		if(taskSet!=null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<taskSet.count();i++){
				sb.append(taskSet.getJpo(i).getString("PERSON.DISPLAYNAME")+",");
			}
			if(sb.length()>0){
				String names = sb.toString().substring(0, sb.length()-1);
				this.getAppBean().getJpo().setValue("ACTTASKPERSON",names);
			}
		}
		
	}
}
