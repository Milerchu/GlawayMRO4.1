package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

public class FldErploc extends JpoField {

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		if(this.getValue().equalsIgnoreCase(ItemUtil.ERPLOC_1020)){//库房属性-1020
//			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
//			locationsset.setUserWhere("erploc='"+ItemUtil.ERPLOC_1020+"'");//过滤-1020
//			locationsset.reset();
//			int count=locationsset.count();
//			String location="";
//			if(count>=0 && count<9){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="Y100"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=9 && count<99){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="Y10"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=99 && count<999){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="Y1"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
			/*10月8日肖林宝修改自动编号*/
			String location=this.getJpo().getString("location");
			if(!StringUtil.isStrEmpty(location)&&location.startsWith("Y")){
				location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("X")){
        		location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("GZ")){
        		location= location.substring(2);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("QT")){
        		location= location.substring(2);
        	}
			location="Y"+location;
			this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			/*10月8日肖林宝修改自动编号*/
			this.getJpo().setValue("STOREROOMGRADE", "二级",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("jxorfw", GWConstant.S_READONLY, false);
			this.getJpo().setValue("jxorfw", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
		}
		if(this.getValue().equalsIgnoreCase(ItemUtil.ERPLOC_1030)){//库房属性-1030
//			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
//			locationsset.setUserWhere("erploc='"+ItemUtil.ERPLOC_1030+"'");//过滤-1030
//			locationsset.reset();
//			int count=locationsset.count();
//			String location="";
//			if(count>=0 && count<9){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="X100"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=9 && count<99){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="X10"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=99 && count<999){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="X1"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
			/*10月8日肖林宝修改自动编号*/
			String location=this.getJpo().getString("location");
			if(!StringUtil.isStrEmpty(location)&&location.startsWith("Y")){
				location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("X")){
        		location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("GZ")){
        		location= location.substring(2);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("QT")){
        		location= location.substring(2);
        	}
			location="X"+location;
			this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			/*10月8日肖林宝修改自动编号*/
			this.getJpo().setValue("STOREROOMGRADE", "二级",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("jxorfw", "检修",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("jxorfw", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("jxorfw", GWConstant.S_READONLY, true);
		}
		if(this.getValue().equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)){//库房属性-其他改造物料库
//			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
//			locationsset.setUserWhere("erploc='"+ItemUtil.ERPLOC_QTGZ+"'");//过滤-其他改造物料库
//			locationsset.reset();
//			int count=locationsset.count();
//			String location="";
//			if(count>=0 && count<9){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="GZ100"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=9 && count<99){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="GZ10"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=99 && count<999){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="GZ1"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
			/*10月8日肖林宝修改自动编号*/
			String location=this.getJpo().getString("location");
			if(!StringUtil.isStrEmpty(location)&&location.startsWith("Y")){
				location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("X")){
        		location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("GZ")){
        		location= location.substring(2);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("QT")){
        		location= location.substring(2);
        	}
			location="GZ"+location;
			this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			/*10月8日肖林宝修改自动编号*/
			this.getJpo().setValue("STOREROOMGRADE", "一级",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("jxorfw", GWConstant.S_READONLY, false);
			this.getJpo().setValue("jxorfw", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		if(this.getValue().equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)){//库房属性-其他待处理物资库
//			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
//			locationsset.setUserWhere("erploc='"+ItemUtil.ERPLOC_QTDCL+"'");//过滤-其他待处理物资库
//			locationsset.reset();
//			int count=locationsset.count();
//			String location="";
//			if(count>=0 && count<9){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="QT100"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=9 && count<99){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="QT10"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
//			if(count>=99 && count<999){
//				Integer newcount=count+1;
//				String stringcount=newcount.toString();
//				location="QT1"+""+stringcount+"";
//				this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			}
			/*10月8日肖林宝修改自动编号*/
			String location=this.getJpo().getString("location");
			if(!StringUtil.isStrEmpty(location)&&location.startsWith("Y")){
				location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("X")){
        		location= location.substring(1);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("GZ")){
        		location= location.substring(2);
        	}else if(!StringUtil.isStrEmpty(location)&&location.startsWith("QT")){
        		location= location.substring(2);
        	}
			location="QT"+location;
			this.getJpo().setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			/*10月8日肖林宝修改自动编号*/
			this.getJpo().setValue("STOREROOMGRADE", "一级",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);			
			this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("jxorfw", GWConstant.S_READONLY, false);
			this.getJpo().setValue("jxorfw", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		this.getJpo().setValue("STOREROOMLEVEL", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("LOCATIONTYPE", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
}
