package com.glaway.sddq.config.zcconfig.data;

import org.apache.commons.lang.StringUtils;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 现场配置产品序列号字段类
 * 
 * @author hyhe
 * @version [版本号, 2017-11-20]
 * @since [产品/模块版本]
 */
public class FldSqn extends JpoField {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "SQN", "ITEMNUM", "XASSETNUM",
				"SOFTNAME", "SOFTVERSION", "SOFTUPDATE" }, new String[] {
				"SQN", "ITEMNUM", "ASSETNUM", "SOFTNAME", "SOFTVERSION",
				"SOFTUPDATE" });
	}

	public IJpoSet getList() throws MroException {
		if (this.getJpo() != null && this.getJpo().getAppName() != null
				&& this.getJpo().getAppName().equals("ZCCONFIG")) {
			if (this.getJpo().getParent() != null
					&& "CAR".equals(this.getJpo().getParent()
							.getString("ASSETLEVEL"))) {
				if (!StringUtil.isNullOrEmpty(this.getJpo()
						.getString("itemnum"))) {
					String itemnums = ItemUtil.getAltItemnums(this.getJpo()
							.getString("itemnum"));
					this.setListObject("ASSET");
					this.setListWhere("assetlevel='ASSET'  and iserp != '0' and  type='0' and itemnum in ("
							+ itemnums + ")");
				} else {
					this.setListObject("ASSET");
					this.setListWhere("assetlevel='ASSET'  and iserp != '0' and type='0'");
				}
			} else if (this.getJpo() != null
					&& (!StringUtil.isNullOrEmpty(this.getJpo().getParent()
							.getString("itemnum"))
							&& ItemUtil.getItem(this.getJpo().getParent()
									.getString("itemnum")) != null && ItemUtil
							.getItem(
									this.getJpo().getParent()
											.getString("itemnum")).getBoolean(
									"ISIV"))) {

				if (!StringUtil.isNullOrEmpty(this.getJpo()
						.getString("itemnum"))) {
					String itemnums = ItemUtil.getAltItemnums(this.getJpo()
							.getString("itemnum"));
					this.setListObject("ASSET");
					this.setListWhere("assetlevel='ASSET'  and iserp != '0' and type='0' and itemnum in ("
							+ itemnums + ")");
				} else {
					this.setListObject("ASSET");
					this.setListWhere("assetlevel='ASSET'  and iserp != '0' and type='0'");
				}

			} else {
				this.setListObject("ASSET");
				this.setListWhere("1=2");
			}
		} else {
			this.setListObject("ASSET");
			this.setListWhere("1=2");
		}
		return super.getList();
	}

	@Override
	protected void validateList() throws MroException {

		if (this.getJpo() != null && this.getJpo().getAppName() != null
				&& this.getJpo().getAppName().equals("ZCCONFIG")) {
			if ("CAR".equals(this.getJpo().getParent().getString("ASSETLEVEL"))) {
				super.validateList();
			}
			if (this.getJpo() != null
					&& (!StringUtil.isNullOrEmpty(this.getJpo().getParent()
							.getString("itemnum"))
							&& ItemUtil.getItem(this.getJpo().getParent()
									.getString("itemnum")) != null && ItemUtil
							.getItem(
									this.getJpo().getParent()
											.getString("itemnum")).getBoolean(
									"ISIV"))) {
				super.validateList();
			}
		}
	}

	@Override
	public void validate() throws MroException {
		super.validate();
		String sqn = this.getInputMroType().getStringValue();
		String assetnum = this.getJpo().getString("assetnum");
		String itemnum = this.getJpo().getString("itemnum");
		this.getField("itemnum").clearError();
		if ("ZCCONFIG".equals(this.getJpo().getAppName())) {
			if (!StringUtils.isEmpty(sqn) && sqn.length() == 10) {
				if ("CAR".equals(this.getJpo().getParent()
						.getString("ASSETLEVEL"))) {
					IJpoSet jposet = this.getUserServer().getJpoSet(
							"ASSET",
							"SQN='" + sqn + "' and assetnum != '" + assetnum
									+ "' and type='2' ");
					if (jposet != null && jposet.count() > 0) {
						throw new AppException("ASSET", "sqnisrepeat");
					}
				} else if (this.getJpo() != null
						&& (!StringUtil.isNullOrEmpty(this.getJpo().getParent()
								.getString("itemnum"))
								&& ItemUtil.getItem(this.getJpo().getParent()
										.getString("itemnum")) != null && ItemUtil
								.getItem(
										this.getJpo().getParent()
												.getString("itemnum"))
								.getBoolean("ISIV"))) {
					IJpoSet jposet = this.getUserServer().getJpoSet(
							"ASSET",
							"SQN='" + sqn + "' and assetnum != '" + assetnum
									+ "' and type='2' ");
					if (jposet != null && jposet.count() > 0) {
						throw new AppException("ASSET", "sqnisrepeat");
					}
				} else {
					IJpoSet jposet = this.getUserServer().getJpoSet(
							"ASSET",
							"SQN='" + sqn + "' and assetnum != '" + assetnum
									+ "'");
					if (jposet != null && jposet.count() > 0) {
						throw new AppException("ASSET", "sqnisrepeat");
					}
				}
			} else {
				if (StringUtils.isNotEmpty(itemnum)) {
					if ("CAR".equals(this.getJpo().getParent()
							.getString("ASSETLEVEL"))) {
						IJpoSet jposet = this.getUserServer().getJpoSet(
								"ASSET",
								"SQN='" + sqn + "' and assetnum != '"
										+ assetnum
										+ "' and type='2'  and itemnum='"
										+ itemnum + "'");
						if (jposet != null && jposet.count() > 0) {
							throw new AppException("ASSET", "sqnitemisrepeat");
						}
					} else if (this.getJpo() != null
							&& (!StringUtil.isNullOrEmpty(this.getJpo()
									.getParent().getString("itemnum"))
									&& ItemUtil.getItem(this.getJpo()
											.getParent().getString("itemnum")) != null && ItemUtil
									.getItem(
											this.getJpo().getParent()
													.getString("itemnum"))
									.getBoolean("ISIV"))) {
						IJpoSet jposet = this.getUserServer().getJpoSet(
								"ASSET",
								"SQN='" + sqn + "' and assetnum != '"
										+ assetnum
										+ "' and type='2'  and itemnum='"
										+ itemnum + "'");
						if (jposet != null && jposet.count() > 0) {
							throw new AppException("ASSET", "sqnitemisrepeat");
						}
					} else {
						IJpoSet jposet = this.getUserServer().getJpoSet(
								"ASSET",
								"SQN='" + sqn + "' and assetnum != '"
										+ assetnum + "'  and itemnum='"
										+ itemnum + "'");
						if (jposet != null && jposet.count() > 0) {
							throw new AppException("ASSET", "sqnitemisrepeat");
						}
					}
				}
			}
		} else {
			if (!StringUtils.isEmpty(sqn) && sqn.length() == 10) {
				IJpoSet jposet = this.getUserServer().getJpoSet("ASSET",
						"SQN='" + sqn + "' and assetnum != '" + assetnum + "'");
				if (jposet != null && jposet.count() > 0) {
					throw new AppException("ASSET", "sqnisrepeat");
				} else {
//					if ("CXCONFIG".equals(this.getJpo().getAppName())) {
//						IJpoSet hcJposet = this.getJpo().getJpoSet();
//						if (hcJposet != null && hcJposet.count() > 0) {
//							boolean flag = false;
//							for (int index = 0; index < hcJposet.count(); index++) {
//								IJpo hcjpo = hcJposet.getJpo(index);
//								if (sqn.equals(hcjpo.getString("sqn"))) {
//									flag = true;
//									break;
//								}
//							}
//							if (flag) {
//								throw new AppException("ASSET",
//										"sqnitemisrepeat");
//							}
//						}
//					}
				}
			} else {
				if (StringUtils.isNotEmpty(itemnum)) {
					IJpoSet jposet = this.getUserServer().getJpoSet(
							"ASSET",
							"SQN='" + sqn + "' and assetnum != '" + assetnum
									+ "' and itemnum='" + itemnum + "'");
					if (jposet != null && jposet.count() > 0) {
						throw new AppException("ASSET", "sqnitemisrepeat");
					}
//					if ("CXCONFIG".equals(this.getJpo().getAppName())) {
//						IJpoSet hcJposet = this.getJpo().getJpoSet();
//						if (hcJposet != null && hcJposet.count() > 0) {
//							boolean flag = false;
//
//							for (int index = 0; index < hcJposet.count(); index++) {
//								IJpo hcjpo = hcJposet.getJpo(index);
//								if (sqn.equals(hcjpo.getString("sqn"))
//										&& itemnum.equals(hcjpo
//												.getString("itemnum"))) {
//									flag = true;
//									break;
//								}
//							}
//							if (flag) {
//								throw new AppException("ASSET",
//										"sqnitemisrepeat");
//							}
//						}
//					}
				}
			}
		}
	}
}
