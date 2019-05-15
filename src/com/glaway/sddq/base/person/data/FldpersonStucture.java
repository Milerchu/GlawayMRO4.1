package com.glaway.sddq.base.person.data;

import java.util.HashSet;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 人员信息中服务人员类型控制类
 * 
 * @author 谭卓
 */
public class FldpersonStucture extends JpoField {

	private static final long serialVersionUID = 24341L;

	@Override
    public void init()
        throws MroException
    {
		
		// 根据人员所属部门及上级部门 属于12个办事处，则可编辑  ，否则字段只读
		String deptment =this.getJpo().getString("DEPARTMENT");
		String dept =this.getJpo().getString("DEPT.PARENT");
		
		if(isIn(deptment)||isIn(dept)){
			
			
		}else{
			this.setFlag(GWConstant.S_READONLY, true);
		}
    }

	/**
	 * 
	 * 判断部门编号是否在办事处编号集合中
	 * @param dept
	 * @return [参数说明]
	 *
	 */
	private boolean isIn(String dept) {
		HashSet<String> set = new HashSet<String>();
		set.add("01061900");//广州办事处
		set.add("01061500");//兰州办事处
		set.add("01061600");//西安办事处
		set.add("01061700");//重庆办事处
		set.add("01060800");//配件管理部
		set.add("01061400");//北京办事处
		set.add("01062000");//上海办事处
		set.add("01062300");//武汉办事处
		set.add("01061800");//株洲办事处
		set.add("01062100");//沈阳办事处
		set.add("01062700");//美洲办事处
		set.add("01062600");//非洲
		set.add("01062500");//东南亚
		set.add("01062800");//广州检修基地
		set.add("01062400");//青岛检修分公司
		set.add("01062900");//检修技术部
		set.add("01060100");//技术支持部
		set.add("01060200");//质量安全部
		
		return set.contains(dept);
	}
	
}
