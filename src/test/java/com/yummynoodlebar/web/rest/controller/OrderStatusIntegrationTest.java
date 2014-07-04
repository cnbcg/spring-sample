package com.yummynoodlebar.web.rest.controller;

import static com.yummynoodlebar.web.rest.controller.fixture.RestDataFixture.orderStatus;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.yummynoodlebar.web.controller.OrderStatusController;

public class OrderStatusIntegrationTest {

	MockMvc mockMvc;

	@InjectMocks
	OrderStatusRestController controller;

	@Mock
	OrderStatusController orderStatusController;

	UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter(),
				new Jaxb2RootElementHttpMessageConverter()).build();

		when(orderStatusController.getOrderStatus(any(String.class))).thenReturn(orderStatus(key, "Cooking"));
	}

	@Test
	public void thatViewOrderUsesHttpOK() throws Exception {
		this.mockMvc.perform(get("/aggregators/orders/{id}/status", key.toString()).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void thatViewOrderRendersJSONCorrectly() throws Exception {
		this.mockMvc.perform(get("/aggregators/orders/{id}/status", key.toString()).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.orderId").value(key.toString())).andExpect(jsonPath("$.status").value("Cooking"));
	}

	@Test
	public void thatViewOrderRendersXMLCorrectly() throws Exception {
		this.mockMvc.perform(get("/aggregators/orders/{id}/status", key.toString()).accept(MediaType.TEXT_XML)).andDo(print())
				.andExpect(content().contentType(MediaType.TEXT_XML)).andExpect(xpath("/orderStatus/orderId").string(key.toString()))
				.andExpect(xpath("/orderStatus/status").string("Cooking"));
	}
}
