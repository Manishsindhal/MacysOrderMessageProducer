package com.macys.macysordermessageproducer.service;

import com.macys.macysordermessageproducer.component.RabbitQueueSender;
import com.macys.macysordermessageproducer.dto.json.OrderJsonMessage;
import com.macys.macysordermessageproducer.dto.xml.FulfillmentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MACYSProducerServiceImpl implements MACYSProducerService {

    @Autowired
    RabbitQueueSender rabbitQueueSender;

    @Override
    public ResponseEntity<Boolean> produceXmlMessage(FulfillmentOrder fulfillmentOrder) {
        try {
            rabbitQueueSender.send(fulfillmentOrder);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Boolean> produceJsonMessage(OrderJsonMessage orderMessageJson) {
        try {
            rabbitQueueSender.send(orderMessageJson);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
