package com.glaway.sddq.tools;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.glaway.mro.exception.MroException;

/**
 * 
 * 故障数据包上传工具类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class BigDataFTPUploader {

	private String FTP_Charset = "GBK";
	private static final String FTP_SERVCER_Charset = "ISO-8859-1";

	public boolean upload(String dict, String filename, long length,
			InputStream input) {
		FTPClient client = null;
		String num = "";
		try {
			num = IFUtil.addIfHistory("BIGDATA_FTPUPLOAD", "上传文件：" + dict + "/"
					+ filename + " 长度：" + length, IFUtil.TYPE_OUTPUT);
			client = makeObject();
			String dic = new String(("/" + dict).getBytes(FTP_Charset),
					FTP_SERVCER_Charset);
			client.changeWorkingDirectory(dic);
			boolean ret = client.storeFile(
					new String(filename.getBytes(FTP_Charset),
							FTP_SERVCER_Charset), input);
			if (ret) {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "上传成功，上传文件：" + dict + "/" + filename
								+ " 长度：" + length);
			} else {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "上传失败，上传文件：" + dict + "/" + filename
								+ " 长度：" + length);
			}
			return ret;
		} catch (Exception e) {
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "上传失败，错误原因：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				input.close();
				destroyObject(client);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private FTPClient makeObject() throws Exception {
		String host = IFUtil.getIfServiceInfo("bigdata.ftphost");
		int port = Integer.parseInt(IFUtil.getIfServiceInfo("bigdata.ftpport"));
		String user = IFUtil.getIfServiceInfo("bigdata.ftpusername");
		String password = IFUtil.getIfServiceInfo("bigdata.ftppassword");

		FTPClient ftpClient = new FTPClient();
		// FTPClientConfig conf=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		// conf.setServerLanguageCode("zh");
		// ftpClient.configure(conf);
		ftpClient.setDataTimeout(20000);
		ftpClient.setConnectTimeout(20000);
		ftpClient.setControlKeepAliveTimeout(300);

		ftpClient.connect(host, port);
		ftpClient.login(user, password);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		int comm = ftpClient.sendCommand("OPTS UTF8", "ON");
		if (FTPReply.isPositiveCompletion(comm)) {
			FTP_Charset = "UTF-8";
		}

		ftpClient.setControlEncoding(FTP_Charset);

		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			return null;
		}
		return ftpClient;
	}

	private void destroyObject(Object obj) throws Exception {
		if (obj instanceof FTPClient) {
			FTPClient ftpClient = (FTPClient) obj;
			if (!ftpClient.isConnected())
				return;
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
