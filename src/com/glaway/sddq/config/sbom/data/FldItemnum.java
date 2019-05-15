package com.glaway.sddq.config.sbom.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 物料编码字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-7]
 * @since  [产品/模块版本]
 */
public class FldItemnum extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"ITEMNUM","DESCRIPTION","MODEL"}, new String[] {"ITEMNUM","DESCRIPTION","SPECIFICATION"});
    }

//    @Override
//    public void validate() throws MroException {
//        super.validate();
//        //校验唯一性
//        String itemnum = this.getInputMroType().getStringValue();
//        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP", "ASSETTMPLEVEL = 'ASSET' and ITEMNUM='"+itemnum+"'");
//        if(jposet != null && jposet.count() > 0){
//            throw new AppException("assettmp", "itemisrepeat");
//        }
//    }

	@Override
	public void action() throws MroException {
		super.action();
		String itemnum = this.getInputMroType().getStringValue();
		String newVersion = getNextMaxVersion(itemnum);  
		this.getJpo().setValue("version", newVersion,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
    
	 public static String getNextMaxVersion(String itemnunm) throws MroException{
	    
    	IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP", "version=(select max(version) from ASSETTMP where itemnum='"+itemnunm+"' and assettmplevel='ASSET') and itemnum='"+itemnunm+"' and assettmplevel='ASSET'");
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
