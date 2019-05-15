package com.glaway.sddq.base.dept.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * <功能描述> 部门结构-下级部门设置
 * 
 * @author jxiang
 * @version [MRO4.0, 2016-4-19]
 * @since [MRO4.0/模块版本]
 */
public class ChirldrenDeptBean extends DataBean
{
    @Override
    public void initialize()
        throws MroException
    {
        super.initialize();
        
    }
    
    /**
     * @return
     * @throws MroException
     * @throws IOException
     */
    @Override
    public int addrow()
        throws MroException, IOException
    {
        IJpo parent = this.getParent().getJpo();
       String hierarchy = parent.getString("HIERARCHY");
       
		if (hierarchy.equals("1级组织")) {
			String[] params = { "2级组织部门不能手动新建。" };
			throw new MroException("dept", "validate", params);
		} else if (hierarchy.equals("2级组织")) {
			String[] params = { "3级组织部门不能手动新建。" };
			throw new MroException("dept", "validate", params);
		} else if (hierarchy.equals("3级组织")) {
			String[] params = { "4级组织部门不能手动新建。" };
			throw new MroException("dept", "validate", params);
        }

       
        if (null != parent)
        {
            
            if (parent.isNew())
            {
				String[] params = { "请先保存上级部门，再添加下级部门。" };
                throw new MroException("dept", "validate", params);
            }
            int re = super.addrow();
            
            if (hierarchy.equals("4级组织")) {
    			this.getJpo().setValue("hierarchy", "5级组织");
    		} else if (hierarchy.equals("5级组织")) {
    			this.getJpo().setValue("hierarchy", "6级组织");
    		} else if (hierarchy.equals("6级组织")) {
    			this.getJpo().setValue("hierarchy", "7级组织");
            } else if (hierarchy.equals("7级组织")) {
    			this.getJpo().setValue("hierarchy", "8级组织");
            }
            
            if (currJpo.toBeAdded())
            {
                IJpoSet ancestors = currJpo.getJpoSet("DEPTANCESTOR");
                IJpo ancestor = ancestors.addJpo();
                ancestor.setValue("ancestor", currJpo.getId());
                ancestor.setValue("deptid", currJpo.getId());
                ancestor.setValue("orgid", getString("ORGID"));
                ancestor.setValue("siteid", getString("SITEID"));
                if (null != parent)
 { // /添加中间节点
                    IJpoSet parentAncestors = parent.getJpoSet("DEPTANCESTOR");
                    for (int i = 0; i < parentAncestors.count(); i++)
                    {
                        IJpo parentAncestor = parentAncestors.getJpo(i);
                        IJpo ancestorTemp = ancestors.addJpo();
                        ancestorTemp.setValue("ancestor", parentAncestor.getInt("ancestor"));
                        ancestorTemp.setValue("deptid", currJpo.getId());
                        ancestorTemp.setValue("orgid", parentAncestor.getString("ORGID"));
                        ancestorTemp.setValue("siteid", parentAncestor.getString("SITEID"));
                    }
                }
            }
            else
            {
                throw new AppException("jobbook", "isForSche");
            }
            return re;
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    @Override
    public void insertWithoutSubRefresh()
        throws MroException
    {
        super.insertWithoutSubRefresh();
        String parDptNum = parent.getString("DEPTNUM");
        int deptLevel = parent.getJpo().getInt("DEPTLEVEL");
        //        if (deptLevel >= 4) {
        //            throw new MroException("dept", "overlevel");
        //        }
        if (StringUtil.isStrEmpty(parDptNum))
        {
			String[] params = { "请填写父级部门编号后，再执行此操作。" };
            throw new MroException("dept", "validate", params);
        }
        
        //        int count = jpoSet.count();
        //        String deptNum  = (new StringBuilder(String.valueOf(parDptNum))).append(count >= 10 ? ((Object) (Integer.valueOf(count))) : ((Object) ((new StringBuilder("0")).append(count).toString()))).toString();
		// String deptNum = parDptNum + (count < 10 ? ("0" + count) : count);
		// ///新的部门ID
        currJpo.setValue("PARENT", parDptNum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        currJpo.setValue("DEPTLEVEL", deptLevel + 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		currJpo.setValue("HASPARENT", "1",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // /设置父级节点信息
		currJpo.setValue("TYPE", (deptLevel + 1) + "级部门",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        //        currJpo.setValue("DEPTNUM", deptNum, GWConstant.P_NOVALIDATION);
        currJpo.setValue("HASCHILDREN", "0",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        currJpo.setValue("siteid", parent.getString("siteid"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        currJpo.setValue("ORGID", parent.getString("orgid"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		DeptTreeBean tree = (DeptTreeBean) this.getDataBean("depttree"); // /设置父子级关系
        TreeNode treeNode = tree.getCurrNode();
        IJpo treeJpoSet = treeNode.getJpo();
        treeJpoSet.setValue("HASCHILDREN", "1",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        parent.setValue("ISOPEN", "1", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
    }
    
    @Override
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        super.addEditRowCallBackOk();
        ((TreeBean)parent).reloadNode();
    }
    
    public void addEditRowCallBackCancel()
        throws MroException, IOException
    {
        ((TreeBean)parent).reloadNode();
    }
    
    /*
	 * @Override public void initJpoSet() throws MroException {
	 * super.initJpoSet(); try { if (getString("HIERARCHY").equals("5级组织")) { if
	 * (getPage().getControlByXmlId("13762968470002") != null) {
	 * getPage().getControlByXmlId("13762968470002").show(); } if
	 * (getPage().getControlByXmlId("1376296535796") != null) {
	 * getPage().getControlByXmlId("1376296535796").show(); } } else { if
	 * (getPage().getControlByXmlId("13762968470002") != null) {
	 * getPage().getControlByXmlId("13762968470002").hide(); } if
	 * (getPage().getControlByXmlId("1376296535796") != null) {
	 * getPage().getControlByXmlId("1376296535796").hide(); } } } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */
}
