package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障工单工具使用记录物料编码字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-20]
 * @since [产品/模块版本]
 */
public class FldJxTaskToolItemnnum extends JpoField {
	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 过滤可选择的物料数据
	 * @return
	 * @throws MroException
	 */
	public IJpoSet getList() throws MroException {

		String itemnums = "";
		setListObject("sys_item");
		IJpoSet jxtasktoolsSet = getJpo().getJpoSet();
		if (jxtasktoolsSet != null && jxtasktoolsSet.count() > 0) {
			for (int i = 0; i < jxtasktoolsSet.count(); i++) {
				if (jxtasktoolsSet.getJpo(i) != this.getJpo()) {
					String assetnums = jxtasktoolsSet.getJpo(i).getString(
							"ITEMNUM");
					itemnums += "'" + assetnums + "',";
				}
			}
			if (StringUtil.isStrNotEmpty(itemnums)) {
				itemnums = itemnums.substring(0, itemnums.length() - 1);
			}
			String listwhere = "";
			if (StringUtil.isStrNotEmpty(itemnums)) {
				listwhere += "  itemnum not in(" + itemnums + ")";
			}
			this.setListWhere(listwhere);
		}
		return super.getList();
	}
}
