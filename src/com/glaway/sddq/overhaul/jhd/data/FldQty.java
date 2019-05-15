package com.glaway.sddq.overhaul.jhd.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 数量字段类
 * 
 * @author public2175
 * @version [版本号, 2019-1-21]
 * @since [产品/模块版本]
 */
public class FldQty extends JpoField {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 校验拣配数量不能大于当前库存可用数量!
	 * @throws MroException
	 */
	@Override
	public void validate() throws MroException {
		
		if (StringUtil.isNullOrEmpty(this.getJpo().getString("LOCATION"))) {
			throw new AppException("jxjhd", "selectLoc");
		}
		double dvalue = 0;
		String value = this.getInputMroType().getStringValue();
		double qtyValue = this.getField("LOCQTY").getDoubleValue();
		if(!StringUtil.isNullOrEmpty(value)){
			dvalue = Double.valueOf(value);
			if(dvalue > qtyValue){
				throw new AppException("jxjhd", "jpdycc");
			}
		}
		if(this.getJpo() != null && this.getJpo().getJpoSet() != null && this.getJpo().getJpoSet().count() > 0){
			double sumqty = 0;
			IJpoSet qtyJposet = this.getJpo().getJpoSet();
			for(int index = 0;index < this.getJpo().getJpoSet().count();index++){
				IJpo jpo = qtyJposet.getJpo(index);
				if(jpo != this.getJpo()){
					sumqty = sumqty + jpo.getDouble("qty");
				}else{
					sumqty = sumqty + dvalue;
				}
			}
			double jpQty = this.getJpo().getParent().getDouble("LFIMG");
			if(sumqty > jpQty){
				throw new AppException("jxjhd", "ckslequaljpsl");
			}
		}
	}
}
