/*
 * 文 件 名:  ItemAppBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.material.item.bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.session.MroSession;
import com.glaway.mro.util.GWConstant;

/**
 * <物料管理应用程序APPBEAN>
 * 
 * @author yyang
 * @version [版本号, 2016-4-25]
 * @since [产品/模块版本]
 */
public class ItemAppBean extends AppBean {
	protected MroSession mroSession = null; // 会话

	/**
	 * 
	 * 清除分类
	 * 
	 * @return [参数说明]
	 */
	public int clearClassStructure() {
		return 1;
	}

	/**
	 * @param currTab
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		super.afterTabChange(currTab);
	}

	/**
	 * @param attribute
	 * @throws MroException
	 */
	@Override
	protected void afterSetValue(String attribute) throws MroException {
		if (attribute.equalsIgnoreCase("CLASSNUM")) {
			DataBean itemspectable = getDataBean("specification_itemspectable");
			if (itemspectable != null) {
				try {
					itemspectable.reloadSelfAndSubs();
				} catch (IOException e) {
					e.printStackTrace();
					throw new MroException(e.getMessage());
				}
			}
		}
		super.afterSetValue(attribute);

	}
}
