package biz.c24.io.fixml.sample.application;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.fixml.sample.configuration.C24iOConfiguration;
import biz.c24.io.fixml.sample.configuration.ExternalPropertiesConfiguration;
import biz.c24.io.fixml.sample.storage.MongoDbWriter;
import biz.c24.io.fixml.sample.util.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * @author Enrico Musuruana
 * @since 03/04/2013
 */
@ContextConfiguration(classes = {
        ExternalPropertiesConfiguration.class,
        IntegrationTestConfiguration.class,
        C24iOConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DbStoreFlowIntegrationTest {

    @Resource(name = "compass-store-channel")
    private QueueChannel compassStoreChannel;
    @Resource(name = "file-reading-adapter")
    private SourcePollingChannelAdapter sourcePollingChannelAdapter;
    @Resource(name = "mongoDbFixMlCollectionWriter")
    private MongoDbWriter mongoDbWriter;
    @Value("${file.source.dir}")
    private String dataFixturesDirectory;
    private File testFile;
    @Resource(name = "c24-parser-channel")
    private DirectChannel parseChannel;

    public DbStoreFlowIntegrationTest() {
        System.setProperty("IO_HOME", "/Applications/C24");
    }

    @Before
    public void reset() {
        Mockito.reset(mongoDbWriter);
        testFile = FileUtils.getRawFileList(dataFixturesDirectory, true)[0];
    }

    @Test
    public void normalFlow() {

        when(mongoDbWriter.store((ComplexDataObject) anyObject())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[0];
            }
        });
        sendMessage(parseChannel, testFile);
        Message<?> message = compassStoreChannel.receive(5000);
        verify(mongoDbWriter, times(1)).store((ComplexDataObject) anyObject());

        assertThat(message, notNullValue());
        assertThat(message.getPayload(), notNullValue());
    }

    @Test(expected = MessageHandlingException.class)
    public void exceptionFlow() {

        when(mongoDbWriter.store((ComplexDataObject) anyObject())).thenThrow(IllegalArgumentException.class);
        sendMessage(parseChannel, testFile);
        compassStoreChannel.receive(2000);
    }

    @Test(expected = MessageHandlingException.class)
    public void badTypeFlow() {

        when(mongoDbWriter.store((ComplexDataObject) anyObject())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "Bad type";
            }
        });
        sendMessage(parseChannel, testFile);
        compassStoreChannel.receive(2000);
    }

    private void sendMessage(final DirectChannel channel, final File file) {
        Message<?> message = MessageBuilder.withPayload(file).build();
        channel.send(message);
    }
}