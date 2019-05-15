package com.glaway.sddq.service.transnotice.workflow.action;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单-TNNOTICEDIRECTOR 操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月10日]
 * @since [产品/模块版本]
 */
public class TNNoticeDirector implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo curjpo, String arg1)
			throws MroException {
		// 改造通知单编号
		String noticenum = curjpo.getString("TRANSNOTICENUM");
		List<String> directors = new ArrayList<String>();
		// 改造范围子表
		IJpoSet rangeSet = curjpo.getJpoSet("TRANSRANGE");
		if (rangeSet != null && rangeSet.count() > 0) {
			for (int index = 0; index < rangeSet.count(); index++) {
				IJpo range = rangeSet.getJpo(index);
				String deptnum = range.getString("IMPDEPT");
				directors.addAll(WorkorderUtil
						.getOfficeDirectorByStation(deptnum));// 添加所有产品范围的办事处主任
			}
		}

		// 发送mro消息通知
		String[] receivers = directors.toArray(new String[directors.size()]);
		WorkorderUtil.sendMsg("TRNSNOTICE", curjpo.getId(), receivers,
				"您有一个改造通知单正在审核", noticenum + "改造通知单正在审核，请注意查收！");

	}
}
