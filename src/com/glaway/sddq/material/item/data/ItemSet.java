package com.glaway.sddq.material.item.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 物料表JpoSet
 * 
 * @author qhsong
 * @version  [GlawayMro4.0, 2017-11-8]
 * @since  [GlawayMro4.0/物料]
 */
public class ItemSet extends JpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public Item getJpoInstance()
    {
        return new Item();
    }
}
