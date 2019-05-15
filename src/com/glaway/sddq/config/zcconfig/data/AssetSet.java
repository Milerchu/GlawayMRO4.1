/*
 * 文 件 名:  AssetSet.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-6
 */
package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.jpo.HierarchicalJpoSet;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpoSet;

/**
 * 实时台帐JpoSet
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-6]
 * @since  [MRO4.0/模块版本]
 */
public class AssetSet extends HierarchicalJpoSet implements IStatusJpoSet, IJpoSet
{
    /**
     * 默认序列化Id
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Asset getJpoInstance()
    {
        return new Asset();
    }
    
}
