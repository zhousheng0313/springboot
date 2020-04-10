package com.zzcx.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
@Slf4j
public class RestfulHttpClient {
	/**
	 * post 方式发送请求
	 * @param url
	 * @param requestBody
	 * @return
	 */
	public static String doPost(String url, String requestBody) {
		CloseableHttpClient httpclient = createHttpClient();
		RequestConfig requestconfig = createRequestConfig();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Invoker","MACWEB");
		httpPost.setConfig(requestconfig);
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(requestBody, Charset.forName("UTF-8"));
//			stringEntity.setContentType("application/json; charset=GBK");
			httpPost.addHeader("Content-type","application/json; charset=utf-8");
			httpPost.setEntity(stringEntity);
			// execute
			response = httpclient.execute(httpPost);
			// 判断访问的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				// 状态非200
				throw new Exception("远程调用失败");
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			return EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.info("远程调用失败", e);
		} finally {
			free(httpPost,response,httpclient);
		}
		return null;
	}

	/**
	 * httpget
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpclient = createHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-Invoker","MACWEB");
		RequestConfig requestconfig = createRequestConfig();
		httpGet.setConfig(requestconfig);
		CloseableHttpResponse response = null;
		try {
			// execute
			response = httpclient.execute(httpGet);
			// 判断访问的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				// 状态非200
				throw new Exception("远程调用失败");
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			return EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.info("远程调用失败", e);
		} finally {
			free(httpGet,response,httpclient);
		}

		return null;
	}

	/**
	 * put 方式发送请求
	 * @param url
	 * @param requestBody
	 * @return
	 */
	public static String doPut(String url, String requestBody) {
		CloseableHttpClient httpclient = createHttpClient();
		RequestConfig requestconfig = createRequestConfig();

		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("X-Invoker","MACWEB");
		httpPut.setConfig(requestconfig);
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(requestBody,Charset.forName("UTF-8"));
			httpPut.addHeader("Content-type","application/json; charset=utf-8");
			httpPut.setEntity(stringEntity);
			// execute
			response = httpclient.execute(httpPut);
			// 判断访问的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				// 状态非200
				throw new Exception("远程调用失败");
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			return EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.info("远程调用失败", e);
		} finally {
			free(httpPut,response,httpclient);
		}
		return null;
	}

	/**
	 * 创建httpclient
	 * 
	 * @return
	 */
	private static CloseableHttpClient createHttpClient() {
		return HttpClients.createDefault();
	}

	/**
	 * 创建RequestConfig
	 * 
	 * @return
	 */
	private static RequestConfig createRequestConfig() {
		return RequestConfig.custom().setSocketTimeout(1000 * 60)
				.setConnectTimeout(1000 * 60).build();
	}

	/**
	 * 释放资源
	 * @param httpUriRequest
	 * @param httpResponse
	 * @param httpClient
	 */
	private static void free(HttpRequestBase httpUriRequest,
							 CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
		try {
			if (httpResponse != null) {
				httpResponse.close();
			}
			if (httpUriRequest != null) {
				httpUriRequest.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (IOException e) {
			log.info("http资源释放失败", e);
		}
	}
}