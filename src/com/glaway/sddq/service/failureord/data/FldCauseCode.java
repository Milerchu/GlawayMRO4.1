package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障管理—故障件故障原因字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public class FldCauseCode extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("FAULTCAUSE");
        String failurecode = this.getJpo().getString("FAILURECODE");
        IJpoSet jposet = null;
        if (StringUtil.isStrEmpty(failurecode))
        {
            throw new AppException("faultcause", "carnonull");
        }
        else
        {
            jposet = MroServer.getMroServer().getSysJpoSet("FAULTCAUSE", "FAILURECODE='" + failurecode + "'");
        }
        return jposet;
    }
}
