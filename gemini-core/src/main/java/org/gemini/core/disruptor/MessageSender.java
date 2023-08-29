package org.gemini.core.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.gemini.core.dto.MessageData;

import static org.gemini.core.constant.MessageConstant.Common_PREFIX;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/29 20:54
 */
public class MessageSender {
    private RingBuffer<MessageData> ringBuffer;
    public MessageSender(RingBuffer<MessageData> ringBuffer) {
        this.ringBuffer=ringBuffer;
    }

    public void sendCommonMessage(String message) {
        long sequence = ringBuffer.next();

        try {
            MessageData messageData = ringBuffer.get(sequence);
            messageData.setMessage(Common_PREFIX+message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public void sendTraceMessage(String message) {
        long sequence = ringBuffer.next();

        try {
            MessageData messageData = ringBuffer.get(sequence);
            messageData.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
