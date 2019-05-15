package com.glaway.sddq.material.detectedmater.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;

/**
 * 
 * 有效性检测Jpo
 * 
 * @author  bchen
 * @version  [版本号, 2018-6-2]
 * @since  [产品/模块版本]
 */
public class DetectedMater extends StatusJpo implements IStatusJpo, FixedLoggers
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        
        if (getString("STATUS").equals("已锁定"))
        {
            throw new AppException("detectedmater", "cannotnull");
        }
        super.delete(flag);
    }
    
}
