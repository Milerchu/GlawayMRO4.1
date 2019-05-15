package com.glaway.sddq.service.servplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 里程碑绑定类
 * 
 * @author  ygao
 * @version  [版本号, 2017-11-8]
 * @since  [产品/模块版本]
 */
public class MilestoneDataBean extends DataBean
{
    @Override
    public int toggledeleterow()
        throws MroException, IOException
    {
        Boolean isedit = getJpo().getBoolean("ISEDITED");//是否可以裁剪
        if (isedit)
        {
            super.toggledeleterow();
        }
        else
        {
            throw new MroException("", "该里程碑不可被删除！");
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
