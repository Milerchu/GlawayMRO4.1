package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造车辆信息-车号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class FldCarnum extends JpoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8428915915332429652L;

	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { "CARNUM", "ASSETNUM" };
		String[] srcAttrs = { "CARNO", "ASSETNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	@Override
	public IJpoSet getList() throws MroException {
		IJpo parentDist = this.getJpo().getParent();
		if(parentDist!=null){
			// 办事处
			String office = parentDist.getString("WHICHOFFICE");
			// 车型
			String models = parentDist.getString("TRANSMODELS");
			String where = "ownercustomer in (select custnum from custinfo where whichoffice='"
					+ office + "') and cmodel='" + models + "'";
			// 过滤已选择车号
			IJpoSet transCarSet = parentDist.getJpoSet("TRANSCAR");
			String existCar = "";
			for(int i=0;i<transCarSet.count();i++){
				if(transCarSet.getJpo(i).getId()==this.getJpo().getId()){
					continue;
				}
				existCar +="'"+transCarSet.getJpo(i).getString("CARNUM")+"',";
			}
			if(StringUtil.isStrNotEmpty(existCar)){
				existCar=existCar.substring(0,existCar.length()-1);
				where += " and carno not in("+existCar+")";
			}
			// 根据车型和办事处过滤车辆选择
			this.setListWhere(where);
		}else{
			this.setListWhere("1=2");
		}
		return super.getList();
	}

}
