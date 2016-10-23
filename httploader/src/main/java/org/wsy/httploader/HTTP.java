package org.wsy.httploader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTP {

	public static String get(String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(get);
		get.releaseConnection();
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			return result;
		} else {
			throw new Exception("Http code:" + response.getStatusLine().getStatusCode());
		}

	}

}
