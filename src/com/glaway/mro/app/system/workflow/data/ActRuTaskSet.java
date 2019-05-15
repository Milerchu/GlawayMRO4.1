/*
 * 文 件 名:  FldApp.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流APP过滤字段类
 * 修 改 人:  hyhe
 * 修改时间:  2016-6-21
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 工作流 ACT_RU_TASK 
 * 
 * @author  hyhe
 * @version  [版本号, 2016-6-21]
 * @since  [产品/模块版本]
 */
public class ActRuTaskSet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public String getIdColName()
    {
        return "ID_";
    }
}
