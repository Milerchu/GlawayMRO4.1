package com.glaway.sddq.material.toolnsp.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具校检单Jpo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class Toolinsp extends Jpo {

	private static final long serialVersionUID = 59754576740756780L;

	/**
	 * 初始化页面控制
	 * 
	 * @throws MroException
	 */
	public void init() throws MroException {

		// String usrIdString=getUserServer().getUserInfo().getUserName();

		String createby = this.getString("CREATEBY");
		if (getString("STATUS").equals("待审核")
				|| getString("STATUS").equals("已结束")) {
			this.setFieldFlag("INSPDATE", GWConstant.S_READONLY, true);
			this.setFieldFlag("CREATEBY", GWConstant.S_READONLY, true);
			this.setFieldFlag("REVIEWPERSON", GWConstant.S_READONLY, true);

			IJpoSet toolcheckTmp = this.getJpoSet("TOOLCHECK");
			toolcheckTmp.setFlag(GWConstant.S_READONLY, true);

		} else {

			if (getString("STATUS").equals("驳回")
					&& createby.equalsIgnoreCase(getUserServer().getUserInfo()
							.getUserName())) {
				IJpoSet toolcheckTmp = this.getJpoSet("TOOLCHECK");
				toolcheckTmp.getJpo().setFieldFlag("ACTCHECKDATE",
						GWConstant.S_READONLY, true);
				toolcheckTmp.getJpo().setFieldFlag("ISRETURN",
						GWConstant.S_READONLY, true);
				toolcheckTmp.getJpo().setFieldFlag("CHECKRESULT",
						GWConstant.S_READONLY, true);
				toolcheckTmp.getJpo().setFieldFlag("STATUS",
						GWConstant.S_READONLY, true);

			} else {
				if (getString("STATUS").equals("驳回")
						&& (!createby.equalsIgnoreCase(getUserServer()
								.getUserInfo().getUserName()))) {
					this.setFieldFlag("REVIEWPERSON", GWConstant.S_READONLY,
							true);
					IJpoSet toolcheckTmp = this.getJpoSet("TOOLCHECK");
					toolcheckTmp.getJpo().setFieldFlag("ITEMNUM",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("ACTCHECKDATE",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("ISRETURN",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("CHECKRESULT",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("STATUS",
							GWConstant.S_READONLY, true);
					toolcheckTmp.setFlag(GWConstant.S_READONLY, true);
				}
			}

			if (getString("STATUS").equals("送检中")
					&& createby.equalsIgnoreCase(getUserServer().getUserInfo()
							.getUserName())) {

				this.setFieldFlag("INSPDATE", GWConstant.S_READONLY, true);
				this.setFieldFlag("CREATEBY", GWConstant.S_READONLY, true);
				this.setFieldFlag("REVIEWPERSON", GWConstant.S_READONLY, true);

				IJpoSet toolcheckTmp = this.getJpoSet("TOOLCHECK");
				toolcheckTmp.getJpo().setFieldFlag("ITEMNUM",
						GWConstant.S_READONLY, true);
				toolcheckTmp.getJpo().setFieldFlag("PLANCHECKDATE",
						GWConstant.S_READONLY, true);

			} else {
				if (getString("STATUS").equals("送检中")
						&& (!createby.equalsIgnoreCase(getUserServer()
								.getUserInfo().getUserName()))) {
					this.setFieldFlag("REVIEWPERSON", GWConstant.S_READONLY,
							true);
					IJpoSet toolcheckTmp = this.getJpoSet("TOOLCHECK");
					toolcheckTmp.getJpo().setFieldFlag("ITEMNUM",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("ACTCHECKDATE",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("ISRETURN",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("CHECKRESULT",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("STATUS",
							GWConstant.S_READONLY, true);
					toolcheckTmp.getJpo().setFieldFlag("PLANCHECKDATE",
							GWConstant.S_READONLY, true);
					this.setFieldFlag("REVIEWPERSON", GWConstant.S_READONLY,
							true);
					toolcheckTmp.setFlag(GWConstant.S_READONLY, true);
				}
			}

		}
		super.init();
	}
}
