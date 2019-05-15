package com.glaway.sddq.tools.ConTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.EwsEmail;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 改造计划即将到期预警 20 (改造计划完成时间到期后 未提交完成所有工单) 定时任务
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class transorvailpltimeyjConTask extends BaseStatefulJob {
	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		IJpoSet transplanJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSPLAN");
		transplanJpoSet.setUserWhere("status='执行中' and plantype='改造'");

		transplanJpoSet.reset();
		
		if (transplanJpoSet != null && transplanJpoSet.count() > 0) {
			for (int i = 0; i < transplanJpoSet.count(); i++) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date plamtime = transplanJpoSet.getJpo(i).getDate("ENDDATE");// 取出计划完成日期
				// 取当前是时间
				java.util.Date newdate = MroServer.getMroServer().getDate();
				String plamtimenew = "";
				String startdatenew = "";
				if (plamtime != null) {
					plamtimenew = dateFormat.format(plamtime);
				}

				Date startdate = transplanJpoSet.getJpo(i).getDate("STARTDATE");// 取出计划启动日期
				// SimpleDateFormat dateFormat1 = new SimpleDateFormat(
				// "yyyy-MM-dd");
				if (startdate != null) {
					startdatenew = dateFormat.format(startdate);

				}

				String transplannum = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNUM");// 计划编号
				String transplanname = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNAME");// 计划名称

				String projectnum = "";
				long timetmp = (newdate.getTime() - plamtime.getTime())
						/ (24 * 60 * 60 * 1000);
				boolean flag = false;
				if (timetmp > 0) {
					// 取改造车辆分布Jposet
					IJpoSet transdistJpoSet = transplanJpoSet.getJpo(i)
							.getJpoSet("TRANSDIST");
					String abbreviation = "";
					String station = "";
					String transcount = "";
					String surplus = "";
					String responsible = "";
					Vector<String> personall = new Vector();
					Set<String> personset = new HashSet<String>();
					Set<String> personnameset = new HashSet<String>();
					ArrayList<String> list = new ArrayList();
					for (int index = 0; index < transdistJpoSet.count(); index++) {
						projectnum = transdistJpoSet.getJpo(index).getString(
								"PROJECTNUM");// 所属项目
						abbreviation = transdistJpoSet.getJpo(index).getString(
								"SYS_DEPT.ABBREVIATION");// 办事处

						station = transdistJpoSet.getJpo(index).getString(
								"STATION.DESCRIPTION");// 配属站段

						transcount = transdistJpoSet.getJpo(index).getString(
								"TRANSCOUNT");// 改造数量

						surplus = transdistJpoSet.getJpo(index).getString(
								"SURPLUS");// 剩余数量

						responsible = transdistJpoSet.getJpo(index).getString(
								"RESPONSIBLE");// 办事处主任

						// 取改造工单jposet
						IJpoSet workorderset = transdistJpoSet.getJpo(index)
								.getJpoSet("TRANSORDER");


						if (workorderset != null && workorderset.count() > 0) {
							for (int j = 0; j < workorderset.count(); j++) {
								// 取改造工单中的状态值
								String status = workorderset.getJpo(j)
										.getString("STATUS");
								// 取人员记录的jposet
								IJpoSet jxtaskexecpersonset = workorderset
										.getJpo(j)
										.getJpoSet("JXTASKEXECPERSON");
								for (int a = 0; a < jxtaskexecpersonset.count(); a++) {

									personset.add(jxtaskexecpersonset.getJpo(a)
											.getString("PERSONNUM"));// 任务接收人
									personnameset.add(jxtaskexecpersonset
											.getJpo(a)
											.getString("PERSON.DISPLAYNAME"));
									// 任务接收人姓名
								}

								if (!status.equals("关闭")) {
									flag = true;
								}

							}

						}
					}
					if (flag) {
						// 拼接表格
						String bgsy = "进度通知";
						String message = "改造计划已下发，请尽快处理";
						String news = "相关信息";
						String[] personcc = null;// 抄送人
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
						sb.append("<td>" + "办事处：");
						sb.append("</td>");
						sb.append("<td>" + abbreviation);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "配属站段：");
						sb.append("</td>");
						sb.append("<td>" + station);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "改造数量：");
						sb.append("</td>");
						sb.append("<td>" + transcount);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "剩余数量：");
						sb.append("</td>");
						sb.append("<td>" + surplus);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "计划启动时间：");
						sb.append("</td>");
						sb.append("<td>" + startdatenew);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "计划完成时间：");
						sb.append("</td>");
						sb.append("<td>" + plamtimenew);
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

							Vector<String> personVector = WorkorderUtil
									.getProjectRole(projectnum, "售后技术经理");

							for (int t = 0; t < personVector.size(); t++) {

								personset.add(personVector.get(t));
								// personall.add(personVector.get(t));
							}

							Vector<String> personxmVector = WorkorderUtil
									.getProjectRole(projectnum, "售后项目经理");
							for (int b = 0; b < personxmVector.size(); b++) {
								personset.add(personxmVector.get(b));
								// personall.add(personxmVector.get(b));
							}

							Vector<String> personzlVector = WorkorderUtil
									.getProjectRole(projectnum, "售后质量经理");
							for (int c = 0; c < personzlVector.size(); c++) {
								personset.add(personzlVector.get(c));
								// personall.add(personzlVector.get(c));
							}
							if (!responsible.isEmpty()) {
								// personall.add(responsible);// 办事处主任
								personset.add(responsible);// 办事处主任
							}

							String[] object = personset
									.toArray(new String[personset.size()]);

							try {
								System.out.println("成功");
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
