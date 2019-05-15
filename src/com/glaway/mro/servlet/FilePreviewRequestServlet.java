package com.glaway.mro.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilePreviewRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 8086176079319551232L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("OPENURLSTR") != null) {
			String urlname = (String) session.getAttribute("OPENURLSTR");
			String encryptName = (String) session.getAttribute("encryptName");
			req.setAttribute("OPENURLSTR", urlname);
			req.setAttribute("encryptName", encryptName);
			req.getRequestDispatcher("page/portal/jsp/filepv/filePreview.jsp")
					.forward(req, resp);
		}
	}
}