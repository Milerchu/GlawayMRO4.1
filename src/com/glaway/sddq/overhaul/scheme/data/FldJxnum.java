package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修方案中的规程文件字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-17]
 * @since  [产品/模块版本]
 */
public class FldJxnum extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("JXRULE");
        IJpo jpo = this.getJpo();
        String cmodel = jpo.getString("CMODEL");
        String repairProcess = jpo.getString("REPAIRPROCESS");
        String type = jpo.getString("TYPE");
        this.setListWhere("CMODEL='" + StringUtil.getSafeSqlStr(cmodel) + "' and REPAIRPROCESS='"
            + StringUtil.getSafeSqlStr(repairProcess) + "' and TYPE='规程'");
        return super.getList();
    }
}
