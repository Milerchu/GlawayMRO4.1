package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造物料计划-物料分配databean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class TransplanListLineDataBean extends DataBean {
	public int changestatus() throws MroException, IOException {
		IJpo app = this.getJpo();

		if (app.getString("status").equals("已分配"))
			throw new MroException("", "已分配完毕");//
		String itemnum = app.getString("itemnum");
		IJpoSet locationsSet = app
				.getJpoSet("$LOCATIONS", "LOCATIONS",
						"ERPLOC='改造物料库' and STOREROOMLEVEL='中心库' and LOCATIONTYPE='常规'");
		String location = locationsSet.getJpo(0).getString("location");
		String STOREROOM = location;
		double distributeqty = app.getDouble("distributeqty");
		IJpoSet itemSet = app.getJpoSet("$SYS_item", "SYS_item",
				"itemnum=:itemnum");
		IJpo item;
		if (itemSet.isEmpty()) {
			item = itemSet.addJpo();
			item.setValue("itemnum", app.getString("itemnum"),
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			// item.setValue("CURBALTOTAL", qty, 2L);
		} else {
			item = itemSet.getJpo(0);
			// item.setValue("CURBALTOTAL", qty +
			// item.getDouble("CURBALTOTAL"),
			// 2L);
		}
		// 库房记录ITEMNUM IN (SELECT DISTINCT ITEMNUM FROM SYS_INVENTORY where
		// location in (select location from locations where
		// erploc='改造物料库'))
		IJpoSet INVENTORYSet = app.getJpoSet("$SYS_INVENTORY", "SYS_INVENTORY",
				"itemnum=:itemnum and LOCATION = '" + STOREROOM + "'");
		if (INVENTORYSet.isEmpty()) {
			IJpo inventor = INVENTORYSet.addJpo();
			inventor.setValue("itemnum", itemnum,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			inventor.setValue("LOCATION", STOREROOM,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			inventor.setValue("CURBAL", distributeqty, 2L);// 余量
		} else {
			IJpo inventor = INVENTORYSet.getJpo();
			inventor.setValue("CURBAL",
					distributeqty + inventor.getDouble("CURBAL"), 2L);
		}
		// 出入库记录
		IJpoSet InvtransSet = item.getJpoSet("Invtrans");
		IJpo Invtrans = InvtransSet.addJpo();
		Invtrans.setValue("itemnum", itemnum,
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		Invtrans.setValue("TRANSTYPE", "入库",
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		Invtrans.setValue("QTY", distributeqty,
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		Invtrans.setValue("STOREROOM", STOREROOM,
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		Invtrans.setValue("TRANSDATE", MroServer.getMroServer().getDate(),
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		this.setValue("status", "已分配");
		this.setValue("endby", getUserInfo().getPersonId(), 11L);
		Date date = MroServer.getMroServer().getDate();
		this.getJpo().setValue("enddate", date, 11L);
		this.getAppBean().SAVE();
		showMsgbox("提示", "分配成功");
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 删除校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		if (this.getString("status").equals("已分配")) {
			throw new MroException("已分配的物料无法删除");
		} else {
			// TODO Auto-generated method stub
			return super.toggledeleterow();
		}
	}

}
