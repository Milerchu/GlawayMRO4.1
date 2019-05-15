package com.glaway.mro.app.system.doclinks.bean;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysPropCache;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.HandleRequestUtil;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.NetDiskUtil;

public class DoclinksBean extends DataBean {
	DataBean dataBean = null;

	@Override
	public void initialize() throws MroException {

		super.initialize();
		if (getDialog() != null) {
			dataBean = getDialog().getCreatorBean();
			if (dataBean.isEmpty()) {
				dataBean.setCurrMboDirectly(MroServer.getMroServer()
						.getSysJpoSet("TRANSPLANLIST").getJpo(0));
			}
		} else {
			dataBean = getParent();
		}
	}

	public int downloadattachments() throws IOException, MroException {
		HttpServletRequest request = getMroSession().getRequest();
		HandleRequestUtil hu = new HandleRequestUtil();
		if (hu.checkIsIntranet(request)) {
			throw new AppException("system", "cannotdownload");
		}
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		PageControl parent = getCurrEventCtrl().getParent();
		if (parent != null) {

			String urlattribute = parent.getProp("urlattribute");
			if(StringUtil.isStrEmpty(urlattribute)){
				urlattribute="sys_docinfo.urlname";
			}
			String urlname = getString(urlattribute);
			if (!path.endsWith("/")) {
				path += "/";
			}

			String uuid = getString("encryptName");
			if ("0".equals(MroServer.getMroServer().getSysPropCache()
					.getPropValue("mro.doc.netdisk"))) {// 非网盘模式才检查文件
				checkFileExists(urlname, uuid);
			}

			// 添加审计日志
			String app = getAppName();
			String action = Property.DOWNLOAD_LABLE;
			String p[] = { urlname };
			String content = getMessage("system", "downloadfile", p);
			String ip = getMroSession().getRequest().getRemoteAddr();
			String objectname = getJpoSet().getName();
			String memo = "";
			boolean result = true;
			writeAuditLog(app, content, action, result, memo, objectname, ip,
					false);
			
			HttpSession session = request.getSession();
			session.setAttribute("OPENURLSTR", urlname);
			session.setAttribute("encryptName", uuid);
			path += "downloadattachments";
			String remote = MroServer.getMroServer().getSysPropCache()
					.getPropValue("mro.doc.remote");
			boolean remoteflag = Boolean.valueOf(remote);
			if (remoteflag) {
				String username = MroServer.getMroServer().getSysPropCache()
						.getPropValue("mro.doc.remote.username");
				String password = MroServer.getMroServer().getSysPropCache()
						.getPropValue("mro.doc.remote.password");
				String remoteIp = MroServer.getMroServer().getSysPropCache()
						.getPropValue("mro.doc.remote.domain");
				if (remoteIp == null || remoteIp.equals("")) {
					throw new AppException("doclink", "请设置共享目录的ip地址");
				}
				NtlmPasswordAuthentication mpa = null;
				try {
					mpa = new NtlmPasswordAuthentication(remoteIp, username,
							password);
				} catch (Exception e) {
					throw new AppException("remoteFile", "connectouttime");
				}
				String remoteIpAddress = MroServer.getMroServer()
						.getSysPropCache()
						.getPropValue("mro.doc.remote.ipaddress");
				if (StringUtil.isNull(remoteIpAddress)) {
					throw new AppException("remoteFile", "errorconfig");
				}
			}
			if (session.getAttribute("OPENURLSTR") == null) {
//				HandleRequestUtil hu = new HandleRequestUtil();
				if (hu.checkIsIntranet(request)) {
					throw new AppException("system", "cannotdownload");
				}
			}
			openurl(path);
		}
		return 1;
	}
	
