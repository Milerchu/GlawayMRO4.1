package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 改造计划 改造车型字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class FldTransmodels extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4358269467158169222L;

	@Override
	public IJpoSet getList() throws MroException {

		// 当前改造通知单/验证申请单编号
		String transnoticenum = getJpo().getString("transnoticenum");
		String appName = getJpo().getAppName();

		if ("TRANSPLAN".equalsIgnoreCase(appName)) {// 改造计划
			if(getJpo().getJpoSet("TRANSDIST").count()>0){
				throw new MroException("该改造计划已存在改造车辆，无法修改车型！");
			}
			IJpoSet trnsntcSet = MroServer.getMroServer().getJpoSet(
					"TRANSNOTICE", getJpo().getUserServer());
			trnsntcSet.setUserWhere("transnoticenum='" + transnoticenum + "'");
			trnsntcSet.reset();
			if (!trnsntcSet.isEmpty()) {
				IJpo transnotice = trnsntcSet.getJpo();
				// 改造范围表
				IJpoSet transrangeSet = transnotice.getJpoSet("TRANSRANGE");
				if (!transrangeSet.isEmpty()) {
					String models = "";
					for (int index = 0; index < transrangeSet.count(); index++) {
						IJpo trange = transrangeSet.getJpo(index);
						models += "'" + trange.getString("TRANSMODELS") + "',";
					}
					models = models.substring(0, models.length() - 1);
					// 根据改造通知单过滤可选车型
					setListWhere("modelnum in(" + models + ")");
				}
			} else {
				setListWhere("1=2");
			}
		} else {// 验证计划
			IJpoSet trnsntcSet = MroServer.getMroServer().getJpoSet(
					"VALIREQUEST", getJpo().getUserServer());
			trnsntcSet.setUserWhere("VALIREQUESTNUM='" + transnoticenum + "'");
			trnsntcSet.reset();
			if (!trnsntcSet.isEmpty()) {
				IJpo transnotice = trnsntcSet.getJpo();
				// 验证范围表
				IJpoSet transrangeSet = transnotice.getJpoSet("VALIPRORANGE");
				if (!transrangeSet.isEmpty()) {
					String models = "";
					for (int index = 0; index < transrangeSet.count(); index++) {
						IJpo trange = transrangeSet.getJpo(index);
						models += "'" + trange.getString("TRANSMODELS") + "',";
					}
					models = models.substring(0, models.length() - 1);
					// 根据改造通知单过滤可选车型
					setListWhere("modelnum in(" + models + ")");
				}
			} else {
				setListWhere("1=2");
			}
		}

		return super.getList();
	}
}
