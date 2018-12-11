/**
 *
 */
package com.risepu.ftk.server;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author q-wang
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AbstractServerTestCase extends AbstractJUnit4SpringContextTests {

	@Test
	public void init() throws Exception {
		assertNotNull(applicationContext);
	}
}
