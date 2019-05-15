package com.glaway.sddq.tools;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 接口返回结果对象类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class Result {

	private String stateCode = "200";

	private String message = "操作成功";

	// 操作结果
	private boolean actionResult = false;

	private Object data;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean getActionResult() {
		return actionResult;
	}

	public void setActionResult(boolean actionResult) {
		this.actionResult = actionResult;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String toXmlString() {
		JSONObject jObj = JSONObject.parseObject(this.toString());
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		jsonToXml(root, jObj);
		return doc.asXML();
	}

	/**
	 * 转xml递归方法 <功能描述>
	 * 
	 * @param node
	 * @param jObj
	 *            [参数说明]
	 * 
	 */
	private static void jsonToXml(Element node, JSONObject jObj) {
		for (String key : jObj.keySet()) {
			Element e = node.addElement(key);
			if (jObj.get(key).toString().startsWith("[")
					&& jObj.get(key).toString().endsWith("]")) {
				JSONArray jArr = JSONArray.parseArray(jObj.get(key).toString());
				for (int i = 0; i < jArr.size(); i++) {
					Element sub = e.addElement(key);
					JSONObject subObj = JSONObject.parseObject(jArr.get(i)
							.toString());
					jsonToXml(sub, subObj);
				}
			} else if (jObj.get(key) instanceof JSONObject) {
				jsonToXml(e, JSONObject.parseObject(jObj.get(key).toString()));
			} else {
				e.addText(jObj.get(key).toString());
			}
		}
	}
}
