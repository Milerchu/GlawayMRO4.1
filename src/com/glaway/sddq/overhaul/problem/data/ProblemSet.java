package com.glaway.sddq.overhaul.problem.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修问题JpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2018-3-26]
 * @since  [产品/模块版本]
 */
public class ProblemSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public Problem getJpoInstance()
    {
        return new Problem();
    }
}
