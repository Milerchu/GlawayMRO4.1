package com.glaway.sddq.tools.ConTask;

import java.text.SimpleDateFormat;
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
 * 改造计划物料发料报警定时任务（15）
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class TransfersendbjConTask extends BaseStatefulJob {
	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		IJpoSet transplanJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSPLAN");
		transplanJpoSet.setUserWhere("STATUS='执行中'and plantype='改造'");
		transplanJpoSet.reset();

		// 2.循环过滤数据，取出JPO
		if (transplanJpoSet != null && transplanJpoSet.count() > 0) {
			for (int i = 0; i < transplanJpoSet.count(); i++) {

				String status = transplanJpoSet.getJpo(i).getString("STATUS");// 状态
				String plantype = transplanJpoSet.getJpo(i).getString(
						"PLANTYPE");// 计划类型
				String transplannum = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNUM");// 计划编号
				String transplanname = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNAME");// 计划名称
				String transworkordernum = transplanJpoSet.getJpo(i).getString(
						"TRANSNOTICE.TRANSWORKORDERNUM");// 工作令号
				String projectnum = "";

				// 取改造车辆分布Jposet
				IJpoSet transdistJpoSet = transplanJpoSet.getJpo(i).getJpoSet(
						"TRANSDIST");
				for (int index = 0; index < transdistJpoSet.count(); index++) {
					projectnum = transdistJpoSet.getJpo(index).getString(
							"PROJECTNUM");// 所属项目
				}

				// 取物料计划jposet

				IJpoSet transmaterialplanSet = transplanJpoSet.getJpo(i)
						.getJpoSet("TRANSMATERIALPLAN");

				if (transmaterialplanSet != null
						&& transmaterialplanSet.count() > 0) {
					String productcode = "";
					String productcodename = "";
					String materialcount = "";
					String plansendtimenew = "";
					Date plansendtime = null;

					for (int j = 0; j < transmaterialplanSet.count(); j++) {
						productcode = transmaterialplanSet.getJpo(j).getString(
								"PRODUCTCODE");// 物料编码
						productcodename = transmaterialplanSet.getJpo(j)
								.getString("ITEM.DESCRIPTION");// 物料编码名称
						materialcount = transmaterialplanSet.getJpo(j)
								.getString("MATERIALCOUNT");// 数量

						// 取改造计划物料分布表Jposet
						IJpoSet transplanlocset = transmaterialplanSet
								.getJpo(j).getJpoSet("TRANSPLANLOC");
						if (transplanlocset != null
								&& transplanlocset.count() > 0) {
							for (int a = 0; a < transplanlocset.count(); a++) {
								// 取出计划发出日期
								plansendtimenew = "";
								plansendtime = transplanlocset.getJpo(a)
										.getDate("PLANSENDTIME");
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"yyyy-MM-dd");
								if (plansendtime != null) {
									plansendtimenew = dateFormat
											.format(plansendtime);
								}

							}
						}

					}
					// 取当前是时间
					java.util.Date newdate = MroServer.getMroServer().getDate();
					long timetmp = (plansendtime.getTime() - newdate.getTime())
							/ (24 * 60 * 60 * 1000);

					if (plansendtime != null) {
						if (timetmp <= 0) {

							IJpoSet transferJpoSet = MroServer.getMroServer()
									.getSysJpoSet("TRANSFER");
							transferJpoSet.setUserWhere("TRANSWORKORDERNUM='"
									+ transworkordernum + "' and TYPE='GZZXD'");

							transferJpoSet.reset();
							if (transferJpoSet.count() == 0) {
								// 拼接表格
								System.out.println("成功");
								String bgsy = "发货预警";
								String message = "改造物料临近发出";
								String news = "相关信息";
								String[] personcc = null;
								StringBuilder sb = new StringBuilder();
								sb.append("<table border=\"1\">");
								sb.append("<tr>");
								sb.append("<th colspan=\"2\">" + bgsy);
								sb.append("</th>");
								sb.append("</tr>");
								sb.append("<tr>");
								sb.append("<td colspan=\"2\" align=\"center\">"
										+ message);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<th colspan=\"2\">" + news);
								sb.append("</th>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "计划编号：");
								sb.append("</td>");
								sb.append("<td>" + transplannum);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "计划名称：");
								sb.append("</td>");
								sb.append("<td>" + transplanname);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "物料编码：");
								sb.append("</td>");
								sb.append("<td>" + productcode);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "物料名称：");
								sb.append("</td>");
								sb.append("<td>" + productcodename);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "物料数量：");
								sb.append("</td>");
								sb.append("<td>" + materialcount);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "发料时间：");
								sb.append("</td>");
								sb.append("<td>" + plansendtimenew);
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("<tr>");
								sb.append("<td>" + "计划链接：");
								sb.append("</td>");
								sb.append("<td>"
										+ "<a href=\"http://mro.csrzic.com/gwmro\">登陆系统</a>");
								sb.append("</td>");
								sb.append("</tr>");

								sb.append("</table>");
								String result = sb.toString();
								if (!projectnum.isEmpty()) {
									Vector<String> personall = new Vector();
									Vector<String> personVector = WorkorderUtil
											.getProjectRole(projectnum,
													"售后技术经理");
									for (int index = 0; index < personVector
											.size(); index++) {
										personall.add(personVector.get(index));
									}

									Vector<String> personxmVector = WorkorderUtil
											.getProjectRole(projectnum,
													"售后计划经理");
									for (int index = 0; index < personxmVector
											.size(); index++) {
										personall
												.add(personxmVector.get(index));
									}
									String[] object = personall
											.toArray(new String[personall
													.size()]);

									try {
										System.out.println("成功");
										EwsEmail.sends(bgsy, result, object,
												personcc);

									} catch (Exception e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
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
