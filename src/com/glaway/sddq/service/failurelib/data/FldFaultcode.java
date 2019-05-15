package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

public class FldFaultcode extends JpoField
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
            getJpo().setFieldFlag("CAUSECODE", GWConstant.S_READONLY, false);
        }
        super.action();
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        IJpo failurelib = getJpo();
        if (failurelib.getString("FAILURECODE") != null)
        { //根据故障代码过滤
            setListWhere("faultcode='" + failurelib.getString("FAILURECODE") + "'");
        }
        else
        {
            setListWhere("1=2");
        }
        return super.getList();
    }
}
