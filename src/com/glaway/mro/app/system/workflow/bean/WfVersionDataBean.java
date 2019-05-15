package com.glaway.mro.app.system.workflow.bean;

import java.io.IOException;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaway.mro.app.system.workflow.data.WfVersion;
import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName: WfVersionDataBean
 * @Description: 工作流版本操作bean
 * @author hyhe
 * @date Mar 23, 2016 2:51:12 PM
 */
public class WfVersionDataBean extends DataBean
{
    
    /**
     * 
     * @Title: editWf
     * @Description: 编辑流程
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int editWf()
        throws MroException, IOException
    {
        if (getString("MODELID") != null)
        {
            mroSession.returnReponse("", "openUrl", "<url>" + getServerURL() + "mservice/editor?id=" + getString("MODELID") + "</url>");
        }
        else
        {
            this.showMsg("wfinfo", "choseVerson");
        }
        
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: deployWf
     * @Description: 部署并同时启用流程
     * @return
     * @throws MXException
     * @throws IOException
     * @return int
     */
    public int deployWf()
        throws MroException, IOException
    {
        if (getJpo() == null)
        {
            this.showMsg("wfinfo", "choseVerson");
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        if (WFConstant.ENABLE.equals(getJpo().getString("STATUS")))
        {
            throw new MroException("wfinfo", "samestatus", new String[] {"启用"});
        }
        if (getString("MODELID") != null && !getString("MODELID").equals("")
            && (getJpo().getString("deploymentId") == null || ("").equals(getJpo().getString("deploymentId"))))
        {
            try
            {
                ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
                RepositoryService repositoryService = processEngine.getRepositoryService();
                String modelId = getString("MODELID");
                Model modelData = repositoryService.getModel(modelId);
                ObjectNode modelNode = (ObjectNode)new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
                byte[] bpmnBytes = null;
                BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
                bpmnBytes = new BpmnXMLConverter().convertToXML(model);
                String processName = modelData.getName() + ".bpmn20.xml";
                DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
                Deployment deployment =
                    deploymentBuilder.name(modelData.getName()).addString(processName, new String(bpmnBytes, "utf-8")).deploy();
                getJpo().setValue("deploymentId", deployment.getId());
                // 获取流程定义ID
                ProcessDefinition processDefinition =
                    repositoryService.createProcessDefinitionQuery().deploymentId(getString("deploymentId")).singleResult();
                getJpo().setValue("PROCDEFINITIONID", processDefinition.getId());
            }
            catch (Exception e)
            {
            	e.printStackTrace();
                throw new AppException("wfinfo", "deployWfFailure");
            }
        }
        getJpo().setValue("STATUS", WFConstant.ENABLE);
        getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: disableWf
     * @Description: 禁用流程
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int disableWf()
        throws MroException, IOException
    {
        if (getJpo() == null)
        {
            this.showMsg("wfinfo", "choseVerson");
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        if (WFConstant.DISABLE.equals(getJpo().getString("STATUS")))
        {
            throw new MroException("wfinfo", "samestatus", new String[] {"禁用"});
        }
        if (!getJpo().getJpoSet("ACT_RU_EXECUTION").isEmpty())
        {
            throw new AppException("wfinfo", "hasExection");
        }
        if (getJpo().getString("STATUS").equals(WFConstant.ACTIVI))
        {
            this.showMsg("wfinfo", "cannotdisableWf");
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        else
        {
            getJpo().setValue("STATUS", WFConstant.DISABLE);
        }
        getAppBean().SAVE();
        this.reloadSelfAndSubs();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: activtiWf
     * @Description: 激活流程
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int activtiWf()
        throws MroException, IOException
    {
        if (getJpo() != null)
        {
            
            if (WFConstant.ACTIVI.equals(getJpo().getString("STATUS")))
            {
                throw new MroException("wfinfo", "samestatus", new String[] {"激活"});
            }
            if (getJpo().getString("STATUS").equals(WFConstant.EDIT))
            {
                throw new AppException("wfinfo", "enableNotActivi");
            }
            else if (getJpo().getString("STATUS").equals(WFConstant.DISABLE))
            {
                throw new AppException("wfinfo", "disableNotActivi");
            }
            
            // 激活该过程的同时将当前已激活过程取消激活
            IJpoSet wfversionSet = getAppBean().getJpo().getJpoSet("WFVERSION");
            if (wfversionSet != null && !wfversionSet.isEmpty())
            {
                for (int index = 0; index < wfversionSet.count(); index++)
                {
                    WfVersion wfVersion = (WfVersion)wfversionSet.getJpo(index);
                    if (wfVersion.getString("STATUS").equals(WFConstant.ACTIVI))
                    {
                        wfVersion.setValue("STATUS", WFConstant.ENABLE);
                    }
                }
            }
            getJpo().setValue("STATUS", WFConstant.ACTIVI);
            this.reloadSelfAndSubs();
        }
        else
        {
            this.showMsg("wfinfo", "choseVerson");
        }
        getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: cancleActivtiWf
     * @Description: 取消激活
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int cancleActivtiWf()
        throws MroException, IOException
    {
        if (getJpo() == null)
        {
            this.showMsg("wfinfo", "choseVerson");
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        if (WFConstant.ENABLE.equals(getJpo().getString("STATUS")))
        {
            throw new MroException("wfinfo", "samestatus", new String[] {"取消激活"});
        }
        if (getJpo().getString("STATUS").equals(WFConstant.ACTIVI))
        {
            getJpo().setValue("STATUS", WFConstant.ENABLE);
            getJpo().setValue("AUTO", WFConstant.DISAUTO, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        else
        {
            throw new AppException("wfinfo", "isnotActivi");
        }
        this.reloadSelfAndSubs();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: stopWf
     * @Description: 挂起流程
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int stopWf()
        throws MroException, IOException
    {
    	getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: setAutoWf
     * @Description: 设置流程自启动
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int setAutoWf()
        throws MroException, IOException
    {
        if (getJpo().getString("STATUS").equals(WFConstant.ACTIVI))
        {
            getJpo().setValue("AUTO", WFConstant.AUTO);
        }
        else
        {
            throw new AppException("wfinfo", "notActivi");
        }
        this.reloadSelfAndSubs();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * @Title: setNoAutoWf
     * @Description: 取消流程自启动
     * @return
     * @throws MroException
     * @throws IOException
     * @return int
     */
    public int setNoAutoWf()
        throws MroException, IOException
    {
        if (getJpo() == null)
        {
            throw new AppException("wfinfo", "choseVerson");
        }
        if (getJpo().getString("AUTO").equals(WFConstant.DISAUTO))
        {
            throw new AppException("wfinfo", "notAuto");
        }
        else
        {
            getJpo().setValue("AUTO", WFConstant.DISAUTO);
            this.reloadSelfAndSubs();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * 版本升级
     * @throws IOException
     * @throws MroException [参数说明]
     *
     */
    @Override
    public void addEditRowCallBackOk()
        throws IOException, MroException
    {
        if (getJpo() != null && getJpo().isNew())
        {
            IJpoSet wfversionSet = getAppBean().getJpo().getJpoSet("WFVERSION");
            IJpo wfVersion = null;
            if (wfversionSet != null && wfversionSet.count() > 1)
            {
                wfVersion = ((WfVersion)this.getJpo()).getMaxVersionJpo();
            }
            else if (wfversionSet != null)
            {
                wfVersion = wfversionSet.getJpo(0);
            }
            if (wfVersion != null)
            {
                String modelId = wfVersion.getString("MODELID");
                ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
                RepositoryService repositoryService = processEngine.getRepositoryService();
                Model modelData = repositoryService.getModel(modelId);
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode modelObjectNode = objectMapper.createObjectNode();
                Model newModel = repositoryService.newModel();
                
                modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, getAppBean().getJpo().getString("NAME"));
                modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, getString("VERSION"));
                String description = StringUtils.defaultString(getString("DESCRIPTION"));
                modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
                
                newModel.setMetaInfo(modelObjectNode.toString());
                newModel.setName(getString("DESCRIPTION"));
                newModel.setKey(StringUtils.defaultString(wfVersion.getString("WFINFONUM")));
                repositoryService.saveModel(newModel);
                ObjectNode modelNode = (ObjectNode)new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
                repositoryService.addModelEditorSource(newModel.getId(), modelNode.toString().getBytes("utf-8"));
                getJpo().setValue("MODELID", newModel.getId());
                getJpo().setValue("STATUS", WFConstant.EDIT, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                getJpo().setValue("AUTO", WFConstant.DISAUTO, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                getAppBean().SAVE();
                mroSession.returnReponse("", "openUrl", "<url>" + getServerURL() + "mservice/editor?id=" + newModel.getId() + "</url>");
            }
        }
    }
    
    @Override
    public synchronized void delete()
        throws MroException
    {
        //判断当前是否至少保留了一个版本
        IJpoSet versionSet = this.getJpoSet();
        if (versionSet != null && versionSet.count() > 0)
        {
            int num = 0;
            for (int index = 0; index < versionSet.count(); index++)
            {
                if (!versionSet.getJpo(index).isDeleted())
                {
                    num++;
                }
            }
            if (num <= 1)
            {
                throw new AppException("wfinfo", "oneWfversion");
            }
        }
        super.delete();
    }
    
    @Override
    public int addrow()
        throws MroException, IOException
    {
        this.getAppBean().checkSave();
        if (this.getAppBean().getJpo().isNew() && this.getAppBean().getJpo().toBeAdded())
        {
            throw new AppException("wfinfo", "saveWfinfo");
        }
        return super.addrow();
    }
}
