package com.glaway.mro.app.system.workflow.bean;

import java.io.IOException;
import java.util.Date;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 工作流设计器AppBean
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-17]
 * @since  [MRO4.0/工作流设计器]
 */
public class WfControlAppBean extends AppBean
{
    
    @Override
    public int SAVE()
        throws IOException, MroException
    {
        if (getJpo().isNew() && getJpo().toBeAdded())
        {
            //校验必填项
            checkSave();
            // 第一次新增时，默认创建一个版本为1的流程实例
            IJpoSet wfversionSet = getJpo().getJpoSet("WFVERSION");
            Date curDate = MroServer.getMroServer().getDate();
            IJpo wfversion = wfversionSet.addJpo();
            wfversion.setValue("WFINFONUM", getString("WFINFONUM"));
            wfversion.setValue("CHANGETIME", curDate);
            wfversion.setValue("DESCRIPTION", getString("DESCRIPTION"));
            wfversion.setValue("CHANGEBY", getUserInfo().getPersonId());
            wfversion.setValue("VERSION", "1");
            
            // 保存activiti基本信息，并获取modelId
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ObjectMapper objectMapper = new ObjectMapper();
            
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, getString("NAME"));
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = StringUtils.defaultString(getString("DESCRIPTION"));
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            
            Model modelData = repositoryService.newModel();
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(getString("DESCRIPTION"));
            modelData.setKey(StringUtils.defaultString(getString("WFINFONUM")));
            
            repositoryService.saveModel(modelData);
            
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            
            wfversion.setValue("MODELID", modelData.getId());
            wfversion.setValue("STATUS", WFConstant.EDIT);
            wfversion.setValue("AUTO", WFConstant.DISAUTO);
            wfversion.setFlag(7L, true);
        }
        super.SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * 添加至应用程序
     * @return
     * @throws MroException
     * @throws IOException [参数说明]
     *
     */
    public int ADDSUPPORT()
        throws MroException, IOException
    {
        //首先判断当前流程中是否存在已激活的流程版本
        if (this.getJpo() != null)
        {
            IJpoSet jposet = this.getJpo().getJpoSet("ACTIVITYVERSION");
            if (jposet.isEmpty())
            {
                throw new AppException("wfinfo", "cannotAddSupport");
            }
            //1、首先要判断是否已经添加至应用程序
            IJpoSet isAddSupport =
                getAppBean().getJpo().getJpoSet("$isAddSupport",
                    "SYS_SIGOPTION",
                    "app = :app and optionname ='" + StringUtil.getSafeSqlStr(WFConstant.STARTWF) + "'");
            if (isAddSupport.isEmpty())
            {
                //2、推送配置到 签名选项
                WfControlUtil.initSigOption(getAppBean());
                //3、初始化配置到 选择菜单
                WfControlUtil.initMenuOption(getAppBean());
                //4、初始化权限
                WfControlUtil.initAppAuth(getAppBean());
                getAppBean().SAVE();
                this.showOperInfo("wfinfo", "addSupportSuccess");
            }
            else
            {
                this.showOperInfo("wfinfo", "addSupportSuccess");
            }
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
}
