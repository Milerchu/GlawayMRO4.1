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
 * 验证计划进度通知 (通报改造完成进度,每周五)18(2) 定时任务
 * 
 * @author zzx
 * @version [版本号, 2018-9-27]
 * @since [产品/模块版本]
 */
public class VailnewsConTask extends BaseStatefulJob {
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
				String plantype = transplanJpoSet.getJpo(i).getString(
						"PLANTYPE");// 计划类型
				String status = transplanJpoSet.getJpo(i).getString("STATUS");// 状态
				String projectnum = transplanJpoSet.getJpo(i).getString(
						"TRANSPRJNUM");// 项目编号
				String[] personcc = null;// 抄送人
				String bspersons = "";// 取办事处主任

				IJpoSet valiprorangeJpoSet = transplanJpoSet.getJpo(i)
						.getJpoSet("VALIPRORANGE");
				for (int j = 0; j < valiprorangeJpoSet.count(); j++) {
					// 取办事处主任
					IJpo range = valiprorangeJpoSet.getJpo(j);
					bspersons += ""
							+ WorkorderUtil.getOfficeDirectorByOfficenum(range
									.getString("office")) + ",";

					if (StringUtil.isStrNotEmpty(bspersons)) {
						bspersons = bspersons.substring(0,
								bspersons.length() - 1);
					}

					String abbreviation = valiprorangeJpoSet.getJpo(j)
							.getString("OFFICE.ABBREVIATION");// 办事处

					String station = valiprorangeJpoSet.getJpo(j).getString(
							"DEPT.DESCRIPTION");// 配属站段

					String valicount = valiprorangeJpoSet.getJpo(j).getString(
							"VALICOUNT");// 验证数量
					// 取验证工单jposet
					IJpoSet valiorderset = valiprorangeJpoSet.getJpo(j)
							.getJpoSet("VALIORDER");
					Vector<String> personall = new Vector();
					Set<String> personset = new HashSet<String>();
					Set<String> personnameset = new HashSet<String>();
					ArrayList<String> list = new ArrayList();

					if (valiorderset != null && valiorderset.count() > 0) {
						for (int index = 0; index < valiorderset.count(); index++) {
							// 取人员记录的jposet
							IJpoSet jxtaskexecpersonset = valiorderset.getJpo(
									index).getJpoSet("JXTASKEXECPERSON");
							for (int a = 0; a < jxtaskexecpersonset.count(); a++) {
								// personset.add(jxtaskexecpersonset.getJpo(a)
								// .getString("PERSONNUM"));// 任务接收人
								personnameset.add(jxtaskexecpersonset.getJpo(a)
										.getString("PERSON.DISPLAYNAME"));// 任务接收人姓名
							}

						}
						String bgsy = "进度通知";
						String message = "改造计划已下发，请尽快处理";
						String news = "相关信息";

						// 拼接表格
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
							if (!planeditor.isEmpty()) {
								// // personall.add(planeditor);//软件验证发起人（计划编制人）
								personset.add(planeditor);// 软件验证发起人（计划编制人）
							}

							if (!bspersons.isEmpty()) {
								personset.add(bspersons);// 办事处主任
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
