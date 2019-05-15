package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 试验记录JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-31]
 * @since  [产品/模块版本]
 */
public class JxTestRecordSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public JxTestRecord getJpoInstance()
    {
        return new JxTestRecord();
    }
}
