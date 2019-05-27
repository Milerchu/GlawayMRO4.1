package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <装箱单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class ZxdTransferLineDataBean extends DataBean {
	/**
	 * 
	 * <接收按钮方法，判断是否可以接收>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void status() throws MroException {

		if (!WfControlUtil.isCurUser(this.page.getAppBean().getJpo())) { /* 当前登陆人不是流程审批人 */
			throw new MroException("transferline", "noreceiveby");
		} else {
			String personid = this.page.getAppBean().getUserInfo().getLoginID();
			personid = personid.toUpperCase();
			String RECEIVEBY = this.page.getAppBean().getJpo()
					.getString("RECEIVEBY");// 现场库管员
			String status = this.getJpo().getString("status");
			String OPENBY = this.getJpo().getParent().getString("OPENBY");
			String RECEIVEDATE = this.getJpo().getParent()
					.getString("RECEIVEDATE");
			String RECEIVECHECKBY = this.getJpo().getParent()
					.getString("RECEIVECHECKBY");
			if (personid.equalsIgnoreCase(RECEIVEBY)) {
				if (status.equalsIgnoreCase("已接收")) {
					throw new MroException("transferline", "receive");
				}
				if (!this.getJpo().getParent().getString("status")
						.equalsIgnoreCase("在途")) {
					throw new MroException("transferline", "statusreceive");
				} else if (this.getJpo().getParent().getString("status")
						.equalsIgnoreCase("在途")) {
					if (OPENBY.equalsIgnoreCase("")
							|| RECEIVEDATE.equalsIgnoreCase("")
							|| RECEIVECHECKBY.equalsIgnoreCase("")) {
						throw new MroException("transferline",
								"norecrivemessage");
					}
				}
			} else {
				throw new MroException("transferline", "endbyreceive");
			}
		}

	}

	/**
	 * 
	 * <选择缴库单按钮方法，判断是否可以选择缴库单>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void selectjkdline() throws MroException, IOException {
		this.page.getAppBean().SAVE();
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String RECEIVESTOREROOM = this.page.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		String isfxfy = this.page.getAppBean().getJpo().getString("isfxfy");
		String mrnum = this.page.getAppBean().getJpo().getString("mrnum");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		String SXTYPE = this.page.getAppBean().getJpo().getString("SXTYPE");
		if (TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
			if (personid.equalsIgnoreCase(CREATEBY)) {
				if (ISSUESTOREROOM.isEmpty()) {
					throw new MroException("transferline",
							"pleaseselectissuestoreroom");
				} else {
					if (!SXTYPE.isEmpty()) {
						throw new MroException("transferline",
								"noselectjkdlinetype");
					}
				}
				if (RECEIVESTOREROOM.isEmpty()) {
					throw new MroException("请先选择接收库房");
				} else {
					String LOCATIONTYPE = this.page.getAppBean().getJpo()
							.getJpoSet("RECEIVESTOREROOM").getJpo()
							.getString("LOCATIONTYPE");
					if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
						if (!RECEIVESTOREROOM.equalsIgnoreCase("Y1081")&&!RECEIVESTOREROOM.equalsIgnoreCase("QT2013")) {
							throw new MroException("不能选择缴库单");
						} else {
							IJpoSet thistransferlineset = this.page
									.getAppBean().getJpo()
									.getJpoSet("transferline");
							if (!thistransferlineset.isEmpty()) {
								for (int i = 0; i < thistransferlineset.count(); i++) {
									IJpo thistransferline = thistransferlineset
											.getJpo(i);
									String zxdlineid = thistransferline
											.getString("zxdlineid");
									String jkdlineid = thistransferline
											.getString("jkdlineid");
									if (zxdlineid.isEmpty()
											&& jkdlineid.isEmpty()) {
										throw new MroException(
												"已通过新建行创建数据，不能选择缴库单");
									}
									if (!zxdlineid.isEmpty()) {
										throw new MroException(
												"已通过装箱单创建数据，不能选择缴库单");
									}
								}
							}
						}
					}
					if (LOCATIONTYPE.equalsIgnoreCase("常规")) {
						if (RECEIVESTOREROOM.equalsIgnoreCase("Y1710")) {
							throw new MroException("不能选择缴库单,请选择装箱单");
						} else {
							IJpoSet thistransferlineset = this.page
									.getAppBean().getJpo()
									.getJpoSet("transferline");
							if (!thistransferlineset.isEmpty()) {
								for (int i = 0; i < thistransferlineset.count(); i++) {
									IJpo thistransferline = thistransferlineset
											.getJpo(i);
									String jkdlineid = thistransferline
											.getString("jkdlineid");
									String zxdlineid = thistransferline
											.getString("zxdlineid");
									if (jkdlineid.isEmpty()
											&& zxdlineid.isEmpty()) {
										throw new MroException(
												"已通过新建行创建数据，不能选择装箱单");
									}
								}
							}
						}
					}
				}
			} else {
				throw new MroException("transferline", "personnoselectjkdline");
			}
		} else {
			if (!isfxfy.equalsIgnoreCase("是")) {
				throw new MroException("非中心到中心和非返修后发运不能选择缴库单");
			} else {
				if (mrnum.isEmpty()) {
					IJpoSet thistransferlineset = this.page.getAppBean()
							.getJpo().getJpoSet("transferline");
					if (!thistransferlineset.isEmpty()) {
						for (int i = 0; i < thistransferlineset.count(); i++) {
							IJpo thistransferline = thistransferlineset
									.getJpo(i);
							String zxdlineid = thistransferline
									.getString("zxdlineid");
							String jkdlineid = thistransferline
									.getString("jkdlineid");
							if (zxdlineid.isEmpty() && jkdlineid.isEmpty()) {
								throw new MroException("已通过新建行创建数据，不能选择缴库单");
							}
						}
					}
				}
			}

		}

	}

	/**
	 * 删除行方法，判断是否可以删除行
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		String status = this.page.getAppBean().getJpo().getString("status");
		String issue = this.page.getAppBean().getJpo().getString("issue");
		String newstatus = this.getJpo().getString("status");
		if (!personid.equalsIgnoreCase(CREATEBY)) {
			throw new MroException("transferline", "nodelete");
		} else {
			// zzx add start 3.12
			if (issue.equalsIgnoreCase("是")) {
				if (status.equalsIgnoreCase("驳回")
						&& this.getJpo().getString("status")
								.equalsIgnoreCase("未接收")) {
					return super.toggledeleterow();
				} else {
					throw new MroException("transferline", "statusnodelete");
				}
				// zzx add end
			} else {
				if (!this.getJpo().getString("status").equalsIgnoreCase("未接收")) {
					throw new MroException("transferline", "statusnodelete");
				}
			}
		}
		return super.toggledeleterow();
	}

	/**
	 * 新增行方法，判断是否可以新增行
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		String mrnum = this.page.getAppBean().getJpo().getString("mrnum");
		String SXTYPE = this.page.getAppBean().getJpo().getString("SXTYPE");
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String RECEIVESTOREROOM = this.page.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		String status = this.page.getAppBean().getJpo().getString("status");
		String ISMR = this.page.getAppBean().getJpo().getString("ISMR");
		if (ISMR.equalsIgnoreCase("是")) {
			throw new MroException("transferline", "notaddrow");
		}
		if (!TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
			if (RECEIVESTOREROOM.isEmpty()) {
				throw new MroException("transferline", "norecstoreroom");
			}
		}
		if (!TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
			if (ISSUESTOREROOM.isEmpty()) {
				throw new MroException("transferline", "nostoreroom");
			}

		}
		if (!status.equalsIgnoreCase("未处理")
				&& !status.equalsIgnoreCase("申请人修改")
				&& !status.equalsIgnoreCase("驳回")) {
			throw new MroException("transferline", "notaddrow");
		} else {
			if (!personid.equalsIgnoreCase(CREATEBY)) {
				throw new MroException("transferline", "notaddrow");
			} else {
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
					if (RECEIVESTOREROOM.isEmpty()) {
						throw new MroException("请先选择接收库房");
					} else {
						String LOCATIONTYPE = this.page.getAppBean().getJpo()
								.getJpoSet("RECEIVESTOREROOM").getJpo()
								.getString("LOCATIONTYPE");
						String issLOCATIONTYPE = this.page.getAppBean()
								.getJpo().getJpoSet("ISSUESTOREROOM").getJpo()
								.getString("LOCATIONTYPE");
						if (issLOCATIONTYPE.equalsIgnoreCase("维修")) {
							if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
								if (!RECEIVESTOREROOM.equalsIgnoreCase("Y1081")) {
									throw new MroException(
											"不能新建行，请通过装箱单或者缴库单创建数据");
								} else {
									IJpoSet thistransferlineset = this.page
											.getAppBean().getJpo()
											.getJpoSet("transferline");
									if (!thistransferlineset.isEmpty()) {
										for (int i = 0; i < thistransferlineset
												.count(); i++) {
											IJpo thistransferline = thistransferlineset
													.getJpo(i);
											String jkdlineid = thistransferline
													.getString("jkdlineid");
											String zxdlineid = thistransferline
													.getString("zxdlineid");
											if (!jkdlineid.isEmpty()) {
												throw new MroException(
														"已通过缴库单创建数据，不能新建行");
											}
											if (!zxdlineid.isEmpty()) {
												throw new MroException(
														"已通过装箱单创建数据，不能新建行");
											}
										}
									}
								}
							}
							if (LOCATIONTYPE.equalsIgnoreCase("常规")) {
								if (RECEIVESTOREROOM.equalsIgnoreCase("Y1710")) {
									throw new MroException("不能新建行,请选择装箱单");
								} else {
									IJpoSet thistransferlineset = this.page
											.getAppBean().getJpo()
											.getJpoSet("transferline");
									if (!thistransferlineset.isEmpty()) {
										for (int i = 0; i < thistransferlineset
												.count(); i++) {
											IJpo thistransferline = thistransferlineset
													.getJpo(i);
											String jkdlineid = thistransferline
													.getString("jkdlineid");
											if (!jkdlineid.isEmpty()) {
												throw new MroException(
														"已通过缴库单创建数据，不能新建行");
											}
										}
									}
								}
							}
						}
					}
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
					IJpoSet thistransferlineset = this.page.getAppBean()
							.getJpo().getJpoSet("transferline");
					if (!thistransferlineset.isEmpty()) {
						for (int i = 0; i < thistransferlineset.count(); i++) {
							IJpo thistransferline = thistransferlineset
									.getJpo(i);
							String jkdlineid = thistransferline
									.getString("jkdlineid");
							if (!jkdlineid.isEmpty()) {
								throw new MroException("已通过缴库单创建数据，不能新建行");
							}
						}
					}
				}
			}
		}
		int i = super.addrow();
		return i;
	}

	/**
	 * 
	 * <选择配件申请按钮方法，判断是否可以选择配件申请>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void SELECTMRLINE() throws IOException, MroException {
		this.page.getAppBean().SAVE();
		String MRNUM = this.page.getAppBean().getJpo().getString("MRNUM");
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		String ISMR = this.page.getAppBean().getJpo().getString("ISMR");
		String ISFXFY = this.page.getAppBean().getJpo().getString("ISFXFY");
		if (!ISMR.equalsIgnoreCase("是")) {
			throw new MroException("transferline", "selectmrline");
		} else {
			if (ISFXFY.equalsIgnoreCase("是")) {
				throw new MroException("此单位配件申请的返修后发货，请选择缴库单");
			}
		}
		if (!TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
			if (MRNUM.isEmpty()) {
				throw new MroException("transferline", "selectmrline");
			}
			if (ISSUESTOREROOM.isEmpty()) {
				throw new MroException("transferline", "nostoreroom");
			}
		}

		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = this.page.getAppBean().getJpo().getString("CREATEBY");
		if (!this.page.getAppBean().getJpo().getString("status")
				.equalsIgnoreCase("未处理")) {
			if (!this.page.getAppBean().getJpo().getString("status")
					.equalsIgnoreCase("申请人修改")) {
				if (!this.page.getAppBean().getJpo().getString("status")
						.equalsIgnoreCase("驳回")) {
					throw new MroException("transferline", "statusselectmr");
				}
			}

		} else {
			if (!personid.equalsIgnoreCase(CREATEBY)) {
				throw new MroException("transferline", "createbyselectmr");
			}
		}
		if (!MRNUM.equalsIgnoreCase("")) {
			String MRTYPE = this.page.getAppBean().getJpo().getJpoSet("MR")
					.getJpo(0).getString("MRTYPE");
			if (MRTYPE.equalsIgnoreCase("零星")) {
				this.page.loadDialog("selectmrlined",
						this.page.getCurrEventCtrl());
			}
			if (MRTYPE.equalsIgnoreCase("项目")) {
				this.page.loadDialog("selectmrlinepro",
						this.page.getCurrEventCtrl());
			}
		}
	}

	@Override
	public int editrow() throws MroException, IOException {
		return super.editrow();
	}

	/**
	 * 编辑确认方法，确认编辑后如果是新增保存记录
	 * 
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.addEditRowCallBackOk();
//		if (this.getJpo().isNew()) {
//			this.page.getAppBean().SAVE();
//		}
		this.page.getAppBean().SAVE();
	}

	/**
	 * 
	 * <选择装箱单按钮方法，判断是否可以选择装箱单>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectzxdline() throws IOException, MroException {
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String RECEIVESTOREROOM = this.page.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
			if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1090")) {/* 宁波维修子库 */

			} else if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1710")) {/* 再利用库 */

			} else if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1081")) {/* 待处理库-- */
				IJpoSet thistransferlineset = this.page.getAppBean().getJpo()
						.getJpoSet("transferline");
				if (!thistransferlineset.isEmpty()) {
					for (int i = 0; i < thistransferlineset.count(); i++) {
						IJpo thistransferline = thistransferlineset.getJpo(i);
						String zxdlineid = thistransferline
								.getString("zxdlineid");
						String jkdlineid = thistransferline
								.getString("jkdlineid");
						if (zxdlineid.isEmpty() && jkdlineid.isEmpty()) {
							throw new MroException("已通过新建行创建数据，不能选择装箱单");
						}
						if (!jkdlineid.isEmpty()) {
							throw new MroException("已通过缴库单创建数据，不能选择装箱单");
						}
					}
				}
			} else if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1711")) {/* 进口件中心维修库 */

			} else if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1712")) {/* 三菱维修库 */

			} else if (ISSUESTOREROOM.equalsIgnoreCase("Y1711")
					&& RECEIVESTOREROOM.equalsIgnoreCase("Y1087")) {/* 中心维修库 */

			} else {
				throw new MroException("不能选择装箱单");
			}
		} else {
			throw new MroException("非中心到中心移动类型不能选择装箱单");
		}
	}
}
