package com.glaway.sddq.tools.ConTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.EwsEmail;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划即将到期预警 20(2) (改造/验证计划完成时间到期后 未提交完成所有工单)
 * 
 * @author zzx
 * @version [版本号, 2018-9-25]
 * @since [产品/模块版本]
 */
public class vailplantimeyjConTask extends BaseStatefulJob {
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		IJpoSet transplanJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSPLAN");
		transplanJpoSet.setUserWhere("status='执行中' and plantype='验证'");

		transplanJpoSet.reset();

		// 2.循环过滤数据，取出JPO
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
				String planeditor = transplanJpoSet.getJpo(i).getString(
						"PLANEDITOR");// 计划编制人

				String projectnum = transplanJpoSet.getJpo(i).getString(
						"TRANSPRJNUM");// 项目编号
				String[] personcc = null;// 抄送人
				String bspersons = "";// 取办事处主任

				long timetmp = (newdate.getTime() - plamtime.getTime())
						/ (24 * 60 * 60 * 1000);
				Vector<String> personall = new Vector();
				Set<String> personset = new HashSet<String>();
				Set<String> personnameset = new HashSet<String>();
				ArrayList<String> list = new ArrayList();
				boolean flag = false;
				String abbreviation = "";
				String station = "";
				String transcount = "";
				String valicount = "";
				if (timetmp > 0) {

					// 取验证计划关联的验证产品范围Jposet
					IJpoSet valiprorangeJpoSet = transplanJpoSet.getJpo(i)
							.getJpoSet("VALIPRORANGE");
					for (int j = 0; j < valiprorangeJpoSet.count(); j++) {
						// 取办事处主任
						IJpo range = valiprorangeJpoSet.getJpo(j);
						bspersons += ""
								+ WorkorderUtil
										.getOfficeDirectorByOfficenum(range
												.getString("office")) + ",";

						if (StringUtil.isStrNotEmpty(bspersons)) {
							bspersons = bspersons.substring(0,
									bspersons.length() - 1);
						}

						station = valiprorangeJpoSet.getJpo(j).getString(
								"DEPT.DESCRIPTION");// 配属站段

						valicount = valiprorangeJpoSet.getJpo(j).getString(
								"VALICOUNT");// 验证数量
						// 取验证工单jposet
						IJpoSet workorderset = valiprorangeJpoSet.getJpo(j)
								.getJpoSet("VALIORDER");
						if (workorderset != null && workorderset.count() > 0) {
							for (int index = 0; index < workorderset.count(); index++) {
								// 取改造工单中的状态值
								String status = workorderset.getJpo(index)
										.getString("STATUS");
								// 取人员记录的jposet
								IJpoSet jxtaskexecpersonset = workorderset
										.getJpo(index)
										.getJpoSet("JXTASKEXECPERSON");
								for (int a = 0; a < jxtaskexecpersonset.count(); a++) {

									// personset.add(jxtaskexecpersonset.getJpo(a)
									// .getString("PERSONNUM"));// 任务接收人
									personnameset.add(jxtaskexecpersonset
											.getJpo(a).getString(
													"PERSON.DISPLAYNAME"));
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

						String bgsy = "报警事由";
						String message = "验证计划完成时间已到期，请尽快处理";
						String news = "相关信息";
						StringBuilder sb = new StringBuilder();
						sb.append("<table border=\"1\">");
						sb.append("<tr>");
						sb.append("<th colspan=\"2\">" + bgsy);
						sb.append("</th>");
						sb.append("</tr>");
						sb.append("<tr>");
						sb.append("<td colspan=\"2\" align=\"center\"> "
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
						sb.append("<td>" + "验证数量：");
						sb.append("</td>");
						sb.append("<td>" + valicount);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "计划启动日期：");
						sb.append("</td>");
						sb.append("<td>" + startdatenew);
						sb.append("</td>");
						sb.append("</tr>");

						sb.append("<tr>");
						sb.append("<td>" + "计划完成日期：");
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
							// if (!bspersons.isEmpty()) {
							// personset.add(bspersons);// 办事处主任
							// }

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
