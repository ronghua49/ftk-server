/**
 *
 */
package com.risepu.ftk.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risepu.ftk.server.service.SampleService;

/**
 * @author q-wang
 */
public class SampleServiceTest extends AbstractServerTestCase {

	@Autowired
	private SampleService sampleService;

	@Test
	public void testSample() throws Exception {
		sampleService.sample1();
	}
}
