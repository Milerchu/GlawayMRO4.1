package com.glaway.sddq.back.Interface.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 接口配置JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-8]
 * @since  [产品/模块版本]
 */
public class UserInterfaceSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public UserInterface getJpoInstance()
    {
        return new UserInterface();
    }
}
