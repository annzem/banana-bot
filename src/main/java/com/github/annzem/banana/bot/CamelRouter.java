package com.github.annzem.banana.bot;

import com.github.annzem.banana.protocol.TgMessage;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

    @Autowired
    private Bot bot;

    @Override
    public void configure() {

        from("telegram:bots")
                .routeId("tg-in-route")
                .bean(Bot.class, "process(${header.CamelTelegramChatId}, ${body})")
                .marshal(new JacksonDataFormat(TgMessage.class))
                .setHeader("class", constant(TgMessage.class.getCanonicalName()))
                .to("jms:queue:{{com.company.bananaapp.broker.inputqueue}}");

        from("jms:queue:{{com.company.bananabot.broker.inputqueue}}")
                .routeId("tg-out-route")
                .unmarshal(new JacksonDataFormat(TgMessage.class))
                .bean(Bot.class, "processTgMessage(${body})")
                .process(exchange -> {
                    TgMessage tgMessage = (TgMessage) exchange.getIn().getBody();
                    exchange.getIn().getHeaders().put("CamelTelegramChatId", tgMessage.getChatId());
                    exchange.getIn().setBody(tgMessage.getText());
                })
                .to("telegram:bots");

//        from("direct:start")
//                .process()
//                .to("telegram:bots/123456789:insertAuthorizationTokenHere");

        from("direct:start")
                .routeId("greetings-route")
                .setBody(constant("text"))
                .to("file:output");

    }
}
