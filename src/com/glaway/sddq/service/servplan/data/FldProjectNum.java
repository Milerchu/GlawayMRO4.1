package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 项目立项编号绑定字段类
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-18]
 * @since  [产品/模块版本]
 */
public class FldProjectNum extends JpoField {
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		MroType value = getInputMroType();
		IJpo servplanjpo = getJpo();
    	IJpoSet projectinfoset = MroServer.getMroServer().getJpoSet("PROJECTINFO", MroServer.getMroServer().getSystemUserServer());
    	projectinfoset.setQueryWhere("projectnum = '"+value.asString()+"'");
    	projectinfoset.reset();
    	if(!projectinfoset.isEmpty()){
    		IJpo projectinfo = projectinfoset.getJpo();
    		servplanjpo.setValue("WORKORDERNUM", projectinfo.getString("WORKORDERNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    		servplanjpo.setValue("PRJMANAGER", projectinfo.getString("PRJMANAGER"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    		servplanjpo.setValue("COMMUNICATIONPLAN", projectinfo.getString("COMMUNICATIONPLAN"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    		servplanjpo.setValue("PROJECTTARGET", projectinfo.getString("GOAL"));
    		servplanjpo.setValue("PROJECTRANGE", projectinfo.getString("PROJECTRANGE"));
    		servplanjpo.setValue("RISKPLAN", projectinfo.getString("MAJORRISKASMENT"));
    	}
	}
	
}
