package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <处置管理-工作令号字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-2]
 * @since  [产品/模块版本]
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
		String location =this.getJpo().getString("location");
		if(!location.isEmpty()){
			String erploc = this.getJpo().getJpoSet("location").getJpo().getString("erploc");
			if (erploc.equalsIgnoreCase("改造物料库")) {
				String[] thisAttrs = { this.getFieldName() };
				String[] srcAttrs = { "TRANSWORKORDERNUM" };
				setLookupMap(thisAttrs, srcAttrs);
			} else {
				String[] thisAttrs = { this.getFieldName()};
				String[] srcAttrs = { "WORKORDERNUM"};
				setLookupMap(thisAttrs, srcAttrs);
			}
		}
	}

	/**
	 * 获取工作令号
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String location =this.getJpo().getString("location");
		if(!location.isEmpty()){
			String erploc = this.getJpo().getJpoSet("location").getJpo().getString("erploc");
			if (erploc.equalsIgnoreCase("改造物料库")) {
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
		String location =this.getJpo().getString("location");
		if(!location.isEmpty()){
			String erploc = this.getJpo().getJpoSet("location").getJpo().getString("erploc");
			if (!erploc.equalsIgnoreCase("改造物料库")) {
				String WORKORDERNUM = this.getValue();
				String COSTCOLLECDEPT = this.getJpo().getJpoSet("WORKORDERNUM")
						.getJpo(0).getString("COSTCOLLECDEPT");
				if (COSTCOLLECDEPT.isEmpty()) {
					this.getJpo().setValue("COSTCENTER", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					throw new MroException("成本中心为空，请到服务管理-项目管理中工作令号为："
							+ WORKORDERNUM + "的项目中维护成本中心");
				}else{
					this.getJpo().setValue("COSTCENTER", COSTCOLLECDEPT,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}
		
		super.action();
	}

}
