package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修计划中的检修方案字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class FldJxSchemeNum extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("REPAIRSCHEME");
        IJpo jpo = this.getJpo();
        String cmodel = jpo.getString("CMODEL");
        String repairProcess = jpo.getString("REPAIRPROCESS");
        this.setListWhere("CMODEL='"+StringUtil.getSafeSqlStr(cmodel)+"' and REPAIRPROCESS='"+StringUtil.getSafeSqlStr(repairProcess)+"' and STATUS='已发布'");
        return super.getList();
    }
}
