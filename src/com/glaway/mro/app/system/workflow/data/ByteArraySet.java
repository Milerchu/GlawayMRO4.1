package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 工作流 ACT_GE_BYTEARRAY 
 * 
 * @author  jmeng
 * @version  [版本号, 2017-9-27]
 * @since  [产品/模块版本]
 */
public class ByteArraySet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8243324645346981723L;
    
    @Override
    public String getIdColName()
    {
        return "ID_";
    }
    
}
