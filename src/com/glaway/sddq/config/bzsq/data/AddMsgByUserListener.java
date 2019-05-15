package com.glaway.sddq.config.bzsq.data;

import io.netty.util.internal.StringUtil;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.MsgUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;
/**
 * 
 * 发送异常信息到指定的人员
 * 
 * @author  public2175
 * @version  [版本号, 2018-12-24]
 * @since  [产品/模块版本]
 */
public class AddMsgByUserListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		if (curJpo != null) {
			String newsqn = curJpo.getString("NEWSQN");
			if (!StringUtil.isNullOrEmpty(newsqn)) {
				String assetnum = curJpo.getString("assetnum");
				IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
						"ASSET",
						"sqn='" + newsqn + "' and assetnum != '" + assetnum
								+ "'");
				if (jposet != null && jposet.count() > 0) {
					for (int index = 0; index < jposet.count(); index++) {
						IJpo jpo = jposet.getJpo(index);
						if ("ASSET".equals(jpo.getString("ASSETLEVEL"))) {
							if ("0".equals(jpo.getString("type"))) {
								String errorMsg = "变更后的产品序列号在初始配置中已存在,"
										+ "物料编码为：" + jpo.getString("itemnum")
										+ "\n";
								String persons = WorkorderUtil
										.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
								MsgUtil.addMsgByPersons("PZBGASK",
										curJpo.getId(), SddqConstant.PZBG_BT,
										errorMsg.toString(), persons);
							} else if ("1".equals(jpo.getString("type"))) {
								if(StringUtil.isNullOrEmpty(jpo.getString("location"))){
									String errorMsg = "变更后的产品序列号在初始配置中已存在,"
											+ "物料编码为：" + jpo.getString("itemnum")
											+ "\n";
									String persons = WorkorderUtil
											.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
									MsgUtil.addMsgByPersons("PZBGASK",
											curJpo.getId(), SddqConstant.PZBG_BT,
											errorMsg.toString(), persons);
								}else{
									String errorMsg = "变更后的产品序列号在库房"
											+ jpo.getString("location") + "中已存在,"
											+ "物料编码为：" + jpo.getString("itemnum")
											+ "\n";
									String location = jpo.getString("location");//通过库房编号去找办事处
									String deptnum = WorkorderUtil.getDeptnumByLoc(location);
									String persons = WorkorderUtil.getPersonByPost(deptnum, SddqConstant.PZBG_JS);
									MsgUtil.addMsgByPersons("PZBGASK",
											curJpo.getId(), SddqConstant.PZBG_BT,
											errorMsg.toString(), persons);
								}
							} else if ("3".equals(jpo.getString("type"))) {
								if (StringUtil.isNullOrEmpty(jpo
										.getString("location"))) {
									String errorMsg = "变更后的产品序列号在车下配置中已存在,"
											+ "物料编码为："
											+ jpo.getString("itemnum") + "\n";
									String persons = WorkorderUtil
											.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
									MsgUtil.addMsgByPersons("PZBGASK",
											curJpo.getId(), SddqConstant.PZBG_BT,
											errorMsg.toString(), persons);
								} else {
									String errorMsg = "变更后的产品序列号在库房"
											+ jpo.getString("location")
											+ "中已存在," + "物料编码为："
											+ jpo.getString("itemnum") + "\n";
									String location = jpo.getString("location");//通过库房编号去找办事处
									String deptnum = WorkorderUtil.getDeptnumByLoc(location);
									String persons = WorkorderUtil.getPersonByPost(deptnum, SddqConstant.PZBG_JS);
									MsgUtil.addMsgByPersons("PZBGASK",
											curJpo.getId(), SddqConstant.PZBG_BT,
											errorMsg.toString(), persons);
								}
							}
						} else if ("SYSTEM".equals(jpo.getString("ASSETLEVEL"))) {
							String ancestor = jpo.getString("ancestor");
							IJpoSet ancestorJpoSet = MroServer.getMroServer()
									.getSysJpoSet("ASSET",
											"assetnum='" + ancestor + "'");
							if (ancestorJpoSet != null
									&& ancestorJpoSet.count() > 0) {
								IJpo ancestorJpo = ancestorJpoSet.getJpo(0);
								if (ancestorJpo != null) {
									if ("0".equals(jpo.getString("type"))) {
										String	errorMsg = "变更后的产品序列号在产品"
												+ ancestorJpo.getString("sqn")
												+ "的子级节点中已存在," + "物料编码为："
												+ jpo.getString("itemnum")
												+ "\n";
										String persons = WorkorderUtil
												.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
										MsgUtil.addMsgByPersons("PZBGASK",
												curJpo.getId(), SddqConstant.PZBG_BT,
												errorMsg.toString(), persons);
										
									} else if ("1"
											.equals(jpo.getString("type"))) {
										if (StringUtil
												.isNullOrEmpty(ancestorJpo
														.getString("location"))) {
											String errorMsg = "变更后的产品序列号在初始配置中的产品"
													+ ancestorJpo
															.getString("sqn")
													+ "的子级节点中已存在,"
													+ "物料编码为："
													+ jpo.getString("itemnum")
													+ "\n";
											String persons = WorkorderUtil
													.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
											MsgUtil.addMsgByPersons("PZBGASK",
													curJpo.getId(), SddqConstant.PZBG_BT,
													errorMsg.toString(), persons);
											
										} else {
											String errorMsg = "变更后的产品序列号在库房"
													+ ancestorJpo
															.getString("location")
													+ "中的产品"
													+ ancestorJpo
															.getString("sqn")
													+ "的子级节点中已存在," + "物料编码为："
													+ jpo.getString("itemnum")
													+ "\n";
											
											String location = jpo.getString("location");//通过库房编号去找办事处
											String deptnum = WorkorderUtil.getDeptnumByLoc(location);
											String persons = WorkorderUtil.getPersonByPost(deptnum, SddqConstant.PZBG_JS);
											MsgUtil.addMsgByPersons("PZBGASK",
													curJpo.getId(), SddqConstant.PZBG_BT,
													errorMsg.toString(), persons);
											
										}
									} else if ("3"
											.equals(jpo.getString("type"))) {
										if (StringUtil
												.isNullOrEmpty(ancestorJpo
														.getString("location"))) {
											String errorMsg = "变更后的产品序列号在车下配置中的产品"
													+ ancestorJpo
															.getString("sqn")
													+ "的子级节点中已存在,"
													+ "物料编码为："
													+ jpo.getString("itemnum")
													+ "\n";
											
											String persons = WorkorderUtil
													.getPersonsFromPersonGroup(SddqConstant.PZBG_PZGLY);
											MsgUtil.addMsgByPersons("PZBGASK",
													curJpo.getId(), SddqConstant.PZBG_BT,
													errorMsg.toString(), persons);
											
										} else {
											String errorMsg = "变更后的产品序列号在库房"
													+ ancestorJpo
															.getString("location")
													+ "中的产品"
													+ ancestorJpo
															.getString("sqn")
													+ "的子级节点中已存在," + "物料编码为："
													+ jpo.getString("itemnum")
													+ "\n";
											String location = jpo.getString("location");//通过库房编号去找办事处
											String deptnum = WorkorderUtil.getDeptnumByLoc(location);
											String persons = WorkorderUtil.getPersonByPost(deptnum, SddqConstant.PZBG_JS);
											MsgUtil.addMsgByPersons("PZBGASK",
													curJpo.getId(), SddqConstant.PZBG_BT,
													errorMsg.toString(), persons);
										}
									} else {
										String errorMsg = "变更后的产品序列号在车号为"
												+ ancestorJpo
														.getString("carno")
												+ "，车型为"
												+ ancestorJpo
														.getString("cmodel")
												+ " 的车上已存在," + "物料编码为："
												+ jpo.getString("itemnum")
												+ "\n";
										String deptnum = ancestorJpo.getString("OWNERCUSTOMER.WHICHOFFICE");
										if(!StringUtil.isNullOrEmpty(deptnum)){
											String  persons = WorkorderUtil.getPersonByPost(deptnum, SddqConstant.PZBG_JS);
											MsgUtil.addMsgByPersons("PZBGASK",
													curJpo.getId(), SddqConstant.PZBG_BT,
													errorMsg.toString(), persons);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
