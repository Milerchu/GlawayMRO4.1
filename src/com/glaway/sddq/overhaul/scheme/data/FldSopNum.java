package com.glaway.sddq.overhaul.scheme.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 标准作业指导书编号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-3]
 * @since  [产品/模块版本]
 */
public class FldSopNum extends JpoField
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"SOPNUM"}, new String[] {"JPNUM"});
    }
    
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("JOBBOOK");
        IJpo jpo = this.getJpo();
        IJpo ower = jpo.getParent();
        String repairProcess = ower.getString("REPAIRPROCESS");
        String cmodel = ower.getString("CMODEL");
        
        IJpoSet jobScopeJposet = MroServer.getMroServer().getSysJpoSet("jobscope", "cmodel='" + cmodel + "'");
        String jpnums = "";
        for (int index = 0; index < jobScopeJposet.count(); index++)
        {
            jpnums = jpnums + "'" + jobScopeJposet.getJpo(index).getString("jpnum") + "',";
        }
        if (StringUtils.isNotEmpty(jpnums))
        {
            if (jpnums.endsWith(","))
            {
                jpnums = jpnums.substring(0, jpnums.length() - 1);
            }
            this.setListWhere("jpnum in (" + jpnums + ") and  repairprocess = '"
                + repairProcess + "' and STATUS='已发布'");
        }
        else
        { 
            this.setListWhere("1=2");
        }
        return super.getList();
    }
    
}
