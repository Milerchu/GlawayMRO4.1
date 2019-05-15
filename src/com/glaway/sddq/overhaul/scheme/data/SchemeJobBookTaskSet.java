package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修准作业指导书任务项 JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-3]
 * @since  [产品/模块版本]
 */
public class SchemeJobBookTaskSet extends JpoSet implements IJpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public SchemeJobBookTask getJpoInstance()
    {
        return new SchemeJobBookTask();
    }
}
