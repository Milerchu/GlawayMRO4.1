package com.glaway.mro.servlet;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.security.MroLicenseAuthenticate;
import com.glaway.mro.system.session.MroSession;
import com.glaway.mro.system.session.MroSessionMgr;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub;
import com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub.CheckSSO;
import com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub.CheckSSOResponse;

public class HandleLoginRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 8086176079319551232L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String gotoPage = null, queryString = null;
		// zzx add start 11.12
		String ssoStr = req.getParameter("SSO");

		String username = "";
		if (StringUtil.isNull(ssoStr)) {
			ssoStr = req.getParameter("sso");
		}
		PortalSSOStub service = new PortalSSOStub();
		CheckSSO csoo = new CheckSSO();
		csoo.setSSOValue(ssoStr);
		CheckSSOResponse ckso = service.checkSSO(csoo);
		username = ckso.getCheckSSOResult();
		// zzx add end 11.12
		if (null != username) {
			Connection conn = DBManager.getDBManager().getConnection();
			try {
				Statement s = conn.createStatement();

				ResultSet rs = s
						.executeQuery("select * from sys_user where loginid = '"
								+ username + "'");
				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						MroSession mroSession = MroSessionMgr
								.getMroSessionMgr().createMroSession(req, resp);
						if (!mroSession.isConnected()) {
							try {
								mroSession.setUserName(username);
								if (mroSession.connect()) {
									// 缓存用户权限等 MroclientSession 初始化
									mroSession.initUserResources();
									gotoPage = "mro";
									queryString = "event=loadapp&value=startcntr";
									String loginstamp = mroSession.getRequest().getParameter("loginstamp");
									if(StringUtil.isStrNotEmpty(loginstamp)){
										queryString += "&loginstamp=" + loginstamp;
									}
								} else {
									// 及时清理未通过的临时会话，避免成为未被收集的垃圾内存
									gotoPage = "page/login/login.jsp";
									queryString = req.getQueryString();
								}

							} catch (Exception e) {
								req.getSession().setAttribute("loginexception",
										e);
								gotoPage = "page/login/login.jsp";
								queryString = req.getQueryString();
								MroSessionMgr.getMroSessionMgr()
										.removeMroSessionWithoutClose(
												mroSession.getMroSessionID());
								handleUrl(req, resp, mroSession.getLangCode(),
										mroSession.getLocale(), gotoPage,
										queryString);
								return;
							}
							// 校验当前是否有license浮动空间以创建新会话(访问首页)
							try {
								MroLicenseAuthenticate.MLV
										.checkLicenseServerStatus(mroSession,
												"STARTCNTR");
							} catch (Exception e) {
								req.getSession().setAttribute("loginexception",
										e);
								gotoPage = "page/login/login.jsp";
								queryString = req.getQueryString();
								try {
									MroSessionMgr.getMroSessionMgr()
											.closeMroSession(mroSession);
								} catch (MroException e1) {
									FixedLoggers.EXCEPTIONLOGGER.error(e1);
								}
							}
						} else {
							gotoPage = "mro";
							queryString = "event=loadapp&value=startcntr";
							String loginstamp = mroSession.getRequest().getParameter("loginstamp");
							if(StringUtil.isStrNotEmpty(loginstamp)){
								queryString += "&loginstamp=" + loginstamp;
							}

						}

						handleUrl(req, resp, mroSession.getLangCode(),
								mroSession.getLocale(), gotoPage, queryString);
					}
				} else {
					gotoPage = "page/login/login.jsp";
					queryString = req.getQueryString();
					handleUrl(req, resp, "ZH", req.getLocale(), gotoPage,
							queryString);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				req.getSession().setAttribute("loginexception", e);
				gotoPage = "page/login/login.jsp";
				queryString = req.getQueryString();
				handleUrl(req, resp, "ZH", req.getLocale(), gotoPage,
						queryString);
				return;
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			gotoPage = "page/login/login.jsp";
			queryString = req.getQueryString();
			handleUrl(req, resp, "ZH", req.getLocale(), gotoPage, queryString);
			return;
		}
	}

	public void handleUrl(HttpServletRequest request,
			HttpServletResponse response, String langCode, Locale loc,
			String gotoPage, String queryString) throws IOException {
		String langcode = langCode;
		Locale locale = loc;
		if (langcode == null || locale == null) {
			Object[] settings = MroServer.getMroServer().getLocaleFromRequest(
					request);
			if (langcode == null && (settings[0] instanceof String)) {
				langcode = (String) settings[0];
			}
			if (locale == null && (settings[1] instanceof Locale)) {
				locale = (Locale) settings[1];
			}
		}

		StringBuilder sb = new StringBuilder(50);
		sb.append(
				request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()
						+ "/").append(gotoPage);
		if (StringUtil.isStrNotEmpty(queryString)) {
			sb.append("?").append(queryString);
		}
		String url = new URL(sb.toString()).toString();
		// 获取异步请求参数
		boolean isHideFrame = Boolean.valueOf(request.getParameter("isHXR"))
				.booleanValue();
		if (isHideFrame) {// 异步处理方式
			response.getWriter().println("<script type='text/javascript'>");
			response.getWriter().println("window.location.href='" + url + "'");
			response.getWriter().println("</script>");
		} else {
			response.sendRedirect(url);
		}
	}

}
