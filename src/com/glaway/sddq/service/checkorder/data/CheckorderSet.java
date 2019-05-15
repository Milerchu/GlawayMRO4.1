package com.glaway.sddq.service.checkorder.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <检查单 jposet>
 * 
 * @author  hzhu
 * @version  [MRO4.1, 2018-5-17]
 * @since  [MRO4.1/模块版本]
 */
public class CheckorderSet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -3695556194976919702L;
    
    @Override
    public Jpo getJpoInstance()
    {
        return new Checkorder();
    }
}
