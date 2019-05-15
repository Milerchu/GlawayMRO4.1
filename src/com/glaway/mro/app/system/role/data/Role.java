/*
 * 文 件 名:  Role.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  角色Jpo
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-14
 */
package com.glaway.mro.app.system.role.data;

import java.util.List;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 角色Jpo
 * 
 * @author  hyhe
 * @version  [版本号, 2016-4-14]
 * @since  [产品/模块版本]
 */
public class Role extends Jpo implements IJpo
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if (!toBeAdded())
        {
            setFieldFlag("type", GWConstant.S_READONLY, true);
            setFieldFlags();
        }
    }
    
    public String getType()
        throws MroException
    {
        String type = getString("type");
        if ((type == null) || (type.trim().length() == 0))
        {
            return type;
        }
        return MroServer.getMroServer().getSysDomainCache().getInnerValue("ROLETYPE", type);
    }
    
    /**
     * 
     * 设置字段状态
     * @throws MroException [参数说明]
     *
     */
    public void setFieldFlags()
        throws MroException
    {
        if (!this.isNew())
        {
            setFieldFlag("maxrole", GWConstant.S_READONLY, true);
        }
        String type = getType();
        if (StringUtil.isStrEmpty(type))
        {
            return;
        }
        setFieldFlag("parameter", GWConstant.S_READONLY, !type.equalsIgnoreCase("CUSTOM"));
        //        setFieldFlag("objectname", GWConstant.S_REQUIRED, type.equalsIgnoreCase("DATASET"));
        //        setFieldFlag("objectname", GWConstant.S_READONLY, !type.equalsIgnoreCase("DATASET"));
    }
    
    /**
     * 
     * 获取用户ID
     * @param userList 工作流所需用户ID
     * @return 工作流所需用户ID
     * @throws MroException [自定义异常]
     *
     */
    public List<String> getPersonByRole(IJpo appJpo, List<String> userList)
        throws MroException
    {
        String type = getType();
        if (StringUtil.isStrEmpty(type))
        {
            return userList;
        }
        if ("PERSONGROUP".equals(type))
        {
            String personGroupId = getString("value");
            if (personGroupId != null)
            {
                IJpoSet personGroupJpoSet =
                    getUserServer().getJpoSet("sys_persongroup",
                        "persongroup='" + StringUtil.getSafeSqlStr(personGroupId) + "'");
                try
                {
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
                finally
                {
                    personGroupJpoSet.destroy();
                }
            }
        }
        else if ("DATASET".equals(type))
        {
            String paramer = getString("VALUE");
            String personId = appJpo.getString(paramer);
            if (StringUtil.isStrNotEmpty(personId) && !userList.contains(personId))
            {
                userList.add(personId);
            }
        }
        else if ("CUSTOM".equals(type))
        {
            String className = getString("VALUE");
            String parameter = getString("parameter");
            RoleCustomClass customClass = null;
            try
            {
                customClass = (RoleCustomClass)Class.forName(className).newInstance();
            }
            catch (Exception e)
            {
                throw new AppException("common", "classnotfound", e);
            }
            IJpoSet jposet = customClass.executeCustomRole(appJpo,parameter);
            if (!jposet.isEmpty())
            {
                for (int i = 0; i < jposet.count(); i++)
                {
                    IJpo jpo = jposet.getJpo(i);
                    String personId = jpo.getString("PERSONID");
                    if (StringUtil.isStrNotEmpty(personId) && !userList.contains(personId))
                    {
                        userList.add(personId);
                    }
                }
            }
        }
        return userList;
    }
    
    /**
     * 复制之后,将唯一字段maxrole设置为空
     * @return
     * @throws MroException
     */
    @Override
    public IJpo duplicate()
        throws MroException
    {
        IJpo newJpo = super.duplicate();
        newJpo.setValueNull("maxrole", GWConstant.P_NOVALIDATION_AND_NOACTION);
        if (getType() != null && (getType().equals("DATASET") || getType().equals("PERSONGROUP")))
        {
            newJpo.setFieldFlag("parameter", GWConstant.S_READONLY, true);
        }
        return newJpo;
    }
}
