package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 耗损件记录 上车件父级 parent字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-10]
 * @since [产品/模块版本]
 */
public class FldParent extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 4708380565417190140L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "parent", "UPPARENTASSETNUM",
				"UPITEMNUM" }, new String[] { "sqn", "assetnum", "ITEMNUM" });
		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		String listwhere = "";
		IJpo wo = this.getJpo().getParent();
		if ("WORKORDER".equalsIgnoreCase(wo.getName())) {
			// 故障、改造、验证工单
			// 根据车型车号过滤
			listwhere = "assetlevel='SYSTEM' and assetnum in (select assetnum from asset where ancestor in (select assetnum from asset where carno='"
					+ wo.getString("CARNUM")
					+ "' and cmodel='"
					+ wo.getString("MODELS") + "'))";
			this.setListWhere(listwhere);
		}

		return super.getList();
	}

}
