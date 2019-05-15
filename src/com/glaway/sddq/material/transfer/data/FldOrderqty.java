package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <调拨数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldOrderqty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 校验数量不能大于库存数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		String location = this.getJpo().getParent().getString("ISSUESTOREROOM");
		double orderqty = this.getDoubleValue();
		double total = this
				.getJpo()
				.getJpoSet(
						"$SYS_INVENTORY",
						"SYS_INVENTORY",
						"location='" + location + "' and itemnum ='"
								+ this.getJpo().getString("itemnum") + "'")
				.sum("CURBAL");

		if (orderqty > total) {
			throw new MroException("调拨数量不能大于库存数量");

		} else {
			if (this.getJpo().getAppName().equals("GZTRANSFER")) {
				double all = this
						.getJpo()
						.getJpoSet(
								"$TRANSFERLINE",
								"TRANSFERLINE",
								"TRANSFERNUM in (select TRANSFERNUM from TRANSFER where type = 'GZZXD') and itemnum =:itemnum and status!='已接收' and TRANSFERLINEID!=:TRANSFERLINEID")
						.sum("ORDERQTY");
				if (all + orderqty > total) {
					throw new MroException("调拨数量不能大于库存数量");

				}
			}
		}
		super.action();
	}

}
