package com.bocloud.paas.auth.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
/**
 * Spring RestTemplate工具类
 * @author zll
 */
public class RestTemplateUtils {
	
	private static SimpleClientHttpRequestFactory factory;
	private RestTemplateUtils() {}
	
	/**
	 * 单例对象实例
	 */
	private static class SingletonRestTemplate {
		final static RestTemplate INSTANCE = new RestTemplate(factory);
	}
	
	/**
	 * 单例实例
	 */
	public static RestTemplate getInstance() {
		factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
		return SingletonRestTemplate.INSTANCE;
	}

	
	/**
	 * 
	 * 发送post请求
	 * 
	 * @param url
	 *            请求URL地址
	 * @param data
	 *            json数据
	 */
	public static String post(String url, String data, String token)throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Encoding", "UTF-8");
		headers.add("Content-Type", "application/json; charset=UTF-8");
		headers.add("Token", token);

		HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
		return RestTemplateUtils.getInstance().postForObject(url, formEntity, String.class);
	}
	
	/**
	 * get根据url获取对象
	 */
	public static String get(String url) {
		return RestTemplateUtils.getInstance().getForObject(url, String.class,
				new Object[] {});
	}

	/**
	 * getById根据ID获取对象
	 */
	public String getById(String url, String id) {
		return RestTemplateUtils.getInstance().getForObject(url, String.class,
				id);
	}

	/**
	 * post提交对象
	 */
	public String post(String url, String data) {
		return RestTemplateUtils.getInstance().postForObject(url, null,
				String.class, data);
	}

	/**
	 * put修改对象
	 */
	public void put(String url, String data) {
		RestTemplateUtils.getInstance().put(url, null, data);
	}

	/**
	 * delete根据ID删除对象
	 */
	public void delete(String url, String id) {
		RestTemplateUtils.getInstance().delete(url, id);
	}
	
	public static void main(String[] args) {
		String aaa=RestTemplateUtils.get("http://localhost:9090/application/2.0/user/list");
		System.out.println("#################################################");
		System.out.println(aaa);
	}
}
