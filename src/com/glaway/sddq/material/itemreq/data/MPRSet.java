package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 领料单JpoSet
 * 
 * @author qhsong
 * @version  [GlawayMro4.0, 2017-11-8]
 * @since  [GlawayMro4.0/领料单]
 */
public class MPRSet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public MPR getJpoInstance()
    {
        return new MPR();
    }
}
