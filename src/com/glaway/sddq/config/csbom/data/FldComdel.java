package com.glaway.sddq.config.csbom.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 车型项目字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-10]
 * @since [产品/模块版本]
 */
public class FldComdel extends JpoField {


	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

/*	public void validate() throws MroException {
		super.validate();
		// String cmodel = this.getValue();
		String cmodel = getInputMroType().asString();
		String version = this.getJpo().getString("VERSION");
		// 1.取当前jposet
		IJpoSet assetcsSet = MroServer.getMroServer().getSysJpoSet("ASSETCS");
		assetcsSet.setUserWhere("CMODEL='" + cmodel + "'and VERSION='"
				+ version + "' ");
		assetcsSet.reset();
		if (assetcsSet.count() > 0) {
			throw new MroException("ASSETCS", "COMDEL");
		}

	}*/

	@Override
	public void action() throws MroException {

		super.action();
		String cmodel = this.getInputMroType().getStringValue();
		String newVersion = getNextMaxVersion(cmodel);  
		this.getJpo().setValue("version", newVersion,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//		// this.getField("VERSION").setValueNull();
//		String version = this.getJpo().getField("VERSION").getInputMroType()// 获取版本输入框里的的版本值
//				.asString();
//		// System.out.print(version);
//		this.getJpo().setValue("VERSION", version);
	}
	  
	 public static String getNextMaxVersion(String cmodel) throws MroException{
	    
    	IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS", "version=(select max(version) from ASSETCS where cmodel='"+cmodel+"') and cmodel='"+cmodel+"'");
    	if(jposet != null && jposet.count() > 0){
    		String version = jposet.getJpo(0).getString("version");
    		if(!StringUtil.isNullOrEmpty(version)){
    			String curVersion = String.valueOf(version);
            	String pre = curVersion.substring(1,2);
            	int ver = Integer.valueOf(pre)+1;
            	return "V"+ver+".0";
    		}else{
    			return "V1.0";
    		}
    	}else{
    		return "V1.0";
    	}
    }

}
