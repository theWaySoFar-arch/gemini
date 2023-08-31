package org.gemini.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 压缩日志
 * @date 2023/8/23 19:30
 */
@Data
@NoArgsConstructor
public class CompressMessage {
    /**
     * 压缩日志长度
     */
    private Integer length;
    /**
     * 日志字节数组
     */
    private byte[] body;
}
