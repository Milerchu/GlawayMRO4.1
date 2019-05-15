package com.glaway.sddq.service.valiorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 验证工单检查员字段类
 * 
 * @author  public2797
 * @version  [版本号, 2018-9-29]
 * @since  [产品/模块版本]
 */
public class FldCheckPerson extends JpoField {

	private static final long serialVersionUID = 1L;
	
	public IJpoSet getList() throws MroException {
		this.setListObject("SYS_PERSON");
		String whichoffice =this.getJpo().getString("WHICHOFFICE");
		//IJpoSet deptnumSet =MroServer.getMroServer().getSysJpoSet("SYS_PERSON");
		this.setListWhere("DEPARTMENT='" +whichoffice+ "'");
		return super.getList();
	}
}
