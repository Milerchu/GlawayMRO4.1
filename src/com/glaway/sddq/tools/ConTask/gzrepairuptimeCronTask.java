package com.glaway.sddq.tools.ConTask;

import java.util.Date;
import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.EwsEmail;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障品/改造品维修进度超期报警（故障品返修超过应修复日期未返回）（10）
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class gzrepairuptimeCronTask extends BaseStatefulJob {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();

		System.out.println("已经进入定时任务方法！！！");
		IJpoSet transferJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSFER");
		transferJpoSet.setUserWhere("STATUS !='已接收'and TYPE='SX'");
		transferJpoSet.reset();

		// 2.循环过滤数据，取出每条的数据的应修复日期
		if (transferJpoSet != null && transferJpoSet.count() > 0) {
			for (int i = 0; i < transferJpoSet.count(); i++) {
				// 送修单编号
				String transfernum = transferJpoSet.getJpo(i).getString(
						"TRANSFERNUM");
				// 取出应修复日期
				Date planrepaurdate = transferJpoSet.getJpo(i).getDate(
						"PLANREPAURDATE");
				// 项目编号
				String projectnum = transferJpoSet.getJpo(i).getString(
						"PROJECTNUM");

				// 取接收库房库管员（承修单位接收人取接收库房库管员）
				String keeper = transferJpoSet.getJpo(i).getString(
						"RECEIVESTOREROOM.KEEPER");

				// 取经办人（售后返修人员取经办人）
				String agentby = transferJpoSet.getJpo(i).getString("AGENTBY");

				// 取当前时间
				java.util.Date newdate = MroServer.getMroServer().getDate();

				if (planrepaurdate != null) {

					long timetmp = (newdate.getTime() - planrepaurdate
							.getTime()) / (24 * 60 * 60 * 1000);
					if (timetmp > 0) {
						// 缴库单jposet
						IJpoSet transferjkJpoSet = MroServer.getMroServer()
								.getSysJpoSet("TRANSFER");
						transferjkJpoSet.setUserWhere("SENDNUM='"
								+ transfernum + "'and TYPE='JKD'");

						transferjkJpoSet.reset();

						if (transferjkJpoSet.count() > 0) {
							// 送修单行的Jposet
							IJpoSet transferlinesxset = transferJpoSet
									.getJpo(i).getJpoSet("TRANSFERLINE");// 送修单行
							String sqn = "";
							String itemnum = "";
							for (int j = 0; j < transferlinesxset.count(); j++) {
								itemnum = transferlinesxset.getJpo(j)
										.getString("ITEMNUM");// 送修单物料编码

								sqn = transferlinesxset.getJpo(j).getString(
										"SQN");// 送修单 序列号

							}
							// 缴库单行Jposet
							if (!sqn.isEmpty()) {
								IJpoSet transferlinejkset = transferjkJpoSet
										.getJpo(i).getJpoSet("TRANSFERLINE");
								transferlinejkset.setUserWhere("ITEMNUM='"
										+ itemnum + "' and SQN='" + sqn + "'");
								transferlinejkset.reset();
								if (transferlinejkset.count() == 0) {
									String bgsy = "故障品维修进度超期报警";
									String result = "送修单编号编号为" + transfernum
											+ " 的故障品返修超过应修复日期未返回";
									String[] object = null;
									String[] personcc = null;

									if (!projectnum.isEmpty()) {
										Vector<String> personall = new Vector();
										Vector<String> personVector = WorkorderUtil
												.getProjectRole(projectnum,
														"售后项目经理");
										for (int index = 0; index < personVector
												.size(); index++) {
											personall.add(personVector
													.get(index));
										}

										if (!keeper.isEmpty()) {
											personall.add(keeper);// 接收库房库管员（承修单位接收人取接收库房库管员）
										}
										if (!agentby.isEmpty()) {
											personall.add(agentby);// 经办人（售后返修人员取经办人）
										}
										object = personall
												.toArray(new String[personall
														.size()]);
									}

									try {
										EwsEmail.sends(bgsy, result, object,
												personcc);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						} else {
							String bgsy = "故障品维修进度超期报警";
							String result = "送修单编号编号为" + transfernum
									+ " 的故障品返修超过应修复日期未返回";
							String[] object = null;
							String[] personcc = null;

							if (!projectnum.isEmpty()) {
								Vector<String> personall = new Vector();
								Vector<String> personVector = WorkorderUtil
										.getProjectRole(projectnum, "售后项目经理");
								for (int index = 0; index < personVector.size(); index++) {
									personall.add(personVector.get(index));
								}

								if (!keeper.isEmpty()) {
									personall.add(keeper);// 接收库房库管员（承修单位接收人取接收库房库管员）
								}
								if (!agentby.isEmpty()) {
									personall.add(agentby);// 经办人（售后返修人员取经办人）
								}
								object = personall.toArray(new String[personall
										.size()]);
							}

							try {
								EwsEmail.sends(bgsy, result, object, personcc);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
