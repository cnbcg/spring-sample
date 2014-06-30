package com.yummynoodlebar.rest.functional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.yummynoodlebar.rest.controller.fixture.RestDataFixture;
import com.yummynoodlebar.rest.domain.Order;

public class OrderTests {

	@Test
	public void thatOrdersCanBeAddedAndQueried() {
		ResponseEntity<Order> entity = postOrder(getHttpEntity(getHeaders("1bcg", "bcg")));

		Order order = entity.getBody();
		String path = entity.getHeaders().getLocation().getPath();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertTrue(path.startsWith("/aggregators/orders/"));

		System.out.println("The Order ID is " + order.getKey());
		System.out.println("The Location is " + entity.getHeaders().getLocation());

		assertEquals(2, order.getItems().size());
	}

//TODO	@Test
//	public void thatOrdersCannotBeAddedAndQueriedWithBadUser() {
//		try {
//			ResponseEntity<Order> entity = postOrder(getHttpEntity(getHeaders("bcg", "BADPASSWORD")));
//
//			fail("Request Passed incorrectly with status " + entity.getStatusCode());
//		} catch (HttpClientErrorException ex) {
//			assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
//		}
//	}

	@Test
	public void thatOrdersHaveCorrectHateoasLinks() {
		ResponseEntity<Order> entity = postOrder(getHttpEntity(getHeaders("bcg", "bcg")));

		Order order = entity.getBody();
		String orderBase = "/aggregators/orders/" + order.getKey();

		assertEquals(entity.getHeaders().getLocation().toString(), order.getLink("self").getHref());
		assertTrue(order.getLink("Order Status").getHref().endsWith(orderBase + "/status"));
	}

	private ResponseEntity<Order> postOrder(HttpEntity<String> httpEntity) {
		RestTemplate template = new RestTemplate();
		return template.postForEntity("http://localhost:8080/aggregators/orders", httpEntity, Order.class);
	}

	private HttpEntity<String> getHttpEntity(HttpHeaders headers) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(RestDataFixture.standardOrderJSON(), headers);
		return requestEntity;
	}

	private HttpHeaders getHeaders(final String username, final String password) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		String auth = username + ":" + password;
		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

		return headers;
	}
}
