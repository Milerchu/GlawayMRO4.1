/*
 * 文 件 名:  AssetModelSet.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  装备型号JpoSet
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.config.sbom.data;

import com.glaway.mro.jpo.HierarchicalJpoSet;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 装备型号JpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2016-4-25]
 * @since  [产品/模块版本]
 */
public class AssetModelSet extends HierarchicalJpoSet implements IJpoSet
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    public AssetModel getJpoInstance()
    {
        return new AssetModel();
    }
}
