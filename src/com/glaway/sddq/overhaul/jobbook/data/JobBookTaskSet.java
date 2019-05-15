package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 准作业指导书任务项 JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-2]
 * @since  [产品/模块版本]
 */
public class JobBookTaskSet extends JpoSet implements IJpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public JobBookTask getJpoInstance()
    {
        return new JobBookTask();
    }
}
