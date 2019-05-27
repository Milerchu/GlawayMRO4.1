package com.glaway.sddq.material.mprline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.ConvertlocCommonInventory;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * <入库单接收窗口DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MprlineqtyDataBean extends DataBean {
	/**
	 * 确认按钮接收数据
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			double statuswjsqty = this.getJpo().getDouble("wjsqty");
			double statusjsqty = this.getJpo().getDouble("jsqty");
			double YJSQTY = parent.getDouble("YJSQTY");
			double newyjsqty = YJSQTY + statusjsqty;
			String mprnum = parent.getString("mprnum");
			String mprlineid = parent.getString("mprlineid");
			String lotnum = parent.getString("lotnum");
			String location = parent.getParent().getString("MPRSTOREROOM");
			String itemnum = parent.getString("itemnum");
			String binnum = "";
			String sqn = parent.getString("sqn");
			String sxtype = parent.getString("sxtype");
			String rktype = parent.getParent().getString("rktype");
			String parentsqnassetnum = parent.getString("parentsqnassetnum");
			String assetnum = parent.getString("assetnum");
			if (statusjsqty > statuswjsqty) {
				throw new MroException("transferline", "qty");
			} else {
				parent.setValue("YJSQTY", newyjsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("endby", getUserInfo().getLoginID(),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				java.util.Date date = MroServer.getMroServer().getDate();
				parent.setValue("enddate", date,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (statuswjsqty == statusjsqty) {
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet mprlineset = MroServer.getMroServer().getJpoSet(
							"mprline",
							MroServer.getMroServer().getSystemUserServer());
					mprlineset.setUserWhere("mprnum='" + mprnum
							+ "' and status!='已接收' and mprlineid!='"
							+ mprlineid + "'");
					if (mprlineset.isEmpty()) {
						parent.getParent().setValue("status", "已接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						parent.getParent().setValue("endby",
								getUserInfo().getLoginID(),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						java.util.Date parentdate = MroServer.getMroServer()
								.getDate();
						parent.getParent().setValue("ENDTIME", parentdate,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				} else {
					parent.setValue("status", "部分接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.getParent().setValue("status", "部分接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				// 调用入库方法
				ConvertlocCommonInventory.MPRININVENTORY(lotnum, statusjsqty,
						location, itemnum, binnum, sqn, sxtype);
				InventoryQtyCommon.SQZYQTY(itemnum, location);
				InventoryQtyCommon.FCZTQTY(itemnum, location);
				InventoryQtyCommon.JSZTQTY(itemnum, location);
				InventoryQtyCommon.KYQTY(itemnum, location);
				if(rktype.equalsIgnoreCase("拆借件入库")){
					IJpoSet parentsqnset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
					parentsqnset.setUserWhere("assetnum='"+parentsqnassetnum+"'");
					if(!parentsqnset.isEmpty()){
						parentsqnset.getJpo(0).setValue("status", "残品",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						parentsqnset.save();
					}
					IJpoSet sqnset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
					sqnset.setUserWhere("assetnum='"+assetnum+"'");
					if(!sqnset.isEmpty()){
						IJpo asset=sqnset.getJpo(0);
						String commomtype="入库";
						sqnset.getJpo(0).setValue("status", "可用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						sqnset.getJpo(0).setValue("parent", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						sqnset.getJpo(0).setValue("assetlevel", "ASSET",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						sqnset.getJpo(0).setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						sqnset.getJpo(0).setValue("type", "1",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
						sqnset.save();
					}
				}else{
					// 调用增加序列号方法
					ADDASSET();
				}
				
			}

		}
		this.getAppBean().SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * <增加序列号方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDASSET() throws MroException {
		// IJpoSet jpoSet = this.getJpo().getJpoSet("$asset", "ASSET", "1=2");
		IJpoSet jpoSet = MroServer.getMroServer().getJpoSet("asset",
				MroServer.getMroServer().getSystemUserServer());
		String PROJNUM = this.getJpo().getParent().getParent()
				.getString("PROJECTNUM");
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String SQN = this.getJpo().getParent().getString("SQN");
		String ITEMNUM = this.getJpo().getParent().getString("ITEMNUM");
		String LOCATION = this.getJpo().getParent().getParent()
				.getString("MPRSTOREROOM");
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setUserWhere("itemnum='" + ITEMNUM + "'");
		String type = ItemUtil.getItemInfo(ITEMNUM);
		String commomtype="入库";
		if (ItemUtil.SQN_ITEM.equals(type)) {
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setUserWhere("itemnum='"
					+ ITEMNUM
					+ "' and sqn='"
					+ SQN
					+ "' and assetlevel='ASSET' and type!='2' and location is null");
			if (assetset.isEmpty()) {
				IJpo jpo = jpoSet.addJpo();
				jpo.setValue("assetlevel", "ASSET",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("itemnum", ITEMNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("sqn", SQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("LOCATION", LOCATION,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("ancestor", jpo.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("PROJNUM", PROJNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("isnew", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("iserp", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("MSGFLAG", SddqConstant.MSG_INFO_NOSQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.getParent()
						.setValue("assetnum", jpo.getString("assetnum"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(jpo, commomtype);//调用一物一档入库计算方法
				jpoSet.save();
			} else {
				IJpo asset = assetset.getJpo(0);
				asset.setValue("LOCATION", LOCATION,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.getParent()
						.setValue("assetnum", asset.getString("assetnum"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
				assetset.save();
			}
		}
	}
}
