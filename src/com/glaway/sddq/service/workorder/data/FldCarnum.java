package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * <工单车号字段类>
 * 
 * @author hzhu
 * @version [MRO4.0, 2018-5-5]
 * @since [MRO4.0/模块版本]
 */
public class FldCarnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 4403137576538667161L;

	@Override
	public void init() throws MroException {

		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {

		if ("验证".equals(getJpo().getString("type"))) {// 验证工单
			setLookupMap(
					new String[] { "CARNUM", "REPAIRPROCESS", "ASSETNUM" },
					new String[] { "CARNO", "REPAIRPROCESS", "ASSETNUM" });
		} else if ("服务".equals(getJpo().getString("type"))
				|| "故障".equals(getJpo().getString("type"))) {// 服务工单

			String[] status = { "草稿", "处理中" };

			if (StringUtil.isHaveStr(status, getJpo().getString("status"))) {

				getJpo().setFieldFlag("carnum", GWConstant.S_READONLY, false);

			}

			if ("服务".equals(getJpo().getString("type"))
					&& SddqConstant.SO_TYPE_HWDC.equals(getJpo().getString(
							"SERVORDERTYPE"))) {
				getJpo().setFieldFlag("carnum", GWConstant.S_READONLY, true);
			}

			setLookupMap(new String[] { "CARNUM", "MODELS", "REPAIRPROCESS",
					"ASSETNUM", "MODELPROJECT", "UPDATETIME", "RUNKILOMETRE",
					"REPAIRAFTERKILOMETER", "OVERHAULER", "OWNERCUSTOMER" },
					new String[] { "CARNO", "CMODEL", "REPAIRPROCESS",
							"ASSETNUM", "MODELS.MODELDESC", "UPDATETIME",
							"RUNKILOMETRE", "REPAIRAFTERKILOMETER",
							"OVERHAULER", "OWNERCUSTOMER" });
		} else {
			setLookupMap(new String[] { "CARNUM", "MODELS", "REPAIRPROCESS",
					"ASSETNUM", "MODELPROJECT", "PROJECTNUM" }, new String[] {
					"CARNO", "CMODEL", "REPAIRPROCESS", "ASSETNUM",
					"MODELS.MODELDESC", "PROJECTNUM" });
		}

		IJpo orderJpo = getJpo();
		String orderType = orderJpo.getString("type");
		if ("改造".equals(orderType)) {// 改造工单

			String where = " 1=1 ";
			// 去除改造车辆分布中已经选择的车号
			IJpoSet transdistSet = orderJpo.getJpoSet("TRANSDIST");
			if (!transdistSet.isEmpty()) {
				IJpo transdist = transdistSet.getJpo(0);
				String carnums = transdist.getString("carnums");
				if (StringUtil.isStrNotEmpty(carnums)) {
					String[] carnos = carnums.split(",");
					String carStr = "";
					for (String car : carnos) {
						carStr += "'" + car + "',";
					}
					carStr = carStr.substring(0, carStr.length() - 1);
					where += "and carno not in(" + carStr + ") ";
				}
			}

			if (orderJpo.getString("models") != null) {// 车型不为空

				// 根据车型过滤车号
				where += " and cmodel='" + orderJpo.getString("models") + "'";

			}

			setListWhere(where);

		} else if ("验证".equals(orderType)) {
			String where = "";
			if (orderJpo.getString("models") != null) {// 车型不为空
				// 根据车型过滤车号
				where = "cmodel='" + orderJpo.getString("models") + "'";
			}
			if (orderJpo.getString("whichoffice") != null) {
				// 增加根据办事处过滤
				where += " and ownercustomer in (select custnum from custinfo where whichoffice='"
						+ orderJpo.getString("whichoffice") + "') ";
			}
			// 过滤其他工单已经选择过的车号
			if (orderJpo.getParent() != null
					&& "VALIPRORANGE".equalsIgnoreCase(orderJpo.getParent()
							.getName())) {
				IJpo range = orderJpo.getParent();
				IJpoSet orderSet = range.getJpoSet("VALIORDER");
				String selectedCars = "";
				if (!orderSet.isEmpty()) {
					for (int index = 0; index < orderSet.count(); index++) {

						IJpo order = orderSet.getJpo(index);
						String carnum = order.getString("carnum");
						if (StringUtil.isStrNotEmpty(carnum)) {
							selectedCars += "'" + carnum + "',";
						}

					}
					if (StringUtil.isStrNotEmpty(selectedCars)) {
						selectedCars = selectedCars.substring(0,
								selectedCars.length() - 1);
						where += " and carno not in(" + selectedCars + ") ";
					}
				}

			}
			setListWhere(where);
		}

		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();

		IJpo order = getJpo();// 工单jpo
		// 当前输入车号
		String inputCarnum = getInputMroType().asString();
		if (StringUtil.isStrNotEmpty(inputCarnum)) {
			// zzx add 9.18
			if ("改造".equals(order.getString("type"))) {// 改造工单

				String preValue = order.getField("CARNUM").getPreviousMroType()
						.getStringValue();// 获取之前的值
				// 车号回填到改造车辆分布
				IJpoSet transdistset = this.getJpo().getJpoSet("TRANSDIST");
				if (transdistset != null && transdistset.count() > 0) {

					String carnums = transdistset.getJpo(0)
							.getString("CARNUMS");
					if (StringUtil.isStrNotEmpty(carnums)) {
						if (StringUtil.isStrNotEmpty(preValue)) {

							inputCarnum = carnums.replaceFirst(preValue,
									inputCarnum); // 选完车号保存后，下次修改车号则需未替换上次保存的车号

						} else {

							inputCarnum = carnums + "," + inputCarnum;

						}

					}

					transdistset.getJpo(0).setValue("CARNUMS", inputCarnum,
							GWConstant.P_NOVALIDATION_AND_NOACTION);

				}

				// zzx add end
			} else if ("故障".equals(order.getString("type"))) {

				order.setFieldFlag("carnum", GWConstant.S_READONLY, true);// 车号只读

				String[] status = { "草稿", "处理中" };

				if (isValueChanged()
						&& (StringUtil.isHaveStr(status,
								order.getString("status")))) {// 切换车号时清空已填数据

					// workorder需要清空的字段
					String[] woAllAttrs = { "models", "RUNKILOMETRE",
							"REPAIRPROCESS", "UPDATETIME",
							"REPAIRAFTERKILOMETER", "AFTREPAIRRUNTIME",
							"OWNERCUSTOMER", "OVERHAULER", "MODELPROJECT",
							"ASSETNUM" };

					for (String attr : woAllAttrs) {
						order.setValueNull(attr, GWConstant.P_NOVALIDATION);
					}

					if (!order.getJpoSet("FAILURELIB").isEmpty()) {

						// failurelib需要清空的字段
						String[] flAllAttrs = { "CARSECTIONNUM",
								"PRODUCTNICKNAME", "FAILURECODE", "FAULTDESC",
								"PREREASONALYS", "DEALMEASURE", "FAULTTIME",
								"FINDPROCESS", "RUNNINGMODE", "DEALMETHOD",
								"FAULTQUALIT", "FAULTCONSEQ",
								"ANALYSISREPNEED", "FAULTLOCATION", "GDQJ",
								"ROADTYPE", "FAILWEATHER", "QYFZDW" };

						order.getJpoSet("FAILURELIB")
								.getJpo()
								.setFieldFlag("ANALYSISREPNEED",
										GWConstant.S_READONLY, false);
						for (String attr : flAllAttrs) {
							order.getJpoSet("FAILURELIB")
									.getJpo()
									.setValueNull(attr,
											GWConstant.P_NOVALIDATION);
						}
					}

				}

			} else if ("服务".equals(order.getString("type"))) {

				// order.setFieldFlag("carnum", GWConstant.S_READONLY, true);//
				// 车号只读

			}

		}
	}
}
