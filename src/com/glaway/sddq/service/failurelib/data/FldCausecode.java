package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

public class FldCausecode extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        if (getInputMroType().getStringValue() != null)
        {
            getJpo().setFieldFlag("REMEDYCODE", GWConstant.S_READONLY, false);
        }
        super.action();
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        
        IJpo failurelib = getJpo();
        if (failurelib.getString("FAULTCODE") != null)
        { //根据故障现象代码过滤
            setListWhere("FAILURECODE='" + failurelib.getString("FAULTCODE") + "'");
        }
        else
        {
            setListWhere("1=2");
        }
        return super.getList();
    }
}
