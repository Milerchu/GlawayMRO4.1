package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 工作流基本信息表jpo-wfinfo
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-5]
 * @since  [MRO4.0/模块版本]
 */
public class WfInfo extends Jpo implements IJpo
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if (this.isNew())
        {
            this.setFieldFlag("APP", GWConstant.S_READONLY, false);
        }
        else
        {
            this.setFieldFlag("APP", GWConstant.S_READONLY, true);
        }
    }
    
    /**
     * 删除流程的同时删除流程的各个版本
     * @throws MroException
     */
    @Override
    public void beforeSave()
        throws MroException
    {
        if (this.toBeDeleted())
        {
            IJpoSet jposet = this.getJpoSet("WFVERSION");
            jposet.deleteAll();
        }
    }
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        IJpoSet jposet = this.getJpoSet("WFVERSION");
        if (jposet.isEmpty())
        {
            super.delete(flag);
        }
        else
        {
            int count = 0;
            for (int index = 0; index < jposet.count(); index++)
            {
                IJpo jpo = jposet.getJpo(index);
                if (!jpo.getString("STATUS").equals(WFConstant.DISABLE))
                {
                    throw new AppException("wfinfo", "cannotdeleteAll");
                }
                else
                {
                    count++;
                }
            }
            if (count == jposet.count())
            {
                super.delete(flag);
            }
        }
    }
}
