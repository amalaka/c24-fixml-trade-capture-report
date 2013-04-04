package biz.c24.io.fixml.sample.application;

import biz.c24.io.fixml.sample.configuration.C24iOConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: Enrico Musuruana
 * Date: 03/04/2013
 * Time: 13:35
 */
@ContextConfiguration(classes = C24iOConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FlowTest
{
    @Autowired
    @Qualifier(value = "c24-parser-channel")
    MessageChannel parseChannel;

    @Test
    public void testInputHandler()
    {
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}