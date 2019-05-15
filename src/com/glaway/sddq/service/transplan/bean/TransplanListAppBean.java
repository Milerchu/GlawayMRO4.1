package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <功能描述> 改造物料分配单，状态变更
 * 
 * @author 20167088
 * @version [版本号, 2018-7-23]
 * @since [产品/模块版本]
 */
public class TransplanListAppBean extends AppBean {

	@Override
	public void initialize() throws MroException {

		super.initialize();

	}

	public int APPR() throws MroException, IOException {
		IJpo apps = this.getJpo();
		if (apps.getString("status").equals("已分配")) {
			showMsgbox("提示", "物料已分配，无需重复操作");

			return 0;

		}

		if (apps.getJpoSet("transplanlistline").isEmpty()) {
			showMsgbox("提示", "未分配物料");
			return 0;
		}

		IJpoSet transplanlistlineSet = apps.getJpoSet("$transplanlistline", "transplanlistline", "transplanlistnum=:transplanlistnum and status!='已分配'");
		transplanlistlineSet.reset();
		if (transplanlistlineSet != null && transplanlistlineSet.count() > 0) {
			for (int i = 0; i < transplanlistlineSet.count(); i++) {
				IJpo app = transplanlistlineSet.getJpo(i);
				String itemnum = app.getString("itemnum");
				IJpoSet locationsSet = app.getJpoSet("$LOCATIONS", "LOCATIONS", "ERPLOC='改造物料库' and STOREROOMLEVEL='中心库' and LOCATIONTYPE='常规'");
				String location = locationsSet.getJpo(0).getString("location");
				String STOREROOM = location;
				if (STOREROOM.isEmpty()) {
					throw new MroException("无改造中心库，请联系管理员");

				}
				double distributeqty = app.getDouble("distributeqty");
				IJpoSet itemSet = app.getJpoSet("$SYS_item", "SYS_item",
						"itemnum='" + this.getAppBean().getJpo().getString("itemnum") + "'");
				IJpo item;
				if (itemSet.isEmpty()) {
					item = itemSet.addJpo();
					item.setValue("itemnum", app.getString("itemnum"), 2L);
					// item.setValue("CURBALTOTAL", qty, 2L);
				} else {
					item = itemSet.getJpo(0);
					// item.setValue("CURBALTOTAL", qty +
					// item.getDouble("CURBALTOTAL"),
					// 2L);
				}
				// 库房记录ITEMNUM IN (SELECT DISTINCT ITEMNUM FROM SYS_INVENTORY
				// where
				// location in (select location from locations where
				// erploc='改造物料库'))
				IJpoSet INVENTORYSet = app.getJpoSet("$SYS_INVENTORY",
						"SYS_INVENTORY", "itemnum=:itemnum and LOCATION = '" + STOREROOM +
								"'");
				if (INVENTORYSet.isEmpty()) {
					IJpo inventor = INVENTORYSet.addJpo();
					inventor.setValue("itemnum", itemnum, 2L);
					inventor.setValue("LOCATION", STOREROOM, 2L);
					inventor.setValue("CURBAL", distributeqty, 112L);// 余量
				} else {
					IJpo inventor = INVENTORYSet.getJpo();
					inventor.setValue("CURBAL", distributeqty +
							inventor.getDouble("CURBAL"), 112L);
				}
				// 出入库记录
				IJpoSet InvtransSet = item.getJpoSet("Invtrans");
				IJpo Invtrans = InvtransSet.addJpo();
				Invtrans.setValue("itemnum", itemnum, 2L);
				Invtrans.setValue("TRANSTYPE", "入库", 2L);
				Invtrans.setValue("QTY", distributeqty, 112L);
				Invtrans.setValue("STOREROOM", STOREROOM, 2L);
				Invtrans.setValue("TRANSDATE", MroServer.getMroServer().getDate(),
						2L);
				app.setValue("status", "已分配");
				app.setValue("endby", getUserInfo().getPersonId(), 11L);
				Date date = MroServer.getMroServer().getDate();
				app.setValue("enddate", date, 11L);
				app.setValue("status", "已分配", 11L);
			}
		}
		this.setValue("status", "已分配", 11L);
		showMsgbox("提示", "分配成功");
		this.getAppBean().SAVE();
		return 0;

	}

	@Override
	public int DELETE() throws MroException, IOException {
		IJpoSet jposet = this.getJpo().getJpoSet("TRANSPLANLISTLINE");
		if (jposet != null && jposet.count() > 0) {
			throw new MroException("详细清单行有数据");
		} else {
			return super.DELETE();

		}
	}

}
