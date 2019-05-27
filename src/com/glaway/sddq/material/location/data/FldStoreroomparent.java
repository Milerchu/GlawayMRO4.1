package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <父级库房选择字段类>
 * 
 * @author xlb
 * @version [GlawayMro4.0, 2018-3-1]
 * @since [GlawayMro4.0/库房管理]
 */
public class FldStoreroomparent extends JpoField {
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		// 如果库房等级是二级，列出一级库房列表，如果是三级，列出二级库房列表
		if (this.getJpo().getString("storeroomgrade").equalsIgnoreCase("二级")) {
			this.setListWhere("storeroomgrade='一级'");
		}
		if (this.getJpo().getString("storeroomgrade").equalsIgnoreCase("三级")) {
			this.setListWhere("storeroomgrade='二级'");
		}
		return super.getList();
	}

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String STOREROOMPARENT=this.getValue();
		IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
		locationsset.setUserWhere("LOCATION='"+STOREROOMPARENT+"'");
		locationsset.reset();
		String erploc=locationsset.getJpo(0).getString("erploc");
		this.getJpo().setValue("erploc", erploc);
		this.getJpo().setFieldFlag("erploc", GWConstant.S_READONLY,true);
		this.getJpo().setFieldFlag("STOREROOMGRADE", GWConstant.S_READONLY,true);
	}
}
