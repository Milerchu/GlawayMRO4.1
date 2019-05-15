package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FldRemedycode extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        
        IJpo failurelib = getJpo();
        if (failurelib.getString("CAUSECODE") != null)
        { //根据故障现象代码过滤
            setListWhere("CAUSECODE='" + failurelib.getString("CAUSECODE") + "'");
        }
        else
        {
            setListWhere("1=2");
        }
        return super.getList();
    }
}
