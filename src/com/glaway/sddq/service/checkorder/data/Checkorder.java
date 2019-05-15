package com.glaway.sddq.service.checkorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <检查单 jpo>
 * 
 * @author  hzhu
 * @version  [MRO4.1, 2018-5-17]
 * @since  [MRO4.1/模块版本]
 */
public class Checkorder extends Jpo
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -131309955446030234L;
    
    @Override
    public void init()
        throws MroException
    {
        String status = getString("STATUS");
        if ("检查完成".equals(status))
        {//检查完成 所有记录只读
            this.setFlag(GWConstant.S_READONLY, true);
        }
        
        super.init();
    }
}
