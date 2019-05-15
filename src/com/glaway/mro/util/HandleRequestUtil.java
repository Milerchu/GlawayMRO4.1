package com.glaway.mro.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * <功能描述>
 * 判断内外网
 * 
 * @author  yangyi
 * @version  [版本号, 2018-9-29]
 * @since  [产品/模块版本]
 */
public class HandleRequestUtil {
	/**
	 * 
	 * 检查IP网段，是否为外网
	 * @param ipAdds
	 * @return [true 为外网， false 为内网]
	 *
	 */
	public boolean checkIsIntranet(HttpServletRequest req){
		String clientAdds=getIpAdrress(req);
		return checkByIP(clientAdds);
//		String servname = req.getHeader("Referer");
//		String  remotedomain =  MroServer.getMroServer().getSysProp("mro.firstdomainname");
//		if(servname.contains(remotedomain)){
//			return true;
//		}
//		return false;
	}
	
	/**
	 * 
	 * 检查IP网段，是否为外网
	 * @param ipAdds
	 * @return [true 为外网， false 为内网]
	 *
	 */
	private static boolean checkByIP(String ipAdds){
		int position1=ipAdds.indexOf(".");
		int position2=ipAdds.indexOf(".",position1+1);
		String firstIP=ipAdds.substring(0,position1);
		String secndIP=ipAdds.substring(position1+1,position2);
		if(firstIP==null && secndIP==null){
			return false;
		}
		int secondNum=Integer.parseInt(secndIP);
//		1)         10.96.0.0/13(10.96.0.0～10.103.255.255)
//		2)         10.176.0.0/12 (10.176.0.0～10.191.255.255)
//		3)         10.12.0.0/16(10.12.0.0～10.12.255.255)
		if("10".equals(firstIP)){
			if(secondNum<=103&&secondNum>=96){
				return false;
			}else if(secondNum<=191&&secondNum>=176){
				return false;
			}else if(secondNum==12){
				return false;
			}else{
				return true;
			}
		}else if("127".equals(firstIP)){
			return false;
		}
		else{
			return true;
		}
	}
	
	private static String getIpAdrress(HttpServletRequest request) {
		  String Xip = request.getHeader("X-Real-IP");
		  String XFor = request.getHeader("X-Forwarded-For");
		  if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
		   int index = XFor.indexOf(",");
		   if(index != -1){
		    return XFor.substring(0,index);
		   }else{
		    return XFor;
		   }
		  }
		  XFor = Xip;
		  if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
		   return XFor;
		  }
		  if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
		   XFor = request.getHeader("Proxy-Client-IP");
		  }
		  if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
		   XFor = request.getHeader("WL-Proxy-Client-IP");
		  }
		  if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
		   XFor = request.getHeader("HTTP_CLIENT_IP");
		  }
		  if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
		   XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		  }
		  if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
		   XFor = request.getRemoteAddr();
		  }
		  return XFor;
		 }
}
