package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 配件申请选择新状态字段类
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-7]
 * @since   [GlawayMro4.0/配件申请]
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
