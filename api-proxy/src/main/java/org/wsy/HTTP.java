package org.wsy;

import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTP {

	public static String post(List<NameValuePair> params, String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(params));
		CloseableHttpResponse response = httpclient.execute(httppost);
		httppost.releaseConnection();
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			return result;
		} else {
			throw new Exception("Http code:" + response.getStatusLine().getStatusCode());
		}

	}

}
