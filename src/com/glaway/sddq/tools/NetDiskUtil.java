package com.glaway.sddq.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.glaway.mro.exception.MroException;

/**
 * 
 * AnyShare网盘操作工具类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月26日]
 * @since [产品/模块版本]
 */

class SSLClient extends DefaultHttpClient {
	public SSLClient() throws Exception {
		super();
		SSLContext ctx = SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));
	}
}

class ASEntryDoc {
	public String docId;
	public String docName;
	public String docType;
}

class ASBeginUploadResult {
	public String httpMethod;
	public String url;
	public HashMap<String, String> headers;
	public String docId;
	public String name;
	public String rev;
}

public class NetDiskUtil {

	static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7JL0DcaMUHumSdhxXTxqiABBC\n"
			+ "DERhRJIsAPB++zx1INgSEKPGbexDt1ojcNAc0fI+G/yTuQcgH1EW8posgUni0mcT\n"
			+ "E6CnjkVbv8ILgCuhy+4eu+2lApDwQPD9Tr6J8k21Ruu2sWV5Z1VRuQFqGm/c5vaT\n"
			+ "OQE5VFOIXPVTaa25mQIDAQAB";

	// 服务器配置和帐号信息
	private static String ip = IFUtil.getIfServiceInfo("netdisk.ip");
	private static String account = IFUtil.getIfServiceInfo("netdisk.username");
	private static String password = IFUtil
			.getIfServiceInfo("netdisk.password");

	// 保存userid和tokenid鉴权信息，用来发送后续的请求
	private static String userid;
	private static String tokenid;

	private static String userDocGnsPath;
	private static ArrayList<ASEntryDoc> entryDocs;

	public void SetServerIP(String ip) {
		this.ip = ip;
	}

