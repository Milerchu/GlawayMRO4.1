package com.glaway.sddq.tools.mobiletools;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.Base64Helper;
import com.glaway.sddq.tools.NetDiskUtil;

/**
 * 
 * 移动端上传附件处理类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class MobileUploadFile {

	/**
	 * 
	 * 文件上传至Mro服务器硬盘上
	 * 
	 * @param param
	 * @param is
	 * @return
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public static MobileResult uploadToMroDisk(String imgStr) {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		if (StringUtil.isStrEmpty(imgStr)) {
			result.setCode("400");
			result.setMsg("图片为空，请检查");
			return result;
		}

		Date date = MroServer.getMroServer().getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timestamp = sdf.format(date);
		String fileName = "mobile" + timestamp + ".jpg";
		imgStr = imgStr.replace(" ", "+");

		byte[] b = Base64Helper.getByte(imgStr);

		// 上传图片异常状况检查
		// if(!ImageHelper.check(b)){
		// result.setCode("402");
		// result.setMsg("图片已损坏，请重新上传");
		// return result;
		// }

		// Base64Helper.generateImage("C:/image.jpg", imgStr);

		String dir = getMroDir();
		String[] dirs = dir.split("/");
		String newdir = dirs[dirs.length - 2] + "/" + dirs[dirs.length - 1];
		// 网盘上传
		try {
			fileName = NetDiskUtil.uploadFile(newdir, b, fileName);
		} catch (Exception e) {
			result.setCode("401");
			result.setMsg(e.getMessage());
		}
		try {
			// 唯一id在这个表中
			IJpoSet docInfoSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_DOCINFO");
			IJpo docInfo = docInfoSet.addJpo();
			docInfo.setValue("DOCTYPE", "ATTACHMENTS",
					GWConstant.P_NOVALIDATION);
			docInfo.setValue("CHANGEDATE", MroServer.getMroServer().getDate());
			docInfo.setValue("CREATEDATE", MroServer.getMroServer().getDate());
			docInfo.setValue("URLTYPE", "文件");
			docInfo.setValue("DESCRIPTION", fileName);
			docInfo.setValue("URLNAME", dir + "/" + fileName);
			String id = docInfo.getString("document");
			docInfoSet.save();

			IJpoSet docSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_DOCLINKS");
			IJpo doc = docSet.addJpo();
			doc.setValue("DOCTYPE", "ATTACHMENTS", GWConstant.P_NOVALIDATION);
			doc.setValue("CHANGEDATE", MroServer.getMroServer().getDate());
			doc.setValue("CREATEDATE", MroServer.getMroServer().getDate());
			doc.setValue("DESCRIPTION", fileName);
			doc.setValue("DOCUMENT", id);
			doc.setValue("ownertable", "0", GWConstant.P_NOVALIDATION);
			doc.setValue("ownerid", "0", GWConstant.P_NOVALIDATION);
			doc.setValue("DOCINFOID", id, GWConstant.P_NOVALIDATION);
			docSet.save();
			ret.put("fileName", fileName);
			ret.put("fileSize", b.length);
			ret.put("uniqueId", id);
		} catch (MroException e) {
			result.setCode("401");
			result.setMsg(e.getMessage());
		}
		result.setData(ret);
		return result;
	}

	// 根据Mro逻辑获取文件夹路径
	private static String getMroDir() {
		// String rootDir = MroServer.getMroServer().getSysPropCache()
		// .getPropValue("mro.doc.rootfolder");
		String rootDir = "C:/MRO/DOCLINKS/";
		Calendar c = Calendar.getInstance();

		int monthInt = c.get(Calendar.MONTH) + 1;
		String month = monthInt > 9 ? monthInt + "" : "0" + monthInt;
		// doctype为为默认ATTACHMENTS不可修改
		return rootDir + "ATTACHMENTS/" + c.get(Calendar.YEAR) + month;
	}
}
