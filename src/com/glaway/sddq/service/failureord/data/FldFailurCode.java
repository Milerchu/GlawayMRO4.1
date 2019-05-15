package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障管理—故障现象字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public class FldFailurCode extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //        this.setListObject("FAULTMNG");
        String repairtype = this.getJpo().getString("REPAIRTYPE");
        if (StringUtil.isStrEmpty(repairtype))
        {
            throw new AppException("faultmng", "carnonull");
        }
        //        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("FAULTMNG", "FAULTCODE='" + repairtype + "'");
        //        jposet.reset();
        
        setListWhere("FAULTCODE='" + repairtype + "'");
        return super.getList();
    }
    
}
