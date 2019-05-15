package com.glaway.sddq.back.msgConfig.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.back.msgConfig.bean.IMsgConfig;

/**
 * 
 * msgConfig中class的字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-3-1]
 * @since  [产品/模块版本]
 */
public class FldClass extends JpoField
{
    private static final long serialVersionUID = -3724065511343262363L;
    
    @Override
    public void action()
        throws MroException
    {
        String classname = this.getValue();
        if (!StringUtil.isNullOrEmpty(classname))
        {
            try
            {
                //判断类是否存在
                Class.forName(classname);
                //判断是否实现了ActionCustomClass接口
                if (!IMsgConfig.class.isAssignableFrom(Class.forName(classname)))
                {
                    throw new AppException("imsg", "imsgconfig");
                }
            }
            catch (ClassNotFoundException e)
            {
                throw new AppException("common", "classnotfound");
            }
        }
    }
}
