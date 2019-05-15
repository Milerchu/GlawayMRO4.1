/*
 * 文 件 名:  FldLocationsItemNum.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-13
 */
package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 位置中itemnum 字段控制类
 * @author  yyang
 * @version  [版本号, 2016-5-13]
 * @since  [产品/模块版本]
 */
public class FldLocationsItemNum extends JpoField
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6714808953825812433L;
    
    /**
     * @throws MroException
     */
    @Override
    public void init()
        throws MroException
    {
        super.init();
        String[] thisAttrs = {this.getFieldName()};
        String[] srcAttrs = {"ITEMNUM"};
        setLookupMap(thisAttrs, srcAttrs);
    }
    
    /**
     * 获取可选择列表数据
     * @return
     * @throws MroException
     */
    @Override
    public IJpoSet getList()
        throws MroException
    {
        setListObject("SYS_ITEM");
        return super.getList();
    }
}
