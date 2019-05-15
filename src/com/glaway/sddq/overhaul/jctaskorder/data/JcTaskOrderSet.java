package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

public class JcTaskOrderSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public JcTaskOrder getJpoInstance()
    {
        return new JcTaskOrder();
    }
}
