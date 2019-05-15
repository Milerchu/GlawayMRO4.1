package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 消息配置JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-8]
 * @since  [产品/模块版本]
 */
public class MsgConfigSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public MsgConfig getJpoInstance()
    {
        return new MsgConfig();
    }
}