	 public int previewattachments()
		        throws IOException, MroException
		    {
		        HttpServletRequest request = getMroSession().getRequest();
		        String path =
		            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		        PageControl parent = getCurrEventCtrl().getParent();
		        if (parent != null)
		        {
		            String urlattribute = parent.getProp("urlattribute");
		            if(StringUtil.isStrEmpty(urlattribute)){
						urlattribute="sys_docinfo.urlname";
					}
		            String urlname = getString(urlattribute);
		            if (!path.endsWith("/"))
		            {
		                path += "/";
		            }
		            String filename = "";
		            filename = urlname.replaceAll("/", "\\\\");
		            if (filename.lastIndexOf("\\") > -1)
		            {
		        		filename = filename.substring(filename.lastIndexOf("\\") + 1);
		            }
		            if (!checkFileExtensions(filename))
		            {
		                String[] params = {filename};
		                throw new AppException("jspmessages", "notAllowedPreview", params);
		            }
		            
		            String uuid = getString("encryptName");
					if ("0".equals(MroServer.getMroServer().getSysPropCache()
							.getPropValue("mro.doc.netdisk"))) {// 非网盘模式才检查文件
						checkFileExists(urlname, uuid);
					}
					
		            //添加审计日志
		            String app = getAppName();
		            String action = Property.DOWNLOAD_LABLE;
		            String p[] = {urlname};
		            String content = getMessage("system", "downloadfile", p);
		            String ip = getMroSession().getRequest().getRemoteAddr();
		            String objectname = getJpoSet().getName();
		            String memo = "";
		            boolean result = true;
		            writeAuditLog(app, content, action, result, memo, objectname, ip, false);
		            
		            HttpSession session = request.getSession();
		            session.setAttribute("OPENURLSTR", urlname);
		            session.setAttribute("encryptName", uuid);
		            path += "filePreview";
		            String remote = MroServer.getMroServer().getSysPropCache().getPropValue("mro.doc.remote");
		            boolean remoteflag = Boolean.valueOf(remote);
		            if (remoteflag)
		            {
		                String username = MroServer.getMroServer().getSysPropCache().getPropValue("mro.doc.remote.username");
		                String password = MroServer.getMroServer().getSysPropCache().getPropValue("mro.doc.remote.password");
		                String remoteIp = MroServer.getMroServer().getSysPropCache().getPropValue("mro.doc.remote.domain");
		                if (remoteIp == null || remoteIp.equals(""))
		                {
		                    throw new AppException("doclink", "请设置共享目录的ip地址");
		                }
		                NtlmPasswordAuthentication mpa = null;
		                try
		                {
		                    mpa = new NtlmPasswordAuthentication(remoteIp, username, password);
		                }
		                catch (Exception e)
		                {
		                    throw new AppException("remoteFile", "connectouttime");
		                }
		                String remoteIpAddress = MroServer.getMroServer().getSysPropCache().getPropValue("mro.doc.remote.ipaddress");
		                if (StringUtil.isNull(remoteIpAddress))
		                {
		                    throw new AppException("remoteFile", "errorconfig");
		                }
		            }
//		            openurl(path);
		            this.getMroSession().openURL(path);
		        }
		        return 1;
		    }
	 
    private boolean checkFileExtensions(String filename){
    	String extName = "";
        extName = filename.substring(filename.lastIndexOf(".") + 1);
        
        String fileextension = MroServer.getMroServer().getSysProp("mro.doc.allowedPreViewFileExtensions");
        String extNames[] = fileextension.split(",");
        for (String name : extNames)
        {
            if (StringUtil.isEqualIgnoreCase(extName, name))
            {
            	return true;
            }
        }
        return false;
    }
	private void checkFileExists(String urlname, String uuid)
			throws MroException {
		String path = urlname;
		path = path.replaceAll("\\\\", "\\/");
		if (!StringUtil.isStrEmpty(uuid)) {
			int lastIndexof = path.lastIndexOf("/");
			path = path.substring(0, lastIndexof + 1) + uuid;
		}
		SysPropCache spc = MroServer.getMroServer().getSysPropCache();
		String remote = spc.getPropValue("mro.doc.remote");
		boolean isRemote = false;
		if (StringUtil.isStrNotEmpty(remote)) {
			isRemote = Boolean.valueOf(remote);
		}
		if (isRemote) {
			String username = spc.getPropValue("mro.doc.remote.username");
			String password = spc.getPropValue("mro.doc.remote.password");
			String remoteIp = spc.getPropValue("mro.doc.remote.domain");
			NtlmPasswordAuthentication mpa = new NtlmPasswordAuthentication(
					remoteIp, username, password);
			SmbFile remoteFile;
			try {
				remoteFile = new SmbFile("smb://"
						+ spc.getPropValue("mro.doc.remote.ipaddress") + "/"
						+ path.replace(":", ""), mpa);
				if (remoteFile.exists()) {
					return;
				}
			} catch (MalformedURLException e) {
				throw new MroException(e.getMessage());
			} catch (SmbException e) {
				throw new MroException(e.getMessage());
			}
		} else {
			File file = new File(path);
			if (file.exists()) {
				return;
			}
		}
		throw new MroException("doclink", "filenotfound");
	}

