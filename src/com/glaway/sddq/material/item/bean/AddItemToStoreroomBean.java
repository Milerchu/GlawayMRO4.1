package com.glaway.sddq.material.item.bean;

import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 物料清单添加物料到库房DataBean
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-1]
 * @since [GlawayMro4.0/物料清单]
 */
public class AddItemToStoreroomBean extends DataBean {
	@Override
	public void initialize() throws MroException {
		super.initialize();
		if (!this.getPage().isOnFirstTab()) {
			// 过滤掉已经在列表中的库房
			IJpoSet srSet = getJpoSet();
			IJpoSet invSet = getParent().getJpoSet();
			if (!invSet.isEmpty()) {
				String where = "";
				for (int i = 0; i < invSet.count(); i++) {
					IJpo invJpo = invSet.getJpo(i);
					where += ",'" + invJpo.getString("LOCATION") + "'";
				}
				if (StringUtil.isStrNotEmpty(where)) {
					where = "LOCATION NOT IN (" + where.substring(1) + ")";
					srSet.setUserWhere(where);
					srSet.reset();
				}
			}
		}
	}

	@Override
	public int execute() throws MroException {
		if (!this.getPage().isOnFirstTab()) {
			IJpo mr = getAppBean().getJpo();
			// 列表数据为空时
			if (mr == null) {
				return GWConstant.NOACCESS_SAMEMETHOD;
			}
			addInventory(mr);
		} else {
			List<IJpo> itemList = getAppBean().getJpoSet().getSelections();
			for (IJpo jpo : itemList) {
				addInventory(jpo);
			}
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 添加库存记录
	 * 
	 * @param mr
	 *            当前库存项目
	 * @throws MroException
	 * 
	 */
	public void addInventory(IJpo mr) throws MroException {
		if (mr != null) {
			String where = "ITEMNUM='" + mr.getString("ITEMNUM") + "'";
			IJpoSet invSet = getMroSession().getUserServer().getJpoSet(
					"SYS_INVENTORY", where);
			List<IJpo> srList = this.getJpoSet().getSelections();
			if (srList != null) {
				for (IJpo srJpo : srList) {
					if (!isExistLoc(srJpo.getString("LOCATION"), invSet)) {
						IJpo invjpo = invSet.addJpo();
						invjpo.setValue("ITEMNUM", mr.getString("ITEMNUM"), 11L);
						invjpo.setValue("LOCATION",
								srJpo.getString("LOCATION"), 11L);
						invSet.save();
					}
				}
			}
		}
	}

	/**
	 * 
	 * 检查物资项目是否已经添加到库房
	 * 
	 * @param loc
	 * @param invSet
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public boolean isExistLoc(String loc, IJpoSet invSet) throws MroException {
		boolean flag = false;
		for (int i = 0; i < invSet.count(); i++) {
			IJpo invjpo = invSet.getJpo(i);
			if (StringUtil.isEqual(invjpo.getString("LOCATION"), loc)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
