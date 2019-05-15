package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 工作流  ACT_RE_MODEL
 * 
 * @author  jmeng
 * @version  [版本号, 2017-9-27]
 * @since  [产品/模块版本]
 */
public class ActReModelSet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -6541128570931980541L;
    
    @Override
    public String getIdColName()
    {
        return "ID_";
    }
    
}
