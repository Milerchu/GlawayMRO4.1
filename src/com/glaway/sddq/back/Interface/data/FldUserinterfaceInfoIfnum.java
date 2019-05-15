package com.glaway.sddq.back.Interface.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

public class FldUserinterfaceInfoIfnum extends JpoField {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6714808953825812433L;
	
	@Override
	public void validate() throws MroException {
		// TODO Auto-generated method stub
		super.validate();
		IJpoSet userinterfaceinfoset=this.getJpo().getThisJpoSet();
		userinterfaceinfoset.setQueryWhere("ifnum!='"+this.getJpo().getString("ifnum")+"'");
		if(userinterfaceinfoset.count()!=0){
			for(int i=0;i<userinterfaceinfoset.count();i++){
				String ifnum=userinterfaceinfoset.getJpo(i).getString("ifnum");
				if(ifnum.equalsIgnoreCase(getInputMroType().asString())&&!"".equalsIgnoreCase(getInputMroType().asString())){
					throw new MroException("userinterface", "ifnum", new String[]{getInputMroType().asString()});
				}
			}
		}
		
	}
}
