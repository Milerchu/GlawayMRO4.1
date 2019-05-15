package com.glaway.mro.app.imp.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaway.mro.app.imp.bean.data.ImpObjectIface;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

public class ImpIfaceDataBean extends DataBean
{
    
    @Override
    public int addrow()
        throws MroException, IOException
    {
        if (this.getAppBean().getJpo() != null && !this.getAppBean().getJpo().toBeAdded())
        {
            return super.addrow();
        }
        else
        {
            throw new AppException("imp", "beforesave");
        }
    }
    
    public void impData(String filename, long doclinksid)
        throws IOException, MroException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
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
            EXCEPTIONLOGGER.error(e);
        }
        
        String impclass = getJpo().isNull("IMPCLASS") ? null : getJpo().getString("IMPCLASS");
        Sheet sheet = null;
        if (workbook != null)
        {
            sheet = workbook.getSheetAt(0);
        }
        if (sheet == null)
        {
            return;
        }
        
//        if (null != impclass && getJpo().getString("OBJECTNAME").equals("ASSET"))
//        {
////            ImportAssetIface impasset = null;
            ImpObjectIface impjpo = new ImpObjectIface();
            if (impclass != null)
            {
            	try{
            		impjpo = (ImpObjectIface) Class.forName(impclass).newInstance();
            	}catch(Exception e){
            		throw new AppException("impclass", "impclassnotfound");
            	}
            }else{
            	impjpo = new ImpObjectIface();
            }
            impjpo.setDoclinksid(doclinksid);
            impjpo.setIfacename(getJpo().getString("ifacename"));
            impjpo.setObjectname(getJpo().getString("objectname"));
            HashMap<String, String> returninfo = impjpo.addJpo(workbook, getJpo());
            String successinfo = returninfo.get("successMessage");
            String errorInfo = returninfo.get("errorMessage");
            if (!"".equals(successinfo))
            {
                throw new MroException("impdata","impdatasuccess");
            }
            else if (!"".equals(errorInfo))
            {
                throw new MroException("impdata", "impfail", new String[] {"批处理出现错误，请检查导入文件。"});
            }
//        }
//        else
//        {
//            throw new MroException("impdata", "errorobject");
//        }
    }
    
    /**
     * 标记删除导入对象时标记删除其子级
     */
    @Override
    public synchronized void delete()
        throws MroException
    {
        if (getJpo() == null || getJpo().isZombie())
        {
            return;
        }
        IJpoSet attributeSet = getJpo().getJpoSet("IMPATTRIBUTE");
        if (attributeSet != null && attributeSet.count() > 0)
        {
            attributeSet.deleteAll();
        }
        IJpoSet doclinksSet = getJpo().getJpoSet("DOCLINKS");
        if(!doclinksSet.isEmpty()){
        	doclinksSet.deleteAll();
        }
//        this.getTable().getBody().getCols().get(5).setEvent("");
//        this.getTable().getBody().getCols().get(5).build(this.getTable().getBody());
        super.delete();
    }
    
    /**
     * 取消标记删除导入对象时也取消标记删除其子级
     */
    @Override
    public synchronized void undelete()
        throws MroException
    {
        if (getJpo() == null || getJpo().isZombie())
        {
            return;
        }
        IJpoSet attributeSet = getJpo().getJpoSet("IMPATTRIBUTE");
        if (attributeSet != null && attributeSet.count() > 0)
        {
            attributeSet.undeleteAll();
        }
        IJpoSet doclinksSet = getJpo().getJpoSet("DOCLINKS");
        if(!doclinksSet.isEmpty()){
        	doclinksSet.undeleteAll();
        }
////        this.getTable().getBody().getCols().get(5).readonly=false;
////        this.getTable().getBody().getCols().get(5).build( this.getTable().getBody());
//        this.getTable().getBody().getCols().get(5).setEvent("impdatafile");
        super.undelete();
    }

    @Override
    public void toggleEditRow() throws MroException, IOException {
    	IJpoSet attrset = currJpo.getJpoSet("IMPATTRIBUTE");
    	if(attrset.count()>0){
    		currJpo.setFieldFlag("OBJECTNAME", GWConstant.S_READONLY, true);
    	}else{
    		currJpo.setFieldFlag("OBJECTNAME", GWConstant.S_READONLY, false);
    	}
    	super.toggleEditRow();
    }
}

