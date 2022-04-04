package org.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for routing the messages from and to the Telegram chat.
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Autowired
    private Bot bot;

    @Value("com.company.bananaapp.broker.inputqueue")
    private String bananaappQueue;

    @Override
    public void configure() {

        from("telegram:bots")
                .bean(Bot.class, "process(${header.CamelTelegramChatId}, ${body})")
                //.to("telegram:bots")
                .to("jms:queue:{{com.company.bananaapp.broker.inputqueue}}");

//        from("jms:queue:{{com.company.bananabot.broker.inputqueue}}")
//                .bean(bot)
//                .setHeader("chatId", constant())
//                .to("telegram:bots");

    }
}
