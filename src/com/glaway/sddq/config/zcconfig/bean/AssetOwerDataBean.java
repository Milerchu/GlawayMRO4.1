package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.config.zcconfig.data.Asset;
/**
 * 
 * 配属动态DataBean
 * 
 * @author  hyhe
 * @version  [版本号, 2018-5-10]
 * @since  [产品/模块版本]
 */
public class AssetOwerDataBean extends DataBean {
	
	 @Override
	    public int execute()
	        throws MroException, IOException
	    {
	        super.checkSave();
	        String newCarno = this.getString("newcarno");
	        if(!StringUtil.isNullOrEmpty(newCarno)){
	        	String oldCarno = this.getString("carno");
		        if (newCarno != null && oldCarno != null && oldCarno.equals(newCarno))
		        {
		            throw new MroException("asset", "carnonovalid");
		        }
		        if (!StringUtils.isEmpty(newCarno))
		        {
		            Asset asset = (Asset)this.getAppBean().getJpo();
		            asset.changeOwer(oldCarno,
		            		newCarno,"1",
		                getString("NP_STATUSMEMO"),
		                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            this.getAppBean().getJpo().setValue("carno", newCarno,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		        }
		        else
		        {
		            throw new MroException("asset", "carnonovalid");
		        }
	        }
	        String newownercustomer = this.getString("NEWOWNERCUSTOMER");
	        if(!StringUtil.isNullOrEmpty(newownercustomer)){
	        	String ownercustomer = this.getString("OWNERCUSTOMER");
		        if (newownercustomer != null && ownercustomer != null && ownercustomer.equals(newownercustomer))
		        {
		            throw new MroException("asset", "customernonovalid");
		        }
		        if (!StringUtils.isEmpty(newownercustomer))
		        {
		            Asset asset = (Asset)this.getAppBean().getJpo();
		            asset.changeOwer(ownercustomer,
		            		newownercustomer,"2",
		                getString("NP_STATUSMEMO"),
		                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            this.getAppBean().getJpo().setValue("OWNERCUSTOMER", newownercustomer,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		        }
		        else
		        {
		            throw new MroException("asset", "customernonovalid");
		        }
	        }
	        String newoverhauler = this.getString("NEWOVERHAULER");
	        if(!StringUtil.isNullOrEmpty(newoverhauler)){
	        	String overhauler = this.getString("OVERHAULER");
		        if (newoverhauler == null  && newownercustomer.equals(overhauler))
		        {
		            throw new MroException("asset", "overhaulernonovalid");
		        }
		        if (!StringUtils.isEmpty(newoverhauler))
		        {
		            Asset asset = (Asset)this.getAppBean().getJpo();
		            asset.changeOwer(overhauler,
		            		newoverhauler,"3",
		                getString("NP_STATUSMEMO"),
		                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		            this.getAppBean().getJpo().setValue("overhauler", newoverhauler,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		        }
		        else
		        {
		            throw new MroException("asset", "overhaulernonovalid");
		        }
	        }
	        
	        return super.execute();
	    }
}
