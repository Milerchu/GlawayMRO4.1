/*
 * 文 件 名:  AssetCsSet.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐JpoSet
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-27
 */
package com.glaway.sddq.config.csbom.data;

import com.glaway.mro.jpo.HierarchicalJpoSet;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 出所台帐JpoSet
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-4-27]
 * @since  [MRO4.0/模块版本]
 */
public class AssetCsSet  extends HierarchicalJpoSet implements IJpoSet
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public AssetCs getJpoInstance()
    {
        return new AssetCs();
    }
    
}
