package com.glaway.sddq.back.Interface.webservice.comm;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringMD5Util;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.Result;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/mro")
public class MroUserAuthenticateService {
	@POST
	@Path("/authenticateuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String authenticateUser(@FormParam("userid") String userid,
			@FormParam("password") String password) throws MroException {
		Result result = new Result();
		if (userid == null || userid.isEmpty() || password == null || password.isEmpty() ) {
			result.setStateCode("401");
			result.setMessage("请求参数错误:缺少用户名或密码");
			return result.toString();
		}
		try {
			JpoSet sysUserSet =
		            (JpoSet)MroServer.getMroServer().getSysJpoSet("SYS_USER", " LOGINID ='" + StringUtil.getSafeSqlStr(userid) + "' ");
			sysUserSet.reset();
		    if (!sysUserSet.isEmpty())
            {
                Jpo user = (Jpo)sysUserSet.getJpo();
                byte[] dbpassByte = user.getBytes("PASSWORD");
                String dbpasswordStr = new String(dbpassByte);
                byte[] passwordbytes = password.getBytes();
                String inputPassword = StringMD5Util.genStrMD5ByLength(user.getString("USERID"), new String(passwordbytes));
                if (StringUtil.isEqual(dbpasswordStr, inputPassword)){
					result.setActionResult(true);
					result.setStateCode("200");
					result.setMessage("验证成功！");
                }else{
					result.setActionResult(false);
					result.setStateCode("401");
					result.setMessage("验证失败：密码错误！");
                }
            }else{
		    	result.setActionResult(false);
				result.setStateCode("401");
				result.setMessage("验证失败：用户名不存在！");
			}
		} catch (MroException e) {
			result.setStateCode("409");
			result.setMessage("其他错误："+e.getMessage());
		} finally {
			if (result.getStateCode().equals("200")) {
				result.setActionResult(true);
			}
		}
		return result.toString();
	}
}