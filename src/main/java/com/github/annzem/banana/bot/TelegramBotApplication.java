package com.github.annzem.banana.bot;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TelegramBotApplication {

    @Value("${com.company.bananabot.broker-url}")
    private String brokerUrl;

    @Value("${com.company.bananabot.username}")
    private String brokerUsername;

    @Value("${com.company.bananabot.password}")
    private String brokerPassword;

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

    @Bean
    public JmsComponent jmsComponent() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL(brokerUrl);
        cf.setUserName(brokerUsername);
        cf.setPassword(brokerPassword);

        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(cf);
        return jms;
    }

}
