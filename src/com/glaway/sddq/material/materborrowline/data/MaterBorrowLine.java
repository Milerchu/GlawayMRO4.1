package com.glaway.sddq.material.materborrowline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件借用行JPO类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class MaterBorrowLine extends Jpo {
	/**
	 * 根据状态初始化字段控制
	 * @throws MroException
	 */
		@Override
		public void init() throws MroException {
			// TODO Auto-generated method stub
			super.init();
			if(!this.toBeAdded()){//判断如果不是新建
				String status=this.getParent().getString("status");//状态
				if(!status.equalsIgnoreCase("新建")){//如果不是新建状态，下列字段只读
					String[] readonlystr={"itemnum","seqnum","lotnum","use","memo"};
					this.setFieldFlag(readonlystr, GWConstant.S_READONLY, true);
				}
			}
		}
}
