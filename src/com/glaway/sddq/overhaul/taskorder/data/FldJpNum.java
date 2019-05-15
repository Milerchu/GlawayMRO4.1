package com.glaway.sddq.overhaul.taskorder.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 检修工单中的标准作业指导书字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-24]
 * @since  [产品/模块版本]
 */
public class FldJpNum extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        String status = this.getJpo().getString("STATUS");
        if (status != null && status.equals(SddqConstant.JX_STATUS_CG)) {
            throw new AppException("jxtaskorder", "dispatch");
        }
        this.setListObject("JOBBOOK");
        String cmodel = this.getJpo().getString("CMODEL");
        String itemnum = this.getJpo().getString("ITEMNUM");
        if (StringUtils.isEmpty(itemnum))
        {
            throw new AppException("jxtaskorder", "cannotnull");
        }
        boolean nullFlag = false;
        IJpoSet productJposet = this.getJpo().getJpoSet("JXPRODUCT");
        if (productJposet != null && productJposet.count() > 0) {
            for (int index = 0; index < productJposet.count(); index++) {
                String sqn = productJposet.getJpo(index).getString("SQN");
                if (StringUtil.isStrEmpty(sqn)) {
                    nullFlag = true;
                    break;
                }
            }
            if (nullFlag) {
                throw new AppException("jxtaskorder", "sqnisnull");
            }
        } else {
            // 请填写序列号信息
            throw new AppException("jxtaskorder", "addsqn");
        }
        
        String repairprocess = this.getJpo().getString("repairprocess");
        
        IJpoSet jobScopeJposet = MroServer.getMroServer().getSysJpoSet("jobscope", "cmodel='" + cmodel + "'");
        String jpnums = "";
        for (int index = 0; index < jobScopeJposet.count(); index++)
        {
            jpnums = jpnums + "'" + jobScopeJposet.getJpo(index).getString("jpnum") + "',";
        }
        if (StringUtils.isNotEmpty(jpnums))
        {
            if (jpnums.endsWith(","))
            {
                jpnums = jpnums.substring(0, jpnums.length() - 1);
            }
            this.setListWhere("jpnum in (" + jpnums + ") and itemnum = '" + itemnum + "' and repairprocess = '"
                + repairprocess + "' and STATUS='已发布'");
        }
        else
        {
            this.setListWhere("1=2");
        }
        return super.getList();
    }
    
    @Override
    public void action()
        throws MroException
    {
        super.action();
        if (this.isValueChanged())
        {
        	
            //            String jpnum = this.getValue();
            //            IJpoSet jxtaskitemSet = this.getJpo().getJpoSet("JXTASKITEM");
            //            if (!jxtaskitemSet.isEmpty())
            //            {
            //                jxtaskitemSet.deleteAll();
            //            }
            //            //自动生成任务项
            //            IJpoSet jobtaskSet = this.getJpo().getJpoSet("$JOBBOOKTASK", "JOBBOOKTASK", "JPNUM='" + jpnum + "'");
            //            if (!jobtaskSet.isEmpty())
            //            {
            //                String jxtasknum = this.getJpo().getString("JXTASKNUM");
            //                String[] attrs = {"jxtasknum"};
            //                String[] vals = {jxtasknum};
            //                String[] fromAttrs = {"JPNUM", "DESCRIPTION", "SEQ", "REMARK", "PLANTASKTIME"};
            //                String[] toAttrs = {"JPNUM", "DESCRIPTION", "SEQ", "REMARK", "TASKTIME"};
            //                jxtaskitemSet.duplicate(jobtaskSet, fromAttrs, toAttrs, GWConstant.P_NOVALIDATION);
            //                jxtaskitemSet.updateAll(attrs, vals);
            //            }
            String jpnum = this.getValue();
            IJpoSet jxproductSet = this.getJpo().getJpoSet("JXPRODUCT");
            if (jxproductSet.count() > 0)
            {
            	String jxTaskNum = this.getJpo().getString("JXTASKNUM");
            	//String sqn =this.getJpo().getString("SQN");
            	//String assetnum =this.getJpo().getString("ASSETNUM");
            	
                //IJpoSet allJobtaskSet = this.getJpo().getJpoSet("$All_JOBBOOKTASK", "JXTASKITEM", "JXTASKNUM='" + jxTaskNum + "'");
            	IJpoSet alljobtaskrecordSet = this.getJpo().getJpoSet("$All_JOBTASKRECORD", "JXTASKRECORD", "JXTASKNUM='" + jxTaskNum + "'");
            	IJpoSet alljobtestrecordSet = this.getJpo().getJpoSet("$All_JOBTESTRECORD", "JXTESTRECORD", "JXTASKNUM='" + jxTaskNum + "'");
            	//allJobtaskSet.deleteAll();
            	alljobtaskrecordSet.deleteAll();
            	alljobtestrecordSet.deleteAll();
                for (int index = 0; index < jxproductSet.count(); index++)
                {
                    IJpo jpo = jxproductSet.getJpo(index);
                    String jxtasknum = jpo.getString("JXTASKNUM");
                    String sqn =jpo.getString("SQN");
                    String assetnum =jpo.getString("ASSETNUM");
                    String autonum =jpo.getString("AUTONUM");
                    //IJpoSet jxtaskitemSet = jpo.getJpoSet("JXTASKITEM");
                    //IJpoSet jobtaskrecordSet = jpo.getJpoSet("JXTASKRECORD");
                    IJpoSet jobtestrecordSet = jpo.getJpoSet("JXTESTRECORD");
                    
                    //自动生成任务项
                    //IJpoSet jobtaskSet = jpo.getJpoSet("$JOBBOOKTASK" + index, "JOBBOOKTASK", "JPNUM='" + jpnum + "'");
                    //检查单记录
                    IJpoSet jobTaskrecordSet = jpo.getJpoSet("$JOBTASKRECORD", "JOBTASKRECORD", "JPNUM='" + jpnum + "'");
                    //试验项记录
                    IJpoSet jobTestrecordSet = jpo.getJpoSet("$JOBTESTRECORD", "JOBTESTRECORD", "JPNUM='" + jpnum + "'");
                    
                    String[] attrs = { "JXTASKNUM","SQN","ASSETNUM","AUTONUM"};
        			String[] vals = { jxtasknum,sqn,assetnum,autonum};
                    if(!jobTaskrecordSet.isEmpty())
                    {
                    	String[] fromAttrs = { "CHECKITEM", "PROJECTSTEPS","SEQ","DESCRIPTION"};
        				String[] toAttrs = { "CHECKITEM", "PROJECTSTEPS","SEQ","DESCRIPTION" };
        				IJpoSet newjxtaskrecordSet = jpo.getJpoSet(
        						"JXTASKRECORDTMP", "JXTASKRECORD",
        						"1=0");
        				newjxtaskrecordSet.duplicate(jobTaskrecordSet,
        						fromAttrs, toAttrs, GWConstant.P_NOVALIDATION);
        				newjxtaskrecordSet.updateAll(attrs, vals);
                    }
                    
                    if(!jobTestrecordSet.isEmpty())
                    {
        				for (int i = 0; i < jobTestrecordSet.count(); i++) {
        					IJpo jobtestrecord = jobTestrecordSet.getJpo(i);
        					JxTestRecord newJpoTask = (JxTestRecord) jobtestrecordSet
        							.addJpo(0);
        					newJpoTask.copyTask(jobtestrecord, jpo);
        				}
                    }
                /*    if (!jobtaskSet.isEmpty())
                    {
                    	for(int i=0;i<jobtaskSet.count();i++){
                    		
                    		IJpo jxtaskItem = jobtaskSet.getJpo(i);
                    		JxTaskItem newJxTaskItem = (JxTaskItem)jxtaskitemSet.addJpo();
                        	newJxTaskItem.copyTask(jxtaskItem,jpo);
                    	}
                    }*/
                }
            }else{
                throw new AppException("JXTASKORDER", "NOPRODUCT");
            }
        }
    }
}
