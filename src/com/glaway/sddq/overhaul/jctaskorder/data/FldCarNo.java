package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 交车产品列表车号类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-24]
 * @since  [产品/模块版本]
 */
public class FldCarNo extends JpoField {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "CARNO", "CMODEL"},
				new String[] { "CARNO", "CMODEL"});
	}

	@Override
	public void action() throws MroException {
		if(this.isValueChanged()){
//		    IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
//                    "ASSET","CARNO='"+this.getJpo().getString("CARNO")+"' and CMODEL='"+this.getJpo().getString("cmodel")+"'");
//		    if(assetSet != null && assetSet.count() > 0){
//		        this.getField("CARASSETNUM").setValue(assetSet.getJpo().getString("ancestor"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//		    }
		     this.getJpo().setValueNull("CARRIAGENUM");
		}		
		super.action();
	}
}
