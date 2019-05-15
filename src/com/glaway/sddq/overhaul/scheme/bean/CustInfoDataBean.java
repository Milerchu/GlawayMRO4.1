package com.glaway.sddq.overhaul.scheme.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 承修单位多选
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-5]
 * @since  [产品/模块版本]
 */
public class CustInfoDataBean extends DataBean {

	@Override
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean chengxiuunitBean = this.page.getAppBean().getDataBean("15082258236067");
		IJpoSet chengxiuunitSet=chengxiuunitBean.getJpoSet();
		
		String schemenum =chengxiuunitSet.getParent().getString("SCHEMENUM");
		
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty())
        {
			for (int i = 0; i < list.size(); i++){		
				IJpo lotnumjpo = list.get(i);
				IJpo jobscope = chengxiuunitSet.addJpo();	
				jobscope.setValue("CUSTNUM", lotnumjpo.getString("CUSTNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jobscope.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
				jobscope.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
				jobscope.setValue("SCHEMENUM", schemenum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
        }
		chengxiuunitBean.reloadPage();
		return super.dialogok();
	}
}
