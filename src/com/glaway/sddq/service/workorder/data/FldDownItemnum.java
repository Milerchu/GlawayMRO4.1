package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 耗损件记录 下车件物料编码downitemnum字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-9]
 * @since [产品/模块版本]
 */
public class FldDownItemnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -843238708685679143L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "DOWNITEMNUM", "DOWNASSETNUM",
				"DOWNSQN", "DOWNAMOUNT", "DOWNLOTNUM", "DOWNPARTNUM" },
				new String[] { "ITEMNUM", "ASSETNUM", "ASSET.SQN", "QTY",
						"LOTNUM", "ASSETPARTNUM" });
		super.init();
		if (getJpo().getParent() != null) {
			// 服务模块工单
			if ("WORKORDER".equalsIgnoreCase(getJpo().getParent().getName())) {
				// 根据物料类型变更上车件lookup
				if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(getJpo()
						.getString("downitemnum")))) {
					this.getField("ITEMNUM").setUserLookup("losspart1");
				} else {
					this.getField("ITEMNUM").setUserLookup("losspart2");
				}
			}

		}

	}

	@Override
	public IJpoSet getList() throws MroException {

		this.setListObject("ASSETPART");
		String where = "";
		IJpo wo = this.getJpo().getParent();
		if ("WORKORDER".equalsIgnoreCase(wo.getName())) {// 故障、改造工单

			where = "assetnum in (select assetnum from asset where ancestor ='"
					+ wo.getString("ASSETNUM") + "')";

			// 上下车记录表
			IJpoSet exchangeSet = null;
			// 故障工单
			if ("FAILUREORD".equalsIgnoreCase(wo.getAppName())) {

				IJpoSet failurelibSet = wo.getJpoSet("FAILURELIB");
				if (!failurelibSet.isEmpty()) {
					exchangeSet = failurelibSet.getJpo(0).getJpoSet(
							"EXCHANGERECORD");
				}
			} else {// 改造工单

				exchangeSet = wo.getJpoSet("EXCHANGERECORD");

			}

			if (exchangeSet != null && exchangeSet.count() > 0) {

				String exchangeAssetnum = "";
				for (int index = 0; index < exchangeSet.count(); index++) {
					IJpo exchange = exchangeSet.getJpo(index);
					exchangeAssetnum += "'" + exchange.getString("ASSETNUM")
							+ "',";
				}
				// 去除末尾逗号
				exchangeAssetnum = exchangeAssetnum.substring(0,
						exchangeAssetnum.length() - 1);
				// 去除已经下车的周转件上的耗损件
				where += " and assetnum not in (select assetnum from asset where parent in("
						+ exchangeAssetnum
						+ ") or ancestor in("
						+ exchangeAssetnum + "))";
			}

			this.setListWhere(where + " and (qty-(lockqty-1)) > 0 ");
		}

		if ("JXPRODUCT".equalsIgnoreCase(wo.getName())) {

			IJpo jxwo = this.getJpo().getParent().getParent();
			if ("JXTASKORDER".equalsIgnoreCase(jxwo.getName())) {
//				where = "assetnum in (select assetnum from asset where ancestor in (select assetnum from asset where carno='"
//						+ jxwo.getString("CARNO")
//						+ "' and cmodel='"
//						+ jxwo.getString("CMODEL") + "'))";
				String ancestor =  wo.getString("ASSETNUM");
				where = "assetnum in (select assetnum from asset where ancestor ='"+ancestor+"')";
				// 排除可下车数量为0的
				String selectedItem = "";
				IJpoSet lossSet = getJpo().getParent().getJpoSet(
						"JXTASKLOSSPART");
				if (lossSet.count() > 1) {
					for (int i = 0; i < lossSet.count(); i++) {
						if (lossSet.getJpo(i).getId() == getJpo().getId()) {
							continue;
						}
						lossSet.getJpo(i).getField("QTY").init();
						int canDownQty = lossSet.getJpo(i).getInt("QTY");
						if (canDownQty > 0) {
							continue;
						}
						selectedItem += "'"
								+ lossSet.getJpo(i).getString("itemnum") + "',";
					}
				}
				if (!StringUtil.isStrEmpty(selectedItem)) {
					selectedItem = selectedItem.substring(0,
							selectedItem.length() - 1);
					where += " and itemnum not in (" + selectedItem + ")";
				}
			}
			this.setListWhere(where);
		}
		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();
		if (isValueChanged()) {

			//批次号件设置批次号字段必填
			if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(getInputMroType().asString()))) {
				getJpo().setFieldFlag("DOWNLOTNUM",GWConstant.S_REQUIRED,true);
			}
			// 清空已选的上车件信息
			getField("INVENTORY").setValueNull();
			// getField("AMOUNT").setValueNull();
			getField("UPLOC").setValueNull();
			getField("LOTNUM").setValueNull();
			getField("ITEMNUM").setValueNull();
		}
		if (!getJpo().getJpoSet("SUPERIORASSET").isEmpty()) {
			// 清空故障件上级
			getJpo().getJpoSet("SUPERIORASSET").deleteAll();
		}
		if (getJpo().getParent() != null) {

			IJpo wo = getJpo().getParent();
			String orderType = wo.getString("type");// 工单类型
			String erploc = "";
			String storeroomLevel = "";
			String locationType = "";
			// 服务模块工单
			if ("WORKORDER".equalsIgnoreCase(wo.getName())) {
				if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(getJpo()
						.getString("downitemnum")))) {
					this.getField("ITEMNUM").setUserLookup("losspart1");
				} else {
					this.getField("ITEMNUM").setUserLookup("losspart2");
				}

				if ("改造".equals(orderType) || "验证".equals(orderType)) {
					erploc = ItemUtil.ERPLOC_QTGZ;
					storeroomLevel = ItemUtil.STOREROOMLEVEL_XCK;
					locationType = ItemUtil.LOCATIONTYPE_CG;
				} else if ("故障".equals(orderType)) {
					erploc = ItemUtil.ERPLOC_1020;
					storeroomLevel = ItemUtil.STOREROOMLEVEL_XCZDK
							+ "' or STOREROOMLEVEL='"
							+ ItemUtil.STOREROOMLEVEL_XCK;
					locationType = ItemUtil.LOCATIONTYPE_WX;
				}

				String where = "erploc='" + erploc + "' and (STOREROOMLEVEL='"
						+ storeroomLevel + "') and LOCATIONTYPE='"
						+ locationType + "' and status='正常'  and locsite='"
						+ wo.getString("WHICHSTATION") + "' and jxorfw ='"
						+ ItemUtil.JXORFW_FW + "'";

				if (!getJpo().getBoolean("ISCUSTITEM")) {

					IJpoSet locationSet = MroServer.getMroServer()
							.getSysJpoSet("LOCATIONS", where);
					if (!locationSet.isEmpty()) {
						IJpo loc = locationSet.getJpo(0);
						getJpo().setValue("UNDERLOC",
								loc.getString("LOCATION"),
								GWConstant.P_NOVALIDATION);// 设置下车库房
					}
				}
			}

		}
	}
}
