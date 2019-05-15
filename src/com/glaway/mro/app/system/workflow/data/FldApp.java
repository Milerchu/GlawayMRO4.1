/*
 * 文 件 名:  FldApp.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流APP过滤字段类
 * 修 改 人:  hyhe
 * 修改时间:  2016-6-21
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 工作流APP过滤字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2016-6-21]
 * @since  [产品/模块版本]
 */
public class FldApp extends JpoField
{
    
    /**
     * 默认序列号ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //过滤掉已经配置工作流的APP
        String where = "VISIBLE ='1' AND APP not in(select APP FROM SYS_WFINFO)";
        IJpoSet appset = this.getUserServer().getJpoSet("SYS_APP", where);
        appset.reset();
        
        return appset;
    }
    
}
