package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修范围中的物料编码字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-23]
 * @since  [产品/模块版本]
 */
public class FldItemNum extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        super.action();
        if (this.isValueChanged())
        {
            this.getField("SOPNUM").setValueNull(GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
        }
    }
}
