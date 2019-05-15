package com.glaway.sddq.config.soft.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.config.soft.data.SoftConfig;
/**
 * 
 * 软件变更状态DataBean
 * 
 * @author  public2175
 * @version  [版本号, 2018-6-7]
 * @since  [产品/模块版本]
 */
public class SoftChangeStatusDataBean extends DataBean {
	
	 @Override
	    public int execute()
	        throws MroException
	    {
	        try
	        {
	            IJpo mr = getAppBean().getJpo();
	            // 列表数据为空时
	            if (mr == null)
	            {
	                return GWConstant.NOACCESS_SAMEMETHOD;
	            }
	            
	            SoftConfig ct = (SoftConfig)mr;
	            
	            ct.changestatus(getString("status"), getString("memo"));
	            getAppBean().SAVE();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        return GWConstant.NOACCESS_SAMEMETHOD;
	    }
	
}
