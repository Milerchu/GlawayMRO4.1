/*
 * 文 件 名:  FldWfName.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流设计名称字段类
 * 修 改 人:  hyhe
 * 修改时间:  2016-6-22
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 工作流设计名称字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2016-6-22]
 * @since  [产品/模块版本]
 */
public class FldWfName extends JpoField
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void validate()
        throws MroException
    {
        String wfInfoId = getJpo().getString("WFINFONUM");
        String name = StringUtil.getSafeSqlStr(this.getInputMroType().asString());
        IJpoSet jposet = getUserServer().getJpoSet("SYS_WFINFO", "NAME ='" + name + "' AND WFINFONUM !=" + wfInfoId);
        
        if (!jposet.isEmpty())
        {
            throw new MroException("WFINFO", "numrepeat", new String[] {name});
        }
    }
}
