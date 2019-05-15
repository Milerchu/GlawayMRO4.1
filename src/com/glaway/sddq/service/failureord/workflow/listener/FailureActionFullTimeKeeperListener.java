package com.glaway.sddq.service.failureord.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 库管员审核节点监听定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年11月12日]
 * @since [产品/模块版本]
 */
public class FailureActionFullTimeKeeperListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}

		if (curJpo != null) {

			String whichoffice = curJpo.getString("whichoffice");// 所属办事处

			String fullTimeKeeperGrp = "";// 专职库管员人员组

			if ("01061500".equals(whichoffice)) {// 兰州办事处

				fullTimeKeeperGrp = "LZZZKGY";

			} else if ("01061800".equals(whichoffice)) {// 株洲办事处

				fullTimeKeeperGrp = "ZZZZKGY";

			} else if ("01061900".equals(whichoffice)) {// 广州办事处

				fullTimeKeeperGrp = "GZZZKGY";

			} else if ("01062100".equals(whichoffice)) {// 沈阳办事处

				fullTimeKeeperGrp = "SYZZKGY";

			} else if ("01062400".equals(whichoffice)) {// 青岛检修分公司

				fullTimeKeeperGrp = "QDZZKGY";

			} else if ("01062700".equals(whichoffice)) {// 美洲办事处

				fullTimeKeeperGrp = "MZZZKGY";

			} else if ("01062500".equals(whichoffice)) {// 东南亚办事处

				fullTimeKeeperGrp = "DNYZZKGY";

			} else if ("01061600".equals(whichoffice)) {// 西安办事处

				fullTimeKeeperGrp = "XAZZKGY";

			} else if ("01061700".equals(whichoffice)) {// 重庆办事处

				fullTimeKeeperGrp = "CQZZKGY";

			} else if ("01062000".equals(whichoffice)) {// 上海办事处

				fullTimeKeeperGrp = "SHZZKGY";

			} else if ("01062300".equals(whichoffice)) {// 武汉办事处

				fullTimeKeeperGrp = "WHZZKGY";

			} else if ("01062600".equals(whichoffice)) {// 非洲办事处

				fullTimeKeeperGrp = "FZZZKGY";

			} else if ("01061400".equals(whichoffice)) {// 北京办事处

				fullTimeKeeperGrp = "BJZZKGY";

			}

			String persons = WorkorderUtil
					.getPersonsFromPersonGroup(fullTimeKeeperGrp);// 人员组人员
			if ("1".equals(execution.getVariable("approved"))) {// 审批通过

				if (StringUtil.isStrEmpty(persons)) {// 无专职库管员，则跳过该审核

					execution.setVariable("approved", "3");

				}
			}
		}

	}

}
