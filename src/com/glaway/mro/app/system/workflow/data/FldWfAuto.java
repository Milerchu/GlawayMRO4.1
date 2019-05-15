/*
 * 文 件 名:  FldWfAuto.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流是否自启动字段类
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-17
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 工作流是否自启动字段类
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-17]
 * @since  [MRO4.0/工作流设计器]
 */
public class FldWfAuto extends JpoField
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void validate()
        throws MroException
    {
        super.validate();
        //        if (!getJpo().getString("STATUS").equals(WFConstant.ACTIVI))
        //        {
        //            throw new AppException("wfinfo", "notActivi");
        //        }
    }
}
