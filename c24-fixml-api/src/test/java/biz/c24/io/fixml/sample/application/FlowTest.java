package biz.c24.io.fixml.sample.application;

import biz.c24.io.fixml.sample.configuration.C24iOConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Enrico Musuruana
 * @since 03/04/2013
 */
@ContextConfiguration(classes = {
        C24iOConfiguration.class,
        IntegrationTestConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class FlowTest {

    @Resource(name = "compass-store-channel-queue")
    private QueueChannel compassStoreChannel;
    @Resource(name = "file-reading-adapter")
    private SourcePollingChannelAdapter sourcePollingChannelAdapter;

    public FlowTest() {
        System.setProperty("IO_HOME", "/Applications/C24");
    }

    @Test
    public void testInputHandler() {

        sourcePollingChannelAdapter.start();
        Message<?> message = compassStoreChannel.receive(5000);
        assertThat(message, notNullValue());
        //assertThat(message.getPayload(), notNullValue());
    }
}