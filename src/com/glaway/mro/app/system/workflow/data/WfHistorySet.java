/*
 * 文 件 名:  WfHistorySet.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流历史记录JPOSet
 * 修 改 人:  hyhe
 * 修改时间:  2016-7-11
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 工作流历史记录JPOSet
 * 
 * @author  hyhe
 * @version  [版本号, 2016-7-11]
 * @since  [产品/模块版本]
 */
public class WfHistorySet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public WfHistory getJpoInstance()
    {
        return new WfHistory();
    }
    
}
