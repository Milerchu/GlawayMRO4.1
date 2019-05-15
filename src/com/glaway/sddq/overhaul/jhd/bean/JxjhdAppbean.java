package com.glaway.sddq.overhaul.jhd.bean;

import java.io.IOException;
import java.rmi.RemoteException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.Interface.webservice.InterfaceUtil;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 检修交货单
 * 
 * @author public2175
 * @version [版本号, 2018-12-26]
 * @since [产品/模块版本]
 */
public class JxjhdAppbean extends AppBean {

	/**
	 * 
	 * 获取检修交货单
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public void RECEIVEDATA() throws MroException, IOException {
		
		if (SddqConstant.JXJHD_YJH.equalsIgnoreCase(this.getString("status"))) {
			throw new AppException("jxjhd", "hasjh");
		} else {
			String jxjhdnum = this.getString("JHNUM");
			String jxnum = this.getString("jxnum");
			String jxzj = this.getString("VKORG");
			IJpoSet jxjhitem = MroServer.getMroServer().getSysJpoSet(
					"JXDEORDERITEM", "JXNUM='" + jxnum + "'");
			// 先删除已经获取的交货单行信息
			if (jxjhitem != null && jxjhitem.count() > 0) {
				jxjhitem.deleteAll();
				jxjhitem.save();
			}
			try {
				JSONArray data = InterfaceUtil.getErpJxJhd(jxjhdnum, jxzj);
				if (data == null || data.size() == 0) {
					throw new AppException("jxjhd", "erpnull");
				} else {
					IJpoSet jxjhitemSet = this.getJpo().getJpoSet(
							"JXDEORDERITEM");
					for (int index = 0; index < data.size(); index++) {
						JSONObject object = data.getJSONObject(index);
						IJpo jpo = jxjhitemSet.addJpo();
						jpo.setValue("JHNUM", object.getString("JHNUM"));
						jpo.setValue("JXNUM", jxnum);
						jpo.setValue("ADDRESS", object.getString("ADDRESS"));
						jpo.setValue("BWART", object.getString("BWART"));
						jpo.setValue("ITEMNUM", object.getString("ITEMNUM"));
						jpo.setValue("LFDAT", object.getString("LFDAT"));
						jpo.setValue("LFIMG", object.getString("LFIMG"));
						jpo.setValue("LGORT", object.getString("LGORT"));
						jpo.setValue("MAKTX", object.getString("MAKTX"));
						jpo.setValue("POSNR", object.getString("POSNR"));
						jpo.setValue("PSTYV", object.getString("PSTYV"));
						jpo.setValue("REMARK", object.getString("REMARK"));
						jpo.setValue("SHDW", object.getString("SHDW"));
						jpo.setValue("VGBEL", object.getString("VGBEL"));
						jpo.setValue("VGPOS", object.getString("VGPOS"));
						jpo.setValue("VKORG", object.getString("VKORG"));
						jpo.setValue("VRKME", object.getString("VRKME"));
						jpo.setValue("WERKS", object.getString("WERKS"));
					}
					this.setValue("STATUS", SddqConstant.JXJHD_YHQ,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getAppBean().SAVE();
				}
			} catch (RemoteException e) {
				throw new AppException("jxjhd", "erperror");
			}
		}
	}

	/**
	 * 
	 * 检修交货
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public void JXJH() throws MroException, IOException {
		
		if(SddqConstant.JXJHD_DHQ.equals(this.getString("STATUS"))){
			throw new AppException("jxjhd", "nohq");
		}
		
		if (SddqConstant.JXJHD_YJH.equalsIgnoreCase(this.getString("status"))) {
			throw new AppException("jxjhd", "hasjh");
		}
		
		if(this.getJpo() != null && this.getJpo().getJpoSet("JXDEORDERITEM") != null &&  this.getJpo().getJpoSet("JXDEORDERITEM").count() > 0){
			for (int index = 0;index < this.getJpo().getJpoSet("JXDEORDERITEM").count();index++){
				IJpo itemJpo = this.getJpo().getJpoSet("JXDEORDERITEM").getJpo(index);
				double xqqty = itemJpo.getDouble("LFIMG");
				double sumQty = 0;
				IJpoSet itemRJposet = itemJpo.getJpoSet("JXDEORDERITEMLOC");
				if(itemRJposet != null && itemRJposet.count() > 0){
					for(int j=0;j<itemRJposet.count();j++){
						sumQty = sumQty + itemRJposet.getJpo(j).getDouble("QTY");
					}
					if(sumQty != xqqty){
						throw new AppException("jxjhd", "ckslequaljpsl");
					}
				}else{
					throw new AppException("jxjhd", "ckslequaljpsl");
				}
			}
		}
		
		String jxjhdnum = this.getString("JHNUM");
		JSONObject data = InterfaceUtil.toErpJxJH(jxjhdnum);
		if (data.getString("flag").equals("E")) {
			String msg = data.getString("msg");
			this.setValue("ERPMSG", msg);
			this.getAppBean().SAVE();
			throw new MroException("", msg);
		} else {
			this.setValue("ERPMSG", SddqConstant.JXJHD_JHCG,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.setValue("STATUS", SddqConstant.JXJHD_YJH,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			//MRO出库
			if(this.getJpo() != null && this.getJpo().getJpoSet("JXDEORDERITEM") != null &&  this.getJpo().getJpoSet("JXDEORDERITEM").count() > 0){
				IJpoSet itemJposet = this.getJpo().getJpoSet("JXDEORDERITEM");
				for (int index = 0;index < itemJposet.count();index++){
					IJpo itemJpo = itemJposet.getJpo(index);
					String itemnum = ItemUtil.getItemnumFor400(itemJpo.getString("ITEMNUM"));
					IJpoSet itemRJposet = itemJpo.getJpoSet("JXDEORDERITEMLOC");
					if(itemRJposet != null && itemRJposet.count() > 0){
						for(int j=0;j<itemRJposet.count();j++){
							IJpo jpo = itemRJposet.getJpo(j);
							double xqty = jpo.getDouble("QTY");
							CommonInventory.OUTINVENTORY("", xqty, jpo.getString("LOCATION"),
									itemnum, "", jxjhdnum);
						}
					}
				}
			}
			this.getAppBean().SAVE();
		}
	}

	/**
	 * 删除前校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int DELETE() throws MroException, IOException {

		if (SddqConstant.JXJHD_YJH.equals(this.getString("STATUS"))) {
			throw new MroException("jxjhd", "yjh");
		}
		return super.DELETE();
	}

}
