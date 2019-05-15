package com.glaway.sddq.base.dept.bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Dialog;
import com.glaway.mro.page.control.Tree;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * <功能描述>
 * 构建部门树
 * @author  jxiang
 * @version  [版本号, 2016-4-20]
 * @since  [产品/模块版本]
 */
public class DeptTreeBean extends TreeBean
{
    
    @Override
    public List<Map<String, String>> buildAndGetChildMaps(PageControl parCtrl)
            throws MroException {
        parCtrl.setSubs(null);
        IJpoSet nodesSet = null;
        if (parCtrl instanceof Tree)
        { 
            // 创建根节点
            nodesSet = getRootJpoSet();
        } else {
            nodesSet = getChildJpoSet(((TreeNode) parCtrl).getJpo());
        }
        return buildAndGetChildMaps(nodesSet, parCtrl);
    }
    
    @Override
    public int clickNode(TreeNode treeNode)
        throws MroException, IOException
    {
        String appName = getAppBean().getAppName().toUpperCase();
        if(!"DEPT".equals(appName)){
            String id = treeNode.getJpo().getString("deptnum");
            if(!StringUtil.isNull(id)){
                    Dialog dialog = getDataBean("lookup_depttree").getDialog();
                    PageControl pc = dialog.getCreator();
                    pc.setValue(id);
                    dialog.close();
            }
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    @Override
    public boolean beforeClickNode(TreeNode treeNode)
        throws MroException, IOException
    {
        if("PERSON".equals(getAppName())){
            return true;
        }else{
            return super.beforeClickNode(treeNode);
        }
    }
    
    @Override
    public String getNodeIcon(IJpo jpo, Map<String, String> node)
    {
        String icon = "treenodeicon/org_temp.png";
        return icon;
    }
    
    @Override
    public String getNodeStyle(IJpo jpo, Map<String, String> arg1) {
    	// TODO Auto-generated method stub
    	try {
			String deptnum = jpo.getString("deptnum");
			if(StringUtil.isEqualIgnoreCase(deptnum, "01060000")){
				return "{\"color\":\"red\"}";
			}
		} catch (MroException e) {
			e.printStackTrace();
		}
    	return super.getNodeStyle(jpo, arg1);
    }
    
    
    
    
}