	@Override
	public int execute() throws MroException, IOException {
		boolean needSave = dataBean.clearDoclinksAndFile(2);
		int re = 0;
		try {
			re = super.execute();
		} catch (Exception e) {
			e.getMessage();
		}
		if (needSave) {
			showOperInfo("system", "saverecord");
		}
		return re;
	}

	@Override
	public synchronized void delete() throws MroException {
		String netdisk = MroServer.getMroServer().getSysPropCache()
				.getPropValue("mro.doc.netdisk");
		String appName = getAppName();
		// 不同附件模式
		if ("1".equals(netdisk) && (!"IMPIFACE".equalsIgnoreCase(appName))) {// 网盘
			showYesNoDialog("确认删除附件？", "yesdelete", "nodelete");
		} else {
			super.delete();
			if (getJpo() != null) {
				long uid = getJpo().getId();
				if (dataBean != null) {
					dataBean.putDocliksMap(uid, "D");
				}
				getAppBean().putDocliksMap(uid, "D");
			}
		}
	}

	@Override
	public synchronized void undelete() throws MroException {

		String netdisk = MroServer.getMroServer().getSysPropCache()
				.getPropValue("mro.doc.netdisk");
		String appName = getAppName();
		// 不同附件模式
		if ("1".equals(netdisk) && (!"IMPIFACE".equalsIgnoreCase(appName))) {// 网盘
			showYesNoDialog("确认删除附件？", "yesdelete", "nodelete");
		} else {
			if (getJpo() != null) {
				long uid = getJpo().getId();
				if (dataBean != null) {
					dataBean.putDocliksMap(uid, "UD");
				}
				getAppBean().putDocliksMap(uid, "UD");
			}
			super.undelete();

		}
	}

	@Override
	public int dialogcancel() throws IOException, MroException {
		if (dataBean != null) {
			dataBean.clearDoclinksAndFile(1);
		}
		return super.dialogcancel();
	}

	/**
	 * 
	 * 确认删除附件
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * @throws SmbException
	 * 
	 */
	public void yesdelete() throws MroException, IOException {

		String docid = getString("docinfoid");
		// 用原来getjposet方式会导致之后操作时getparent获取不到jpo，故修改
		IJpo docJpo = MroServer.getMroServer()
				.getSysJpoSet("SYS_DOCINFO", "SYS_DOCINFOID='" + docid + "'")
				.getJpo();
		String fileName = docJpo.getString("URLNAME");
		String[] fNames = fileName.split("/");
		String monthFold = fNames[fNames.length - 2];
		String filepath = getJpo().getString("DOCTYPE") + "/" + monthFold + "/"
				+ getJpo().getString("DESCRIPTION");
		// doctype为非FAILURE的附件才上传网盘
		if (!"FAILURE".equals(getJpo().getString("DOCTYPE"))) {
			try {
				NetDiskUtil.deleteFile(filepath);
			} catch (Exception e) {
				throw new MroException(e.getMessage());
			}
		} else {
			getParent().setValue("FAILURELIB.FAULTDATAREC", "待下载",
					GWConstant.P_NOVALIDATION);
		}
		// 刷新页面
		// dataBean.reloadSelfAndSubs();
		// getDataBean("showattachments_table");
		getJpo().delete();
		getAppBean().save();
		resetAndReload();
	}

	/**
	 * 
	 * 取消删除附件
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public void nodelete() throws MroException, IOException {
		getJpo().undelete();
		resetAndReload();
	}
	
	 public int dowlwww()
		        throws IOException, MroException
		    {
		 return GWConstant.NOACCESS_SAMEMETHOD;
		    }
	 
	 public void downloaddata() throws Exception {
			String url = this.getAppBean().getJpo().getString("FAILURELIB.FAULTDATAURL");
			
			if(StringUtil.isStrNotEmpty(url)){
		        mroSession.returnReponse("", "openUrl", "<url>" + url + "</url>");
			}else{
				throw new AppException("servorder", "nodata");
			}
		}
	 
}
