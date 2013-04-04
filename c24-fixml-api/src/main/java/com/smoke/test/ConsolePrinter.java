package com.smoke.test;

import org.springframework.integration.Message;

/**
 * @author: Enrico Musuruana
 * Date: 03/04/2013
 * Time: 12:39
 */
public class ConsolePrinter
{

    public Message<?> service(Message<?> message)
    {
        System.out.println("Pulled: " + message.getPayload().toString());
        return message;
    }
}