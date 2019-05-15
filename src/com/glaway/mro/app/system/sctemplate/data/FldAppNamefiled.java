package com.glaway.mro.app.system.sctemplate.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.table.MroTable;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 应用程序名称字段类：根据不同的应用程序获取对应的编号
 * 
 * @author public2175
 * @version [版本号, 2019-1-10]
 * @since [产品/模块版本]
 */
public class FldAppNamefiled extends JpoField {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = -4631931371037837314L;

	@Override
	public void action() throws MroException {
		super.action();
		String bkey = getJpo().getString("BUSINESSKEY");
		String appname = getJpo().getString("APPNAME");
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("INBOXAPPMAP",
				"APP='" + appname + "'");
		if (jposet != null && jposet.count() > 0 && jposet.getJpo() != null) {
			String mainnum = jposet.getJpo().getString("MAINNUM");
			IJpoSet appset = MroServer.getMroServer().getJpoSet("sys_app",
					this.getUserServer());
			appset.setQueryWhere("app = '" + appname + "'");
			appset.reset();
			String tbname = appset.getJpo().getString("MAINTBNAME");
			IJpoSet tbset = MroServer.getMroServer().getJpoSet(tbname,
					this.getUserServer());
			MroTable mt = MroServer.getMroServer().getTableInfo(tbname);
			String primarykey = mt.getPrimaryKeyColName();
			tbset.setQueryWhere(primarykey + "='" + bkey + "'");
			tbset.reset();
			if (tbset != null && tbset.count() > 0) {
				IJpo tbjpo = tbset.getJpo();
				if (tbjpo != null && !StringUtil.isNullOrEmpty(mainnum)) {
					String num = tbjpo.getString(mainnum);
					this.getJpo().setValue("appfield", num == null ? "" : num);
				}
			}
			appset.destroy();
			tbset.destroy();
		}
	}
}
