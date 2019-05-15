package com.glaway.sddq.service.servplan.bean;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * 服务计划AppBean
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-1-25]
 * @since  [MRO4.0/模块版本]
 */
public class ServplanAppBean extends AppBean
{
    /**
     * 发生工作流按钮事件
     * @return
     * @throws Exception
     */
    @Override
    public int ROUTEWF()
        throws Exception
    {
        //项目团队成员子表
        IJpoSet teammemberSet = this.getJpo().getJpoSet("PROJECTTEAMEMBER");
        if (teammemberSet.isEmpty())//如果未填写成员则抛出异常
        {
            throw new MroException("servplan", "teammembercannotnull");
        }
        return super.ROUTEWF();
    }
}
