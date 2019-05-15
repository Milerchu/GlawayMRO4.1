package com.glaway.sddq.material.materreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;

/**
 * 
 * <配件申请APPBEAN类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-16]
 * @since [产品/模块版本]
 */
public class MrAppBean extends AppBean {
	/**
	 * 发送工作前判断是否所以申请的物料已经被处理,并复制到申请项数字段
	 * 
	 * @return
	 * @throws Exception
	 */
	public int ROUTEWF() throws Exception {
		// TODO Auto-generated method stub
		IJpo Mr = this.getJpo();
		if (Mr != null) {
			IJpoSet Mrlineset = Mr.getJpoSet("mrline");
			if (Mrlineset.isEmpty()) {
				throw new MroException("mr", "nomrline");
			}
			IJpoSet MRLINETRANSFER = Mr.getJpoSet("ALLMRLINETRANSFER");
			if (!MRLINETRANSFER.isEmpty()) {
				for (int i = 0; i < MRLINETRANSFER.count(); i++) {
					String transtype = MRLINETRANSFER.getJpo(i).getString(
							"transtype");
					if (transtype.isEmpty()) {
						throw new MroException("请补充处置方式");
					}
				}
			}

		}
		int mrlineqty = this.getJpo().getJpoSet("mrline").count();
		this.getJpo().setValue("mrlineqty", mrlineqty,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		return super.ROUTEWF();
	}
/**
 * 保存触发库存数量计算
 * @return
 * @throws IOException
 * @throws MroException
 */
	@Override
	public int SAVE() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.SAVE();
		IJpo Mr = this.getJpo();
		String mrnum=Mr.getString("mrnum");
		IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet("mrlinetransfer",MroServer.getMroServer().getSystemUserServer());
		mrlinetransferset.setUserWhere("mrnum='"+mrnum+"' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
		mrlinetransferset.reset();
		if(!mrlinetransferset.isEmpty()){
			for(int i=0;i<mrlinetransferset.count();i++){
				IJpo mrlinetransfer=mrlinetransferset.getJpo(i);
				String itemnum=mrlinetransfer.getString("itemnum");
				String location=mrlinetransfer.getString("storeroom");
				InventoryQtyCommon.SQZYQTY(itemnum, location);
				InventoryQtyCommon.FCZTQTY(itemnum, location);
				InventoryQtyCommon.JSZTQTY(itemnum, location);
				InventoryQtyCommon.KYQTY(itemnum, location);
			}
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
	
}
