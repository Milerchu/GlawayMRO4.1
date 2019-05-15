package com.glaway.sddq.config.sbom.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaway.mro.app.imp.bean.data.ImpObjectIface;
import com.glaway.mro.app.system.doclinks.bean.AddDoclinkBean;
import com.glaway.mro.app.system.doclinks.data.SysDocinfo;
import com.glaway.mro.app.system.doclinks.data.SysDoclink;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.Property;
import com.glaway.mro.util.StringUtil;
import com.glaway.mro.util.UploadUtil;

public class PSBOMImportBean extends AddDoclinkBean
{
    
    @Override
    public int uploadfile()
        throws Exception
    {
        
        long doclinksid = 0l;
        if (dataBean != null)
        {
            getMroSession().setIframeResponse(true);
            checkSave();
            if (currJpo != null)
            {
                currJpo.getString("DOCTYPE");
                doclinksid = currJpo.getId();
                if (currJpo.isNull("DOCTYPE"))
                {
                    String[] p = {"DOCTYPE"};
                    throw new AppException("system", "null", p);
                }
                UploadUtil upload = new UploadUtil(getMroSession().getRequest());
                upload.setMroDirectoryName(getString("DOCTYPE"));
                String ip = getMroSession().getRequest().getRemoteAddr();
                if (currJpo instanceof SysDoclink)
                {
                    HashSet<String[]> hs = upload.writeToDisk();
                    for (String[] s : hs)
                    {
                        String content = Property.UPLOADFILE_LABLE + "\"" + s[0] + "\"";
                        writeAuditLog(getAppName(),
                            content,
                            Property.UPLOADFILE_LABLE,
                            true,
                            "",
                            currJpo.getName(),
                            ip,
                            false);
                        if (dataBean.getParent() != null)
                        {
                            IJpo parent = dataBean.getParent().getJpo();
                            ((SysDoclink)currJpo).addDoclink(parent, s[0], s[2], s[0],"");
                            dataBean.getParent().putDocliksMap(currJpo.getId(), "A");
                            getAppBean().putDocliksMap(currJpo.getId(), "A");
                        }
                    }
                    doclinkSet.save();
                }
                if (currJpo instanceof SysDocinfo)
                {
                    if (!currJpo.isNull("DOCUMENT"))
                    {
                        HashSet<String[]> hs = upload.writeToDisk();
                        for (String[] s : hs)
                        {
                            String content = Property.UPLOADFILE_LABLE + "\"" + s[0] + "\"";
                            writeAuditLog(getAppName(),
                                content,
                                Property.UPLOADFILE_LABLE,
                                true,
                                "",
                                currJpo.getName(),
                                ip,
                                false);
                            ((SysDocinfo)currJpo).addDocinfo(s[0], s[2], s[0],"");
                            if (dataBean.getParent() != null)
                            {
                                dataBean.getParent().putDocliksMap(currJpo.getId(), "A");
                                getAppBean().putDocliksMap(currJpo.getId(), "A");
                            }
                        }
                        doclinkSet.save();
                    }
                    else
                    {
                        String[] p = {getJpoSet().getFieldInfo("DOCUMENT").getTitle()};
                        throw new AppException("system", "null", p);
                    }
                }
            }
            dataBean.reset();
            if (dataBean.getJpo() != null)
            {
                dataBean.getJpo().setValue("CHANGEDATE", getSysDate());
                dataBean.getJpo().setValue("CHANGEBY", getUserInfo().getPersonId());
            }
            dialogclose();
        }
        String ownerTable = dataBean.getJpo().getString("OWNERTABLE");
        
        if (!StringUtil.isNull(ownerTable) && "ASSETTMP".equals(ownerTable))
        {
            IJpoSet doclinksSet = dataBean.getJpo().getJpoSet();
            doclinksSet.setOrderBy("createdate desc");
            doclinksSet.reset();
            IJpo doclinkJpo = doclinksSet.getJpo(0);
            IJpoSet docinfoSet = doclinkJpo.getJpoSet("SYS_DOCINFO");
            //          docinfoSet.count();
            //          docinfoSet.setOrderBy("createdate");
            //          docinfoSet.reset();
            IJpo docinfojpo = null;
            String filename = null;
            if (!docinfoSet.isEmpty())
            {
                docinfojpo = docinfoSet.getJpo(0);
                filename = docinfojpo.getString("URLNAME");
            }
            //          ImpIfaceDataBean impdatabean = new ImpIfaceDataBean();
            //          impdatabean.impData(filename);
            //      impdatabean.impdata();
            impData(filename, doclinksid);
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
        
    }
    
    public void impData(String filename, long doclinksid)
        throws IOException, MroException, SQLException, InstantiationException, IllegalAccessException,
        ClassNotFoundException
    {
        Workbook workbook = null;
        
        try
        {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            
            if (file.getName().toLowerCase().endsWith(".xlsx"))
            {
                workbook = new XSSFWorkbook(fis);
            }
            else
            {
                workbook = new HSSFWorkbook(fis);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        String impclass = "com.glaway.sddq.config.sbom.imp.ImportAssetTmpIface";
        
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null)
        {
            return;
        }
        
        ImpObjectIface impjpo = new ImpObjectIface();
        if (impclass != null)
        {
            impjpo = (ImpObjectIface)Class.forName(impclass).newInstance();
        }
        else
        {
            impjpo = new ImpObjectIface();
        }
        impjpo.setDoclinksid(doclinksid);
        impjpo.setIfacename("CSBOMIMP");
        impjpo.setObjectname("ASSETCS");
        IJpoSet imprltset = MroServer.getMroServer().getSysJpoSet("imprelation");
        imprltset.setUserWhere("IFACENAME = 'PRODUCTSBOM' AND OBJECTNAME='ASSETTMP'");
        imprltset.reset();
        IJpo imprltjpo = imprltset.getJpo(0);
        
        HashMap<String, String> returninfo = impjpo.addJpo(workbook, imprltjpo);
        String successinfo = returninfo.get("successMessage");
        String errorInfo = returninfo.get("errorMessage");
        if (!"".equals(successinfo))
        {
            throw new MroException("", successinfo);
        }
        else if (!"".equals(errorInfo))
        {
            throw new MroException("impdata", "impfail", new String[] {"批处理中出现错误。"});
        }
    }
    
}
