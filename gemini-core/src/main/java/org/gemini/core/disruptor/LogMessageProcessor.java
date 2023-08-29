package org.gemini.core.disruptor;

import com.lmax.disruptor.EventHandler;
import org.gemini.core.dto.BaseLogMessage;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/29 19:36
 */
public class LogMessageProcessor implements EventHandler<BaseLogMessage> {
    @Override
    public void onEvent(BaseLogMessage event, long sequence, boolean endOfBatch) throws Exception {

    }
}
