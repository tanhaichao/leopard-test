package io.leopard.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 集成测试基础类(用于开发阶段测试完整流程).
 * 
 * @author 阿海
 * 
 */
@ContextConfiguration(loader = TestContextLoader.class)
@ActiveProfiles(value = "dev", inheritProfiles = false)
@RunWith(IntegrationRunner.class)
public class IntegrationTests extends AbstractJUnit4SpringContextTests {

	protected Log logger = LogFactory.getLog(this.getClass());

	
	// /** 默认username:username */
	// public String username = "username";
	//
	// /** 默认passport:passport */
	// public String passport = "passport";
	//
	// public String sessUsername = "username";
	//
	// public int pageId = 1;
	//
	// public long sessUid = 1;
	//
	// public long uid = 1;
	//
	// /** 默认游戏ID:ddt */
	// public String gameId = "ddt";
	//
	// /** 默认游戏serverId:s1 */
	// public String serverId = "s1";
	// /** 用户IP */
	// public String proxyIp = "127.0.0.1";
	//
	// public String ANY_STR = "any";
	//
	// public int ANY_INT = 8888;

}