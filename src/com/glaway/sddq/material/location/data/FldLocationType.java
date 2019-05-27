package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <库房类型字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-7-31]
 * @since  [产品/模块版本]
 */
public class FldLocationType extends JpoField {
	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		String ERPLOC = getJpo().getString("ERPLOC");
		String STOREROOMLEVEL = getJpo().getString("STOREROOMLEVEL");
		if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {//库房属性-1020
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){//库房级别-中心库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"','"+ItemUtil.LOCATIONTYPE_WX+"','"+ItemUtil.LOCATIONTYPE_DCL+"')");//过滤-库房类型为常规
				domainSet.reset();
			}
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)||STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_QYK)||STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCZDK)){
				//库房级别-现场库，区域库，现场站点库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"','"+ItemUtil.LOCATIONTYPE_WX+"')");//过滤-库房类型为常规，维修
				domainSet.reset();
			}
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_DWXK)){//库房级别-待维修库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_WX+"','"+ItemUtil.LOCATIONTYPE_DCL+"')");//过滤-库房类型为维修，待处理
				domainSet.reset();
			}			
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {//库房属性-1030
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){//库房级别-中心库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"')");
				//过滤-库房类型为机车检修，动车检修，城轨检修，新服务备品
				domainSet.reset();
			}
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)){//库房级别-现场库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"'')");//过滤-库房类型为机车检修，动车检修，城轨检修
				domainSet.reset();
			}
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)) {//库房属性-其他改造物料库
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){//库房级别-中心库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"','"+ItemUtil.LOCATIONTYPE_WX+"')");//过滤-库房类型为常规，维修
				domainSet.reset();
			}
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)){//库房级别-现场库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"')");//过滤-库房类型为常规
				domainSet.reset();
			}
			return domainSet;
		} else if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {//库房属性-其他待处理物资库
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){//库房级别-中心库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in ('"+ItemUtil.LOCATIONTYPE_CG+"','"+ItemUtil.LOCATIONTYPE_WX+"')");//过滤-库房类型为常规，维修
				domainSet.reset();
			}
			if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_XCK)){//库房级别-现场库
				domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN","domainid='LOCATIONTYPE'");
				domainSet.setUserWhere("VALUE in (''"+ItemUtil.LOCATIONTYPE_CG+"')");//过滤-库房类型为常规
				domainSet.reset();
			}
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
		String STOREROOMLEVEL = getJpo().getString("STOREROOMLEVEL");
		String LOCATIONTYPE = this.getValue();
		if(ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020) && STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK) && !LOCATIONTYPE.equalsIgnoreCase(ItemUtil.LOCATIONTYPE_CG)){
			//判断库房属性为1020，级别为中心库，类型不是常规库
			IJpoSet locationsset =MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer()); 
			locationsset.setUserWhere("erploc='"+ERPLOC+"' and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_DWXK+"' and STOREROOMGRADE='一级'");//过滤父级库房数据
			locationsset.reset();
			if(!locationsset.isEmpty()){
				String LOCATION=locationsset.getJpo(0).getString("LOCATION");
				this.getJpo().setValue("STOREROOMPARENT", LOCATION,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			
		}
	}

}
