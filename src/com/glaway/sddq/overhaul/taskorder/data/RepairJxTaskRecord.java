package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;

/**
 * 
 * 检查单记录 JPO
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-31]
 * @since  [产品/模块版本]
 */
public class RepairJxTaskRecord extends Jpo implements IJpo
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
        if (this.getJpoSet("PROBLEMMG") != null)
        {
            this.getJpoSet("PROBLEMMG").deleteAll(flag);
        }
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
        if (this.getJpoSet("PROBLEMMG") != null)
        {
            this.getJpoSet("PROBLEMMG").undeleteAll();
        }
    }
    
}
