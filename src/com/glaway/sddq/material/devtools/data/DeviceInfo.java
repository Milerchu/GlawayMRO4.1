package com.glaway.sddq.material.devtools.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
* @ClassName: FlightLog
* @Description: TODO 
* @author yyang
* @date 2017-1-16 上午10:37:17
*
 */
public class DeviceInfo extends Jpo implements IJpo
{

	
    /**
    * @Fields serialVersionUID : TODO
    */
    
    private static final long serialVersionUID = 1L;
    
    @Override
	public void init() throws MroException {
		super.init();
		String status=this.getString("status");
        if ("已更新".equalsIgnoreCase(status))
        {
			this.setFlag(GWConstant.S_READONLY, true);
		}
        
        if(!toBeAdded()){
            setFieldFlag("DEVICENUM", GWConstant.S_READONLY, true);
        }
	}
    
    @Override
    public void add()
        throws MroException
    {
        super.add();
        String appName = this.getAppName();
        if (StringUtil.isStrNotEmpty(appName))
        {
            String devtype = "";
            if ("DEVTOOLS".equalsIgnoreCase(appName))
            {
                devtype = "DEV";
            }
            else if ("COMPTOOLS".equalsIgnoreCase(appName))
            {
                devtype = "COMP";
            }
            this.setValue("devtype", devtype);
        }
    }
	
}
