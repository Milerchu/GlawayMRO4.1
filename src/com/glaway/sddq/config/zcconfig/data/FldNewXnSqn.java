package com.glaway.sddq.config.zcconfig.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName FldNewXnSqn
 * @Description 工具栏中的添加节点的产品序列号字段类
 * @author public2175
 * @Date 2018-8-14 上午10:36:16
 * @version 1.0.0
 */
public class FldNewXnSqn extends JpoField {

    /**
     * @Field @serialVersionUID :默认序列号ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void validate() throws MroException {
        super.validate();
        //做唯一性校验
        String sqn = this.getInputMroType().getStringValue();
    		if(!StringUtils.isEmpty(sqn) && sqn.length() == 10){
    			IJpoSet jposet = this.getUserServer().getJpoSet("ASSET", "SQN='"+sqn+"'");
                if(jposet != null && jposet.count() > 0){
            		this.getField("itemnum").clearError();
                    throw new AppException("ASSET", "sqnisrepeat");
                }
    		}else{
    			String itemnum = "";
    	        if(this.getField("itemnum").isError()){
    	        	itemnum = this.getField("itemnum").getInputMroType().getStringValue();
            		this.getField("itemnum").clearError();
            		this.getField("itemnum").setValue(itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    	        }else{
    	             itemnum = this.getJpo().getString("itemnum");
    	        }
    			if(StringUtils.isNotEmpty(itemnum)){
    			IJpoSet jposet = this.getUserServer().getJpoSet("ASSET", "SQN='"+sqn+"'  and itemnum='"+itemnum+"'");
                if(jposet != null && jposet.count() > 0){
                    throw new AppException("ASSET", "sqnitemisrepeat");
                }
    		}
    	}
    }
}
