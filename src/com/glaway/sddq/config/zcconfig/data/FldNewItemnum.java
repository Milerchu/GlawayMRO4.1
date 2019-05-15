package com.glaway.sddq.config.zcconfig.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 物料编码字段类-校验唯一性
 * 
 * @author  public2175
 * @version  [版本号, 2018-10-31]
 * @since  [产品/模块版本]
 */
public class FldNewItemnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	
	  @Override
	    public void validate() throws MroException {
	        super.validate();
	        //做唯一性校验
	        String itemnum = this.getInputMroType().getStringValue();
	        String sqn = "";
	        if(this.getField("NEWSQN").isError()){
		         sqn = this.getField("NEWSQN").getInputMroType().getStringValue();
	        }else{
	        	sqn = this.getJpo().getString("NEWSQN");
	        }
	       
	    	if(StringUtils.isNotEmpty(sqn) && sqn.length() != 10){
	    		this.getField("NEWSQN").clearError();
	    		IJpoSet jposet = this.getUserServer().getJpoSet("ASSET", "SQN='"+sqn+"'  and itemnum='"+itemnum+"'");
		        if(jposet != null && jposet.count() > 0){
		            throw new AppException("ASSET", "sqnitemisrepeat");
		        }
	    	}
	    }
}
