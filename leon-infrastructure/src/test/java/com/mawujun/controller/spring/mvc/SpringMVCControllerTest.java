package com.mawujun.controller.spring.mvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // path defaults to "file:src/main/webapp"
@ContextConfiguration(locations={"classpath:com/mawujun/controller/spring/mvc/dispatcher-servlet.xml"})
public class SpringMVCControllerTest {
	@Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockServletContext servletContext;

    @Autowired
    private MockHttpServletRequest request;

    @Autowired
    private MockHttpServletResponse response;

    @Autowired
    private MockHttpSession session;

    @Autowired
    private ServletWebRequest webRequest;
    
    private MockMvc mockMvc; 


	
	@Test
	public void test() {
		assertNotNull(wac);
	}

	@Before
	public void setup() {
		assertNotNull(wac);
		this.mockMvc = webAppContextSetup(this.wac).build();
		//这句话在正式库是不用加的
		ToJsonConfigHolder.reset();
		ToJsonConfigHolder.setAutoWrap(false);

	}
	@Ignore
	@Test
	public void getFoo() throws Exception {
		this.mockMvc.perform(get("/foo").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.name").value("Lee"));

	}
	@Ignore
	@Test
	public void getFoo1() throws Exception {
		mockMvc.perform(get("/form"))
	    .andExpect(status().isOk())
	    .andExpect(content().contentType("text/html"))
	    .andExpect(forwardedUrl("/WEB-INF/layouts/main.jsp"));

	}
	
	@Test
	public void queryPage() throws Exception {
		this.mockMvc.perform(get("/test/queryPage.do").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.total").value(100))
		.andExpect(jsonPath("$..root[0].name").value("name0"));
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));


	}
	
