package com.glaway.sddq.base.locations.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 位置变更状态字段类
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-1]
 * @since   [GlawayMro4.0/位置]
 */
public class FldChangeStatus extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //在库房管理应用程序中可选择状态只包含（'正常','停止使用'）
        if ("STOREROOM".equalsIgnoreCase(getJpo().getJpoSet().getAppName()))
        {
            if (getField("ISSAPSTORE").getBooleanValue())
            {
                this.setListWhere("VALUE IN ('正常')");
            }
            else
            {
                this.setListWhere("VALUE IN ('正常','停止使用')");
            }
        }
        return super.getList();
    }
}
