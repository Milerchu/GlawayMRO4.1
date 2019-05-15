package com.glaway.sddq.overhaul.jhd.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 检修交货单Jpo
 * 
 * @author public2175
 * @version [版本号, 2019-1-10]
 * @since [产品/模块版本]
 */
public class JxJhd extends Jpo implements IJpo {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 初始化时,根据状态设置只读
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		if (SddqConstant.JXJHD_YHQ.equals(this.getString("STATUS"))
				|| SddqConstant.JXJHD_YJH.equals(this.getString("STATUS"))) {
			this.getField("JHNUM").setReadonly(true);
			this.getField("VKORG").setReadonly(true);
		}
		if(SddqConstant.JXJHD_YJH.equals(this.getString("STATUS"))){
			this.getJpoSet("JXDEORDERITEM").setFlag(GWConstant.S_READONLY, true);
		}
	}
	
	/**
	 * 删除时，级联删除子项
	 * @throws MroException
	 */
	@Override
	public void delete() throws MroException {
		if(this.getJpoSet("JXDEORDERITEM") != null && this.getJpoSet("JXDEORDERITEM").count() >0){
			this.getJpoSet("JXDEORDERITEM").deleteAll();
		}
		super.delete();
	}
}
