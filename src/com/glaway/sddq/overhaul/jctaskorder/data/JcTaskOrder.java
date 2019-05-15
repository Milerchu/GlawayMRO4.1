package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 交车工单Jpo
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-21]
 * @since  [产品/模块版本]
 */
public class JcTaskOrder extends StatusJpo implements IStatusJpo, FixedLoggers
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void delete()
        throws MroException
    {
        if (getString("STATUS").equals("草稿"))
        {
            if (this.getJpoSet("JCPRODUCT") != null)
            {
                IJpoSet jcproductset = this.getJpoSet("JCPRODUCT");
                for (int index = 0; index < jcproductset.count(); index++)
                {
                    if (jcproductset.getJpo(index).getJpoSet("JXTESTRECORD") != null)
                    {
                        IJpoSet jxtestrecordset = jcproductset.getJpo(index).getJpoSet("JXTESTRECORD");
                        for (int i = 0; i < jxtestrecordset.count(); i++)
                        {
                            jxtestrecordset.getJpo(i).getJpoSet("JXTESTRECORDITEM").deleteAll();
                            jxtestrecordset.getJpo(i).getJpoSet("REPAIRPROBLEMDESC").deleteAll();
                        }
                        IJpoSet exchangerecordset = jcproductset.getJpo(index).getJpoSet("EXCHANGERECORD");
                        for(int i = 0; i < exchangerecordset.count(); i++)
                        {
                        	exchangerecordset.getJpo(i).getJpoSet("FAILURELIB").deleteAll();
                        }
                    }
                    jcproductset.getJpo(index).getJpoSet("JXTESTRECORD").deleteAll();
                    jcproductset.getJpo(index).getJpoSet("EXCHANGERECORD").deleteAll();
                }
                this.getJpoSet("JCPRODUCT").deleteAll();
            }                      
        }else
        {
        	throw new AppException("jctaskorder", "undelete");
        }
        super.delete();
    }
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (SddqConstant.JC_STATUS_WC.equals(getString("STATUS"))){
			setFieldFlag("WHICHSTATION", GWConstant.S_READONLY, true);
			setFieldFlag("LIABLEPERSON", GWConstant.S_READONLY, true);
			setFieldFlag("REALSTARTTIME", GWConstant.S_READONLY, true);
			setFieldFlag("REALENDTIME", GWConstant.S_READONLY, true);
			setFieldFlag("REPAIRMETHOD", GWConstant.S_READONLY, true);
			setFieldFlag("REMARK", GWConstant.S_READONLY, true);
		}
	}
}
