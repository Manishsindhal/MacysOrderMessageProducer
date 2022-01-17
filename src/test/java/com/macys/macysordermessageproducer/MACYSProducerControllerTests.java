package com.macys.macysordermessageproducer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.macys.macysordermessageproducer.dto.json.OrderJsonMessage;
import com.macys.macysordermessageproducer.dto.xml.FulfillmentOrder;
import com.macys.macysordermessageproducer.service.MACYSProducerService;

@RunWith(SpringRunner.class)
//@WebMvcTest(MACYSProducerController.class)
@AutoConfigureMockMvc
@SpringBootTest
class MACYSProducerControllerTests {

	@MockBean
	MACYSProducerService macysProducerService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testproduceJsonMessage() throws Exception {
		OrderJsonMessage orderJsonMessage = new OrderJsonMessage();

		when(macysProducerService.produceJsonMessage(orderJsonMessage))
				.thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

		mockMvc.perform(post("/macys/producer/json")
				.content(asJsonString(orderJsonMessage))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void testproduceXmlMessage() throws Exception {

		FulfillmentOrder fulfillmentOrder = new FulfillmentOrder();

		when(macysProducerService.produceXmlMessage(fulfillmentOrder))
				.thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

		mockMvc.perform(post("/macys/producer/xml").content(asXMLString(fulfillmentOrder))
				.contentType(MediaType.APPLICATION_XML_VALUE)).andExpect(status().isOk()).andDo(print());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String asXMLString(final Object obj) {
		try {
			return new XmlMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
