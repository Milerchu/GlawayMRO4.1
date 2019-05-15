package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <工作令号字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-2]
 * @since [产品/模块版本]
 */
public class FldWorkordernum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String mprtype = this.getJpo().getString("mprtype");
		if (mprtype.equalsIgnoreCase("GZ")) {
			String[] thisAttrs = { this.getFieldName() };
			String[] srcAttrs = { "TRANSWORKORDERNUM" };
			setLookupMap(thisAttrs, srcAttrs);
		} else {
			String[] thisAttrs = { this.getFieldName(), "COSTCENTER" };
			String[] srcAttrs = { "WORKORDERNUM", "COSTCOLLECDEPT" };
			setLookupMap(thisAttrs, srcAttrs);
		}
	}

	/**
	 * 获取工作令号，改造计划数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String mprtype = this.getJpo().getString("mprtype");
		if (mprtype.equalsIgnoreCase("GZ")) {
			setListObject("TRANSNOTICE");
			String listSql = "";
			listSql = "STATUS='已审核'";
			if (!listSql.equals("")) {
				setListWhere(listSql);
			}
		} else {
			setListObject("PROJECTINFO");
			String listSql = "";
			listSql = listSql + "STATUS='已立项'";
			if (!listSql.equals("")) {
				setListWhere(listSql);
			}
		}

		return super.getList();
	}
/**
 * 校验成本中心是否为空
 * @throws MroException
 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		String MPRTYPE = this.getJpo().getString("MPRTYPE");
		String WORKORDERNUM = this.getValue();
		if (MPRTYPE.equalsIgnoreCase("CB")||MPRTYPE.equalsIgnoreCase("QT")) {
			String COSTCOLLECDEPT = this.getJpo().getJpoSet("WORKORDERNUM")
					.getJpo(0).getString("COSTCOLLECDEPT");
			if (COSTCOLLECDEPT.isEmpty()) {
				throw new MroException("成本中心为空，请到服务管理-项目管理中工作令号为："
						+ WORKORDERNUM + "的项目中维护成本中心");
			}
		}
		super.action();
	}

}
