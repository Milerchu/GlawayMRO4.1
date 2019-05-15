package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 标准作业指导书jpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-3]
 * @since  [产品/模块版本]
 */
public class JobBookSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public JobBook getJpoInstance()
    {
        return new JobBook();
    }
}
