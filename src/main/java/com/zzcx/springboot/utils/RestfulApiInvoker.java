package com.zzcx.springboot.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service("restfulApiInvoker")
@Slf4j
public class RestfulApiInvoker {
	/**
	 * get请求
	 *
	 * @param restfulApiUrl
	 * @param resultType
	 * @return
	 */
	public <T> T doGetInvokeWithReturnCode(String restfulApiUrl, Type resultType) {
		log.info("restfulApiUrl:" + restfulApiUrl);
		String response = RestfulHttpClient.doGet(restfulApiUrl);
		log.info("doGetInvoke response:" + restfulApiUrl +",response:"+ response);
		if (StringUtils.isBlank(response)) {
			return null;
		}

		Gson gson = new Gson();
		return gson.fromJson(response, resultType);
	}

	/**
	 * get请求
	 * 
	 * @param restfulApiUrl
	 * @param resultType
	 * @return
	 */
	public <T> T doGetInvoke(String restfulApiUrl, Type resultType) {
		log.info("restfulApiUrl:" + restfulApiUrl);
		String response = RestfulHttpClient.doGet(restfulApiUrl);
		log.info("doGetInvoke response:" + restfulApiUrl +",response:"+ response);
		if (StringUtils.isBlank(response)) {
			return null;
		}

		JSONObject responseJson = JSONObject.parseObject(response);
		Object responseBody = responseJson.get("body");
		if (responseBody == null) {
			return null;
		}

		Gson gson = new Gson();
		return gson.fromJson(responseBody.toString(), resultType);
	}

	/**
	 * post请求
	 * 
	 * @param restfulApiUrl
	 * @param requestBody
	 * @param resultType
	 * @return
	 */
	public <T> T doPostInvoke(String restfulApiUrl, String requestBody,
			Type resultType) {
		log.info("restfulApiUrl:" + restfulApiUrl + ",requestBody:"
				+ requestBody);
		String response = RestfulHttpClient.doPost(restfulApiUrl, requestBody);

		log.info("doPostInvoke response:"  + restfulApiUrl +",response:"+ response);
		if (StringUtils.isBlank(response)) {
			return null;
		}

		JSONObject responseJson = JSONObject.parseObject(response);
		Object responseBody = responseJson.get("body");
		if (responseBody == null) {
			return null;
		}

		try {

			Integer returnCode = responseJson.getInteger("returnCode");
			((JSONObject) responseBody).put("errCode", returnCode);

			// restfulapi返回的成功时returnCode为0，为了兼容老接口需要将0转成"0000"
			if (returnCode == 0) {
				((JSONObject) responseBody).put("errCode", "0000");
			}
			((JSONObject) responseBody).put("errMsg",
					((JSONObject) responseJson).get("returnMsg"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		Gson gson = new Gson();
		return gson.fromJson(responseBody.toString(), resultType);
	}

	/**
	 * put请求
	 *
	 * @param restfulApiUrl
	 * @param requestBody
     * @param resultType
	 * @return
	 */
	public <T> T doPutInvoke(String restfulApiUrl, String requestBody,
			Type resultType) {
		log.info("restfulApiUrl:" + restfulApiUrl + ",requestBody:"
				+ requestBody);
		String response = RestfulHttpClient.doPut(restfulApiUrl, requestBody);

		log.info("doPutInvoke response:" + restfulApiUrl +",response:"+ response);
		if (StringUtils.isBlank(response)) {
			return null;
		}

		JSONObject responseJson = JSONObject.parseObject(response);
		Object responseBody = responseJson.get("body");
		if (responseBody == null) {
			return null;
		}

		try {

			Integer returnCode = responseJson.getInteger("returnCode");
			((JSONObject) responseBody).put("errCode", returnCode);

			// restfulapi返回的成功时returnCode为0，为了兼容老接口需要将0转成"0000"
			if (returnCode == 0) {
				((JSONObject) responseBody).put("errCode", "0000");
			}
			((JSONObject) responseBody).put("errMsg",
					((JSONObject) responseJson).get("returnMsg"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		Gson gson = new Gson();
		return gson.fromJson(responseBody.toString(), resultType);
	}

	/**
	 * post请求
	 *
	 * @param restfulApiUrl
	 * @param requestBody
	 * @param resultType
	 * @return
	 */
	public <T> T doPostInvokeResponse(String restfulApiUrl, JSONObject requestBody,
							  Type resultType) {

		log.info("restfulApiUrl:" + restfulApiUrl + ",requestBody:" + requestBody);

		String response = RestfulHttpClient.doPost(restfulApiUrl,
				requestBody.toString());

		log.info("doPostInvoke response:" + restfulApiUrl + ",response:" + response);

		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(response);
		Gson gson = new Gson();
		return gson.fromJson(jsonObject.toString(), resultType);
	}
}