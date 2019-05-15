package com.glaway.sddq.tools;

import java.util.Calendar;
import java.util.Date;

import com.glaway.mro.app.system.user.data.SysUser;
import com.glaway.mro.app.system.user.data.SysUserSet;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysVarCache;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringMD5Util;

public class UpDatePassword {
	
	public   void up2(){
		UpDatePassword up= new UpDatePassword();
		String password="111111";
		String users="'gwadmin','sysadmin','secadmin','audadmin','yangyi','hyhe','xiaolinbao','zhouzhixiong','20110712','chenbin','liyifan','haungkai','zhouwei'";
		String where=" loginid not in ("+users+") ";
		try {
			SysUserSet userSet= (SysUserSet) MroServer.getMroServer().getJpoSet("sys_user", MroServer.getMroServer().getSystemUserServer());
			userSet.setUserWhere(where);
			userSet.reset();
			System.out.println("需要更新"+userSet.count()+"个用户密码");
			if(!userSet.isEmpty())
			{
				for (int i = 0; i < userSet.count(); i++) {
					SysUser user = (SysUser) userSet.getJpo(i);
					System.out.println("开始更新["+user.getString("userid")+" ]用户密码");
					up.updatePassword(user, password);
					System.out.println("更新完成");
				}
			}
			userSet.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  static void main(String[] args){
		UpDatePassword up= new UpDatePassword();
		String password="111111";
		String users="'gwadmin','sysadmin','secadmin','audadmin','yangyi','hyhe','xiaolinbao','zhouzhixiong','20110712','chenbin','liyifan','haungkai',zhouwei";
		String where=" loginid not in ("+users+") ";
		try {
			SysUserSet userSet= (SysUserSet) MroServer.getMroServer().getJpoSet("sys_user", MroServer.getMroServer().getSystemUserServer());
			userSet.setUserWhere(where);
			userSet.reset();
			System.out.println("需要更新"+userSet.count()+"个用户密码");
			if(!userSet.isEmpty())
			{
				for (int i = 0; i < userSet.count(); i++) {
					SysUser user = (SysUser) userSet.getJpo(i);
					System.out.println("开始更新["+user.getString("userid")+" ]用户密码");
					up.updatePassword(user, password);
					System.out.println("更新完成");
				}
			}
			userSet.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public   void up(){
//		UpDatePassword up= new UpDatePassword();
		String password="111111";
		String where=" loginid not in ('gwadmin','sysadmin','secadmin','audadmin','user001','wuzhijing','zhouwei','wubin','heqin','libin') ";
		try {
			SysUserSet userSet= (SysUserSet) MroServer.getMroServer().getJpoSet("sys_user", MroServer.getMroServer().getSystemUserServer());
			userSet.setUserWhere(where);
			userSet.reset();
			System.out.println("需要更新"+userSet.count()+"个用户密码");
			if(!userSet.isEmpty())
			{
				for (int i = 0; i < userSet.count(); i++) {
					SysUser user = (SysUser) userSet.getJpo(i);
					System.out.println("开始更新["+user.getString("userid")+" ]用户密码");
					updatePassword(user, password);
					System.out.println("更新完成");
				}
				
			}
			userSet.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void updatePassword(SysUser user ,String password)
	        throws MroException
	    {
	        String username = user.getString("userid");
	        String passwordMD5Str = StringMD5Util.genStrMD5ByLength(username, password);
	        // 密码使用数据库类型CRYPTOX 未提供setvalue接收String 参数值,需要转成byte[]
	        user.setValue("PASSWORD", passwordMD5Str.getBytes(), GWConstant.P_NOVALIDATION_AND_NOACTION);
	        SysVarCache varCache = MroServer.getMroServer().getSysVarCache();
	        if (!varCache.isNull("PASSWORDDURATION"))
	        {
	            int passwordduration = varCache.getIntVarValue("PASSWORDDURATION");
	            Calendar cal = Calendar.getInstance();
	            Date currDate = MroServer.getMroServer().getDate();
	            cal.setTime(currDate);
	            cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + passwordduration));
	            Date pwexpiration = cal.getTime();
	            user.setValue("pwexpiration", pwexpiration, GWConstant.P_NOVALIDATION_AND_NOACTION);
	        }
	    }
	

}
