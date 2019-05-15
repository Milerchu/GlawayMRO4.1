package com.glaway.sddq.service.servplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <干系人标签页绑定类>
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-20]
 * @since  [产品/模块版本]
 */
public class RelatedPeopleDataBean extends DataBean
{
       public int getRelatedperon() throws MroException, IOException{
           String projectnum = this.page.getAppBean().getJpo().getString("PROJECTNUM");
           IJpoSet projectinfoset = MroServer.getMroServer().getJpoSet("PROJECTINFO", MroServer.getMroServer().getSystemUserServer());
           IJpoSet relatedpeopleset = MroServer.getMroServer().getJpoSet("RELATEDPEOPLE", MroServer.getMroServer().getSystemUserServer());
           IJpoSet relatedproblemMgset = MroServer.getMroServer().getJpoSet("RELATEDPROBLEMMG", MroServer.getMroServer().getSystemUserServer());
           DataBean gxrdataBean = this.page.getAppBean().getDataBean("1508483297336");
           DataBean gxrprobdataBean = this.page.getAppBean().getDataBean("1508483300524");
           
           projectinfoset.setQueryWhere("PROJECTNUM = '"+projectnum+"'");
           projectinfoset.reset();
           if(!projectinfoset.isEmpty()){
              IJpo projectinfo =  projectinfoset.getJpo();
              IJpoSet relatedPeoplergset = projectinfo.getJpoSet("RELATEDPEOPLEREG");
              if(!relatedPeoplergset.isEmpty()){
                  relatedpeopleset.setQueryWhere("SERVPLANNUM = '"+this.page.getAppBean().getJpo().getString("SERVPLANNUM")+"'");
                  relatedpeopleset.reset();
                  relatedpeopleset.deleteAll();
                  relatedpeopleset.save();
                  for(int i =0;i<relatedPeoplergset.count();i++){
                      IJpo relatedpeoplereg = relatedPeoplergset.getJpo(i);
                      IJpo relatedperson = relatedpeopleset.addJpo();
//                      relatedpeopleset.
                      relatedperson.setValue("DISPLAYNAME", relatedpeoplereg.getString("RELATEDPEOPLENAME"));
                      relatedperson.setValue("POSITION", relatedpeoplereg.getString("POSITION"));
                      relatedperson.setValue("ACTIVITIES", relatedpeoplereg.getString("ACTIVITIES"));
                      relatedperson.setValue("IMPORTANCE", relatedpeoplereg.getString("IMPORTANCE"));
                      relatedperson.setValue("SERVPLANNUM", this.page.getAppBean().getJpo().getString("SERVPLANNUM"));
                      IJpoSet customercontactorset = relatedpeoplereg.getJpoSet("CUSTOMERCONTACTOR");
                      if(!customercontactorset.isEmpty()){
                          IJpo customercontactor = customercontactorset.getJpo();
                          IJpoSet custinfoset = customercontactor.getJpoSet("CUSTINFO");
                          if(!custinfoset.isEmpty()){
                              IJpo custinfo  = custinfoset.getJpo();
                              IJpoSet interviewtableset = custinfo.getJpoSet("INTERVIEWTABLE");
                              for(int j =0;j<interviewtableset.count();j++){
                                  IJpo itjpo = interviewtableset.getJpo();
                                  IJpo rpmjpo = relatedproblemMgset.addJpo();
                                  rpmjpo.setValue("DISPLAYNAME",relatedpeoplereg.getString("RELATEDPEOPLENAME"));
                                  rpmjpo.setValue("COMPANY",relatedpeoplereg.getString("CUSTNAME"));
                                  rpmjpo.setValue("POSITION",relatedpeoplereg.getString("POSITION"));
                                  rpmjpo.setValue("PROBLEMDESC",itjpo.getString("INTERVIEWPURPOSE"));
                                  rpmjpo.setValue("RESOLUTION",itjpo.getString("DEALSITUATION"));
                                  rpmjpo.setValue("RELATEDPEOPLENUM", relatedperson.getString("RELATEDPEOPLENUM"));
                                  rpmjpo.setValue("SERVPLANNUM", this.page.getAppBean().getJpo().getString("SERVPLANNUM"));
                              }
                          }
                      }
                  }
              }
           }
           relatedpeopleset.save();
           relatedproblemMgset.save();
           gxrdataBean.resetAndReload();
           gxrprobdataBean.resetAndReload();

           return GWConstant.NOACCESS_SAMEMETHOD;
       }
}
