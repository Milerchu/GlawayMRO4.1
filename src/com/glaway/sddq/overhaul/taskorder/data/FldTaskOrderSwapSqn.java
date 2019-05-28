package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 周转件更换记录产品序列号
 * 
 * @author bchen
 * @version [版本号, 2018-1-26]
 * @since [产品/模块版本]
 */
public class FldTaskOrderSwapSqn extends JpoField {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {

		if (getJpo().getParent() != null) {

			String type = getJpo().getParent().getString("type");// 工单类型
			if ("验证".equals(type)) {
				setLookupMap(new String[] { "ASSETNUM", "SQN", "ITEMNUM",
						"LOTNUM", "FAULTPOSITION" }, new String[] { "ASSETNUM",
						"SQN", "ITEMNUM", "LOTNUM", "RNUM" });
			} else {
				setLookupMap(new String[] { "ASSETNUM", "SQN", "ITEMNUM",
						"LOTNUM", "FAULTPOSITION", "SOFTVERSION" },
						new String[] { "ASSETNUM", "SQN", "ITEMNUM", "LOTNUM",
								"RNUM", "SOFTVERSION" });
			}
		} else {
			setLookupMap(new String[] { "ASSETNUM", "SQN", "ITEMNUM", "LOTNUM",
					"FAULTPOSITION", "SOFTVERSION" }, new String[] {
					"ASSETNUM", "SQN", "ITEMNUM", "LOTNUM", "RNUM",
					"SOFTVERSION" });
		}

	}

	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		String tasktype = this.getJpo().getString("TASKTYPE");
		if (this.getJpo() != null
				&& "JXTASKORDE".equals(this.getJpo().getAppName())) {
			if (SddqConstant.SXC_ZZ.equals(tasktype)) {
				if (this.getJpo() != null && this.getJpo().getParent() != null) {
					String assetnum = this.getJpo().getParent()
							.getString("assetnum");
					String sqn = this.getJpo().getParent().getString("sqn");
					String assetnumnew = "";
					String assetnumone = "";
					// zzx add 8.21
					IJpoSet exchangerecordSet = getJpo().getParent().getJpoSet(
							"EXCHANGERECORD2");

					assetnumnew = WorkorderUtil.serversxitemnum(
							exchangerecordSet, getJpo());

					IJpoSet exchangerecordownSet = getJpo().getJpoSet();
					if (exchangerecordownSet != null
							&& exchangerecordownSet.count() > 0) {
						for (int i = 0; i < exchangerecordownSet.count(); i++) {
							if (exchangerecordownSet.getJpo(i) != this.getJpo()) {
								String assetnums = exchangerecordownSet.getJpo(
										i).getString("ASSETNUM");
								assetnumone += "'" + assetnums + "',";
							}

						}
						if (StringUtil.isStrNotEmpty(assetnumone)) {
							assetnumone = assetnumone.substring(0,
									assetnumone.length() - 1);
						}

					}
					// zzx end

					String sqljx = "assetnum in  (select assetnum  from asset start with assetnum='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)  and type in(2,3) and sqn !='"
							+ sqn
							+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP = 1 and itemnum in (select itemnum from MUSTCHANGEINFO ))";

					/*
					 * this.setListWhere(
					 * "assetnum in  (select assetnum  from asset start with assetnum='"
					 * + assetnum +
					 * "' connect by parent = PRIOR assetnum)  and type in(2,3) and sqn !='"
					 * + sqn + "'");
					 */
					if (StringUtil.isStrNotEmpty(assetnumone)) {
						sqljx += " and assetnum not in(" + assetnumone + ")";
					}

					if ((StringUtil.isStrNotEmpty(assetnumnew))
							&& (StringUtil.isStrNotEmpty(assetnumone))) {
						sqljx += " and assetnum not in(" + assetnumnew
								+ ") and assetnum not in(" + assetnumone + ")";
					} else if ((StringUtil.isStrEmpty(assetnumnew))
							&& (StringUtil.isStrEmpty(assetnumone))) {
						this.setListWhere(sqljx);
					} else if ((StringUtil.isStrNotEmpty(assetnumnew))
							&& (StringUtil.isStrEmpty(assetnumone))) {
						sqljx += " and assetnum not in(" + assetnumnew + ")";
					}

					this.setListWhere(sqljx);

				} else {
					this.setListWhere("1=2");
				}
			} else if (SddqConstant.SXC_OH.equals(tasktype)) {
				if (this.getJpo() != null && this.getJpo().getParent() != null) {
					String assetnum = this.getJpo().getParent()
							.getString("assetnum");
					String sqn = this.getJpo().getParent().getString("sqn");
					String assetnumnew = "";
					String assetnumone = "";
					// zzx add 8.21

					// 周转件中已存在的数据assetnum
					IJpoSet exchangerecordSet = getJpo().getParent().getJpoSet(
							"EXCHANGERECORD");

					assetnumnew = WorkorderUtil.serversxitemnum(
							exchangerecordSet, getJpo());

					IJpoSet exchangerecordownSet = getJpo().getJpoSet();
					if (exchangerecordownSet != null
							&& exchangerecordownSet.count() > 0) {
						for (int i = 0; i < exchangerecordownSet.count(); i++) {
							if (exchangerecordownSet.getJpo(i) != this.getJpo()) {
								String assetnums = exchangerecordownSet.getJpo(
										i).getString("ASSETNUM");
								assetnumone += "'" + assetnums + "',";
							}

						}
						if (StringUtil.isStrNotEmpty(assetnumone)) {
							assetnumone = assetnumone.substring(0,
									assetnumone.length() - 1);
						}

					}
					// zzx end

					String sqljx = "assetnum in  (select assetnum  from asset start with assetnum='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)  and type in(2,3) and assetnum !='"
							+ assetnum + "'";

					/*
					 * this.setListWhere(
					 * "assetnum in  (select assetnum  from asset start with assetnum='"
					 * + assetnum +
					 * "' connect by parent = PRIOR assetnum)  and type in(2,3) and sqn !='"
					 * + sqn + "'");
					 */
					if (StringUtil.isStrNotEmpty(assetnumone)) {
						sqljx += " and assetnum not in(" + assetnumone + ")";
					}

					if ((StringUtil.isStrNotEmpty(assetnumnew))
							&& (StringUtil.isStrNotEmpty(assetnumone))) {
						sqljx += " and assetnum not in(" + assetnumnew
								+ ") and assetnum not in(" + assetnumone + ")";

					} else if ((StringUtil.isStrEmpty(assetnumnew))
							&& (StringUtil.isStrEmpty(assetnumone))) {
						this.setListWhere(sqljx);
					} else if ((StringUtil.isStrNotEmpty(assetnumnew))
							&& (StringUtil.isStrEmpty(assetnumone))) {
						sqljx += " and assetnum not in(" + assetnumnew + ")";
					}

					this.setListWhere(sqljx);
				} else {
					this.setListWhere("1=2");
				}
			}

		} else if (this.getJpo() != null
				&& "JCTASKORDE".equals(this.getJpo().getAppName())) {

			if (this.getJpo().getParent() != null) {
				String assetnum = this.getJpo().getParent()
						.getString("assetnum");
				String sqn = this.getJpo().getParent().getString("sqn");
				String assetnumnew = "";
				String assetnumone = "";
				// zzx add 8.21

				// 周转件中已存在的数据assetnum
				IJpoSet exchangerecordSet = getJpo().getParent().getJpoSet(
						"EXCHANGERECORD");

				assetnumnew = WorkorderUtil.serversxitemnum(exchangerecordSet,
						getJpo());

				IJpoSet exchangerecordownSet = getJpo().getJpoSet();
				if (exchangerecordownSet != null
						&& exchangerecordownSet.count() > 0) {
					for (int i = 0; i < exchangerecordownSet.count(); i++) {
						if (exchangerecordownSet.getJpo(i) != this.getJpo()) {
							String assetnums = exchangerecordownSet.getJpo(i)
									.getString("ASSETNUM");
							assetnumone += "'" + assetnums + "',";
						}

					}
					if (StringUtil.isStrNotEmpty(assetnumone)) {
						assetnumone = assetnumone.substring(0,
								assetnumone.length() - 1);
					}

				}
				String sqljc = "assetnum in  (select assetnum  from asset start with assetnum='"
						+ assetnum
						+ "' connect by parent = PRIOR assetnum)  and type in ('2','3') and assetnum !='"
						+ assetnum + "'";
				if (StringUtil.isStrNotEmpty(assetnumone)) {
					sqljc += " and assetnum not in(" + assetnumone + ")";
				}

				if ((StringUtil.isStrNotEmpty(assetnumnew))
						&& (StringUtil.isStrNotEmpty(assetnumone))) {
					sqljc += " and assetnum not in(" + assetnumnew
							+ ") and assetnum not in(" + assetnumone + ")";

				} else if ((StringUtil.isStrEmpty(assetnumnew))
						&& (StringUtil.isStrEmpty(assetnumone))) {
					this.setListWhere(sqljc);
				} else if ((StringUtil.isStrNotEmpty(assetnumnew))
						&& (StringUtil.isStrEmpty(assetnumone))) {
					sqljc += " and assetnum not in(" + assetnumnew + ")";
				}

				this.setListWhere(sqljc);

			} else {
				this.setListWhere("1=2");
			}
		} else if (this.getJpo() != null
				&& "FAILUREORD".equals(this.getJpo().getAppName())) {

			// 故障工单
			if (this.getJpo().getParent() != null) {
				String assetnum = "";

				String assetnumnew = "";
				// IJpoSet exchangerecordSet = getJpo().getParent().getJpoSet(
				// "exchangerecord");
				IJpoSet exchangerecordSet = getJpo().getJpoSet();

				assetnumnew = WorkorderUtil.serversxitemnum(exchangerecordSet,
						getJpo());

				// 上下车记录
				assetnum = this.getJpo().getParent().getParent()
						.getString("assetnum");
				String listwhere = "assetnum in  (select assetnum  from asset start with assetnum='"
						+ assetnum
						+ "' connect by parent = PRIOR assetnum)  and type='2' and (islocked<>1 or islocked is null) ";
				if (StringUtil.isStrNotEmpty(assetnumnew)) {
					listwhere += " and assetnum not in(" + assetnumnew + ")";
				}

				this.setListWhere(listwhere);
				// + fltassetnum + "'");

			} else {
				this.setListWhere("1=2");
			}

		} else if (this.getJpo() != null
				&& "TRANSORDER".equals(this.getJpo().getAppName())) {
			// 改造工单
			if (this.getJpo().getParent() != null) {
				String assetnum = "";

				String assetnumnew = "";
				// IJpoSet exchangerecordSet = getJpo().getParent().getJpoSet(
				// "exchangerecord");
				String noticenum = this.getJpo().getParent()
						.getString("NOTICENUM");
				IJpoSet transcontentSet = MroServer.getMroServer()
						.getSysJpoSet("TRANSCONTENT");
				transcontentSet.setQueryWhere("transnoticenum='" + noticenum
						+ "'");
				if (!transcontentSet.isEmpty()) {
					String previousprdnum = "";
					for (int index = 0; index < transcontentSet.count(); index++) {

						IJpo tcontent = transcontentSet.getJpo(index);
						String previousItem = tcontent
								.getString("PREVIOUSPRDNUM");

						// 获取可替换物料字符串
						previousprdnum += ItemUtil.getAltItemnums(previousItem)
								+ ",";
					}
					if (StringUtil.isStrNotEmpty(previousprdnum)) {
						previousprdnum = previousprdnum.substring(0,
								previousprdnum.length() - 1);// 去除末尾逗号
					}

					IJpoSet exchangerecordSet = getJpo().getJpoSet();

					assetnumnew = WorkorderUtil.serversxitemnum(
							exchangerecordSet, getJpo());

					// 上下车记录
					assetnum = this.getJpo().getParent().getString("assetnum");
					String listwhere = "assetnum in  (select assetnum  from asset start with assetnum='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)  and type='2' and (islocked<>1 or islocked is null) and itemnum in("
							+ previousprdnum + ") ";
					if (StringUtil.isStrNotEmpty(assetnumnew)) {
						listwhere += " and assetnum not in(" + assetnumnew
								+ ")";
					}

					this.setListWhere(listwhere);
					// + fltassetnum + "'");

				} else {
					this.setListWhere("1=2");
				}
			}
		} else if (this.getJpo() != null
				&& ("VALIPLAN".equals(this.getJpo().getAppName()) || "VALIORDER"
						.equals(this.getJpo().getAppName()))) {
			// 验证工单&&验证计划
			// 移动端中验证工单为单独的应用，APPNAME使用验证工单的
			String listWhere = "1=1";
			if (getJpo().getParent() != null) {
				String assetnum = "";

				// 上下车记录
				assetnum = getJpo().getParent().getString("assetnum");
				// 下车件assetnum
				listWhere = "assetnum in  (select assetnum  from asset start with assetnum='"
						+ assetnum
						+ "' connect by parent = PRIOR assetnum)  and type='2'";

				boolean sfzy = getJpo().getParent().getBoolean(
						"TRANSPLAN.VALIREQUEST.PLANTOUSE");// 到期是否装用
				if (sfzy) {// 到期装用只需记录一条记录

					// 过滤已选择的产品
					IJpoSet exchangerecordSet = getJpo().getJpoSet();
					String selectedAsset = WorkorderUtil.filterSelected(
							exchangerecordSet, getJpo().getId(), "assetnum");
					if (StringUtil.isStrNotEmpty(selectedAsset)) {
						listWhere += " and assetnum not in(" + selectedAsset
								+ ") ";
					}

				}
				// 验证计划中根据验证产品范围过滤验证前产品信息可选择的产品
				IJpo workOrder = getJpo().getParent();
				String station = workOrder.getString("whichstation");
				String plannum = workOrder.getString("plannum");
				String model = workOrder.getString("models");
				IJpoSet valiProRangeSet = MroServer.getMroServer().getJpoSet(
						"VALIPRORANGE",
						MroServer.getMroServer().getSystemUserServer());
				valiProRangeSet.setQueryWhere("OWNERCUSTOMER='" + station
						+ "' and PLANNUM='" + plannum + "' and transmodels='"
						+ model + "'");
				valiProRangeSet.reset();
				IJpo valiProRangeJpo = valiProRangeSet.getJpo();
				String itemnum = ItemUtil.getAltItemnums(valiProRangeJpo
						.getString("PRODUCTCODE"));
				String excItemnum = ItemUtil.getAltItemnums(valiProRangeJpo
						.getString("PARTCODE"));
				listWhere += " and ITEMNUM in(" + itemnum + "," + excItemnum
						+ ")";

				this.setListWhere(listWhere);
			} else {
				this.setListWhere("1=2");
			}

		}/*
		 * else if (this.getJpo() != null &&
		 * "FAULTMANA".equals(this.getJpo().getAppName())) { // 故障管理 if
		 * (this.getJpo().getParent() != null) { String assetnum =
		 * this.getJpo().getParent() .getString("assetnum"); this.setListWhere(
		 * "assetnum in  (select assetnum  from asset start with assetnum='" +
		 * assetnum + "' connect by parent = PRIOR assetnum)  and type='2'"); }
		 * else { this.setListWhere("1=2"); } }
		 */
		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();

		IJpo exjpo = getJpo();
		String assetnum = exjpo.getString("assetnum");

		IJpoSet asset = MroServer.getMroServer().getSysJpoSet("ASSET",
				" assetnum='" + assetnum + "'");

		if (asset.count() > 0 && asset != null) {

			exjpo.setValue("ITEMNUM", asset.getJpo().getString("ITEMNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		}
		if (isValueChanged()) {
			// 清空上车件信息
			String[] upAssets = new String[] { "NEWSQN", "NEWASSETNUM",
					"NEWITEMNUM", "NEWLOC", "NEWBINNUM", "NEWLOTNUM" };
			for (String upAsset : upAssets) {
				exjpo.setValueNull(upAsset);
			}
		}

		if ("TRANSORDER".equals(exjpo.getAppName())) {// 改造工单
			// 设置入库库房
			String station = exjpo.getParent().getString("WHICHSTATION");
			String where = "erploc='" + ItemUtil.ERPLOC_QTGZ
					+ "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and jxorfw='" + ItemUtil.JXORFW_FW
					+ "' and locsite  ='" + station
					+ "' and TYPE='库房' AND STATUS='正常'";
			IJpoSet locationSet = MroServer.getMroServer().getSysJpoSet(
					"LOCATIONS", where);
			if (!locationSet.isEmpty()) {
				IJpo loc = locationSet.getJpo(0);
				exjpo.setValue("location", loc.getString("LOCATION"),
						GWConstant.P_NOVALIDATION);
			} else {
				throw new MroException("设置库房失败，请检查！");
			}
		} else if ("FAILUREORD".equals(exjpo.getAppName())) {
			// 使用客户料时不修改入库库房
			if (!getJpo().getBoolean("ISCUSTITEM")) {
				// 设置入库库房
				String station = exjpo.getParent().getParent()
						.getString("WHICHSTATION");
				String where = "erploc='" + ItemUtil.ERPLOC_1020
						+ "' and  STOREROOMLEVEL in('"
						+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
						+ ItemUtil.STOREROOMLEVEL_XCK + "') and locationtype='"
						+ ItemUtil.LOCATIONTYPE_WX + "' and jxorfw='"
						+ ItemUtil.JXORFW_FW + "' and locsite ='" + station
						+ "'";
				IJpoSet locationSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS", where);
				if (!locationSet.isEmpty()) {
					IJpo loc = locationSet.getJpo(0);
					exjpo.setValue("location", loc.getString("LOCATION"),
							GWConstant.P_NOVALIDATION);
				} else {
					throw new MroException("设置库房失败，请检查！");
				}
			}
		}
	}

}
