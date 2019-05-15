package com.glaway.sddq.overhaul.plan.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 年度修程字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-6-1]
 * @since  [产品/模块版本]
 */
public class FldRepairProcess extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        
        IJpoSet domainSet = null;
        String cmodel = this.getJpo().getString("CMODEL");
        if (!StringUtil.isNullOrEmpty(cmodel))
        {
            String productline = this.getJpo().getString("MODELS.PRODUCTLINE");
            domainSet =
                getUserServer().getJpoSet("SYS_SYNDOMAIN",
                    "domainid='NEWREPAIRPROCESS' and INNERVALUE = '" + productline + "'");
            domainSet.reset();
            return domainSet;
        }else{
            throw new AppException("repairscheme", "cmodelisnull");
        }
    }
}
