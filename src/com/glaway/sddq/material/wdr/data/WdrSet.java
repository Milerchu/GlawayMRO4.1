package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
import com.glaway.sddq.material.itemreq.data.MPR;

public class WdrSet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public Wdr getJpoInstance()
    {
        return new Wdr();
    }
}
