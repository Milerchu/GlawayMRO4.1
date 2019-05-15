package com.glaway.sddq.material.toolnsp.bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <工具送检待校检Databean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ToolCheckDataBean extends DataBean {
	/**
	 * 编辑确认按钮方法
	 * 
	 * @throws MroException
	 * @throws IOException
	 */
	public void addEditRowCallBackOk() throws MroException, IOException {
		super.addEditRowCallBackOk();

		String itemnum = this.getJpo().getString("ITEMNUM");

		IJpoSet deviceinfoSet = MroServer.getMroServer().getJpoSet(
				"DEVICEINFO", MroServer.getMroServer().getSystemUserServer());
		deviceinfoSet.setUserWhere("ITEMNUM='" + itemnum + "'");
		deviceinfoSet.reset();
		int limit = deviceinfoSet.getJpo(0).getInt("LIMIT");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String actcchcekdate = this.getJpo().getString("ACTCHECKDATE");

		Date date = null;
		Date dt1 = null;
		try {
			date = sdf.parse(actcchcekdate);
			// zzx add
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			rightNow.add(Calendar.YEAR, limit);
			dt1 = rightNow.getTime();
			// zzx end

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int j = 0; j < deviceinfoSet.count(); j++) {
			IJpo devic = deviceinfoSet.getJpo(j);
			devic.setValue("CHECKDATE", dt1);
			deviceinfoSet.getJpo(j).setValue("IDENTIFY", 0);
		}

		deviceinfoSet.save();

	}

	/**
	 * 删除方法
	 * 
	 * @throws MroException
	 */
	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		IJpo parent2 = this.getJpo().getParent();
		String createby = parent2.getString("CREATEBY");
		if (("送检中").equals(parent2.getString("STATUS"))
				&& createby.equalsIgnoreCase(this.getUserInfo().getUserName())) {
			throw new AppException("提示", "当前记录不可删除!!!");
		} else {
			super.delete();
		}

	}
}
