package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 调拨单CHANGESTATUS字段类
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since   [GlawayMro4.0/调拨单]
 */
public class FldChangestatus extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        setListWhere("VALUE!='" + getField("STATUS").getValue() + "'");
        return super.getList();
    }
}
