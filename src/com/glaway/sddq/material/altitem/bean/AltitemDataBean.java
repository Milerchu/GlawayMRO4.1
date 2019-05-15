package com.glaway.sddq.material.altitem.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * @ClassName AltitemDataBean
 * @Description 新建可替换物料DataBean
 * @author public2175
 * @Date 2018-7-25 下午8:55:50
 * @version 1.0.0
 */
public class AltitemDataBean extends DataBean {

	@Override
	/**
	 * 编辑可替换物料时后判断新增自己与自己替换关系记录
	 * @throws MroException
	 * @throws IOException
	 */
	public void addEditRowCallBackOk() throws MroException, IOException {
		super.addEditRowCallBackOk();
		if (this.getJpo().isNew()) {
			String itemnum = this.getAppBean().getJpo().getString("itemnum");
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
					"sys_altitem", "1=2");
			String parent = this.getJpo().getString("PARENT");
			String replace = this.getJpo().getString("REPLACE");
			if (StringUtil.isNullOrEmpty(parent)) {
				jposet.setUserWhere("altitemnum ='" + itemnum
						+ "' and REPLACE='" + replace + "'");
			} else {
				jposet.setUserWhere("altitemnum ='" + itemnum
						+ "' and PARENT='" + parent + "' and REPLACE='"
						+ replace + "'");
			}
			jposet.reset();
			if (jposet == null || jposet.count() == 0) {
				IJpo newJpo = jposet.addJpo();
				newJpo.setValue("altitemnum", itemnum);
				newJpo.setValue("PARENT", this.getJpo().getString("PARENT"));
				newJpo.setValue("REPLACE", this.getJpo().getString("REPLACE"));
				jposet.save();
			}
		}
	}
}
