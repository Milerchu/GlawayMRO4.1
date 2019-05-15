package com.glaway.sddq.material.wdrline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
/**
 * 
 * <处置管理处置行绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-18]
 * @since  [产品/模块版本]
 */
public class WdrLineDataBean extends DataBean {

	public void wdrlineselect() throws MroException {
		// TODO Auto-generated method stub
		String personid = this.getAppBean().getJpo().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String createby=this.getAppBean().getJpo().getString("createby");
		String location = this.getAppBean().getJpo().getString("location");
		String status = this.getAppBean().getJpo().getString("status");
		if (!status.equalsIgnoreCase("草稿")&&!status.equalsIgnoreCase("驳回申请人")) {
			throw new MroException("该状态无法选择");
		} else {
			if(personid.equalsIgnoreCase(createby)){
				if (location.equalsIgnoreCase("")) {
					throw new MroException("请先选择处置库房");
				}
				IJpoSet docset=this.getAppBean().getJpo().getJpoSet("DOCLINKS");
				if(docset.isEmpty()){
					throw new MroException("请先上传附件处置凭证并根据处置凭证添加处置物料");
				}
			}else{
				throw new MroException("非创建人不能编辑处置物料信息");
			}
			
		}
	}
	/**
	 * 删除校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		String personid = this.getAppBean().getJpo().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String createby=this.getAppBean().getJpo().getString("createby");
		if(!personid.equalsIgnoreCase(createby)){
			throw new MroException("非创建人不能删除处置信息");
		}else{
			if (this.getAppBean().getJpo().getString("status").equals("处置完成")) {
				throw new MroException("该状态不能删除处置信息");
			} else {
				// TODO Auto-generated method stub
				return super.toggledeleterow();
			}

		}
	}
}
