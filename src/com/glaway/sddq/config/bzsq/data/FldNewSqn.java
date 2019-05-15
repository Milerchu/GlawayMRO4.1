package com.glaway.sddq.config.bzsq.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

public class FldNewSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		if(StringUtil.isNullOrEmpty(this.getJpo().getString("sqn"))){
            throw new AppException("pzbg", "selectsqn");
		}
		// 当填写的变更后产品序列号在其他车上或者库存中存在，则提示信息中显示类似信息：XXX车型XXX车已存在产品序列号为XXX的产品 或者
		// 在XXX库房中已存在产品序列号为XXX的产品。
		String newsqn = this.getValue();
		if(!StringUtil.isNullOrEmpty(newsqn)){
			String assetnum = this.getJpo().getString("assetnum");
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
					"sqn='" + newsqn + "' and assetnum != '"+assetnum+"'");
			if (jposet != null && jposet.count() > 0) {
				StringBuffer errorMsg = new StringBuffer();
				for (int index = 0; index < jposet.count(); index++) {
					IJpo jpo = jposet.getJpo(index);
					if("ASSET".equals(jpo.getString("ASSETLEVEL"))){
						if("0".equals(jpo.getString("type"))){
							errorMsg.append("变更后的产品序列号在初始配置中已存在\n");
						}else if("1".equals(jpo.getString("type"))){
							errorMsg.append("变更后的产品序列号在库房"+jpo.getString("location")+"中已存在\n");
						}else if("3".equals(jpo.getString("type"))){
							if(StringUtil.isNullOrEmpty(jpo.getString("location"))){
								errorMsg.append("变更后的产品序列号在车下配置中已存在\n");
							}else{
								errorMsg.append("变更后的产品序列号在库房"+jpo.getString("location")+"中已存在\n");
							}
							//errorMsg.append("变更后的产品序列号在库房"+jpo.getString("location")+"中已存在\n");
						}
					}else if("SYSTEM".equals(jpo.getString("ASSETLEVEL"))){
						String ancestor = jpo.getString("ancestor");
						IJpoSet ancestorJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET","assetnum='"+ancestor+"'");
						if(ancestorJpoSet != null && ancestorJpoSet.count() > 0){
							IJpo ancestorJpo = ancestorJpoSet.getJpo(0);
							if(ancestorJpo != null){
								if("0".equals(jpo.getString("type"))){
									errorMsg.append("变更后的产品序列号在产品"+ancestorJpo.getString("sqn")+"的子级节点中已存在\n");
								}else if("1".equals(jpo.getString("type")) || "3".equals(jpo.getString("type"))){
									errorMsg.append("变更后的产品序列号在库房"+ancestorJpo.getString("location")+"中的产品"+ancestorJpo.getString("sqn")+"的子级节点中已存在\n");
								}else{
									errorMsg.append("变更后的产品序列号在车号为"+ancestorJpo.getString("carno")+"，车型为"+ancestorJpo.getString("cmodel")+" 的车上已存在\n");
								}
							}
						}
					}
				}
				this.getField("ERRORMSG").setValue(errorMsg.toString());
			}
		}
	}
}
