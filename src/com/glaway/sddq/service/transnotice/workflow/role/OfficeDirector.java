package com.glaway.sddq.service.transnotice.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单/验证申请单-办事处主任角色定制类
 * 
 * @author  zhuhao
 * @version  [版本号, 2018年6月24日]
 * @since  [产品/模块版本]
 */
public class OfficeDirector implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {
		
		
		String persons = "";
		//申请人
		String appperson = curjpo.getString("appperson");
		//获取申请人所属办事处 主任personid
		persons = WorkorderUtil.getOfficeDirectorByPerson(appperson);
		if(StringUtil.isStrEmpty("persons")){
			persons = appperson;
		}
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON", curjpo.getUserServer());
        personSet.setUserWhere("personid in ('" + persons + "')");
        personSet.reset();
        
        return personSet;
	}

}
