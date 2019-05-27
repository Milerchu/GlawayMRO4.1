package com.glaway.sddq.overhaul.taskorder.data;

import org.apache.axis.utils.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.LocationUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 周转件更换时的新产品序列号
 * 
 * @author hyhe
 * @version [版本号, 2018-1-26]
 * @since [产品/模块版本]
 */
public class FldTaskOrderSwapNewSqn extends JpoField {

	private static final long serialVersionUID = 1L;

	/**
	 * 初始化时，设置字段映射
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "NEWASSETNUM", "NEWSQN", "NEWITEMNUM",
				"NEWLOC", "NEWBINNUM", "NEWLOTNUM", "NEWSOFTVERSION" },
				new String[] { "ASSETNUM", "SQN", "ITEMNUM", "LOCATION",
						"BINNUM", "LOTNUM", "SOFTVERSION" });
	}

	/**
	 * 根据不同的类型，过滤可选择的上车件的产品序列号
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String tasktype = getJpo().getString("TASKTYPE");
		if (getJpo().getString("SQN").isEmpty()
				&& (!SddqConstant.SXC_GAIZ.equals(tasktype))) {
			throw new AppException("exchangerecord", "notnull");
		} else {
			// 周转件上车件选择
			if (SddqConstant.SXC_ZZ.equals(tasktype)) {
				this.setLookupMap(new String[] { "NEWSQN", "NEWITEMNUM",
						"NEWLOC", "NEWLOTNUM", "NEWASSETNUM" },
						new String[] { "SQN", "ITEMNUM", "STOCKADDRESS",
								"LOTNUM", "ASSETNUM" });
				this.setListObject("MUSTCHANGEMPR");
				IJpo wo = this.getJpo().getParent().getParent();
				String whichstation = wo.getString("WHICHSTATION");// 获取站点数据
				String itemnum = this.getJpo().getString("ITEMNUM");
				String itemnums = ItemUtil.getAltItemnums(itemnum);
				String loc = LocationUtil.getLocationForZjSc(whichstation);
				// 通过站点找到该站点下的检修库房，满足条件该站点下的1030现场库
				// zzx add 8.21
				String assetnumnew = "";

				IJpoSet exchangerecordSet = getJpo().getJpoSet();

				assetnumnew = WorkorderUtil.serverupitemnum(exchangerecordSet,
						getJpo());

				// zzx end
				String sqls = "stockaddress = '"
						+ loc
						+ "' and status = '0' and sqn is not null and itemnum in("
						+ itemnums + ")";
				if (!StringUtils.isEmpty(loc)) {
					// this.setListWhere("stockaddress = '" + loc
					// +
					// "' and status = '0' and sqn is not null and itemnum in("
					// + itemnums + ")");
					if (StringUtil.isStrNotEmpty(assetnumnew)) {
						sqls += " and assetnum not in(" + assetnumnew + ")";
					}
					this.setListWhere(sqls);

				} else {
					this.setListWhere("1=2");
				}
			}
			// 偶换件上车件选择
			else if (SddqConstant.SXC_OH.equals(tasktype)) {
				this.setLookupMap(new String[] { "NEWSQN", "NEWASSETNUM",
						"NEWITEMNUM", "NEWLOC", "NEWBINNUM", "NEWLOTNUM" },
						new String[] { "SQN", "ASSETNUM", "ITEMNUM",
								"LOCATION", "BINNUM", "LOTNUM" });
				String itemnum = this.getJpo().getString("ITEMNUM");
				this.setListObject("ASSET");
				// String itemnums = "'" + this.getJpo().getString("ITEMNUM");
				IJpo wo = this.getJpo().getParent().getParent();
				String whichstation = wo.getString("WHICHSTATION");
				Boolean iscustitem = getJpo().getBoolean("ISCUSTITEM");
				// 偶换件上车间应从当前工单中选择的站点的存放检修偶换件的库房，即1020 现场站点库 的检修库房
				// zzx add 8.21
				String assetnumnew = "";

				IJpoSet exchangerecordSet = getJpo().getJpoSet();

				assetnumnew = WorkorderUtil.serverupitemnum(exchangerecordSet,
						getJpo());

				// zzx end
				String loc = LocationUtil.getLocationForOhSc(whichstation,
						iscustitem);
				if (!StringUtil.isStrEmpty(loc)) {
					String itemnums = ItemUtil.getAltItemnums(itemnum);
					String sqloh = "itemnum in("
							+ itemnums
							+ ") and type in('1','3') and iserp != '0' and assetlevel = 'ASSET' and LOCATION='"
							+ loc + "'";
					if (StringUtil.isStrNotEmpty(assetnumnew)) {
						sqloh += " and assetnum not in(" + assetnumnew + ")";
					}
					setListWhere(sqloh);
				} else {
					setListWhere("1=2");
				}
			} else if (SddqConstant.SXC_OH_JC.equals(tasktype)) {
				this.setLookupMap(new String[] { "NEWSQN", "NEWASSETNUM",
						"NEWITEMNUM", "NEWLOC", "NEWBINNUM", "NEWLOTNUM" },
						new String[] { "SQN", "ASSETNUM", "ITEMNUM",
								"LOCATION", "BINNUM", "LOTNUM" });
				String itemnum = this.getJpo().getString("ITEMNUM");
				this.setListObject("ASSET");
				// String itemnums = "'" + this.getJpo().getString("ITEMNUM");
				IJpo wo = this.getJpo().getParent().getParent();
				String whichstation = wo.getString("WHICHSTATION");
				Boolean iscustitem = getJpo().getBoolean("ISCUSTITEM");
				// 偶换件上车间应从当前工单中选择的站点的存放检修偶换件的库房，即1020 现场站点库 的检修库房
				// zzx add 8.21
				String assetnumnew = "";

				IJpoSet exchangerecordSet = getJpo().getJpoSet();

				assetnumnew = WorkorderUtil.serverupitemnum(exchangerecordSet,
						getJpo());

				// zzx end
				String loc = LocationUtil.getLocationForOhSc(whichstation,
						iscustitem);
				if (!StringUtil.isStrEmpty(loc)) {
					String itemnums = ItemUtil.getAltItemnums(itemnum);
					String sqljcoh = "itemnum in("
							+ itemnums
							+ ") and type in('1','3') and iserp != '0' and assetlevel = 'ASSET' and LOCATION='"
							+ loc + "'";
					if (StringUtil.isStrNotEmpty(assetnumnew)) {
						sqljcoh += " and assetnum not in(" + assetnumnew + ")";
					}
					setListWhere(sqljcoh);
				} else {
					setListWhere("1=2");
				}
			} else {// 服务模块上下车
				this.setListObject("ASSET");
				String itemnums = "";

				// 库房过滤
				String locationWhere = "";
				String assetnumnew = "";
				if (SddqConstant.SXC_GZ.equals(tasktype)) {// 故障
					// 可替换物料
					itemnums = ItemUtil.getAltItemnums(getJpo().getString(
							"ITEMNUM"));
					// 工单所属站点
					String locsite = getJpo().getString(
							"WORKORDER.WHICHSTATION");
					// 上车件重复添加过滤

					// IJpoSet exchangerecordSet = getJpo().getParent()
					// .getJpoSet("exchangerecord");
					IJpoSet exchangerecordSet = getJpo().getJpoSet();
					assetnumnew = WorkorderUtil.serverupitemnum(
							exchangerecordSet, getJpo());

					if (getJpo().getBoolean("ISCUSTITEM")) {// 使用客户料

						locationWhere = " location='"
								+ getJpo().getString("LOCATION") + "' ";

					} else {

						locationWhere = "erploc='" + ItemUtil.ERPLOC_1020
								+ "' and  STOREROOMLEVEL in('"
								+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
								+ ItemUtil.STOREROOMLEVEL_XCK
								+ "') and locationtype='"
								+ ItemUtil.LOCATIONTYPE_CG + "' and jxorfw='"
								+ ItemUtil.JXORFW_FW + "' and locsite ='"
								+ locsite + "'";

					}
				} else if (SddqConstant.SXC_GAIZ.equals(tasktype)) {// 改造

					boolean isAddPart = getJpo().getBoolean("ISADDPART");// 是否加装

					if (isAddPart) {
						IJpoSet addContent = getJpo().getParent().getJpoSet(
								"transcontent");
						addContent.setQueryWhere("isaddpart=1");
						addContent.reset();
						for (int idx = 0; idx < addContent.count(); idx++) {
							IJpo content = addContent.getJpo(idx);
							itemnums = "'" + content.getString("NEWSPRDNUM")
									+ "',";
						}
						if (itemnums.endsWith(",")) {
							itemnums = itemnums.substring(0,
									itemnums.length() - 1);
						}
					} else {
						IJpoSet transContentSet = MroServer
								.getMroServer()
								.getSysJpoSet(
										"TRANSCONTENT",
										"transnoticenum='"
												+ getJpo().getParent()
														.getString("noticenum")
												+ "' and PREVIOUSPRDNUM in("
												+ ItemUtil
														.getAltItemnums(getJpo()
																.getString(
																		"ITEMNUM"))
												+ ")");// 改造内容set
						if (!transContentSet.isEmpty()) {
							IJpo content = transContentSet.getJpo(0);
							itemnums = "'" + content.getString("NEWSPRDNUM")
									+ "'";// 改造后物料编码
						}
					}

					IJpoSet exchangerecordSet = getJpo().getJpoSet();
					assetnumnew = WorkorderUtil.serverupitemnum(
							exchangerecordSet, getJpo());
					// 工单所属站点
					String locsite = getJpo().getString(
							"WORKORDER.WHICHSTATION");

					locationWhere = "erploc='" + ItemUtil.ERPLOC_QTGZ
							+ "' and  STOREROOMLEVEL in('"
							+ ItemUtil.STOREROOMLEVEL_XCK + "','"
							+ ItemUtil.STOREROOMLEVEL_XCZDK + "') and jxorfw='"
							+ ItemUtil.JXORFW_FW + "' and locationtype='"
							+ ItemUtil.LOCATIONTYPE_CG + "' and locsite ='"
							+ locsite + "'";

				} else {// 验证
					locationWhere = "1 = 1";
				}

				String sql = "itemnum in("
						+ itemnums
						+ ") and type in( '1','3') and location in (select location from locations where "
						+ locationWhere
						+ " ) and status not in('故障','改造下车') and sqn not like 'LS%' and iserp<>0 and islocked<>1 ";
				if (StringUtil.isStrNotEmpty(assetnumnew)) {
					sql += " and assetnum not in(" + assetnumnew + ")";

				}
				setListWhere(sql);

			}
			return super.getList();
		}
	}

	/**
	 * 根据不同的类型，映射相关数据
	 * 
	 * @throws MroException
	 */
	public void action() throws MroException {
		super.action();
		IJpo exjpo = getJpo();
		IJpoSet asset = getUserServer().getJpoSet("ASSET",
				" ASSETNUM='" + exjpo.getString("NEWASSETNUM") + "'");
		// 首先判断是检修业务还是服务业务，检修又分为检修周转件还是检修偶换件，服务业务问朱昊
		// 如果是检修周转件，
		String tasktype = this.getJpo().getString("TASKTYPE");
		if (SddqConstant.SXC_ZZ.equals(tasktype)) {
			// 根据站点去查找库房，并过滤
			IJpo wo = this.getJpo().getParent().getParent();
			String whichstation = wo.getString("WHICHSTATION");// 获取站点数据
			String newloc = LocationUtil.getLocationForZjSc(whichstation);
			// 周转件上车件是从当前站点下的1030现场检修库且所有已经领料且没有上车的周转件中去选择
			if (asset != null && asset.count() > 0) {
				exjpo.setValue("NEWITEMNUM", asset.getJpo()
						.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				exjpo.setValue("NEWLOC", newloc,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				exjpo.setValue("NEWASSETNUM",
						asset.getJpo().getString("ASSETNUM"));
			}
			// 同时下车件的库房默认设置为该站点下的用于检修的其他待处理物资库
			String loc = LocationUtil.getLocationForZjXc(whichstation);
			exjpo.setValue("LOCATION", loc,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		} else if (SddqConstant.SXC_OH.equals(tasktype)) {// 如果是检修偶换件
			// 上车间应从当前工单中选择的站点的存放检修偶换件的库房，即1020 现场站点库 的检修库房
			IJpo wo = this.getJpo().getParent().getParent();
			String whichstation = wo.getString("WHICHSTATION");
			Boolean iscustitem = getJpo().getBoolean("ISCUSTITEM");
			// 偶换件下车件默认入库到当前工单中选择的站点的检修偶换件的维修库，即1020 现场站点库 的检修库房
			String loc = LocationUtil.getLocationForOhXc(whichstation,
					iscustitem);
			if (loc.isEmpty()) {
				throw new AppException("locations", "notnull");

			} else {
				exjpo.setValue("LOCATION", loc,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (asset != null && asset.count() > 0) {
					exjpo.setValue("NEWITEMNUM",
							asset.getJpo().getString("ITEMNUM"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					exjpo.setValue("NEWLOC",
							asset.getJpo().getString("LOCATION"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					exjpo.setValue("NEWASSETNUM",
							asset.getJpo().getString("ASSETNUM"));
				}
			}
		} else if (SddqConstant.SXC_OH_JC.equals(tasktype)) {
			// 上车间应从当前工单中选择的站点的存放检修偶换件的库房，即1020 现场站点库 的检修库房
			IJpo wo = this.getJpo().getParent().getParent();
			String whichstation = wo.getString("WHICHSTATION");
			Boolean iscustitem = getJpo().getBoolean("ISCUSTITEM");
			// 偶换件下车件默认入库到当前工单中选择的站点的检修偶换件的维修库，即1020 现场站点库 的检修库房
			String loc = LocationUtil.getLocationForOhXc(whichstation,
					iscustitem);
			if (loc.isEmpty()) {
				throw new AppException("locations", "notnull");

			} else {
				exjpo.setValue("LOCATION", loc,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (asset != null && asset.count() > 0) {
					exjpo.setValue("NEWITEMNUM",
							asset.getJpo().getString("ITEMNUM"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					exjpo.setValue("NEWLOC",
							asset.getJpo().getString("LOCATION"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					exjpo.setValue("NEWASSETNUM",
							asset.getJpo().getString("ASSETNUM"));
				}
			}
		} else {
			// 以下是服务模块的逻辑
			if (asset != null && asset.count() > 0) {
				exjpo.setValue("NEWITEMNUM", asset.getJpo()
						.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				exjpo.setValue("NEWLOC", asset.getJpo().getString("LOCATION"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				exjpo.setValue("NEWASSETNUM",
						asset.getJpo().getString("ASSETNUM"));
			}

			if (SddqConstant.SXC_GZ.equals(tasktype)) {// 故障
				// 故障品处置方式
				String dealMode = exjpo.getString("dealmode");
				if (StringUtil.isStrNotEmpty(dealMode)
						&& SddqConstant.FAIL_DEALMODE_RETENTION
								.equals(dealMode)) {// 不返修
					// 将库房设置成一致
					if (!exjpo.getString("location").equalsIgnoreCase(
							exjpo.getString("newloc"))) {
						exjpo.setValue("location", exjpo.getString("newloc"),
								GWConstant.P_NOVALIDATION);
					}

				}
			}
		}
	}
}