	private static String RSAEncode(String pass) throws IOException,
			GeneralSecurityException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(
				new BASE64Decoder().decodeBuffer(pubKey)));

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new BASE64Encoder().encode(
				cipher.doFinal(pass.getBytes("UTF-8"))).replace("\r\n", "\n");
	}

	/**
	 * 
	 * 网盘初始化
	 * 
	 * @throws MroException
	 * 
	 */
	private static void netdiskInit() {
		try {
			// 1.调用GetNew协议进行登录
			logIn(account, password);

			// 2.获取入口文档
			entryDocs = GetEntryDoc();

			// 3.在入口文档中找到个人文档, docType == "userdoc"
			userDocGnsPath = "";
			for (int i = 0; i < entryDocs.size(); i++) {
				ASEntryDoc tmpObj = entryDocs.get(i);
				if (tmpObj.docType.equals("userdoc")) {
					userDocGnsPath = tmpObj.docId;
				}
			}

			if (userDocGnsPath.equals("")) {
				System.out.println("没有找到个人文档");
				// return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * 网盘登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @throws IOException
	 * @throws GeneralSecurityException
	 *             [参数说明]
	 * @throws MroException
	 * 
	 */
	private static void logIn(String username, String password)
			throws IOException, GeneralSecurityException, MroException {
		// 接口格式（包含URL请求格式、参数说明及请求示例）请参见《1-AnyShare5.0访问控制开放API接口文档 - 对外.docx》5.3
		// 节
		// token有过期机制，需定期刷新，详情参见《1-AnyShare5.0访问控制开放API接口文档 - 对外.docx》第五章相关接口
		String url = "http://" + ip + ":9998/v1/auth1?method=getnew";
		String encopassword = RSAEncode(password);
		JSONObject user = new JSONObject();
		user.put("account", username);
		user.put("password", encopassword);
		String user2 = user.toString();

		// create http post
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		StringEntity se = new StringEntity(user2, "UTF-8");
		methodd.addHeader("content-type", "application/json");
		methodd.setEntity(se);

		// get http post response
		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		methodd.releaseConnection();

		// Get userid and tokenid from response json
		JSONObject retunjson = JSONObject.fromObject(strResult);
		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		userid = (String) retunjson.get("userid");
		tokenid = (String) retunjson.get("tokenid");
		// System.out.println("Login response json: " + strResult);
	}

	private static ArrayList<ASEntryDoc> GetEntryDoc() throws IOException,
			MroException {
		// userid、tokenid 由 auth1?method=getnew 接口返回
		String url = "http://" + ip + ":9998/v1/entrydoc?method=get&userid="
				+ userid + "&tokenid=" + tokenid;

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("request url: " + url);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		methodd.releaseConnection();

		// 解析返回值
		JSONObject jsonObj = JSONObject.fromObject(strResult);

		if (!jsonObj.isEmpty()) {
			if (jsonObj.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:" + jsonObj.getString("errmsg"));
			}
		}

		JSONArray jsonDocInfos = jsonObj.getJSONArray("docinfos");

		ArrayList<ASEntryDoc> entryDocs = new ArrayList<ASEntryDoc>();
		for (int i = 0; i < jsonDocInfos.size(); i++) {
			JSONObject docObj = jsonDocInfos.getJSONObject(i);

			ASEntryDoc tmpDoc = new ASEntryDoc();
			tmpDoc.docId = docObj.getString("docid");
			tmpDoc.docName = docObj.getString("docname");
			tmpDoc.docType = docObj.getString("doctype");

			entryDocs.add(tmpDoc);
		}

		return entryDocs;
	}

	/**
	 * 
	 * 创建文件夹
	 * 
	 * @param parentgns
	 * @param name
	 * @throws Exception
	 * 
	 */
	public static void createDir(String name) throws Exception {

		// 初始化网盘信息
		netdiskInit();

		// 多级目录创建
		if (name.indexOf("/") > -1) {
			createMultiLevelDir(userDocGnsPath, name);
			return;
		}
		// userid、tokenid 由 auth1?method=getnew 接口返回
		String url = "http://" + ip + ":9123/v1/dir?method=create&userid="
				+ userid + "&tokenid=" + tokenid;

		JSONObject dir = new JSONObject();
		// docid 可由任意可获取到文件夹ID的接口处获取 （如 entrydoc?method=get dir?method=create
		dir.put("docid", userDocGnsPath);
		dir.put("name", name);
		dir.put("ondup", 2);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("request url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		methodd.releaseConnection();
		if (!strResult.isEmpty()) {
			JSONObject resObj = JSONObject.fromObject(strResult);
			if (!resObj.isEmpty()) {
				if (resObj.containsKey("errmsg")) {
					throw new MroException("网盘操作失败:"
							+ resObj.getString("errmsg"));
				}
			}
		}

		// System.out.println("CreadDirReponse json: " + strResult);
	}

	/**
	 * 
	 * 创建多层目录， 如果存在则返回多层目录的gns
	 * 
	 * @param parentgns
	 * @param serverpath
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	private static String createMultiLevelDir(String parentgns,
			String serverpath) throws Exception {
		String url = "http://" + ip
				+ ":9123/v1/dir?method=createmultileveldir&userid=" + userid
				+ "&tokenid=" + tokenid;
		JSONObject reqbody = new JSONObject();
		reqbody.put("docid", parentgns);
		reqbody.put("path", serverpath);
		String reqstr = reqbody.toString();
		// System.out.println("request json: " + reqstr);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("request url: " + url);
		StringEntity se = new StringEntity(reqstr, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}

		// System.out.println("createmultileveldir: " + strResult);
		return retunjson.getString("docid");
	}

	/**
	 * 
	 * 删除文件夹
	 * 
	 * @param parentgns
	 * @throws IOException
	 *             [参数说明]
	 * @throws MroException
	 * 
	 */
	public static void deleteDir(String parentgns) throws IOException,
			MroException {
		// 初始化网盘信息
		netdiskInit();

		String url = "http://" + ip + ":9123/v1/dir?method=delete&userid="
				+ userid + "&tokenid=" + tokenid;

		JSONObject dir = new JSONObject();
		dir.put("docid", parentgns);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("request url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		methodd.releaseConnection();
		if (!strResult.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(strResult);
			if (!retunjson.isEmpty()) {
				if (retunjson.containsKey("errmsg")) {
					throw new MroException("网盘操作失败:"
							+ retunjson.getString("errmsg"));
				}
			}
		}
	}

	// 根据文件路径获取对应gns
	private static String getInfoByPath(String namepath) throws Exception {
		String url = "http://" + ip
				+ ":9123/v1/file?method=getinfobypath&userid=" + userid
				+ "&tokenid=" + tokenid;
		namepath = "SHMRO/" + namepath;
		JSONObject reqbody = new JSONObject();
		reqbody.put("namepath", namepath);
		String reqstr = reqbody.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		StringEntity se = new StringEntity(reqstr, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}

		return retunjson.getString("docid");
	}

	private static ASBeginUploadResult OSBeginupload(String gns, long length,
			String name, int ondup) throws IOException {

		// userid、tokenid 由 auth1?method=getnew 接口返回
		String url = "http://" + ip
				+ ":9123/v1/file?method=osbeginupload&userid=" + userid
				+ "&tokenid=" + tokenid;

		// 构造请求body
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("docid", gns);
		jsonObj.put("length", length);
		jsonObj.put("name", name);
		jsonObj.put("ondup", ondup);

		String bodyStr = jsonObj.toString();

		// 发送请求
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(bodyStr, "UTF-8");
		post.setEntity(se);

		CloseableHttpResponse response = client.execute(post);

		// 解析回复
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		post.releaseConnection();

		// System.out.println("OSBeginupload: " + strResult);

		// 解析返回值
		JSONObject resJson = JSONObject.fromObject(strResult);
		ASBeginUploadResult result = new ASBeginUploadResult();
		// 返回信息有错误，返回null
		if (resJson.containsKey("errcode")) {
			return null;
		}

		JSONArray authRequestArray = resJson.getJSONArray("authrequest");
		result.httpMethod = authRequestArray.getString(0);
		result.url = authRequestArray.getString(1);

		result.headers = new HashMap<String, String>();
		for (int i = 2; i <= 4; ++i) {
			String[] stringArray = authRequestArray.getString(i).split(":", 2);

			String key = stringArray[0].trim();
			String value = stringArray[1].trim();
			result.headers.put(key, value);
		}

		result.docId = resJson.getString("docid");
		result.name = resJson.getString("name");
		result.rev = resJson.getString("rev");

		return result;
	}

	private static String UploadPut(String url,
			HashMap<String, String> headers, File file) throws Exception {
		// 构造httpsclient
		HttpClient client = new SSLClient();
		HttpPut method = new HttpPut(url);
		// System.out.println("UploadPut url: " + url);

		// 打开文件读流
		InputStreamEntity entity = new InputStreamEntity(new FileInputStream(
				file), file.length());

		// 设置http body内容和headers
		HttpPut put = new HttpPut(url);
		put.setEntity(entity);

		for (String key : headers.keySet()) {
			put.addHeader(key, headers.get(key));
		}

		HttpResponse response = client.execute(put);

		// 解析回复
		HttpEntity resEntity = response.getEntity();
		String strResult = EntityUtils.toString(resEntity);

		if (!strResult.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(strResult);
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		put.releaseConnection();

		return strResult;
	}

	private static JSONObject OSEndUpload(String gns, String rev)
			throws IOException, MroException {
		String url = "http://" + ip
				+ ":9123/v1/file?method=osendupload&userid=" + userid
				+ "&tokenid=" + tokenid;
		// uploadjson = {'docid' : gns, 'length' : length, 'name':name,
		// 'client_mtime':clienttime, 'ondup':ondup}
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("rev", rev);
		// dir.put("md5", name);
		// dir.put("clienttime", clienttime);
		// dir.put("ondup", 2);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		// System.out.println("OSEndUpload: " + strResult);
		return retunjson;
	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param uploadFold
	 *            上传文件夹
	 * @param file
	 *            上传文件对象
	 * @throws Exception
	 * 
	 */
	public static File uploadFile(String uploadFold, File file)
			throws Exception {
		// 初始化网盘信息
		netdiskInit();

		// 创建文件夹
		createDir(uploadFold);
		String gns = getInfoByPath(uploadFold);

		// 读取要上传的文件信息
		String fileName = file.getName();
		long length = file.length();
		String filePath = file.getPath();
		file.getAbsolutePath();

		if (length > 104857600) {// 文件大于100M 则掉用大文件上传方法
			String actFileName = UploadBigFile(gns, filePath, fileName);
			if (!fileName.equals(actFileName)) {
				File tmpFile = new File(file.getParentFile().getAbsoluteFile()
						+ "//" + actFileName);
				file.renameTo(tmpFile);
				file = tmpFile;
			}
		} else {
			// 调用file osbeginupload进行上传文件
			ASBeginUploadResult result = OSBeginupload(gns, length, fileName, 1); // 这里如果有重名文件，返回null
			// 返回null后重命名文件，返回修改后的File对象
			while (result == null) {
				String newFileName = renameFile(fileName);
				File tmpFile = new File(file.getParentFile().getAbsoluteFile()
						+ "//" + newFileName);
				file.renameTo(tmpFile);
				file = tmpFile;
				result = OSBeginupload(gns, length, newFileName, 1);
			}

			// 根据服务器返回的对象存储请求，向对象存储上传数据
			UploadPut(result.url, result.headers, file);

			// 调用file endupload结束上传
			OSEndUpload(result.docId, result.rev);
		}
		return file;
	}

	/**
	 * 
	 * 下载文件
	 * 
	 * @param ndPath
	 *            文件网盘路径
	 * @param savepath
	 *            文件保存路径
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static JSONObject Osdownload(String ndPath, String savePath)
			throws Exception {
		// 初始化网盘信息
		netdiskInit();

		String url = "http://" + ip + ":9123/v1/file?method=osdownload&userid="
				+ userid + "&tokenid=" + tokenid;

		String gns = getInfoByPath(ndPath);
		// uploadjson = {'docid' : gns, 'length' : length, 'name':name,
		// 'client_mtime':clienttime, 'ondup':ondup}
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		// dir.put("md5", name);
		// dir.put("clienttime", clienttime);
		// dir.put("ondup", 2);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("download request url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		// System.out.println("Osdownload: " + strResult);
		String downurl = (String) retunjson.getJSONArray("authrequest").get(1);
		String date = ((String) retunjson.getJSONArray("authrequest").get(2))
				.substring(12);
		String length = ((String) retunjson.getJSONArray("authrequest").get(3))
				.substring(14);
		String auth = ((String) retunjson.getJSONArray("authrequest").get(4))
				.substring(14);
		DownloadGet(downurl, date, length, auth, savePath);
		return retunjson;
	}

	private static String DownloadGet(String url, String date, String length,
			String auth, String savepath) throws Exception {
		HttpClient client = null;
		client = new SSLClient();
		HttpGet methodd = new HttpGet(url);
		methodd.addHeader("X-Eoss-Date", date);
		methodd.addHeader("X-Eoss-Length", length);
		methodd.addHeader("Authorization", auth);
		HttpResponse response = client.execute(methodd);
		HttpEntity ba = response.getEntity();
		File mysavepath = new File(savepath);
		mysavepath.createNewFile();

		FileOutputStream out = new FileOutputStream(mysavepath, false);
		out.write(EntityUtils.toByteArray(ba));
		out.close();
		// HttpEntity entity = response.getEntity();
		methodd.releaseConnection();

		return "download path:" + savepath;
	}

	/**
	 * 
	 * 删除文件
	 * 
	 * @param path
	 * @throws Exception
	 * 
	 */
	public static void deleteFile(String path) throws Exception {
		// 初始化网盘信息
		netdiskInit();

		String url = "http://" + ip + ":9123/v1/file?method=delete&userid="
				+ userid + "&tokenid=" + tokenid;

		String parentgns = getInfoByPath(path);
		JSONObject dir = new JSONObject();
		dir.put("docid", parentgns);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);

		if (!strResult.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(strResult);
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		methodd.releaseConnection();

	}

	private static String UploadBigFile(String docgns, String uploadpath,
			String fileName) throws IOException {
		try {
			File f = new File(uploadpath);
			FileInputStream ipt = new FileInputStream(f);
			byte[] buf = new byte[4 * 1024 * 1024];
			long remain = f.length();
			int part = 1;
			int parts = (int) remain / (4 * 1024 * 1024) + 1;
			String divparts = "1-" + Integer.toString(parts);
			String lastgns = docgns;
			JSONObject multres = Osinitmultiupload(docgns, remain, fileName, 2);
			String actFileName = multres.getString("name");
			JSONObject multpart = Osuploadpart((String) multres.get("docid"),
					(String) multres.get("rev"),
					(String) multres.get("uploadid"), divparts);

			JSONObject partinfos = new JSONObject();
			do {
				int len = ipt.read(buf);
				String url = (String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(1);
				String date = ((String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(3))
						.substring(12).trim();
				String auth = ((String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(4))
						.substring(14).trim();

				String multiuploadre = MultiUploadPut(url, null, date, auth,
						buf, len, Integer.toString(part++), null);
				JSONArray partinflist = new JSONArray();
				partinflist.add(multiuploadre);
				partinflist.add(len);

				partinfos.put(Integer.toString(part - 1), partinflist);
				remain -= len;

			} while (part <= parts);

			// compltest uplad mutlit
			String[] multiuploaddone = Oscompleteupload(
					(String) multres.get("docid"), (String) multres.get("rev"),
					(String) multres.get("uploadid"), partinfos);
			String strbody = multiuploaddone[1];

			String res = multiuploaddone[2];
			JSONObject retunjson = JSONObject.fromObject(res);
			String url = (String) retunjson.getJSONArray("authrequest").get(1);
			String contentype = ((String) retunjson.getJSONArray("authrequest")
					.get(2)).substring(13);
			String date = ((String) retunjson.getJSONArray("authrequest")
					.get(3)).substring(12);
			String auth = ((String) retunjson.getJSONArray("authrequest")
					.get(4)).substring(14);
			// System.out.println("partinfos: " + date);

			// create index
			String re = MultiUploadPut(url, contentype, date, auth, null, 1,
					"1", strbody);

			// end uplaoad
			JSONObject endupload = OSEndUpload((String) multres.get("docid"),
					(String) multres.get("rev"));

			return actFileName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static JSONObject Osuploadpart(String gns, String rev,
			String uploadid, String parts) throws IOException, MroException {
		String url = "http://" + ip
				+ ":9123/v1/file?method=osuploadpart&userid=" + userid
				+ "&tokenid=" + tokenid;
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("rev", rev);
		dir.put("uploadid", uploadid);
		// dir.put("clienttime", clienttime);
		dir.put("parts", parts);
		String strdir = dir.toString();
		// System.out.println("reques json: " + strdir);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		// System.out.println("osuploadpart: " + strResult);
		return retunjson;
	}

	private static JSONObject Osinitmultiupload(String gns, long length,
			String name, int ondup) throws IOException, MroException {
		String url = "http://" + ip
				+ ":9123/v1/file?method=osinitmultiupload&userid=" + userid
				+ "&tokenid=" + tokenid;
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("length", length);
		dir.put("name", name);
		// dir.put("clienttime", clienttime);
		dir.put("ondup", 2);
		String strdir = dir.toString();

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		HttpEntity entity = response.getEntity();
		String strResult = EntityUtils.toString(entity);
		JSONObject retunjson = JSONObject.fromObject(strResult);
		methodd.releaseConnection();

		if (!retunjson.isEmpty()) {
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		// System.out.println("Osbeginupload: " + strResult);
		return retunjson;
	}

	private static String MultiUploadPut(String url, String conttype,
			String data, String auth, byte[] body, int len, String parts,
			String strbody) throws Exception {
		HttpClient client = null;
		client = new SSLClient();
		String re = "";
		String strResult = "";

		// HttpPut methodd = new HttpPut(url);
		if (body != null) {
			HttpPut methodd = new HttpPut(url);
			ByteArrayEntity se = new ByteArrayEntity(body, 0, len);

			methodd.setEntity(se);
			methodd.addHeader("content-type", "application/octet-stream");
			methodd.addHeader("X-Eoss-Date", data);
			methodd.addHeader("Authorization", auth);
			HttpResponse response = client.execute(methodd);
			HttpEntity entity = response.getEntity();
			strResult = EntityUtils.toString(entity);
			re = strResult;
			if (conttype == null) {
				re = response.getFirstHeader("Etag").toString().substring(6);
			}
		} else {
			HttpPost methodd = new HttpPost(url);
			methodd.addHeader("content-type", conttype);
			methodd.addHeader("X-Eoss-Date", data);
			methodd.addHeader("Authorization", auth);
			StringEntity se = new StringEntity(strbody, "UTF-8");
			methodd.setEntity(se);
			HttpResponse response = client.execute(methodd);
			HttpEntity entity = response.getEntity();
			strResult = EntityUtils.toString(entity);
			re = strResult;
		}
		// methodd.setEntity(se);

		if (!strResult.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(strResult);
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		// methodd.releaseConnection();
		return re;
	}

	private static String[] Oscompleteupload(String gns, String rev,
			String uploadid, JSONObject partinfo) throws IOException,
			MroException {
		String url = "http://" + ip
				+ ":9123/v1/file?method=oscompleteupload&userid=" + userid
				+ "&tokenid=" + tokenid;
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("rev", rev);
		dir.put("uploadid", uploadid);
		dir.put("partinfo", partinfo);
		String strdir = dir.toString();
		// System.out.println("oscompleteuploadjson: " + strdir);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost methodd = new HttpPost(url);
		// System.out.println("reques url: " + url);
		StringEntity se = new StringEntity(strdir, "UTF-8");
		methodd.setEntity(se);

		CloseableHttpResponse response = client.execute(methodd);
		// System.out.println("Osbeginuploadcont: " + response);

		String boundary = response.getFirstHeader("Content-Type").toString()
				.substring(43);
		HttpEntity entity = response.getEntity();
		String sss = EntityUtils.toString(entity);
		// System.out.println("Osbeginuploadcont: " + boundary);

		String[] strResult = sss.split("--" + boundary);
		// String [] strResult = sss.split("--");
		methodd.releaseConnection();

		if (!sss.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(sss);
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		return strResult;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * System.out.println("Working Directory = " +
		 * System.getProperty("user.dir"));
		 */

		/*
		 * String ip = "fc.teg.cn"; String account = "shmro"; String password =
		 * "shmro.CSR2018";
		 */

		// 1.调用GetNew协议进行登录
		// NetDiskUtil nd = new NetDiskUtil();
		// nd.logIn(account, password);

		// 2.获取入口文档

		// ArrayList<ASEntryDoc> entryDocs = nd.GetEntryDoc();

		// 3.在入口文档中找到个人文档, docType == "userdoc"
		// String userDocGnsPath = "";
		// for (int i = 0; i < entryDocs.size(); i++) {
		// ASEntryDoc tmpObj = entryDocs.get(i);
		// if (tmpObj.docType.equals("userdoc")) {
		// userDocGnsPath = tmpObj.docId;
		// }
		// }

		// if (userDocGnsPath.equals("")) {
		// System.out.println("没有找到个人文档");
		// return;
		// }

		// 创建文件夹
		// createDir("ZHU/TEST");

		// 删除文件夹
		// String gns = getInfoByPath("ATTACHMENTS");
		// DeleteDir(gns);

		// 上传文件
		// 读取要上传的文件信息
		// String uploadPath = "P:\\DOC\\开发文档\\网盘系统\\2.txt";
		// File file = new File(uploadPath);
		// uploadFile("ATTACHMENTS", file);

		// 下载文档， 1. 根据文件路径获取对应gns
		// Osdownload("ATTACHMENTS/Desert.jpg", "P:\\download\\2.jpg");

		// 删除文件
		// deleteFile("ATTACHMENTS/2.txt");

		// 获取文件路径
		// System.out.println("========" + getInfoByPath("ATTACHMENTS/201806"));
		String path = "P:/MRO/DOCLINKS/DOWNLOADS/故障数据包3.zip";
		path = path.replaceAll("\\/", "\\\\");
		File file = new File(path);
		boolean flag = false;
		if (file.exists() && file.isFile()) {
			flag = file.delete();
		}
		System.out.println(flag);
	}

	/**
	 * 重名文件重命名 <功能描述>
	 * 
	 * @param fileName
	 * @return [参数说明] 修改后的文件名
	 * 
	 */
	private static String renameFile(String fileName) {
		String extension = "";
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			extension = fileName.substring(index);
			fileName = fileName.substring(0, index);
		}

		String uniqueFileName = "";
		long timeInMillis = System.currentTimeMillis();
		uniqueFileName = fileName + timeInMillis + extension;

		return uniqueFileName;
	}

	/**
	 * 移动端上传使用 <功能描述>
	 * 
	 * @param uploadFold
	 * @param b
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static String uploadFile(String uploadFold, byte[] b, String fileName)
			throws Exception {
		// 初始化网盘信息
		netdiskInit();

		// 创建文件夹
		createDir(uploadFold);
		String gns = getInfoByPath(uploadFold);

		long length = b.length;
		String finFileName = fileName;

		if (length > 104857600) {// 文件大于100M 则掉用大文件上传方法
			String actFileName = UploadBigFile(gns, b, fileName);
			if (!fileName.equals(actFileName)) {
				finFileName = actFileName;
			}
		} else {
			// 调用file osbeginupload进行上传文件
			ASBeginUploadResult result = OSBeginupload(gns, length, fileName, 1); // 这里如果有重名文件，返回null
			// 返回null后重命名文件，返回修改后的File对象
			while (result == null) {
				String newFileName = renameFile(fileName);
				finFileName = newFileName;
				result = OSBeginupload(gns, length, newFileName, 1);
			}

			// 根据服务器返回的对象存储请求，向对象存储上传数据
			UploadPut(result.url, result.headers, b);

			// 调用file endupload结束上传
			OSEndUpload(result.docId, result.rev);
		}
		return finFileName;
	}

	/**
	 * 移动端上传使用 <功能描述>
	 * 
	 * @param url
	 * @param headers
	 * @param b
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	private static String UploadPut(String url,
			HashMap<String, String> headers, byte[] b) throws Exception {
		// 构造httpsclient
		HttpClient client = new SSLClient();
		HttpPut method = new HttpPut(url);
		// System.out.println("UploadPut url: " + url);

		// 打开文件读流
		InputStreamEntity entity = new InputStreamEntity(
				new ByteArrayInputStream(b), b.length);

		// 设置http body内容和headers
		HttpPut put = new HttpPut(url);
		put.setEntity(entity);

		for (String key : headers.keySet()) {
			put.addHeader(key, headers.get(key));
		}

		HttpResponse response = client.execute(put);

		// 解析回复
		HttpEntity resEntity = response.getEntity();
		String strResult = EntityUtils.toString(resEntity);

		if (!strResult.isEmpty()) {
			JSONObject retunjson = JSONObject.fromObject(strResult);
			if (retunjson.containsKey("errmsg")) {
				throw new MroException("网盘操作失败:"
						+ retunjson.getString("errmsg"));
			}
		}
		put.releaseConnection();

		return strResult;
	}

	/**
	 * 移动端上传使用 <功能描述>
	 * 
	 * @param docgns
	 * @param b
	 * @param fileName
	 * @return
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	private static String UploadBigFile(String docgns, byte[] b, String fileName)
			throws IOException {
		try {
			InputStream ipt = new ByteArrayInputStream(b);
			byte[] buf = new byte[4 * 1024 * 1024];
			long remain = b.length;
			int part = 1;
			int parts = (int) remain / (4 * 1024 * 1024) + 1;
			String divparts = "1-" + Integer.toString(parts);
			String lastgns = docgns;
			JSONObject multres = Osinitmultiupload(docgns, remain, fileName, 2);
			String actFileName = multres.getString("name");
			JSONObject multpart = Osuploadpart((String) multres.get("docid"),
					(String) multres.get("rev"),
					(String) multres.get("uploadid"), divparts);

			JSONObject partinfos = new JSONObject();
			do {
				int len = ipt.read(buf);
				String url = (String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(1);
				String date = ((String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(3))
						.substring(12).trim();
				String auth = ((String) multpart.getJSONObject("authrequests")
						.getJSONArray(Integer.toString(part)).get(4))
						.substring(14).trim();

				String multiuploadre = MultiUploadPut(url, null, date, auth,
						buf, len, Integer.toString(part++), null);
				JSONArray partinflist = new JSONArray();
				partinflist.add(multiuploadre);
				partinflist.add(len);

				partinfos.put(Integer.toString(part - 1), partinflist);
				remain -= len;

			} while (part <= parts);

			// compltest uplad mutlit
			String[] multiuploaddone = Oscompleteupload(
					(String) multres.get("docid"), (String) multres.get("rev"),
					(String) multres.get("uploadid"), partinfos);
			String strbody = multiuploaddone[1];

			String res = multiuploaddone[2];
			JSONObject retunjson = JSONObject.fromObject(res);
			String url = (String) retunjson.getJSONArray("authrequest").get(1);
			String contentype = ((String) retunjson.getJSONArray("authrequest")
					.get(2)).substring(13);
			String date = ((String) retunjson.getJSONArray("authrequest")
					.get(3)).substring(12);
			String auth = ((String) retunjson.getJSONArray("authrequest")
					.get(4)).substring(14);
			// System.out.println("partinfos: " + date);

			// create index
			String re = MultiUploadPut(url, contentype, date, auth, null, 1,
					"1", strbody);

			// end uplaoad
			JSONObject endupload = OSEndUpload((String) multres.get("docid"),
					(String) multres.get("rev"));

			return actFileName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
