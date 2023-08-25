package org.gemini.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 异常堆栈工具类
 * @date 2023/8/25 20:17
 */
public class ExceptionStackTraceUtils {
    public static String getStackTraceAsString(Object obj) {
        if (obj instanceof Exception) {
            Exception exception = (Exception) obj;
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);

            try {
                exception.printStackTrace(printWriter);
                return stringWriter.toString();
            } finally {
                printWriter.close();
            }
        }

        return obj.toString();
    }
}
