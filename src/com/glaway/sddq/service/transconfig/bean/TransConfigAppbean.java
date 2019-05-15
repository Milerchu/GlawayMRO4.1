package com.glaway.sddq.service.transconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 改造配置变更Appbean
 * 
 * @author public2175
 * @version [版本号, 2018-9-17]
 * @since [产品/模块版本]
 */
public class TransConfigAppbean extends AppBean {

	/**
	 * 
	 * 更新配置
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public void GXPZ() throws MroException, IOException {

		IJpoSet jposet = this.getJpo().getJpoSet("TRANSCONFIGLINE");
		if (jposet != null && jposet.count() > 0) {
			StringBuffer errorMsg = new StringBuffer();
			errorMsg.append("已存在产品序列号：");
			for (int index = 0; index < jposet.count(); index++) {

				IJpo jpo = jposet.getJpo(index);
				String itemnum = jpo.getString("BEFOREITEMNUM");// 原物料编码
				String newitemnum = jpo.getString("NEWITEMNUM");// 新物料编码
				String sqn = jpo.getString("ZXWH");// 原产品序列号
				String newsqn = jpo.getString("CHANGESQN");// 新产品序列号
				String parentSqn = jpo.getString("SQN"); // 父项产品序列号
				String parentItemnum = jpo.getString("ITEMNUM");// 父项物料编码
				String loc = jpo.getString("NWALOCNUM");// 新的位置号
				if (!SddqConstant.GZPZ_STATUS_YCL.equals(jpo
						.getString("STATUS"))) {

					// 通过原物料编码数据去匹配查询物料的追溯属性标记
					if (StringUtil.isStrNotEmpty(itemnum)) {
						String itemType = ItemUtil.getItemInfo(itemnum);
						if (ItemUtil.SQN_ITEM.equals(itemType)) {
							if (StringUtil.isStrNotEmpty(sqn)) {
								IJpoSet assetJpoSet = MroServer.getMroServer()
										.getSysJpoSet("ASSET");
								assetJpoSet.setQueryWhere("sqn='" + sqn
										+ "' and itemnum ='" + itemnum + "'");
								assetJpoSet.reset();
								if (assetJpoSet != null
										&& assetJpoSet.count() > 0) {
									IJpo asset = assetJpoSet.getJpo(0);
									if (!ItemUtil.getAssetInfo(newsqn, itemnum)) {
										// 校验新的产品序列号是否存在
										asset.setValue(
												"sqn",
												newsqn,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										if (StringUtil
												.isStrNotEmpty(newitemnum)) {
											asset.setValue(
													"itemnum",
													newitemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
										if (StringUtil.isStrNotEmpty(loc)) {
											asset.setValue(
													"loc",
													loc,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_YCL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "");
										assetJpoSet.save();
									} else {
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中已存在产品序列号："
												+ newsqn);
									}
								} else {
									jpo.setValue(
											"status",
											SddqConstant.GZPZ_STATUS_CLSB,
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									jpo.setValue("ERRMSG", "系统中找不到产品序列号/批号："
											+ newsqn);
								}
							}
						} else if (ItemUtil.LOT_I_ITEM.equals(itemType)
								|| ItemUtil.LOT_II_ITEM.equals(itemType)) {
							if (StringUtil.isStrNotEmpty(parentSqn)) {
								IJpoSet assetJpoSet = MroServer.getMroServer()
										.getSysJpoSet("ASSET");
								assetJpoSet.setQueryWhere("sqn='" + parentSqn
										+ "' and itemnum ='" + parentItemnum
										+ "'");
								assetJpoSet.reset();
								if (assetJpoSet != null
										&& assetJpoSet.count() > 0) {
									IJpo asset = assetJpoSet.getJpo(0);
									String assetnum = asset
											.getString("assetnum");
									IJpoSet assetpartJpoSet = MroServer
											.getMroServer().getSysJpoSet(
													"ASSETPART");
									assetpartJpoSet.setQueryWhere("itemnum='"
											+ itemnum + "' and LOTNUM='" + sqn
											+ "' and RNUM='" + loc
											+ "' and assetnum='" + assetnum
											+ "'");
									assetpartJpoSet.reset();
									if (assetpartJpoSet != null
											&& assetpartJpoSet.count() > 0) {
										IJpo assetPart = assetpartJpoSet
												.getJpo(0);
										assetPart
												.setValue(
														"itemnum",
														newitemnum,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										assetPart
												.setValue(
														"LOTNUM",
														newsqn,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										assetPart
												.setValue(
														"RNUM",
														loc,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										assetpartJpoSet.save();
									} else {
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中在父项产品序列号：'"
												+ parentSqn + "' 下找不到对应批次号的物料");
									}
								} else {
									jpo.setValue(
											"status",
											SddqConstant.GZPZ_STATUS_CLSB,
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									jpo.setValue("ERRMSG", "系统中找不到父项产品序列号："
											+ parentSqn);
								}
							} else {
								jpo.setValue("status",
										SddqConstant.GZPZ_STATUS_CLSB,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ERRMSG", "父级产品序列号为空，无法定位父级");
							}
						} else {
							jpo.setValue("status",
									SddqConstant.GZPZ_STATUS_CLSB,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							jpo.setValue("ERRMSG", "在系统中不存在物料编码为:" + itemnum
									+ "的物料，无法确认是产品序列号还是批次号");
						}
					} else {
						jpo.setValue("status", SddqConstant.GZPZ_STATUS_CLSB,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("ERRMSG", "原组件物料号为空，无法确认是产品序列号还是批次号");
					}
				}
			}
			this.SAVE();
		}
	}
}
