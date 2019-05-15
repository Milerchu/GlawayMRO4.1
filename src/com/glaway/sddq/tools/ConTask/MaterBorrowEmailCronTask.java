package com.glaway.sddq.tools.ConTask;

import java.util.Date;
import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.EwsEmail;
/**
 * 
 * <配件借用发送邮件定时任务类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-27]
 * @since  [产品/模块版本]
 */
public class MaterBorrowEmailCronTask extends BaseStatefulJob {

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		//获取应该发送邮件的配件借用数据集合
		IJpoSet materborrowJpoSet = MroServer.getMroServer().getSysJpoSet(
				"MATERBORROW");
		materborrowJpoSet.setUserWhere("STATUS in ('已借出','部分归还','借用超期') and sendemail!='2'");
		materborrowJpoSet.reset();
		if (materborrowJpoSet != null && materborrowJpoSet.count() > 0) {
			for (int i = 0; i < materborrowJpoSet.count(); i++) {
				//配件借用编号
				String MBNUM = materborrowJpoSet.getJpo(i).getString(
						"MBNUM");
				//配件借用创建人
				String CREATEBY = materborrowJpoSet.getJpo(i).getString(
						"CREATEBY");
				CREATEBY=CREATEBY.toUpperCase();
				//配件借用借用人
				String BORROWBY = materborrowJpoSet.getJpo(i).getString(
						"BORROWBY");
				BORROWBY=BORROWBY.toUpperCase();
				//配件借用借方部门负责人
				String BORROWAPPR = materborrowJpoSet.getJpo(i).getString(
						"BORROWAPPR");
				BORROWAPPR=BORROWAPPR.toUpperCase();
				//配件借用借方单元负责人
				String BORROWCHECK = materborrowJpoSet.getJpo(i).getString(
						"BORROWCHECK");
				BORROWCHECK=BORROWCHECK.toUpperCase();
				//配件借用借出方责任人
				String SEVICEAPPR = materborrowJpoSet.getJpo(i).getString(
						"SEVICEAPPR");
				SEVICEAPPR=SEVICEAPPR.toUpperCase();
				//配件借用发送邮件标识
				String sendemail = materborrowJpoSet.getJpo(i).getString(
						"sendemail");
				// 取当前时间
				java.util.Date newdate = MroServer.getMroServer().getDate();
				// 配件借用承诺归还时间
				Date PLANRETURNDATE = materborrowJpoSet.getJpo(i).getDate(
						"PLANRETURNDATE");
				// 配件借用续借承诺归还时间
				Date SECONDPLANRETURNDATE = materborrowJpoSet.getJpo(i).getDate(
						"SECONDPLANRETURNDATE");
				//一次邮件都没有发送
				if(sendemail.equalsIgnoreCase("0")){
					//承诺归还时间不为空
					if(PLANRETURNDATE != null){
						System.out.println("已经进入02");
						//续借承诺归还时间不为空
						if(SECONDPLANRETURNDATE != null){
							long timetmp = (newdate.getTime() - SECONDPLANRETURNDATE
									.getTime()) / (24 * 60 * 60 * 1000);
							if (timetmp > 0) {
								String bgsy = "配件借用续借超期提醒";
								String result = "配件借用编号为：" + MBNUM
										+ " 的配件借用续借已超期，请及时归还借用的配件";
								String[] object = null;
								String[] personcc = null;
								Vector<String> personall = new Vector();
								if (!CREATEBY.isEmpty()) {
									personall.add(CREATEBY);// 创建人
								}
								if (!BORROWBY.isEmpty()) {
									personall.add(BORROWBY);// 借用人
								}
								if (!BORROWAPPR.isEmpty()) {
									personall.add(BORROWAPPR);// 借方部门负责人
								}
								if (!BORROWCHECK.isEmpty()) {
									personall.add(BORROWCHECK);// 借方单元负责人
								}
								if (!SEVICEAPPR.isEmpty()) {
									personall.add(SEVICEAPPR);// 借出方责任人
								}
								object = personall
										.toArray(new String[personall
												.size()]);
								try {
									EwsEmail.sends(bgsy, result, object,
											personcc);
									materborrowJpoSet.getJpo(i).setValue("status", "借用超期",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									materborrowJpoSet.getJpo(i).setValue("sendemail", "2",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}else{
							System.out.println("已经进入01");
							long timetmp = (newdate.getTime() - PLANRETURNDATE
									.getTime()) / (24 * 60 * 60 * 1000);
							if (timetmp > 0) {
								String bgsy = "配件借用超期提醒";
								String result = "配件借用编号为：" + MBNUM
										+ " 的配件借用已超期，请及时归还借用的配件或通知被借用人进行续借";
								String[] object = null;
								String[] personcc = null;
								Vector<String> personall = new Vector();
								if (!CREATEBY.isEmpty()) {
									personall.add(CREATEBY);// 创建人
								}
								if (!BORROWBY.isEmpty()) {
									personall.add(BORROWBY);// 借用人
								}
								if (!BORROWAPPR.isEmpty()) {
									personall.add(BORROWAPPR);// 借方部门负责人
								}
								if (!BORROWCHECK.isEmpty()) {
									personall.add(BORROWCHECK);// 借方单元负责人
								}
								if (!SEVICEAPPR.isEmpty()) {
									personall.add(SEVICEAPPR);// 借出方责任人
								}
								object = personall
										.toArray(new String[personall
												.size()]);
								try {
									EwsEmail.sends(bgsy, result, object,
											personcc);
									materborrowJpoSet.getJpo(i).setValue("status", "借用超期",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									materborrowJpoSet.getJpo(i).setValue("sendemail", "1",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
				//发送过一次邮件
				else if(sendemail.equalsIgnoreCase("1")){
					System.out.println("已经进入12");
					if(SECONDPLANRETURNDATE != null){
						long timetmp = (newdate.getTime() - SECONDPLANRETURNDATE
								.getTime()) / (24 * 60 * 60 * 1000);
						if (timetmp > 0) {
							String bgsy = "配件借用续借超期提醒";
							String result = "配件借用编号为：" + MBNUM
									+ " 的配件借用续借已超期，请及时归还借用的配件";
							String[] object = null;
							String[] personcc = null;
							Vector<String> personall = new Vector();
							if (!CREATEBY.isEmpty()) {
								personall.add(CREATEBY);// 创建人
							}
							if (!BORROWBY.isEmpty()) {
								personall.add(BORROWBY);// 借用人
							}
							if (!BORROWAPPR.isEmpty()) {
								personall.add(BORROWAPPR);// 借方部门负责人
							}
							if (!BORROWCHECK.isEmpty()) {
								personall.add(BORROWCHECK);// 借方单元负责人
							}
							if (!SEVICEAPPR.isEmpty()) {
								personall.add(SEVICEAPPR);// 借出方责任人
							}
							object = personall
									.toArray(new String[personall
											.size()]);
							try {
								EwsEmail.sends(bgsy, result, object,
										personcc);
								materborrowJpoSet.getJpo(i).setValue("status", "借用超期",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								materborrowJpoSet.getJpo(i).setValue("sendemail", "2",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				materborrowJpoSet.getJpo(i).getThisJpoSet().save();
			}
		}
	}

}
