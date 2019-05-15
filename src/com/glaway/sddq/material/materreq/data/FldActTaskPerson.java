package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请当前任务节点人员字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldActTaskPerson extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 2064297619076845120L;

	/**
	 * 获取当前任务节点人
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		// 当前执行人的字符串
		String mrid = getJpo().getString("mrid");
		String where = "proc_inst_id_ in (select procinstid from ACT_APP_INFO where businesskey='"
				+ mrid + "' and tablename='MR')";
		IJpoSet taskSet = MroServer.getMroServer().getSysJpoSet("act_ru_task",
				where);
		if (taskSet != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < taskSet.count(); i++) {
				sb.append(taskSet.getJpo(i).getString("PERSON.DISPLAYNAME")
						+ ",");
			}
			if (sb.length() > 0) {
				String names = sb.toString().substring(0, sb.length() - 1);
				setValue(names, GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}
}
