package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 上下车记录-父级序列号parentSqn字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月20日]
 * @since [产品/模块版本]
 */
public class FldParentSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {

		setListObject("ASSET");

		if (getJpo() != null && "TRANSORDER".equals(getJpo().getAppName())) {// 改造工单

			String assetnum = getJpo().getParent().getString("assetnum");// 整车assetnum
			IJpoSet contentSet = getJpo().getParent().getJpoSet("TRANSCONTENT");// 改造内容set

			String parentItems = "";
			for (int idx = 0; idx < contentSet.count(); idx++) {
				IJpo content = contentSet.getJpo(idx);
				if (!content.getBoolean("isaddpart")) {// 跳过不是加装的改造产品
					continue;
				}
				parentItems += "'" + content.getString("TARGET") + "',";
			}
			if (StringUtil.isStrNotEmpty(parentItems)) {
				parentItems = parentItems
						.substring(0, parentItems.length() - 1);// 去除末尾的逗号
			} else {
				parentItems = "''";
			}

			String listwhere = " assetnum in  (select assetnum  from asset start with assetnum='"
					+ assetnum
					+ "' connect by parent = PRIOR assetnum)  and type='2' and islocked<>1 "
					+ " and itemnum in (" + parentItems + ") ";

			this.setListWhere(listwhere);
		}

		return super.getList();

	}
}
