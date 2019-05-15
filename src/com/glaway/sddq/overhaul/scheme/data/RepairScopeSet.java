package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修范围jpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-17]
 * @since  [产品/模块版本]
 */
public class RepairScopeSet extends JpoSet implements IJpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public RepairScope getJpoInstance()
    {
        return new RepairScope();
    }
    
}
