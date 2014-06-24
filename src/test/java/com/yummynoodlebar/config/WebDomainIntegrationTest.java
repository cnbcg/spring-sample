package com.yummynoodlebar.config;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.yummynoodlebar.config.persistence.PersistenceConfig;
import com.yummynoodlebar.core.services.MenuService;
import com.yummynoodlebar.events.menu.CreateMenuItemEvent;
import com.yummynoodlebar.events.menu.MenuItemDetails;
import com.yummynoodlebar.web.controller.fixture.WebDataFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@ContextConfiguration(classes = { CoreConfig.class, WebConfig.class, PersistenceConfig.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class WebDomainIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	MenuService menuService;
	
	@Autowired
	MongoOperations mongo;

	@Before
	public void setup() {
		mongo.dropCollection("menu");
		
		for (MenuItemDetails menuItemDetail : WebDataFixture.allMenuItemDetails()) {
			menuService.createMenuItem(new CreateMenuItemEvent(menuItemDetail));
		}
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@After
	public void teardown() {
		mongo.dropCollection("menu");
	}

	@Test
	public void thatTextReturned() throws Exception {
		mockMvc.perform(get("/")).andDo(print())
				.andExpect(content().string(containsString(WebDataFixture.STANDARD)))
				.andExpect(content().string(containsString(WebDataFixture.CHEF_SPECIAL)))
				.andExpect(content().string(containsString(WebDataFixture.LOW_CAL)));
	}

}
