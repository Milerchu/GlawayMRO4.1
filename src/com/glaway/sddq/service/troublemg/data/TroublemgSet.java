package com.glaway.sddq.service.troublemg.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 项目问题跟踪JpoSet
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-1-25]
 * @since  [MRO4.0/模块版本]
 */
public class TroublemgSet extends JpoSet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1806737694476009367L;
    
    public Troublemg getJpoInstance()
    {
        return new Troublemg();
    }
}
