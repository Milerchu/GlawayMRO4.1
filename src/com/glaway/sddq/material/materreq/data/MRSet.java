package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 配件申请单JpoSet
 * 
 * @author qhsong
 * @version  [GlawayMro4.0, 2017-11-8]
 * @since  [GlawayMro4.0/配件申请]
 */
public class MRSet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public MR getJpoInstance()
    {
        return new MR();
    }
}
