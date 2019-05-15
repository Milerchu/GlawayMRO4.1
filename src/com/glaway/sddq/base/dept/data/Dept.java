package com.glaway.sddq.base.dept.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * <功能描述> 表SYS_DEPT的数据处理类
 * 
 * @author jxiang
 * @version [版本号, 2016-4-19]
 * @since [产品/模块版本]
 */
public class Dept extends Jpo
{
    
    /**
	 * 注释内容
	 */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void add()
        throws MroException
    {
        super.add();
        if (toBeAdded())
        {
			String[] siteOrg = getSiteOrg(); // /获取组织地点信息
            setValue("SITEID", siteOrg[0],GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            setValue("ORGID", siteOrg[1],GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            
        }
    }
  
    @Override
    public void initFields()
        throws MroException
    {
        /*
		 * if (getString("HIERARCHY").equals("级组织")) { } else {
		 * setFieldFlag("SCHEMENUM", GWConstant.S_READONLY, true);
		 * setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
		 * setFieldFlag("VERSION", GWConstant.S_READONLY, true);
		 * setFieldFlag("JXNUM", GWConstant.S_READONLY, true);
		 * setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
		 * setFieldFlag("REPAIRPROCESS", GWConstant.S_READONLY, true);
		 * setFieldFlag("PERIOD", GWConstant.S_READONLY, true);
		 * this.getJpoSet("SCHEMENUM").setFlag(GWConstant.S_READONLY, true);
		 * this.getJpoSet("REPAIRSCOPE").setFlag(GWConstant.S_READONLY, true); }
		 */
    	
    	
    	
    }
    

 
    
    @Override
    public void beforeSave()
        throws MroException
    {
        super.beforeSave();
        int deptlevel = this.getInt("DEPTLEVEL");
		if (toBeAdded() && deptlevel == 0) // 0 级新增部门
        {
            IJpoSet ancestors = getJpoSet("DEPTANCESTOR");
            IJpo ancestor = ancestors.addJpo();
            ancestor.setValue("ancestor", getId());
            ancestor.setValue("deptid", getId());
            ancestor.setValue("orgid", getString("ORGID"));
            ancestor.setValue("siteid", getString("SITEID"));
        }
    }
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        
        if (!isNew())
        {
			// //初始化节点信息
            String parentDeptNum = getString("PARENT");
            String deptNum = getString("DEPTNUM");
            String orgId = getString("ORGID");
            String siteId = getString("SITEID");
            
            if (!StringUtil.isNull(parentDeptNum))
 { // /当PARENT不为空时 表示此部门一个子级部门
                String pwhereSql =
                    "DEPTNUM='" + StringUtil.getSafeSqlStr(parentDeptNum) + "' and ORGID='"
                        + StringUtil.getSafeSqlStr(orgId) + "' and SITEID='" + StringUtil.getSafeSqlStr(siteId) + "'";
				IJpoSet parentJpoSet = getJpoSet("$SYS_DEPT", "SYS_DEPT",
						pwhereSql); // /查询当前部门的父级部门信息
                if (parentJpoSet.count() == 1)
                {
					setValue("HASPARENT", "1",
							GWConstant.P_NOUPD_NOACTION_NOVALIDAT); // /将当前部门的父级节点设置为真
                }
                parentJpoSet.destroy();
            }
            
            String whereSql = "parent='" + deptNum + "' and ORGID='" + orgId + "' and SITEID='" + siteId + "'";
			IJpoSet childrenJpoSet = getJpoSet("$SYS_DEPT", "SYS_DEPT",
					whereSql); // /查询当前部门的下级部门信息
            if (childrenJpoSet.count() > 0)
 { // /当前部门存在下级部门
				setValue("HASCHILDREN", "1",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT); // /将当前部门的子级节点设置为真
                childrenJpoSet.destroy();
            }
            else
            {
				setValue("HASCHILDREN", "0",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT); // /将当前部门的子级节点设置为假
                childrenJpoSet.destroy();
            }
            
            //            if(!getJpoSet("CHILDREN").isEmpty() || !getJpoSet("DEPTCRAFT").isEmpty()){
            setFieldFlag("DEPTNUM", GWConstant.S_READONLY, true);
            //            }else{
            //                setFieldFlag("DEPTNUM", GWConstant.S_READONLY, false);
            //            }
        }
        
		// ||getString("HIERARCHY").equals("3级组织")||getString("HIERARCHY").equals("4级组织")
		// 层级控制字段显示
        /*
		 * if(getString("HIERARCHY").equals("1级组织")||getString("HIERARCHY").equals
		 * ("2级组织")){ //setFieldFlag("DESCRIPTION", GWConstant.S_READONLY,
		 * true); this.setFlag(GWConstant.S_READONLY, true); }else{
		 * if(getString("HIERARCHY").equals("5级组织")){
		 * this.setFlag(GWConstant.S_READONLY, false); } }
		 */
        
        /*
		 * if(getString("HIERARCHY").equals("5级组织")){
		 * this.setFlag(GWConstant.S_READONLY, false);
		 * //setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true); }else{
		 * if(getString("HIERARCHY").equals("2级组织")){
		 * this.setFlag(GWConstant.S_READONLY, true); }
		 * this.setFlag(GWConstant.S_READONLY, true); }
		 */
        String[] fields = new String[] { "LATITUDE", "LONGITUDE","SITEID","ORGID","PARENT","OWNER","ABBREVIATION","SHORTNAME","ADDR"};
		if (getString("HIERARCHY").equals("5级组织") || this.isNull("HIERARCHY")) {
      
        	this.setFlag(GWConstant.S_READONLY, false);
        }else{
        	this.setNoFieldFlag(fields, GWConstant.S_READONLY, true);
        }
    }
    
    /**
     * @throws MroException
     */
    @Override
    public void delete()
        throws MroException
    {
        // TODO Auto-generated method stub
        canDelete();
        getJpoSet("DEPTANCESTOR").deleteAll();
        super.delete();
    }
    
    /**
	 * <功能描述> 判断当前部门是否能删除
	 * 
	 * @throws MroException
	 *             [参数说明]
	 */
    public void canDelete()
        throws MroException
    {
        String deptNum = this.getString("DEPTNUM");
        String subSql =
            "parent='" + StringUtil.getSafeSqlStr(deptNum) + "' and siteid='"
                + StringUtil.getSafeSqlStr(getString("siteid")) + "'";
        IJpoSet children = getJpoSet("$CHILDREN", "SYS_DEPT", subSql);
        boolean hasChildren = getBoolean("HASCHILDREN");
      /*  int deptlevel = this.getInt("DEPTLEVEL");
        int childlevel = deptlevel + 1;*/
        
        String hierarchy = this.getString("HIERARCHY");
        if(StringUtil.isStrNotEmpty(hierarchy)){
        	
        
            /*
             * int cj = Integer.parseInt(hierarchy); int childlevelnew = cj + 1;
             */
        
        //        if (hasChildren)
        //        {
			// String[] p = {"该部门存在子级部门，不可删除。"};
        //            throw new MroException("dept", "validate", p);
        //        }
        //        
        //        if (!children.isEmpty())
        //        {
			// String[] p = {"该部门存在子级部门，不可删除。"};
        //            throw new MroException("dept", "validate", p);
        //        }
            /*
			 * if (deptlevel < 4) { String[] p = {"该部门是" + childlevel +
			 * "级部门，不可删除。"}; throw new MroException("dept", "validate", p); }
			 */
       
            /*
			 * if (cj < 4) { String[] p = {"该部门是" + childlevelnew +
			 * "级部门，不可删除。"}; throw new MroException("dept", "validate", p); }
			 */
			if ((hierarchy.equals("1级组织")) || (hierarchy.equals("2级组织"))
					|| (hierarchy.equals("3级组织")) || (hierarchy.equals("4级组织"))) {

				String[] params = { "该部门是" + hierarchy + "部门，不可删除。" };
                throw new MroException("dept", "validate", params);

        }

        }
        
		// 添加用户关联校验
        if (!getJpoSet("$sys_person", "sys_person", "department='" + StringUtil.getSafeSqlStr(deptNum) + "'").isEmpty())
        {
			String[] p = { "该部门存在用户，不可删除。" };
            throw new MroException("dept", "validate", p);
        }
        
		// 添加工种关联校验
        if (!getJpoSet("$SYS_DEPTCRAFT", "SYS_DEPTCRAFT", "deptnum='" + StringUtil.getSafeSqlStr(deptNum) + "'").isEmpty())
        {
			String[] p = { "该部门存在工种，不可删除。" };
            throw new MroException("dept", "validate", p);
        }
        
    }
    
}
