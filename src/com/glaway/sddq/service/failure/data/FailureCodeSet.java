package com.glaway.sddq.service.failure.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

public class FailureCodeSet extends JpoSet implements FailureCodeSetRemote
{
    @Override
    public Jpo getJpoInstance()
    {
        return new FailureCode();
    }
}
