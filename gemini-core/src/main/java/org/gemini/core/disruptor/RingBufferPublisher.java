package org.gemini.core.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.gemini.core.dto.MessageData;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/29 20:54
 */
public class RingBufferPublisher {
    private RingBuffer<MessageData> ringBuffer;

    private ConcurrentLinkedQueue<MessageData> batchMessages;
    private static final int BATCH_SIZE = 50; // 定义批量大小
    private static final long MAX_BATCH_WAIT_TIME = 1000; // 定义最大等待时间（毫秒）
    private AtomicLong lastSendTime = new AtomicLong(0);

    public RingBufferPublisher(RingBuffer<MessageData> ringBuffer) {
        this.batchMessages=new ConcurrentLinkedQueue<>();
        this.ringBuffer=ringBuffer;
    }
    public void sendMessage(String message,String messageType) {
        addMessageToBatch(message,messageType);
    }



    private void addMessageToBatch(String message, String messageType) {
        MessageData messageData = new MessageData();
        messageData.setMessage(message);
        messageData.setMessageType(messageType);
        batchMessages.offer(messageData);
        if (shouldPublish()) {
            publishBatchMessages();
        }
    }

    private boolean shouldPublish() {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastSendTime.get();
        return batchMessages.size() >= BATCH_SIZE || currentTime - lastTime >= MAX_BATCH_WAIT_TIME;
    }

    private void publishBatchMessages() {
        batchMessages.forEach(messageData -> {
            long sequence = ringBuffer.next();
            MessageData data = ringBuffer.get(sequence);
            data.setMessageType(messageData.getMessageType());
            data.setMessage(messageData.getMessage());
            ringBuffer.publish(sequence);
        });
        batchMessages.clear();
        lastSendTime.set(System.currentTimeMillis());
    }


}
