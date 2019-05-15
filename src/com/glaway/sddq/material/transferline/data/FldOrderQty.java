package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
/**
 * 
 * <装箱单行数量绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-20]
 * @since  [产品/模块版本]
 */
public class FldOrderQty extends JpoField {

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String appname=this.getJpo().getAppName();
		if(appname.equalsIgnoreCase("ZXTRANSFER")){
			double orderqty=this.getDoubleValue();
			String issuestoreroom=this.getJpo().getString("issuestoreroom");
			String itemnum=this.getJpo().getString("itemnum");
			String mrnum=this.getJpo().getParent().getString("mrnum");
			IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
			inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
			double kyqty =0;
			if(mrnum.isEmpty()){
				kyqty = inventoryset.getJpo(0).getDouble("kyqty");// 可用数量	
			}else{
				kyqty = inventoryset.getJpo(0).getDouble("sqzyqty");// 可用数量
			}
			if(kyqty==0){
				throw new MroException("库存可用数量为0");
			}else if(kyqty>0){
				IJpoSet jkdtransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				jkdtransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and receivestoreroom='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type='JKD')");
				jkdtransferlineset.reset();
				
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("itemnum='"+ itemnum+ "' and ISSUESTOREROOM='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type in ('SX','ZXD'))");
				transferlineset.reset();
				if(jkdtransferlineset.isEmpty()){
					if(transferlineset.isEmpty()){
						double newqty=kyqty-orderqty;
						if(newqty<0){
							throw new MroException("数量大于库存可用数量");
						}
					}else{
						double sumtransferorderqty=transferlineset.sum("orderqty");
						double newqty=kyqty-(orderqty+sumtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}
				}else{
					double sumjkdtransferorderqty=jkdtransferlineset.sum("orderqty");
					if(transferlineset.isEmpty()){
						double newqty=kyqty-(orderqty+sumjkdtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}else{
						double sumtransferorderqty=transferlineset.sum("orderqty");
						double newqty=kyqty-(orderqty+sumtransferorderqty+sumjkdtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}
				}
			}
		}else if(appname.equalsIgnoreCase("JKTRANSFER")){
			double orderqty=this.getDoubleValue();
			String issuestoreroom=this.getJpo().getString("receivestoreroom");
			String itemnum=this.getJpo().getString("itemnum");
			IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
			inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
			double kyqty = inventoryset.getJpo(0).getDouble("kyqty");// 可用数量
			if(kyqty==0){
				throw new MroException("库存可用数量为0");
			}else if(kyqty>0){
				IJpoSet jkdtransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				jkdtransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and receivestoreroom='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type='JKD')");
				jkdtransferlineset.reset();
				
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("itemnum='"+ itemnum+ "' and ISSUESTOREROOM='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type in ('SX','ZXD'))");
				transferlineset.reset();
				if(jkdtransferlineset.isEmpty()){
					if(transferlineset.isEmpty()){
						double newqty=kyqty-orderqty;
						if(newqty<0){
							throw new MroException("数量大于库存可用数量");
						}
					}else{
						double sumtransferorderqty=transferlineset.sum("orderqty");
						double newqty=kyqty-(orderqty+sumtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}
				}else{
					double sumjkdtransferorderqty=jkdtransferlineset.sum("orderqty");
					if(transferlineset.isEmpty()){
						double newqty=kyqty-(orderqty+sumjkdtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}else{
						double sumtransferorderqty=transferlineset.sum("orderqty");
						double newqty=kyqty-(orderqty+sumtransferorderqty+sumjkdtransferorderqty);
						if(newqty<0){
							throw new MroException("累计发货数量大于库存可用数量");
						}
					}
				}
			}
		}
	}

}
