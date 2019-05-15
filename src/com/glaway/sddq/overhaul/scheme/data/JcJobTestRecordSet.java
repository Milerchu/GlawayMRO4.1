package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 交车检查项JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-19]
 * @since  [产品/模块版本]
 */
public class JcJobTestRecordSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public JcJobTestRecord getJpoInstance()
    {
        return new JcJobTestRecord();
    }
}
