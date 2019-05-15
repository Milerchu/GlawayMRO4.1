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
 * @ClassName: WfVersion
 * @Description: 工作流版本信息表jpo-wfversion
 * @author hyhe
 * @date Mar 23, 2016 2:46:16 PM
 */
public class WfVersion extends Jpo
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void add()
        throws MroException
    {
        IJpo maxVersionJpo = getMaxVersionJpo();
        if (maxVersionJpo != null && maxVersionJpo.getString("STATUS").equals(WFConstant.EDIT))
        {
        	getMaxVersionJpo().getJpoSet().save();
            throw new MroException("wfinfo", "noupdate");
        }
        
        super.add();
        //获取当前流程的最新版本
        if (!this.getJpoSet().isEmpty())
        {
            int count = this.getJpoSet().count();
            int newVersion = count;
            for (int index = 0; index < count; index++)
            {
                IJpo jpo = this.getJpoSet().getJpo(index);
                if (jpo != null && !jpo.getString("VERSION").isEmpty())
                {
                    int version = Integer.valueOf(jpo.getString("VERSION"));
                    if (version >= newVersion)
                    {
                        newVersion = version + 1;
                    }
                }
            }
            this.setValue("VERSION", newVersion, GWConstant.P_NOVALIDATION_AND_NOACTION);
        }
    }
    
    public IJpo getMaxVersionJpo()
        throws MroException
    {
        IJpoSet jposet =
            this.getUserServer().getJpoSet("SYS_WFVERSION",
                "WFINFONUM = '" + this.getString("WFINFONUM")
                    + "' and version = (select max(VERSION) from SYS_WFVERSION where WFINFONUM = '" + this.getString("WFINFONUM") + "')");
        if (jposet != null && jposet.count() > 0)
        {
            return jposet.getJpo(0);
        }
        else
        {
            return null;
        }
        
    }
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        if (!this.getString("STATUS").equals(WFConstant.DISABLE))
        {
            throw new AppException("wfinfo", "isNotDisable");
        }
        IJpoSet jposet = this.getJpoSet("ACT_RU_EXECUTION");
        if (jposet.isEmpty())
        {
            super.delete(flag);
        }
        else
        {
            throw new AppException("wfinfo", "cannotdelete");
        }
    }
}
