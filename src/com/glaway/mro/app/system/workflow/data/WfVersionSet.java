package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * @ClassName: WfVersionSet
 * @Description: 工作流版本信息表jpoSet-wfversion
 * @author hyhe
 * @date Mar 23, 2016 2:46:56 PM
 */
public class WfVersionSet extends JpoSet implements IJpoSet
{
    
    private static final long serialVersionUID = 1L;
    
    public WfVersion getJpoInstance()
    {
        return new WfVersion();
    }
    
}
