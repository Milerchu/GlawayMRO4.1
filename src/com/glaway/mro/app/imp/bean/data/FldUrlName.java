package com.glaway.mro.app.imp.bean.data;

import java.io.File;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 导入日志urlname字段类
 * @author txu
 *
 */
public class FldUrlName extends JpoField
{
    @Override
    public void init()
        throws MroException
    {
        // TODO Auto-generated method stub
        super.init();
        String curUrl=getValue();
        File file =new File(curUrl);
        if(!file.exists())
        {
            setValue("对应文件已被删除",GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
        }
    }
}
