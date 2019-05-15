package com.glaway.sddq.material.materborrow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件借用借用人字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class FldBorrowBy extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName()};
		String[] srcAttrs = { "personid"};
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 过滤人员数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("SYS_PERSON");
		String listSql = "1=1";
		
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
	/**
	 * 触发赋值借方部门负责人，借方单元负责人
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String DEPARTMENT=this.getJpo().getJpoSet("borrowby").getJpo().getString("DEPARTMENT");//借用人部门
		String parentdept=this.getJpo().getJpoSet("borrowby").getJpo().getJpoSet("dept").getJpo().getString("PARENT");//借用人上级部门
		if(DEPARTMENT.isEmpty()){
			throw new MroException("请补全人员部门信息");
		}else{
			IJpoSet deptset = MroServer.getMroServer().getJpoSet("sys_dept",MroServer.getMroServer().getSystemUserServer());
			deptset.setUserWhere("deptnum='"+DEPARTMENT+"'");
			deptset.reset();
			if(!deptset.isEmpty()){
				String BORROWAPPR=deptset.getJpo(0).getString("OWNER");//借方部门负责人
				this.getJpo().setValue("BORROWAPPR", BORROWAPPR,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			IJpoSet parentdeptset = MroServer.getMroServer().getJpoSet("sys_dept",MroServer.getMroServer().getSystemUserServer());
			parentdeptset.setUserWhere("deptnum='"+parentdept+"'");
			parentdeptset.reset();
			if(!parentdeptset.isEmpty()){
				String BORROWCHECK=parentdeptset.getJpo(0).getString("OWNER");//借方单元责任人
				this.getJpo().setValue("BORROWCHECK", BORROWCHECK,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		
	}
	
}
