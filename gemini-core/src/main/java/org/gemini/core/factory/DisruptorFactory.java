package org.gemini.core.factory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.RequiredArgsConstructor;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.disruptor.LogMessageProcessor;
import org.gemini.core.disruptor.RingBufferPublisher;
import org.gemini.core.dto.MessageData;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/29 21:40
 */
@RequiredArgsConstructor
public class DisruptorFactory {

    private String appName;
    private  String env;
    private  String kafkaHosts;
    private  boolean compressor;
    private  Integer threadNum;
    private KafkaProducerClient kafkaClient;

    public DisruptorFactory(String appName, String env, String kafkaHosts,  boolean compressor, Integer threadNum) {
        this.appName = appName;
        this.env = env;
        this.kafkaHosts = kafkaHosts;
        this.compressor = compressor;
        this.threadNum = threadNum;
    }

    public Disruptor<MessageData> createDisruptor() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        EventFactory<MessageData> eventFactory = MessageData::new;
        WaitStrategy waitStrategy = new BlockingWaitStrategy(); // 适用的等待策略

        this.kafkaClient=KafkaProducerClient.getInstance(this.kafkaHosts,this.compressor==false?"none":"lz4");

        Disruptor<MessageData> disruptor = new Disruptor<>(eventFactory, 1024 * 1024, threadFactory,
                ProducerType.SINGLE, waitStrategy);

        LogMessageProcessor[] processors = new LogMessageProcessor[threadNum == null ? 10 : threadNum];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new LogMessageProcessor(this.kafkaClient);
        }

        disruptor.handleEventsWithWorkerPool(processors);
        disruptor.start();

        return disruptor;
    }

    public RingBufferPublisher createMessageSender(RingBuffer<MessageData> ringBuffer) {
        return new RingBufferPublisher(ringBuffer);
    }
}
