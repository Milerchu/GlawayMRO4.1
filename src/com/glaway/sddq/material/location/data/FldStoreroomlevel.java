package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <库房级别字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-7-31]
 * @since  [产品/模块版本]
 */
public class FldStoreroomlevel extends JpoField {
	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		String ERPLOC = getJpo().getString("ERPLOC");
		if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {//1020库房显示的库房级别
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='STOREROOMLEVEL'");
			//域过滤-中心库，现场库，区域库，现场站点库，待维修库
			domainSet.setQueryWhere("VALUE in ('"+ItemUtil.STOREROOMLEVEL_ZXK+"','"+ItemUtil.STOREROOMLEVEL_XCK+"','"+ItemUtil.STOREROOMLEVEL_QYK+"','"+ItemUtil.STOREROOMLEVEL_XCZDK+"','"+ItemUtil.STOREROOMLEVEL_DWXK+"')");
			domainSet.reset();
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {//1030库房显示的库房级别
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='STOREROOMLEVEL'");
			domainSet.setQueryWhere("VALUE in ('"+ItemUtil.STOREROOMLEVEL_ZXK+"','"+ItemUtil.STOREROOMLEVEL_XCK+"','"+ItemUtil.STOREROOMLEVEL_FWK+"')");//域过滤-中心库，现场库
			domainSet.reset();
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)) {// 其他改造物料库库房显示的库房级别
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='STOREROOMLEVEL'");
			domainSet.setQueryWhere("VALUE in ('"+ItemUtil.STOREROOMLEVEL_ZXK+"','"+ItemUtil.STOREROOMLEVEL_XCK+"')");//域过滤-中心库，现场库
			domainSet.reset();
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {// 其他待处理物资库库房显示的库房级别
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='STOREROOMLEVEL'");
			domainSet.setQueryWhere("VALUE in ('"+ItemUtil.STOREROOMLEVEL_ZXK+"','"+ItemUtil.STOREROOMLEVEL_XCK+"')");//域过滤-中心库，现场库
			domainSet.reset();
			return domainSet;
		} else {
			return super.getList();
		}

	}
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String ERPLOC = getJpo().getString("ERPLOC");
		String STOREROOMLEVEL=this.getValue();
		//库房级别如果是中心库，站点非必填，只读，否则站点必填
		if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){
			this.getJpo().setFieldFlag("LOCSITE", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("LOCSITE", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOCSITE", GWConstant.S_READONLY, true);
		}else{
			this.getJpo().setFieldFlag("LOCSITE", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("LOCSITE", GWConstant.S_REQUIRED, true);
		}
		this.getJpo().setValue("LOCATIONTYPE", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if(ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)){//库房属性-1020
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){//库房级别-中心库
				this.getJpo().setValue("LOCATIONTYPE", ItemUtil.LOCATIONTYPE_CG,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//赋值库房类型为常规库
			}
			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer()); 
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)||STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_DWXK)){
				locationsset.setQueryWhere("erploc='"+ERPLOC+"' and STOREROOMLEVEL='"+STOREROOMLEVEL+"' and STOREROOMGRADE='一级'");//过滤父级库房数据
			}else{
				locationsset.setQueryWhere("erploc='"+ERPLOC+"' and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_XCK+"' and STOREROOMGRADE='一级'");//过滤父级库房数据
			}
			locationsset.reset();
			if(!locationsset.isEmpty()){
				String LOCATION=locationsset.getJpo(0).getString("LOCATION");
				this.getJpo().setValue("STOREROOMPARENT", LOCATION,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			
		}
		if(ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)){//库房属性-其他改造物料库
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)){//库房级别-现场库
				this.getJpo().setValue("LOCATIONTYPE", ItemUtil.LOCATIONTYPE_CG,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//赋值库房类型为常规库
			}
		}
		if(ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)){//库房属性-其他待处理物资库
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)){//库房级别-现场库
				this.getJpo().setValue("LOCATIONTYPE", ItemUtil.LOCATIONTYPE_CG,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//赋值库房类型为常规库
			}
		}
		if(ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)){//库房属性-1030
			this.getJpo().setValue("LOCATIONTYPE", ItemUtil.LOCATIONTYPE_CG,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());   
			locationsset.setQueryWhere("erploc='"+ERPLOC+"' and STOREROOMLEVEL='"+STOREROOMLEVEL+"' and STOREROOMGRADE='一级'");//过滤父级库房数据
			locationsset.reset();
			if(!locationsset.isEmpty()){
				String LOCATION=locationsset.getJpo(0).getString("LOCATION");
				this.getJpo().setValue("STOREROOMPARENT", LOCATION,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			
		}
		
	}

}
