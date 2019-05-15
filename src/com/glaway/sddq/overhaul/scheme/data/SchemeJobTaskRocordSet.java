package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * @ClassName SchemeJobTaskRocordSet
 * @Description 检修方案中的检查项Set
 * @author public2175
 * @Date 2018-8-17 上午11:20:59
 * @version 1.0.0
 */
public class SchemeJobTaskRocordSet extends JpoSet implements IJpoSet {
    
    private static final long serialVersionUID = 1L;
    
    public SchemeJobTaskRocord getJpoInstance()
    {
        return new SchemeJobTaskRocord();
    }
    
}
