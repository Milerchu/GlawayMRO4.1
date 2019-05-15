package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 配件申请汇总调拨数量字段类
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-7]
 * @since   [GlawayMro4.0/配件申请]
 */
public class FldComptransferqty extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if (getJpo() != null)
        {
            IJpoSet mrltSet = getJpo().getJpoSet("MRLINETRANSFER");
            if (!mrltSet.isEmpty())
            {
                double receiveqty = 0.0;
                for (int i = 0; i < mrltSet.count(); i++)
                {
                    IJpo mrltJpo = mrltSet.getJpo(i);
                    IJpoSet tansferSet = mrltJpo.getJpoSet("TRANSFER");
                    if (!tansferSet.isEmpty())
                    {
                        receiveqty += tansferSet.sum("RECEIVEQTY");
                    }
                }
                setValue(String.valueOf(receiveqty), GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
            }
            else
            {
                setValue(String.valueOf(0.0), GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
            }
        }
    }
}
