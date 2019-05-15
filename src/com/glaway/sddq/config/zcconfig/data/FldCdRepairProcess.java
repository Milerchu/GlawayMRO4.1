package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FldCdRepairProcess extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		
		IJpoSet domainSet = null;
		if(this.getJpo() != null && this.getJpo().getParent() != null){
			String cmodel = this.getJpo().getParent().getString("CMODEL");
			if (!StringUtil.isNullOrEmpty(cmodel)) {
				String productline = this.getJpo().getParent().getString("MODELS.PRODUCTLINE");
				domainSet = getUserServer().getJpoSet("SYS_SYNDOMAIN", "domainid='NEWREPAIRPROCESS' and INNERVALUE = '"+productline+"'");
				domainSet.reset();
				return domainSet;
			}else{
	            throw new AppException("repairscheme", "cmodelisnull");
	        }
		}
		return domainSet;
	}
	
}
