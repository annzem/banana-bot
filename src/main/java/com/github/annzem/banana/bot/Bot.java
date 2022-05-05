package com.github.annzem.banana.bot;

import com.github.annzem.banana.protocol.TgMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Bot {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method processes incoming messages and return responses.
     *
     * @param message a message coming from a human user in a chat
     * @return the reply of the bot. Return null if you don't want to answer
     */
    public TgMessage process(String chatId, String message) {

        log.info("Received message: {}", message);


//        return "Why did you say \"" + message.replace("\"", "-") + "\"?";
        return new TgMessage(message, chatId);
    }

    public TgMessage processTgMessage(TgMessage tgMessage) {
        tgMessage.setText("from bananaApp: " + tgMessage.getText());
        return tgMessage;
    }

}