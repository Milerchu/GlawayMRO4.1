package com.glaway.sddq.material.locbinitem.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <仓位物料表数量字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class FldQty extends JpoField {
/**
 * 校验数量是否大于可分配数量
 * @throws MroException
 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		int bzqty=this.getJpo().getParent().getInt("bzqty");
		IJpoSet locbinitemset=this.getJpo().getParent().getJpoSet("locbinitem");
		if(!locbinitemset.isEmpty()){
			int sumfpqty=0;
			for(int i=0;i<locbinitemset.count();i++){
				int fpqty=locbinitemset.getJpo(i).getInt("fpqty");
				sumfpqty=sumfpqty+fpqty;
			}
			int newnobinqty =bzqty-sumfpqty;
			if(newnobinqty<0){
			throw new MroException("locbinitem", "qty");
		}
		}
		super.action();
	}
}