	@Test
	public void queryPage1() throws Exception {
		this.mockMvc.perform(get("/test/queryPage1.do").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		//.andExpect(jsonPath("$.total").value(100))
		.andExpect(jsonPath("$..result[0].name").value("name0"));

	}
	
	@Test
	public void queryMap() throws Exception {
		this.mockMvc.perform(get("/test/queryMap.do").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.name").value("name"))
		.andExpect(jsonPath("$.age").value("111"));
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));


		
	}
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	@Test
	public void queryModel() throws Exception {
		this.mockMvc.perform(get("/test/queryModel.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.name").value("parent"))
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.age").value(11));
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryCycleList() throws Exception {
		this.mockMvc.perform(get("/test/queryCycleList.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.[0]name").value("parent"))
		.andExpect(jsonPath("$.[0]id").value(1))
		.andExpect(jsonPath("$.[0]createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.[0]age").value(11));
		//.andExpect(content().string("{\"age\":11,\"childen\":[{\"age\":11,\"id\":1,\"name\":\"child\"},{\"age\":22,\"id\":2,\"name\":\"child1\"}],\"createDate\":1358225425226,\"id\":1,\"name\":\"parent\"}"));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryCycle() throws Exception {
		this.mockMvc.perform(get("/test/queryCycle.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.name").value("parent"))
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.age").value(11));
		//.andExpect(content().string("{\"age\":11,\"childen\":[{\"age\":11,\"id\":1,\"name\":\"child\"},{\"age\":22,\"id\":2,\"name\":\"child1\"}],\"createDate\":1358225425226,\"id\":1,\"name\":\"parent\"}"));
	}
	@Test
	public void filterProperty() throws Exception {
		this.mockMvc.perform(get("/test/filterProperty.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.root.name").doesNotExist())
		.andExpect(jsonPath("$.root.id").value(1))
		.andExpect(jsonPath("$.root.createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.root.age").doesNotExist());
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));
	}
	@Test
	public void filterPropertyList() throws Exception {
		this.mockMvc.perform(get("/test/filterPropertyList.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.root[0].name").doesNotExist())
		.andExpect(jsonPath("$.root[0].id").value(1))
		.andExpect(jsonPath("$.root[0].createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.root[0].age").doesNotExist());
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));
	}
	/**
	 * 测试只返回id的关联数据
	 * @throws Exception
	 */
	@Test
	public void filterOnlyId() throws Exception {
		this.mockMvc.perform(get("/test/filterOnlyId.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.root.name").value("child"))
		.andExpect(jsonPath("$.root.id").value(1))
		.andExpect(jsonPath("$.root.createDate").value(formatter.format(new Date())))
		.andExpect(jsonPath("$.root.parent.id").value(2));
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));
	}
	
	
	/**
	 * 测试了对象内集合的绑定
	 * @throws Exception
	 */
	@Test
	public void bindModel() throws Exception {//.content("{name:'parent',id:1,createDate:"+((new Date()).toString()+",age:11}")
		this.mockMvc.perform(get("/test/bindModel.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content("{name:'parent',id:1,createDate:'2012-11-11',age:11,childen:[{name:'childrens'}]}").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.name").value("parent"))
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.createDate").value("2012-11-11"))
		.andExpect(jsonPath("$.age").value(11))
		.andExpect(jsonPath("$.childen[0].name").value("childrens"));
		//.andExpect(content().string("{\"fieldErrors\":[{\"path\":\"title\",\"message\":\"The title cannot be empty.\"}]}"));
	}


	@Test
	public void bindPageRequestByJosn() throws Exception {//.content("{name:'parent',id:1,createDate:"+((new Date()).toString()+",age:11}")
		this.mockMvc.perform(get("/test/bindPageRequestByJosn.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content("{start:0,limit:10,wheres:[{key:'name_like',value:'ma'},{property:'age',op:'LT',value:10}],sorts:[{property:'name',direction:'ASC'},{property:'age',direction:'DESC'}]}").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.start").value(0))
		.andExpect(jsonPath("$..limit").value(10))
		.andExpect(jsonPath("$.wheres[0].property").value("name"))
		.andExpect(jsonPath("$.wheres[0].value").value("%ma%"))
		.andExpect(jsonPath("$.wheres[0].op").value("LIKE"))
		.andExpect(jsonPath("$.wheres[1].property").value("age"))
		.andExpect(jsonPath("$.wheres[1].value").value("10"))
		.andExpect(jsonPath("$.wheres[1].op").value("LT"))
		.andExpect(jsonPath("$.sorts[0].property").value("name"))
		.andExpect(jsonPath("$.sorts[0].direction").value("ASC"))
		.andExpect(jsonPath("$.sorts[1].property").value("age"))
		.andExpect(jsonPath("$.sorts[1].direction").value("DESC"));
	}
	
	@Test
	public void bindPageRequestByConverter() throws Exception {//.content("{name:'parent',id:1,createDate:"+((new Date()).toString()+",age:11}")
		this.mockMvc.perform(get("/test/bindPageRequestByConverter.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.param("pageRequest", "{start:0,limit:10,wheres:[{key:'name_like',value:'ma'},{property:'age',op:'LT',value:10}],sorts:[{property:'name',direction:'ASC'},{property:'age',direction:'DESC'}]}").contentType(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(content().encoding("ISO-8859-1"))
		.andExpect(content().contentType("application/json"))
		//.andExpect(jsonPath("$.success").value(true))
		.andExpect(jsonPath("$.start").value(0))
		.andExpect(jsonPath("$.limit").value(10))
		.andExpect(jsonPath("$.wheres[0].property").value("name"))
		.andExpect(jsonPath("$.wheres[0].value").value("%ma%"))
		.andExpect(jsonPath("$.wheres[0].op").value("LIKE"))
		.andExpect(jsonPath("$.wheres[1].property").value("age"))
		.andExpect(jsonPath("$.wheres[1].value").value("10"))
		.andExpect(jsonPath("$.wheres[1].op").value("LT"))
		.andExpect(jsonPath("$.sorts[0].property").value("name"))
		.andExpect(jsonPath("$.sorts[0].direction").value("ASC"))
		.andExpect(jsonPath("$.sorts[1].property").value("age"))
		.andExpect(jsonPath("$.sorts[1].direction").value("DESC"));
	}
////	//http://www.iteye.com/topic/1122793?page=3#2385378
//	@Test
//	public void bindPageRequestNormal() throws Exception {//.content("{name:'parent',id:1,createDate:"+((new Date()).toString()+",age:11}")
//		this.mockMvc.perform(get("/test/bindPageRequestNormal.do").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
//				.param("start", "0").param("limit", "10")
//				.param("wheres[0].key", "name_like")
//				.param("wheres[0].value", "ma")
//				.param("wheres[1].property", "age")
//				.param("wheres[1].op", "LT")
//				.param("wheres[1].value", "10")
//				.param("sorts[0].property", "name")
//				.param("sorts[0].direction", "ASC")
//				.param("sorts[1].prop", "age")
//				.param("sorts[1].dir", "DESC")
//				.contentType(MediaType.TEXT_HTML))
//		.andExpect(status().isOk())
//		.andExpect(content().encoding("ISO-8859-1"))
//		.andExpect(content().contentType("application/json"))
//		.andExpect(jsonPath("$.success").value(true))
//		//.andExpect(jsonPath("$.total").value(1))//http://goessner.net/articles/JsonPath/
//		.andExpect(jsonPath("$..start").value(0))
//		.andExpect(jsonPath("$..limit").value(10))
//		.andExpect(jsonPath("$..wheres[0].property").value("name"))
//		.andExpect(jsonPath("$..wheres[0].value").value("%ma%"))
//		.andExpect(jsonPath("$..wheres[0].op").value("LIKE"))
//		.andExpect(jsonPath("$..wheres[1].property").value("age"))
//		.andExpect(jsonPath("$..wheres[1].value").value("10"))
//		.andExpect(jsonPath("$..wheres[1].op").value("LT"))
//		.andExpect(jsonPath("$..sorts[0].property").value("name"))
//		.andExpect(jsonPath("$..sorts[0].direction").value("ASC"))
//		.andExpect(jsonPath("$..sorts[1].property").value("age"))
//		.andExpect(jsonPath("$..sorts[1].direction").value("DESC"));
//	}
//	
//	/**
//	 * 测试同个异常，以不同的形式返回错误形式
//	 * @throws Exception
//	 */
//	@Test
//	public void testExceptionReturnView() throws Exception {
//		this.mockMvc.perform(get("/test/testException.do").accept(MediaType.TEXT_HTML))
//		.andExpect(status().isServiceUnavailable())
//		//.andExpect(content().contentType("text/html"))
//	    .andExpect(forwardedUrl("/errors/503.jsp"));
//	}
//	
//	@Test
//	public void testExceptionReturnJson() throws Exception {
//		this.mockMvc.perform(get("/test/testException.do").accept(MediaType.APPLICATION_JSON))
//		.andExpect(status().isServiceUnavailable())
//		.andExpect(content().contentType("application/json"))
//		.andExpect(jsonPath("$.success").value(false))
//		.andExpect(jsonPath("$.message").value("后台发生系统异常，请联系管理员或重试!"));
//	}
//	@Test
//	public void testBussinessExceptionReturnJson() throws Exception {
//		this.mockMvc.perform(get("/test/testBussinessException.do").accept(MediaType.APPLICATION_JSON))
//		.andExpect(status().isServiceUnavailable())
//		.andExpect(content().contentType("application/json"))
//		.andExpect(jsonPath("$.success").value(false))
//		.andExpect(jsonPath("$.message").value("系统异常，请重试或者联系管理员"));
//	}
//	@Test
//	public void testConstraintViolationExceptionReturnJson() throws Exception {
//		this.mockMvc.perform(get("/test/testConstraintViolationException.do").accept(MediaType.APPLICATION_JSON))
//		.andExpect(status().isServiceUnavailable())
//		.andExpect(content().contentType("application/json"))
//		.andExpect(jsonPath("$.success").value(false))
//		.andExpect(jsonPath("$.message").value("email字段不是一个合法的电子邮件地址:11;"));
//	}
	
}
