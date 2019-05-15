package com.glaway.mro.app.system.doclinks.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.glaway.mro.app.imp.bean.ImpIfaceDataBean;
import com.glaway.mro.app.system.doclinks.data.SysDocinfo;
import com.glaway.mro.app.system.doclinks.data.SysDoclink;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Dialog;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;

public class AddDoclinkBean extends DataBean {
	protected Dialog dialog = null;

	protected DataBean dataBean = null;

	protected IJpoSet doclinkSet = null;

	@Override
	public void initialize() throws MroException {
		super.initialize();
		dialog = getDialog();
		dataBean = dialog.getCreatorBean();
		doclinkSet = getPage().getJpoSet("SYS_DOCLINKS");
		if ("registerdocfile".equals(getPageControl().getXmlId())) {
			// doclinkSet = jpoSet;
			doclinkSet = getJpoSet();
		}
		doclinkSet.setAppName(getAppName());
		currJpo = doclinkSet.addJpo();
		// 故障工单处将doctype设置为ATTACHMENTS，并去掉下拉框，其余页面可从下拉框修改
		currJpo.getField("DOCTYPE").setValue("ATTACHMENTS");
	}

	@Override
	public int dialogcancel() throws IOException, MroException {
		if ("registerdocfile".equals(getPageControl().getXmlId())) {
			doclinkSet.reset();
		} else {
			if (currJpo.toBeAdded()) {
				doclinkSet.remove(currJpo);
			}
			dataBean.refixPos();
		}
		dialogCloseNoRefresh();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	public int uploadfile() throws Exception {
		mroSession.getRequest().getSession().setAttribute("isSuccess", "");
		boolean importflag = false;
		DataBean dbparent = dataBean.getParent();
		String jponame = null;
		if (dbparent != null) {
			jponame = dbparent.getJpo().getName();
		}
		if (dataBean.getAppBean().getAppName().equals("IMPIFACE")
				&& StringUtil.isEqual(jponame, "IMPRELATION")) {
			importflag = true;
		}
		long doclinksid = 0l;
		try {
			if (dataBean != null) {
				getMroSession().setIframeResponse(true);
				checkSave();
				if (currJpo != null) {
					currJpo.getString("DOCTYPE");
					doclinksid = currJpo.getId();
					if (currJpo.isNull("DOCTYPE")) {
						String[] p = { "DOCTYPE" };
						throw new AppException("system", "null", p);
					}
					UploadUtil upload = new UploadUtil(getMroSession()
							.getRequest());
					upload.setMroDirectoryName(getString("DOCTYPE"));
					// upload.getFileItems();
					String ip = getMroSession().getRequest().getRemoteAddr();
					if (currJpo instanceof SysDoclink) {
						HashSet<String[]> hs = null;
						if (importflag) {
							hs = upload.writeToDiskForImpdata();
						} else {
							hs = upload.writeToDisk(currJpo);
						}
						//0:fileName;2:directoryName;4:encryptFileName
						for (String[] s : hs) {
							String content = Property.UPLOADFILE_LABLE + "\""
									+ s[0] + "\"";
							writeAuditLog(getAppName(), content,
									Property.UPLOADFILE_LABLE, true, "",
									currJpo.getName(), ip, false);
							if (dataBean.getParent() != null) {
								IJpo parent = dataBean.getParent().getJpo();
								((SysDoclink) currJpo).addDoclink(parent, s[0],
										s[2], s[0], s[4]);
								dataBean.getParent().putDocliksMap(
										currJpo.getId(), "A");
								getAppBean()
										.putDocliksMap(currJpo.getId(), "A");
							}
						}
						doclinkSet.save();
					}
					if (currJpo instanceof SysDocinfo) {
						if (!currJpo.isNull("DOCUMENT")) {
							HashSet<String[]> hs = null;
							// if(importflag){
							// hs = upload.writeToDiskForImpdata();
							// }else{
							hs = upload.writeToDisk(currJpo);
							// }
							// upload.writeToDisk();
							for (String[] s : hs) {
								String content = Property.UPLOADFILE_LABLE
										+ "\"" + s[0] + "\"";
								writeAuditLog(getAppName(), content,
										Property.UPLOADFILE_LABLE, true, "",
										currJpo.getName(), ip, false);
								((SysDocinfo) currJpo).addDocinfo(s[0], s[2],
										s[0], s[4]);
								if (dataBean.getParent() != null) {
									dataBean.getParent().putDocliksMap(
											currJpo.getId(), "A");
								} else {
									getAppBean().putDocliksMap(currJpo.getId(),
											"A");
								}
							}
							doclinkSet.save();
						} else {
							String[] p = { getJpoSet().getFieldInfo("DOCUMENT")
									.getTitle() };
							throw new AppException("system", "null", p);
						}
					}
				}
				dataBean.reset();
				if (dataBean.getJpo() != null) {
					dataBean.getJpo().setValue("CHANGEDATE", getSysDate());
					dataBean.getJpo().setValue("CHANGEBY",
							getUserInfo().getPersonId());
				}
				dialogclose();
				// dialogok();
				// 在session中添加上传完成的标记，以便监控上传事件是否完成

			}
		} catch (Exception e) {
			mroSession.getRequest().getSession()
					.setAttribute("isSuccess", "uploadfileSuccess");
			// throw new MroException(((MroException)e).getMessage());
			throw new MroException(e.getMessage());
		}
		// finally
		// {
		// mroSession.getRequest().getSession().setAttribute("isSuccess",
		// "uploadfileSuccess");
		// }

		// String ownerTable = null;
		// if (dataBean != null && dataBean.getJpo() != null)
		// {
		// ownerTable = dataBean.getJpo().getString("OWNERTABLE");
		// }
		mroSession.getRequest().getSession()
				.setAttribute("isSuccess", "uploadfileSuccess");
		if (importflag && currJpo instanceof SysDoclink) {
			IJpoSet doclinksSet = dataBean.getJpo().getJpoSet();
			doclinksSet.setOrderBy("createdate desc");
			doclinksSet.reset();
			IJpo doclinkJpo = doclinksSet.getJpo(0);
			IJpoSet docinfoSet = doclinkJpo.getJpoSet("SYS_DOCINFO");
			// docinfoSet.count();
			// docinfoSet.setOrderBy("createdate");
			// docinfoSet.reset();
			IJpo docinfojpo = null;
			String filename = null;
			if (!docinfoSet.isEmpty()) {
				docinfojpo = docinfoSet.getJpo(0);
				filename = docinfojpo.getString("URLNAME");
			}
			// ImpIfaceDataBean impdatabean = new ImpIfaceDataBean();
			// impdatabean.impData(filename);
			// impdatabean.impdata();
			ImpIfaceDataBean impdataBean = (ImpIfaceDataBean) this.page
					.getAppBean().getDataBean("1487057668704");
			impdataBean.impData(filename, doclinksid);
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	@Override
	public void checkSave() throws MroException {
		StringBuilder sb = new StringBuilder(200);
		HashMap<String, String> hm = new HashMap<String, String>();
		if (control.isDialog()) {
			control.checkBaseCtrl(hm);
			boolean foundThisDialog = false;
			for (PageControl ctrl : page.getDialogs()) {
				if (foundThisDialog) {
					ctrl.checkBaseCtrl(hm); // 检查对话框内部是否存在不合法的输入框
				} else {
					if (control == ctrl) {
						foundThisDialog = true;
					}
				}
			}
		} else if (control.isResultTable()) {
			page.getRootCtrl().checkBaseCtrl(hm);
			if (page.getDialogs() != null && !page.getDialogs().isEmpty()) {
				for (PageControl ctrl : page.getDialogs()) {
					ctrl.checkBaseCtrl(hm); // 检查对话框内部是否存在不合法的输入框
				}
			}
		} else {
			control.findTabAndCheckBaseCtrl(hm);
		}
		// for (String errorStr : hm.values())
		// {
		// sb.append(errorStr);
		// }
		// if (sb.length() > 0)
		// {
		// // throw new
		// FieldsException(sb.toString().substring("GWMRO".indexOf(ch)));
		// }
	}
}
