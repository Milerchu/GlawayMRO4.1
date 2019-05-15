package com.glaway.sddq.back.msgConfig.bean;

import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.msgConfig.data.MsgConfig;

/**
 * 
 * 消息处理基础实现类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public class BaseMsgConfig implements IMsgConfig
{
    
    @Override
    public void execute(IJpo jpo)
        throws MroException
    {
        String condition = jpo.getString("CONDITION");
        String msgnum = jpo.getString("MSGNUM");
        String app = jpo.getString("APP");
        String tablename = jpo.getString("SYSAPP.MAINTBNAME");
        String subjecttype = jpo.getString("SUBJECTTYPE");
        String contenttype = jpo.getString("CONTENTTYPE");
        String homepage = jpo.getString("HOMEPAGE");
        String msg = jpo.getString("MSG");
        String email = jpo.getString("EMAIL");
        String subject = "";
        String content = "";
        //获取满足条件的记录
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(tablename);
        jposet.setUserWhere(condition);
        jposet.reset();
        MsgConfig msgconfig = (MsgConfig)jpo;
        //获取要发送的人员
        List<String> userlist = msgconfig.getSendUser();
        
       // IJpoSet msgJposet = MroServer.getMroServer().getCronTaskUserServer().getJpoSet("MSGMANAGE");
        MroServer.getMroServer().getCronTaskUserServer().getUserInfo().setDefaultOrg("CRRC");
        MroServer.getMroServer().getCronTaskUserServer().getUserInfo().setDefaultSite("ELEC");
        IJpoSet  msgJposet = MroServer.getMroServer().getJpoSet("MSGMANAGE", MroServer.getMroServer().getCronTaskUserServer());
        
        for (int i = 0; i < jposet.count(); i++)
        {
            IJpo curjpo = jposet.getJpo(i);
            long id = curjpo.getId();
            if (subjecttype.equals("1"))
            {
                subject = jpo.getString("SUBJECT");
            }
            else
            {
                subject = curjpo.getString(jpo.getString("SUBJECT"));
            }
            if (contenttype.equals("1"))
            {
                content = jpo.getString("CONTENT");
            }
            else
            {
                content = curjpo.getString(jpo.getString("CONTENT"));
            }
            for (int j = 0; j < userlist.size(); j++)
            {
                if (homepage.equals("1"))
                {
                    //创建之前，先判断是否已经存在该条消息记录
                    if(isExist(id,app,msgnum,userlist.get(j))){
                        IJpo msgjpo = msgJposet.addJpo(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        msgjpo.setValue("appid", id);
                        msgjpo.setValue("app", app);
                        msgjpo.setValue("msgnum", msgnum);
                        msgjpo.setValue("subject", subject);
                        msgjpo.setValue("content", content);
                        msgjpo.setValue("receiver", userlist.get(j));
                    }
                }
                if (msg.equals("1"))
                {
                    //发送短信功能
                    System.out.println("发送短信");
                }
                if (email.equals("1"))
                {
                    //发送邮件功能
                    System.out.println("发送邮件");
                }
            }
        }
        msgJposet.save();
    	
    }
    
    
    /**
     * 判断消息表中是否已经生成消息
     */
    public boolean isExist(long appid, String app, String msgnum, String receiver)
        throws MroException
    {
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("MSGMANAGE");
        jposet.setUserWhere("appid='" + appid + "' and app='" + app + "' and msgnum='" + msgnum + "' and receiver='"
            + receiver + "'");
        jposet.reset();
        if (jposet.isEmpty())
        {
            return true;
        }
        return false;
    }
}
