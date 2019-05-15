package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <送修单，装箱单其他数量计算公共类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class Commondeleteotherqty {
	private static Commondeleteotherqty wfCtrl = null;;

	private Commondeleteotherqty() {
	}

	public synchronized static Commondeleteotherqty getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new Commondeleteotherqty();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <送修单其他数量计算方法>
	 * 
	 * @param transferlineset
	 *            [参数说明]
	 * 
	 */
	public static void sxdeleteqty(IJpoSet transferlineset) {
//		try {
//			if (!transferlineset.isEmpty()) {
//				for (int i = 0; i < transferlineset.count(); i++) {
//					IJpo transferline = transferlineset.getJpo(i);
//					String itemnum = transferline.getString("itemnum");
//					String issuestoreroom = transferline
//							.getString("issuestoreroom");
//					String sxtype = transferline.getString("sxtype");
//					double orderqty = transferline.getDouble("orderqty");
//					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
//							"sys_inventory",
//							MroServer.getMroServer().getSystemUserServer());
//					inventoryset.setUserWhere("itemnum='" + itemnum
//							+ "' and location='" + issuestoreroom + "'");
//					inventoryset.reset();
//					if (!inventoryset.isEmpty()) {
//						IJpo inventory = inventoryset.getJpo(0);
//						if (sxtype.equalsIgnoreCase("GZ")) {
//							double FAULTQTY = inventory.getDouble("FAULTQTY");
//							double newfaulqty = FAULTQTY - orderqty;
//							inventory.setValue("FAULTQTY", newfaulqty,
//									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						} else if (sxtype.equalsIgnoreCase("YXX")) {
//							double TESTINGQTY = inventory
//									.getDouble("TESTINGQTY");
//							double newtestingqty = TESTINGQTY - orderqty;
//							inventory.setValue("TESTINGQTY", newtestingqty,
//									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						}
//						inventoryset.save();
//					}
//				}
//			}
//
//		} catch (MroException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 
	 * <装箱单其他数量计算方法>
	 * 
	 * @param transfermovetype
	 * @param transferlineset
	 *            [参数说明]
	 * 
	 */
	public static void zxdeleteqty(String transfermovetype,
			IJpoSet transferlineset) {
//		try {
//			if (!transferlineset.isEmpty()) {
//				for (int i = 0; i < transferlineset.count(); i++) {
//					IJpo transferline = transferlineset.getJpo(i);
//					String itemnum = transferline.getString("itemnum");
//					String issuestoreroom = transferline
//							.getString("issuestoreroom");
//					String sxtype = transferline.getString("sxtype");
//					double orderqty = transferline.getDouble("orderqty");
//					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
//							"sys_inventory",
//							MroServer.getMroServer().getSystemUserServer());
//					inventoryset.setUserWhere("itemnum='" + itemnum
//							+ "' and location='" + issuestoreroom + "'");
//					inventoryset.reset();
//					if (transfermovetype.equalsIgnoreCase("现场到中心")) {
//						if (!inventoryset.isEmpty()) {
//							IJpo inventory = inventoryset.getJpo(0);
//							if (sxtype.equalsIgnoreCase("GZ")) {
//								double FAULTQTY = inventory
//										.getDouble("FAULTQTY");
//								double newfaulqty = FAULTQTY - orderqty;
//								inventory
//										.setValue(
//												"FAULTQTY",
//												newfaulqty,
//												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//							}
//							inventoryset.save();
//						}
//					}
//				}
//			}
//
//		} catch (MroException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
