package com.github.annzem.banana.bot.test;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("file:output")
public class CamelRouterTest {
    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:file:output")
    private MockEndpoint mock;

    @EndpointInject("mock:telegram:bots")
    private MockEndpoint tgMock;

    @Autowired
    private CamelContext camelContext;

    @Test
    void whenSendBody_thenReceivedSuccessfully() throws InterruptedException {
        mock.expectedBodiesReceived("text");
        template.sendBody("direct:start", null);
        template.sendBodyAndHeader("telegram:bots", "qwe", "CamelTelegramChatId", "123");
        mock.assertIsSatisfied();
    }
}
