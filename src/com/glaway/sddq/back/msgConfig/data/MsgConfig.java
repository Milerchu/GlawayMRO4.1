package com.glaway.sddq.back.msgConfig.data;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.app.system.role.data.Role;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.msgConfig.bean.IMsgConfig;

/**
 * 
 * 消息配置Jpo
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-8]
 * @since  [产品/模块版本]
 */
public class MsgConfig extends StatusJpo implements IStatusJpo, FixedLoggers
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        if (!this.isNew())
        {
            setFieldFlag("MSGNUM", GWConstant.S_READONLY, true);
        }
        if (getString("STATUS").equals("已激活"))
        {
            setFieldFlag("APP", GWConstant.S_READONLY, true);
            setFieldFlag("CLASS", GWConstant.S_READONLY, true);
            setFieldFlag("CONDITION", GWConstant.S_READONLY, true);
            setFieldFlag("HOMEPAGE", GWConstant.S_READONLY, true);
            setFieldFlag("MSG", GWConstant.S_READONLY, true);
            setFieldFlag("EMAIL", GWConstant.S_READONLY, true);
            setFieldFlag("RECEIVETYPE", GWConstant.S_READONLY, true);
            setFieldFlag("RECEIVEUSER", GWConstant.S_READONLY, true);
            setFieldFlag("SUBJECTTYPE", GWConstant.S_READONLY, true);
            setFieldFlag("CONTENTTYPE", GWConstant.S_READONLY, true);
            setFieldFlag("SUBJECT", GWConstant.S_READONLY, true);
            setFieldFlag("CONTENT", GWConstant.S_READONLY, true);
        }
    }
    
    @Override
    public void changeStatus(String newstatus, String memo)
        throws MroException
    {
        if (!newstatus.equals(""))
        {
            String oldstatus = getString("STATUS");
            setValue("STATUS", newstatus);
            IJpoSet ahstatusset = getJpoSet("MSGCONFIGSTATUSHISTORY");
            IJpo ahstatusnew = ahstatusset.addJpo();
            ahstatusnew.setValue("MSGNUM", getString("MSGNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("siteid", getString("siteid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("orgid", getString("orgid"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("STATUS", oldstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("newstatus", newstatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setValue("REMARK", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            ahstatusnew.setFlag(GWConstant.S_READONLY, true);
        }
        else
        {
            throw new AppException("msgconfig", "cannotnull");
        }
    }
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
        if (getString("STATUS").equals("已激活"))
        {
            throw new AppException("msgconfig", "activated");
        }
        else
        {
            if (this.getJpoSet("MSGCONFIGSTATUSHISTORY") != null)
            {
                this.getJpoSet("MSGCONFIGSTATUSHISTORY").deleteAll(flag);
            }
        }
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
        if (this.getJpoSet("MSGCONFIGSTATUSHISTORY") != null)
        {
            this.getJpoSet("MSGCONFIGSTATUSHISTORY").undeleteAll();
        }
    }
    
    /**
     * 
     * 根据既定规则生成消息
     * @throws MroException [参数说明]
     *
     */
    public void createMsg()
        throws MroException
    {
        
        String classpath = this.getString("CLASS");
        if (StringUtil.isNullOrEmpty(classpath))
        {
            IMsgConfig customClass = null;
            try
            {
                customClass =
                    (IMsgConfig)Class.forName("com.glaway.sddq.back.msgConfig.bean.BaseMsgConfig").newInstance();
            }
            catch (Exception e)
            {
                throw new AppException("common", "classnotfound", e);
            }
            customClass.execute(this);
        }
        else
        {
            IMsgConfig customClass = null;
            try
            {
                customClass = (IMsgConfig)Class.forName(classpath).newInstance();
            }
            catch (Exception e)
            {
                throw new AppException("common", "classnotfound", e);
            }
            customClass.execute(this);
        }
    }
    
    /**
     * 
     * 获取发送人
     * @return
     * @throws MroException [参数说明]
     *
     */
    public List<String> getSendUser()
        throws MroException
    {
        String receivetype = this.getString("RECEIVETYPE");
        List<String> userList = new ArrayList<String>();
        String receiver = this.getString("RECEIVEUSER");
        if (receivetype.equals("指定角色"))
        {
            IJpoSet roleSet = MroServer.getMroServer().getSysJpoSet("sys_role", "maxrole='" + receiver + "'");
            Role roleJpo = (Role)roleSet.getJpo(0);
            roleJpo.getPersonByRole(roleJpo, userList);
        }
        if (receivetype.equals("指定人员"))
        {
            userList.add(receiver);
        }
        if (receivetype.equals("人员组"))
        {
            IJpoSet personGroupJpoSet = getUserServer().getJpoSet("sys_persongroup", "persongroup='" + receiver + "'");
            if (!personGroupJpoSet.isEmpty())
            {
                IJpo personGroup = personGroupJpoSet.getJpo(0);
                IJpoSet personJpoSet = personGroup.getJpoSet("PERSONGROUP_PRIMARYMEMBERS");
                if (!personJpoSet.isEmpty())
                {
                    for (int j = 0; j < personJpoSet.count(); j++)
                    {
                        IJpo person = personJpoSet.getJpo(j);
                        String personId = person.getString("RESPPARTY");
                        if (personId != null && !userList.contains(personId))
                        {
                            userList.add(personId);
                        }
                    }
                }
            }
        }
        return userList;
    }
}
