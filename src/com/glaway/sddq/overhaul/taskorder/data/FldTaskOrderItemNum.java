package com.glaway.sddq.overhaul.taskorder.data;

import java.util.Iterator;
import java.util.List;

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

/**
 * 
 * 耗损件记录上车件物料编码字段类
 * 
 * @author bchen
 * @version [版本号, 2018-2-2]
 * @since [产品/模块版本]
 */
public class FldTaskOrderItemNum extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {

		if (getJpo() != null) {
			// I类耗损件lookup
			if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(getJpo()
					.getString("downitemnum")))) {

				this.setLookupMap(new String[] { "UPLOC", "ITEMNUM",
						"INVENTORY", "LOTNUM" }, new String[] { "STOREROOM",
						"ITEMNUM", "LOTQTY", "LOTNUM" });
			} else {

				this.setLookupMap(new String[] { "UPLOC", "ITEMNUM",
						"INVENTORY" }, new String[] { "LOCATION", "ITEMNUM",
						"CURBAL" });
			}
		}
	}

	@Override
	public IJpoSet getList() throws MroException {

		if (this.getJpo().getString("DOWNITEMNUM").isEmpty()) {
			throw new AppException("jxtasklosspart", "notchoice");
		}

		String listwhere = "";
		IJpo jpo = getJpo();
		String mboname = jpo.getParent().getName();
		// 当前工单所属站点
		String station = jpo.getParent().getString("WHICHSTATION");
		// 下车件物料编码
		String downitemnum = jpo.getString("DOWNITEMNUM");
		String itemnums = "";
		String locationWhere = "";
		// 工单类型
		String orderType = getJpo().getParent().getString("type");
		if ("改造".equals(orderType) || "验证".equals(orderType)) {
			locationWhere = " erploc='" + ItemUtil.ERPLOC_QTGZ + "' "
					+ "and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and LOCATIONTYPE='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and jxorfw = '" + ItemUtil.JXORFW_FW
					+ "' and status='正常' and locsite='" + station + "'";
		} else if ("故障".equals(orderType)) {
			if (jpo.getBoolean("ISCUSTITEM")) {// 是否使用客户料

				locationWhere = " location='" + getJpo().getString("UNDERLOC")
						+ "' ";// 上下车库房一致

			} else {

				locationWhere = " erploc='" + ItemUtil.ERPLOC_1020 + "' "
						+ "and STOREROOMLEVEL='"
						+ ItemUtil.STOREROOMLEVEL_XCZDK
						+ "' and LOCATIONTYPE='" + ItemUtil.LOCATIONTYPE_CG
						+ "' and jxorfw = '" + ItemUtil.JXORFW_FW
						+ "' and status='正常' and locsite='" + station + "'";
			}
		} else {
			locationWhere = " 1=2 ";
		}
		if ("WORKORDER".equalsIgnoreCase(mboname)) {// 服务模块工单

			String locationColName = "";
			// 批次号管理
			if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(downitemnum))) {
				this.setListObject("INVBLANCE");
				this.setLookupMap(new String[] { "ITEMNUM", "INVENTORY",
						"UPLOC", "LOTNUM" }, new String[] { "ITEMNUM",
						"PHYSCNTQTY", "STOREROOM", "LOTNUM" });
				locationColName = "storeroom";
				listwhere += " (PHYSCNTQTY-NVL(ORDERAPLYQTY,0))>0 and ";
			} else {
				// 非序列号、非批次号管理
				this.setListObject("SYS_INVENTORY");
				locationColName = "location";
				this.setLookupMap(new String[] { "ITEMNUM", "INVENTORY",
						"UPLOC" }, new String[] { "ITEMNUM", "CURBAL",
						"LOCATION" });
				listwhere += " (CURBAL-NVL(ORDERAPLYQTY,0))>0 and ";
			}

			// 可替换物料
			// itemnums = ItemUtil.getAltItemnums(downitemnum);
			// 过滤掉可替换物料中的序列号件
			List<String> altItemnumList = ItemUtil
					.getAltItemnumsList(downitemnum);
			// 遍历可替换物料集合将序列号件则剔除
			Iterator<String> iterator = altItemnumList.iterator();
			while (iterator.hasNext()) {

				String altItemnum = iterator.next();
				if (ItemUtil.SQN_ITEM.equals(ItemUtil.getItemInfo(altItemnum))) {
					// 移除序列号件物料编码
					iterator.remove();

				}

			}

			// 遍历可替换物料集合拼接字符串
			for (String altItemnum : altItemnumList) {

				itemnums += "'" + altItemnum + "',";

			}
			// 去除结尾逗号
			itemnums = itemnums.substring(0, itemnums.length() - 1);

			// 当前工单办事处下的I类耗损件
			listwhere += locationColName
					+ " in (select location from locations where "
					+ locationWhere + " ) and itemnum in (" + itemnums + ")";

		} else {// 检修模块
			if (this.getJpo().getString("DOWNITEMNUM").isEmpty()) {
				throw new AppException("jxtasklosspart", "notchoice");
			} else {
				this.setLookupMap(new String[] { "NEWSQN", "NEWITEMNUM",
						"LOCATION", "NEWLOTNUM" }, new String[] { "SQN",
						"ITEMNUM", "STOCKADDRESS", "LOTNUM" });
				this.setListObject("MUSTCHANGEMPR");
				this.setLookupMap(
						new String[] { "ITEMNUM", "UPLOC", "LOTNUM" },
						new String[] { "ITEMNUM", "STOCKADDRESS", "LOTNUM" });
				IJpo wo = this.getJpo().getParent().getParent();
				String productionordernum = wo.getString("PRODUCTIONORDERNUM");
				IJpoSet mprSet = MroServer.getMroServer().getSysJpoSet("MPR");
				mprSet.setQueryWhere("PRODUCTIONORDERNUM='"
						+ productionordernum + "'");
				mprSet.reset();
				if (!mprSet.isEmpty()) {
					String mprnum = mprSet.getJpo().getString("MPRNUM");
					// IJpoSet mustchangemprSet =
					// MroServer.getMroServer().getSysJpoSet("MUSTCHANGEMPR");
					listwhere = "MPRNUM='" + mprnum
							+ "'and LOTNUM is not null and sqn is null ";

					// listwhere =
					// "itemnum  in(select itemnum from sys_item where LOTTYPE='I类')";
					itemnums = ItemUtil.getAltItemnums(downitemnum);
					// 当前工单办事处下的I类耗损件
					listwhere += "and AMOUNT >0 and itemnum in (" + itemnums
							+ ")";
					// 排除已选择的耗损件
					String selectedItem = "";
					IJpoSet jxtasklosspartset = jpo.getParent().getJpoSet(
							"JXTASKLOSSPART");
					if (jxtasklosspartset.count() > 1) {
						for (int i = 0; i < jxtasklosspartset.count(); i++) {
							if (jxtasklosspartset.getJpo(i).getId() == getJpo()
									.getId()) {
								continue;
							}
							jxtasklosspartset.getJpo(i).getField("QTY").init();
							int canDownQty = jxtasklosspartset.getJpo(i)
									.getInt("QTY");
							if (canDownQty > 0) {
								continue;
							}
							selectedItem += "'"
									+ jxtasklosspartset.getJpo(i).getString(
											"itemnum") + "',";
						}
					}
					if (!StringUtil.isStrEmpty(selectedItem)) {
						selectedItem = selectedItem.substring(0,
								selectedItem.length() - 1);
						listwhere += " and itemnum not in (" + selectedItem
								+ ")";
					}

				} else {
					listwhere = "1=2";
				}
			}
		}
		this.setListWhere(listwhere);
		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();
		IJpo exjpo = getJpo();
		// String downitemnum = exjpo.getString("downitemnum");
		String itemnum = getInputMroType().asString();
		if (StringUtil.isStrNotEmpty(itemnum)) {
			/*
			 * IJpoSet assetpartSet = getUserServer().getJpoSet("ASSETPART",
			 * " itemnum='" + itemnum + "'");
			 */
			String workordertype = this.getJpo().getString("WORKORDERTYPE");
			if (SddqConstant.SXC_JX.equals(workordertype)) {

				IJpo wo = this.getJpo().getParent().getParent();
				String whichstation = wo.getString("WHICHSTATION");// 获取站点数据
				String newloc = LocationUtil.getLocationForHsXc(whichstation);

				exjpo.setValue("UNDERLOC", newloc,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			} else if (SddqConstant.SXC_GZ.equals(workordertype)) {// 故障工单

				// 故障品处置方式
				String dealMode = exjpo.getString("dealmode");
				if (StringUtil.isStrNotEmpty(dealMode)
						&& SddqConstant.FAIL_DEALMODE_RETENTION
								.equals(dealMode)) {// 不返修
					// 将库房设置成一致
					if (!exjpo.getString("underloc").equalsIgnoreCase(
							exjpo.getString("uploc"))) {
						exjpo.setValue("underloc", exjpo.getString("uploc"),
								GWConstant.P_NOVALIDATION);
					}

				}

			}
		}

	}
}
