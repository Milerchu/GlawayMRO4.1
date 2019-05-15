package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
/**
 * 
 * 车型字段类
 * 
 * @author  public2797
 * @version  [版本号, 2019-4-26]
 * @since  [产品/模块版本]
 */
public class FldCmodel extends JpoField{
	
private static final long serialVersionUID = 1L;
	
	/**
	 * 赋值
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub		
		String cmodel = this.getJpo().getString("CMODEL");
		String carno = this.getJpo().getString("CARNO");
		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
				"ASSET");
		assetSet.setQueryWhere("carno='"+carno+"'and cmodel='"+cmodel+"'");
		//assetSet.reset();
		if(!assetSet.isEmpty()){
		String assetnum=assetSet.getJpo().getString("assetnum");
		this.getJpo().setValue("assetnum", assetnum);
		}else{
			throw new AppException("cmodel", "cmodelisnull");
		}
		
	}	

}
