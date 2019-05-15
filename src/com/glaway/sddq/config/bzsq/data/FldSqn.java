package com.glaway.sddq.config.bzsq.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 产品序列号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-11-29]
 * @since  [产品/模块版本]
 */
public class FldSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "ASSETNUM", "SQN","ITEMNUM"},
				new String[] { "ASSETNUM", "SQN","ITEMNUM"});
	}
	
	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		String type = this.getJpo().getString("type");
		if("1".equals(type)){//车上配置
			if(StringUtil.isNullOrEmpty(this.getJpo().getString("carno"))){
	            throw new AppException("pzbg", "selectCarno");
			}else{
				String ancestor = this.getJpo().getString("ancestor");
				this.setListWhere("assetnum in (select assetnum from asset start with assetnum='"
							+ ancestor
							+ "' connect by parent = PRIOR assetnum) and assetlevel = 'SYSTEM'");
			}
		}else if("2".equals(type)){
			String location = this.getJpo().getString("location");
			if(StringUtil.isNullOrEmpty(location)){
	            throw new AppException("pzbg", "selectLoc");
			}else{
				this.setListWhere("location='"+location+"' and (type = '1' or type = '2') and assetlevel = 'ASSET'");
			}
		}else if("3".equals(type)){
			this.setListWhere("(type = '0' and assetlevel = 'ASSET') or (type='3' and location is null)");
		}else{
			this.setListWhere("1=2");
		}
		return super.getList();
	}
	
	@Override
	public void action() throws MroException {
		if(this.isValueChanged()){
			this.getField("newsqn").setValueNull(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getField("newsqn").clearError();
		}
	}
}
