package com.glaway.sddq.material.devtools.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
* @ClassName: FlightLogSet
* @Description: TODO 
* @author yyang
* @date 2017-1-16 上午10:38:12
*
 */
public class DeviceInfoSet extends JpoSet implements IJpoSet
{

	
	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;

    public DeviceInfo getJpoInstance()
    {
        return new DeviceInfo();
    }
}
