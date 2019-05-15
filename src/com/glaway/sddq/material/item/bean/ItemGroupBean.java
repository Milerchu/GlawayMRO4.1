package com.glaway.sddq.material.item.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
/**
 * 
 * <物料管理物料组管理窗口绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-7]
 * @since  [产品/模块版本]
 */
public class ItemGroupBean extends DataBean {

	@Override
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		IJpoSet itemgroup = this.page.getAppBean()
				.getDataBean("itemgroup_table_1").getJpoSet();
		itemgroup.save();
		return super.dialogok();
	}

}
