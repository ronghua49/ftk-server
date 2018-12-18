/**
 *
 */
package com.risepu.ftk.server.test;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author q-wang
 */
public class AbstractHttpClientTestCase {

	private HttpClient httpClient = HttpClientBuilder.create().build();

	public void testCaptcha() throws Exception {
		HttpPost post = new HttpPost("http://localhost:8080/captcha/v1/get");
		HttpResponse response = httpClient.execute(post);
		System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));

		post = new HttpPost("http://localhost:8080/org/regist");
		post.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").setText("{}").build());
		response = httpClient.execute(post);
		System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
	}

	public static void main(String[] args) throws Exception {
		AbstractHttpClientTestCase test = new AbstractHttpClientTestCase();
		test.testCaptcha();
	}
}
