package com.glaway.sddq.back.Interface.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

public class FldUserinterfaceIfnum extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * serialVersionUID
	 */
	/**
	 * 过滤已经已将存在的接口编号
	 * @return
	 * @throws MroException
	 */
	 /**
     * @throws MroException
     */
    @Override
    public void init()
        throws MroException
    {
        super.init();
        String[] thisAttrs = {this.getFieldName()};
        String[] srcAttrs = {"ifnum"};
        setLookupMap(thisAttrs, srcAttrs);
    }
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("userinterfaceinfo");
		String sql = "ifnum not in (select ifnum from userinterface)";
		setListWhere(sql);
		return super.getList();//
	}
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		String ifnum = this.getValue();
		super.action();
		IJpoSet userinterfaceinfoSet=MroServer.getMroServer().getJpoSet("userinterfaceinfo", MroServer.getMroServer().getSystemUserServer());
		userinterfaceinfoSet.setUserWhere("ifnum='"+ifnum+"'");
		userinterfaceinfoSet.reset();
		if(!userinterfaceinfoSet.isEmpty()){
			this.getJpo().setValue("ifname", userinterfaceinfoSet.getJpo(0).getString("ifname"));
			this.getJpo().setValue("description", userinterfaceinfoSet.getJpo(0).getString("description"));

		}

	}
}
