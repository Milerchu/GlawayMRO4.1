package com.glaway.sddq.material.mrlinetransfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <配件申请处置数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldTransferQty extends JpoField {
	/**
	 * 校验协调数量是否大于库存数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String TRANSTYPE = this.getJpo().getString("TRANSTYPE");/* 处置方式 */
		String STOREROOM = this.getJpo().getString("STOREROOM");/* 发出库房 */
		String ITEMNUM = this.getJpo().getString("ITEMNUM");
		double transferqty = this.getDoubleValue();
		String selecttype = this.getJpo().getString("selecttype");
		String datatype = this.getJpo().getString("datatype");
		if (datatype.equalsIgnoreCase("1")) {/* 服务主管协调 */
			if (TRANSTYPE.equalsIgnoreCase("内部协调")) {
				// 调用计算库存可用数量方法
				double kyqty = invkyqty(ITEMNUM, STOREROOM);
				if (transferqty > kyqty) {
					throw new MroException("协调数量大于库存可用数量!库存可用数量为:" + kyqty + "");
				}
			}
		}
		if (datatype.equalsIgnoreCase("2")) {/* 计划经理协调 */
			if (!TRANSTYPE.equalsIgnoreCase("返修后发运")&&!TRANSTYPE.equalsIgnoreCase("计划交付后发货")&&!TRANSTYPE.equalsIgnoreCase("")) {
				if (selecttype.equalsIgnoreCase("匹配")) {
					// 调用计算库存可用数量方法
					double kyqty = invkyqty(ITEMNUM, STOREROOM);
					if (transferqty > kyqty) {
						throw new MroException("协调数量大于库存可用数量!库存可用数量为:" + kyqty
								+ "");
					}
				}
				if (selecttype.equalsIgnoreCase("新增")) {
					// 调用计算库存可用数量方法
					double kyqty = invkyqty(ITEMNUM, STOREROOM);
					if (transferqty > kyqty) {
						throw new MroException("协调数量大于库存可用数量!库存可用数量为:" + kyqty
								+ "");
					}
				}
			}
		}
	}

	/**
	 * 
	 * <计算库存可用数量方法>
	 * 
	 * @param itemnum
	 *            物料编码
	 * @param location
	 *            库房编码
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public double invkyqty(String itemnum, String location) throws MroException {
		double kyqty = 0;
		IJpoSet inventoryset = MroServer.getMroServer()
				.getJpoSet("sys_inventory",
						MroServer.getMroServer().getSystemUserServer());
		inventoryset.setUserWhere("itemnum='" + itemnum + "' and location='"
				+ location + "'");
		inventoryset.reset();
		double curbal = inventoryset.getJpo(0).getDouble("curbal");// 库存总数量
		double DISPOSEQTY = inventoryset.getJpo(0).getDouble("DISPOSEQTY");// 待处理数量
		double FAULTQTY = inventoryset.getJpo(0).getDouble("FAULTQTY");// 故障数量
		double TESTINGQTY = inventoryset.getJpo(0).getDouble("TESTINGQTY");// 待检测数量
		double sqzyqty = 0;
		IJpoSet mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
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
			double newsqzyqty = sumsqqty - sumSENDQTY;
			if(newsqzyqty<0){
				sqzyqty=0;
			}else{
				sqzyqty=newsqzyqty;
			}
		}
		double fcztqty = 0;
		IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
				"transferline", MroServer.getMroServer().getSystemUserServer());
		transferlineset
				.setUserWhere("itemnum='"
						+ itemnum
						+ "' and status='未接收' and ISSUESTOREROOM='"
						+ location
						+ "' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
		transferlineset.reset();
		if (transferlineset.count() > 0) {
			fcztqty = transferlineset.sum("ORDERQTY");
		}
		kyqty = curbal - DISPOSEQTY - FAULTQTY - TESTINGQTY - sqzyqty - fcztqty;
		return kyqty;

	}
}
