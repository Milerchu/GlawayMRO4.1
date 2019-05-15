package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
/**
 * 
 * 上车件库房字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-23]
 * @since  [产品/模块版本]
 */
public class FldNewLoc extends JpoField {

	 private static final long serialVersionUID = 1L;

	 @Override
	public IJpoSet getList() throws MroException {
		// TODO Auto-generated method stub
		 String tasktype = this.getJpo().getString("TASKTYPE");
		 
		 if (SddqConstant.SXC_ZZ.equals(tasktype)) {
			 IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet("LOCATIONS");
			 this.setListWhere("ERPLOC='" + ItemUtil.ERPLOC_1030
	                    + "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK + "' and locationtype='"
	                    + ItemUtil.LOCATIONTYPE_CG + "'");
	            locationsSet.reset();
		 }if(SddqConstant.SXC_OH.equals(tasktype)){
			 IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet("LOCATIONS");
	            this.setListWhere("ERPLOC='" + ItemUtil.ERPLOC_1020
	                    + "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCZDK + "' and locationtype='"
	                    + ItemUtil.LOCATIONTYPE_CG + "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
	            locationsSet.reset();	            
		 }if(SddqConstant.SXC_OH_JC.equals(tasktype)){
			 IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet("LOCATIONS");
	            this.setListWhere("ERPLOC='" + ItemUtil.ERPLOC_1020
	                    + "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCZDK + "' and locationtype='"
	                    + ItemUtil.LOCATIONTYPE_CG + "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
	            locationsSet.reset();
		 }
		return super.getList();
	}
}
