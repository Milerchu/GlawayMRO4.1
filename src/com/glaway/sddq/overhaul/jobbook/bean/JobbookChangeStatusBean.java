package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.jobbook.data.JobBook;

/**
 * 
 * 标准作业指导书变更状态类
 * 
 * @author hyhe
 * @version [版本号, 2017-11-3]
 * @since [产品/模块版本]
 */
public class JobbookChangeStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException
    {
        try
        {
            IJpo mr = getAppBean().getJpo();
            // 列表数据为空时
            if (mr == null)
            {
                return GWConstant.NOACCESS_SAMEMETHOD;
            }
            
            JobBook ct = (JobBook)mr;
            
            ct.changestatus(getString("status"), getString("memo"));
            getAppBean().SAVE();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
