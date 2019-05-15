package com.glaway.sddq.config.bzsq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
/**
 * 
 * 配置变更申请Jpo
 * 
 * @author  hyhe
 * @version  [版本号, 2018-11-29]
 * @since  [产品/模块版本]
 */
public class Bgsq extends StatusJpo implements IStatusJpo, FixedLoggers{
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		String type = this.getString("type");
		if("1".equals(type)){
			this.getField("carno").setRequired(true);
			this.getField("location").clearError();
		}else if("2".equals(type)){
			this.getField("location").setRequired(true);
			this.getField("carno").clearError();
		}else if("3".equals(type)){
			this.getField("carno").setRequired(false);
			this.getField("location").setRequired(false);
		}
	}
	
}
