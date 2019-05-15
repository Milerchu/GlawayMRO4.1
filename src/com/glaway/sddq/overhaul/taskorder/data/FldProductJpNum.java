package com.glaway.sddq.overhaul.taskorder.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
/**
 * 
 * 检修部件列表标准作业指导书字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-29]
 * @since  [产品/模块版本]
 */
public class FldProductJpNum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws MroException {
		this.setLookupMap(new String[] { "JPNUM"},
				new String[] { "JPNUM"});
	}

	
	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("JOBBOOK");
		IJpo jxtaskorder = this.getJpo().getParent();
		String cmodel = jxtaskorder.getString("CMODEL");
		String jxcode = jxtaskorder.getString("JXCODE");
		
		String repairprocess = jxtaskorder.getString("repairprocess");

		IJpoSet jobScopeJposet = MroServer.getMroServer().getSysJpoSet(
				"jobscope", "cmodel='" + cmodel + "'");
		String jpnums = "";
		for (int index = 0; index < jobScopeJposet.count(); index++) {
			jpnums = jpnums + "'"
					+ jobScopeJposet.getJpo(index).getString("jpnum") + "',";
		}
		if (StringUtils.isNotEmpty(jpnums)) {
			if (jpnums.endsWith(",")) {
				jpnums = jpnums.substring(0, jpnums.length() - 1);
			}
			this.setListWhere("jpnum in (" + jpnums + ") and JXCODE = '"
					+ jxcode + "' and repairprocess = '" + repairprocess
					+ "' and STATUS='已发布'");
		} else {
			this.setListWhere("1=2");
		}
		return super.getList();
	}

}
