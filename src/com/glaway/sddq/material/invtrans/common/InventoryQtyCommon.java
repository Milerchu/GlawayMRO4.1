package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库存数量计算公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-28]
 * @since  [产品/模块版本]
 */
public class InventoryQtyCommon {
	private static InventoryQtyCommon wfCtrl = null;;

	private InventoryQtyCommon() {
	}

	public synchronized static InventoryQtyCommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new InventoryQtyCommon();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <申请占用数量计算>
	 * @param itemnum
	 * @param location [参数说明]
	 *
	 */
	public static void SQZYQTY(String itemnum,String location) {
		double total = 0;
			IJpoSet mrlinetransferset;
			try {
				mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
						"mrlinetransfer");
				mrlinetransferset.setUserWhere("STOREROOM='" + location
						+ "' and itemnum='" + itemnum
						+ "' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
				mrlinetransferset.reset();
				if (!mrlinetransferset.isEmpty()) {
					/* 配件申请中所有申请已发出的数量 */
					double sumsendqty = mrlinetransferset.sum("sendqty");
					/* 配件申请中所有申请的数量 */
					double sumsqqty = 0.00;
					for (int i = 0; i < mrlinetransferset.count(); i++) {
						IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
						sumsqqty += mrlinetransfer.getDouble("transferqty");
					}
					total = sumsqqty - sumsendqty;
					IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
							"sys_inventory");
					inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
					inventoryset.reset();
					if(!inventoryset.isEmpty()){
						if(total<0){
							inventoryset.getJpo(0).setValue("sqzyqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}else{
							inventoryset.getJpo(0).setValue("sqzyqty", total,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						
						inventoryset.save();
					}
				}
			} catch (MroException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 
	 * <可以数量计算>
	 * @param itemnum
	 * @param location [参数说明]
	 *
	 */
	public static void KYQTY(String itemnum,String location) {
		try {
			IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
					"sys_inventory");
			inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
			inventoryset.reset();
			if(!inventoryset.isEmpty()){
				IJpo inventory=inventoryset.getJpo(0);
				double curbal=inventory.getDouble("curbal");//库存数量
				double sqzyqty=inventory.getDouble("sqzyqty");//申请占用数量
				double DISPOSEQTY=inventory.getDouble("DISPOSEQTY");//待处理数量
				double FAULTQTY=inventory.getDouble("FAULTQTY");//故障数量
				double TESTINGQTY=inventory.getDouble("TESTINGQTY");//待检测数量
				double fcztqty=inventory.getDouble("fcztqty");//发出在途数量
				double kyqty=curbal-(sqzyqty+DISPOSEQTY+FAULTQTY+TESTINGQTY+fcztqty);
				inventoryset.getJpo(0).setValue("kyqty", kyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				inventoryset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <发出在途数量计算>
	 * @param itemnum
	 * @param location [参数说明]
	 *
	 */
	public static void FCZTQTY(String itemnum,String location) {
		try {
			IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
					"sys_inventory");
			inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
			inventoryset.reset();
			if(!inventoryset.isEmpty()){
				IJpo inventory=inventoryset.getJpo(0);
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("itemnum='"+itemnum+"'and status='未接收' and ISSUESTOREROOM='"+location+"'and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
				transferlineset.reset();
				if (transferlineset.count() > 0) {
					double fcztqty = 0;
					fcztqty = transferlineset.sum("ORDERQTY");
					inventory.setValue("fcztqty", fcztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}else{
					inventory.setValue("fcztqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			}
			
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <接收在途数量计算>
	 * @param itemnum
	 * @param location [参数说明]
	 *
	 */
	public static void JSZTQTY(String itemnum,String location) {
		try {
			IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
					"sys_inventory");
			inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
			inventoryset.reset();
			if(!inventoryset.isEmpty()){
				IJpo inventory=inventoryset.getJpo(0);
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("itemnum='"+itemnum+"'and status='未接收' and RECEIVESTOREROOM='"+location+"'and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
				transferlineset.reset();
				if (transferlineset.count() > 0) {
					double jsztqty = 0;
					jsztqty = transferlineset.sum("ORDERQTY");
					inventory.setValue("jsztqty", jsztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}else{
					inventory.setValue("jsztqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			}
			
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



/**
 * 
 * <装箱单驳回申请占用数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHSQZYQTY(String itemnum,String location) {
	double total = 0;
		IJpoSet mrlinetransferset;
		try {
			mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
					"mrlinetransfer");
			mrlinetransferset.setUserWhere("STOREROOM='" + location
					+ "' and itemnum='" + itemnum
					+ "' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
			mrlinetransferset.reset();
			if (!mrlinetransferset.isEmpty()) {
				/* 配件申请中所有申请已发出的数量 */
				double sumsendqty = mrlinetransferset.sum("sendqty");
				/* 配件申请中所有申请的数量 */
				double sumsqqty = 0.00;
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					sumsqqty += mrlinetransfer.getDouble("transferqty");
				}
				total = sumsqqty - sumsendqty;
				IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
						"sys_inventory");
				inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
				inventoryset.reset();
				if(!inventoryset.isEmpty()){
					if(total<0){
						inventoryset.getJpo(0).setValue("sqzyqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						inventoryset.getJpo(0).setValue("sqzyqty", total,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					
					inventoryset.save();
				}
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
/**
 * 
 * <装箱单驳回可以数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHKYQTY(String itemnum,String location) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double curbal=inventory.getDouble("curbal");//库存数量
			double sqzyqty=inventory.getDouble("sqzyqty");//申请占用数量
			double DISPOSEQTY=inventory.getDouble("DISPOSEQTY");//待处理数量
			double FAULTQTY=inventory.getDouble("FAULTQTY");//故障数量
			double TESTINGQTY=inventory.getDouble("TESTINGQTY");//待检测数量
			double fcztqty=inventory.getDouble("fcztqty");//发出在途数量
			double kyqty=curbal-(sqzyqty+DISPOSEQTY+FAULTQTY+TESTINGQTY+fcztqty);
			inventoryset.getJpo(0).setValue("kyqty", kyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 
 * <装箱单驳回发出在途数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHFCZTQTY(String itemnum,String location,double qty) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double fcztqty=inventory.getDouble("fcztqty");
			double newfcztqty=fcztqty-qty;
			inventory.setValue("fcztqty", newfcztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
		
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 
 * <装箱单驳回接收在途数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHJSZTQTY(String itemnum,String location,double qty) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double jsztqty=inventory.getDouble("jsztqty");
			double newjsztqty=jsztqty-qty;
			inventory.setValue("jsztqty", newjsztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
		
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


/**
 * 
 * <装箱单驳回后发出申请占用数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHSCSQZYQTY(String itemnum,String location) {
	double total = 0;
		IJpoSet mrlinetransferset;
		try {
			mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
					"mrlinetransfer");
			mrlinetransferset.setUserWhere("STOREROOM='" + location
					+ "' and itemnum='" + itemnum
					+ "' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
			mrlinetransferset.reset();
			if (!mrlinetransferset.isEmpty()) {
				/* 配件申请中所有申请已发出的数量 */
				double sumSENDQTY = mrlinetransferset.sum("SENDQTY");
				/* 配件申请中所有申请的数量 */
				double sumsqqty = 0.00;
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					sumsqqty += mrlinetransfer.getDouble("transferqty");
				}
				total = sumsqqty - sumSENDQTY;
				IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
						"sys_inventory");
				inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
				inventoryset.reset();
				if(!inventoryset.isEmpty()){
					if(total<0){
						inventoryset.getJpo(0).setValue("sqzyqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						inventoryset.getJpo(0).setValue("sqzyqty", total,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					inventoryset.save();
				}
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
/**
 * 
 * <装箱单驳回后发出可用数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHSCKYQTY(String itemnum,String location) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double curbal=inventory.getDouble("curbal");//库存数量
			double sqzyqty=inventory.getDouble("sqzyqty");//申请占用数量
			double DISPOSEQTY=inventory.getDouble("DISPOSEQTY");//待处理数量
			double FAULTQTY=inventory.getDouble("FAULTQTY");//故障数量
			double TESTINGQTY=inventory.getDouble("TESTINGQTY");//待检测数量
			double fcztqty=inventory.getDouble("fcztqty");//发出在途数量
			double kyqty=curbal-(sqzyqty+DISPOSEQTY+FAULTQTY+TESTINGQTY+fcztqty);
			inventoryset.getJpo(0).setValue("kyqty", kyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 
 * <装箱单驳回后发出在途数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHSCFCZTQTY(String itemnum,String location,double qty) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double fcztqty=inventory.getDouble("fcztqty");
			double newfcztqty=fcztqty+qty;
			inventory.setValue("fcztqty", newfcztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
		
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 
 * <装箱单驳回后发出在途数量计算>
 * @param itemnum
 * @param location [参数说明]
 *
 */
public static void ZXDBHSCJSZTQTY(String itemnum,String location,double qty) {
	try {
		IJpoSet inventoryset = MroServer.getMroServer().getSysJpoSet(
				"sys_inventory");
		inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
		inventoryset.reset();
		if(!inventoryset.isEmpty()){
			IJpo inventory=inventoryset.getJpo(0);
			double jsztqty=inventory.getDouble("jsztqty");
			double newjsztqty=jsztqty+qty;
			inventory.setValue("jsztqty", newjsztqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			inventoryset.save();
		}
		
	} catch (MroException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}