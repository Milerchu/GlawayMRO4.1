package com.glaway.sddq.back.msgConfig.bean;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * 消息处理接口类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public interface IMsgConfig
{
    public abstract void execute(IJpo jpo)
        throws MroException;
}
