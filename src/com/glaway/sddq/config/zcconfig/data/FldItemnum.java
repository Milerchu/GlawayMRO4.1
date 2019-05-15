package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * 物料编码字段类-校验唯一性
 * 
 * @author  public2175
 * @version  [版本号, 2018-10-31]
 * @since  [产品/模块版本]
 */
public class FldItemnum extends JpoField {
	
	  /**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	    public void validate() throws MroException {
	        super.validate();
	        String itemnum = this.getInputMroType().getStringValue();
	        String assetnum = this.getJpo().getString("assetnum");
        	String sqn = this.getJpo().getString("sqn");
	        if ("ZCCONFIG".equals(this.getJpo().getAppName())) {
	        	if(StringUtils.isNotEmpty(sqn)){
	        		if(sqn.length() != 10){
	        			this.getField("sqn").clearError();
	        			if ("CAR".equals(this.getJpo().getParent().getString("ASSETLEVEL"))) {
			                IJpoSet jposet = this.getUserServer().getJpoSet("ASSET",
			                        "SQN='" + sqn + "' and assetnum != '" + assetnum + "' and type='2' and itemnum='"+itemnum+"'");
			                if (jposet != null && jposet.count() > 0) {
			                    throw new AppException("ASSET", "sqnitemisrepeat");
			                }
			            } else if (this.getJpo() != null
			                    && (!StringUtil.isNullOrEmpty(this.getJpo().getParent().getString("itemnum"))
			                            && ItemUtil.getItem(this.getJpo().getParent().getString("itemnum")) != null && ItemUtil
			                            .getItem(this.getJpo().getParent().getString("itemnum")).getBoolean("ISIV"))) {
			                IJpoSet jposet = this.getUserServer().getJpoSet("ASSET",
			                        "SQN='" + sqn + "' and assetnum != '" + assetnum + "' and type='2'   and itemnum='"+itemnum+"'");
			                if (jposet != null && jposet.count() > 0) {
			                    throw new AppException("ASSET", "sqnitemisrepeat");
			                }
			            } else {
			                IJpoSet jposet = this.getUserServer().getJpoSet("ASSET",
			                        "SQN='" + sqn + "' and assetnum != '" + assetnum + "'  and itemnum='"+itemnum+"'");
			                if (jposet != null && jposet.count() > 0) {
			                    throw new AppException("ASSET", "sqnitemisrepeat");
			                }
			            }
	        		}
	        	}
	        } else {
	        	if(StringUtils.isNotEmpty(sqn)){
	        		if(sqn.length() != 10){
	        			IJpoSet jposet = this.getUserServer().getJpoSet("ASSET",
		                        "SQN='" + sqn + "' and assetnum != '" + assetnum + "' and itemnum='"+itemnum+"'");
		                if (jposet != null && jposet.count() > 0) {
		                    throw new AppException("ASSET", "sqnitemisrepeat");
		                }
	        		}
	        	}
	        }
	    }
	
}
