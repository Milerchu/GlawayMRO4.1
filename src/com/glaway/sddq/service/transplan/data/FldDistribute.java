package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <功能描述> 子表分发数量校验
 * 
 * @author 20167088
 * @version [版本号, 2018-7-20]
 * @since [产品/模块版本]
 */
public class FldDistribute extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub	
		
		double Distributeqty = this.getJpo().getDouble("Distributeqty");
		if(Distributeqty>this.getJpo().getDouble("TRANSPLANLOC.QTY")){
			throw new MroException("", "分发数量不能大于应分发数量");//
		}
		double qty = this.getJpo().getDouble("TRANSPLANLIST.QTY");// 总数量
		IJpoSet jpoiset = this.getJpo().getJpoSet("$TRANSPLANLISTLINE", "TRANSPLANLISTLINE", "TRANSPLANLISTNUM=:TRANSPLANLISTNUM");

		if (!jpoiset.isEmpty() && jpoiset.count() > 1) {
			IJpoSet jposet = this.getJpo().getJpoSet("$TRANSPLANLISTLINE", "TRANSPLANLISTLINE", "TRANSPLANLISTNUM=:TRANSPLANLISTNUM and TRANSPLANLISTLINEID!=:TRANSPLANLISTLINEID");
			double total = jposet.sum("Distributeqty");
			if (qty < (Distributeqty + total)) {
				throw new MroException("TRANSPLANLIST", "TRANSPLANLIST", new String[]
				{ "分发数量之和不能大于清单数量" });
			}
		} else {
			if (qty < (Distributeqty)) {
				throw new MroException("TRANSPLANLIST", "TRANSPLANLIST", new String[] {
						"分发数量之和不能大于清单数量" });
			}
		}
		super.action();

	}
}
