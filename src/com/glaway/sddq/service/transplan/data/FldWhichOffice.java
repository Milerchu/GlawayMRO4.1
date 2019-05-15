package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造计划 改造车辆分布子表 whichoffice 字段类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-15]
 * @since [MRO4.1/模块版本]
 */
public class FldWhichOffice extends JpoField {

	private static final long serialVersionUID = 4904353185220112075L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		IJpo transplan = getJpo();
		String modelnum = transplan.getString("TRANSMODELS");

		super.init();

	}

	@Override
	public IJpoSet getList() throws MroException {
		// 根据车型过滤办事处
		IJpo transplan = this.getJpo().getParent();
		if (transplan != null) {
			// 过滤已选择的办事处
			IJpoSet transDist = transplan.getJpoSet("TRANSDIST");
			String officeWhere = "";
			for(int i=0;i<transDist.count();i++){
				String office = transDist.getJpo(i).getString("WHICHOFFICE");
				if(office.isEmpty()){
					continue;
				}
				officeWhere+="'"+office+"',";
			}
			if(!StringUtil.isNull(officeWhere)){
				officeWhere = officeWhere.substring(0,officeWhere.length()-1);
			}
			String models = transplan.getString("TRANSMODELS");
			if (StringUtil.isStrNotEmpty(models)) {
				String where ="deptnum in (select whichoffice from custinfo where custnum in (select ownercustomer from asset where cmodel='"
						+ models + "'))";
				if(!StringUtil.isNull(officeWhere)){
					where += " and deptnum not in("+officeWhere.substring(0,officeWhere.length()-1)+"')";
				}
				this.setListWhere(where);
			}
		}else{
			this.setListWhere("1=2");
		}
		return super.getList();
	}

}
