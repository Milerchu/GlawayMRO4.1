package com.glaway.sddq.service.userinfo.date;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 用户资料计划jpoSet
 * 
 * @author   ygao
 * @version  [版本号, 2017-11-7]
 * @since  [产品/模块版本]
 */
public class UserInfoSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public UserInfo getJpoInstance()
    {
        return new UserInfo();
    }
}
