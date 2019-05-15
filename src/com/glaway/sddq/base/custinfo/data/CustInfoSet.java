package com.glaway.sddq.base.custinfo.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 客户资料Set
 * 
 * @author  qinghuis
 * @version  [MRO4.0, 2016-6-1]
 * @since  [MRO4.0]
 */
public class CustInfoSet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -5243891854916031039L;
    
    public CustInfo getJpoInstance()
    {
        return new CustInfo();
    }
}
