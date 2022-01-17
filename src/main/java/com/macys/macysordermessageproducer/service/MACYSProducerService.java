package com.macys.macysordermessageproducer.service;

import com.macys.macysordermessageproducer.dto.json.OrderJsonMessage;
import com.macys.macysordermessageproducer.dto.xml.FulfillmentOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface MACYSProducerService {
    ResponseEntity<Boolean> produceXmlMessage(FulfillmentOrder fulfillmentOrder);

    ResponseEntity<Boolean> produceJsonMessage(OrderJsonMessage orderMessageJson);
}
