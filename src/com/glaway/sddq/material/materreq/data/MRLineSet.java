package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 配件申请单行JpoSet
 * 
 * @author qhsong
 * @version  [GlawayMro4.0, 2017-11-7]
 * @since  [GlawayMro4.0/配件申请]
 */
public class MRLineSet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public MRLine getJpoInstance()
    {
        return new MRLine();
    }
}
