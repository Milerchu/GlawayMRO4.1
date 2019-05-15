package com.glaway.sddq.material.mprline.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * <客户料退库领料单选择领料明细类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-12]
 * @since  [产品/模块版本]
 */
public class SelectJkTransferlineDataBean extends DataBean {
	/**
	 * 过滤物料数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String instoreroom = this.page.getAppBean().getJpo()
				.getString("instoreroom");
		String where = "";
		IJpoSet mprlineset = this.getAppBean().getJpo().getJpoSet("mprline");
		String jktransferlineid = null;

		for (int j = 0; j < mprlineset.count(); j++) {
			IJpo mprline = mprlineset.getJpo(j);

			if (jktransferlineid == null) {
				jktransferlineid = "'" + mprline.getString("jktransferlineid") + "'";
			} else {
				jktransferlineid = jktransferlineid + ",'" + mprline.getString("jktransferlineid") + "'";
			}
		}

		if (jktransferlineid != null) {
			where = " jktransferlineid not in(" + jktransferlineid + ")";
		}
		if (!StringUtil.isStrEmpty(instoreroom)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and  transfernum in (select transfernum from transfer where type='JKD') and ISSUESTOREROOM='"+instoreroom+"' and status='已接收' and failqty>0 and qtmprlineid is null and transferlineid not in(select jktransferlineid from mprline where status='未领用')" +
						"and assetnum is null and tasknum in (select jxtasknum from JXTASKLOSSPART where ISCUSTITEM='1' and itemnum in (select itemnum from mprline where assetnum is null and mprnum in (select mprnum from mpr where mprtype='JS') and issuestoreroom='"+instoreroom+"'))";
			} else {
				where="transfernum in (select transfernum from transfer where type='JKD') and ISSUESTOREROOM='"+instoreroom+"' and status='已接收' and failqty>0 and qtmprlineid is null and transferlineid not in(select jktransferlineid from mprline where status='未领用')" +
						"and assetnum is null and tasknum in (select jxtasknum from JXTASKLOSSPART where ISCUSTITEM='1' and itemnum in (select itemnum from mprline where assetnum is null and mprnum in (select mprnum from mpr where mprtype='JS') and issuestoreroom='"+instoreroom+"'))";
			}

		}
		
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 确认按钮赋值选择的数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean mprlinedataBean = this.page.getAppBean().getDataBean(
				"15087392617582");
		IJpoSet mprlineSet = mprlinedataBean.getJpoSet();
		String mprmnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		String ISSUESTOREROOM=this.page.getAppBean().getJpo().getString("MPRSTOREROOM");
		String INSTOREROOM=this.page.getAppBean().getJpo().getString("INSTOREROOM");
		String thisrkmprlineid="";
		IJpoSet thismprlineset = this.getAppBean().getJpo().getJpoSet("mprline");
		String rkmprlineid = null;

		for (int j = 0; j < thismprlineset.count(); j++) {
			IJpo thismprline = thismprlineset.getJpo(j);

			if (rkmprlineid == null) {
				rkmprlineid = "'" + thismprline.getString("rkmprlineid") + "'";
			} else {
				rkmprlineid = rkmprlineid + ",'" + thismprline.getString("rkmprlineid") + "'";
			}
		}
		
		
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo jkline = list.get(i);
				IJpo mprline = mprlineSet.addJpo();
				mprline.setValue("jktransferlineid", jkline.getString("transferlineid"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("ISSUESTOREROOM", ISSUESTOREROOM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("INSTOREROOM", INSTOREROOM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
				mprline.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
				mprline.setValue("MPRNUM", mprmnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("qty", jkline.getDouble("failqty"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("status", "未领用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("itemnum", jkline.getString("itemnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				
				IJpoSet rkmprlineset = MroServer.getMroServer().getJpoSet("mprline",MroServer.getMroServer().getSystemUserServer());
				if (rkmprlineid != null) {
					rkmprlineset.setUserWhere("mprnum in (select mprnum from mpr where mprtype='JS') and mprlineid not in(" + rkmprlineid + ") and qtmprlineid is null and assetnum is null and issuestoreroom='"+INSTOREROOM+"' and itemnum='"+jkline.getString("itemnum")+"'");
				}else{
					rkmprlineset.setUserWhere("mprnum in (select mprnum from mpr where mprtype='JS') and qtmprlineid is null and assetnum is null and issuestoreroom='"+INSTOREROOM+"' and itemnum='"+jkline.getString("itemnum")+"'");
				}
				if(!rkmprlineset.isEmpty()){
					thisrkmprlineid=rkmprlineset.getJpo(0).getString("mprlineid");
				}
				mprline.setValue("rkmprlineid", thisrkmprlineid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		mprlinedataBean.reloadPage();
		return super.dialogok();
	}
}
