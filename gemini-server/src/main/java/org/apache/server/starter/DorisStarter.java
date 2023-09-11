package org.apache.server.starter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/11 21:35
 */
@Component
public class DorisStarter {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    Connection connection=null;
    Statement statement=null;
    ResultSet resultSet=null;
    @PostConstruct
    public void start() {
        //先执行数据导入监听任务
        //todo 是否有其他更好的方法
         try{
              connection = DriverManager.getConnection(url, username, password);

             // 创建一个Statement对象，用于执行SQL查询
               statement = connection.createStatement();

               try{
                  statement.execute("use simple;");
               }catch (Exception e){
                   e.printStackTrace();
               }

                   // 执行一个简单的查询
                   String sql = "CREATE ROUTINE LOAD simple.kafka_job_newnono111 on student_kafka3\n" +
                           "PROPERTIES\n" +
                           "(\"desired_concurrent_number\"=\"1\",\"strict_mode\"=\"false\",\"format\" = \"json\"\n" +
                           ")\n" +
                           "FROM KAFKA\n" +
                           "(\"kafka_broker_list\"= \"192.168.190.90:9092\",\"kafka_topic\" = \"test\",\"property.group.id\" = \"test_group_1\",\"property.kafka_default_offsets\" = \"OFFSET_BEGINNING\",\"property.enable.auto.commit\" = \"false\"\n" +
                           ");"; // 替换成你的表名
                   boolean execute = statement.execute(sql);

         }catch (Exception e){
             e.printStackTrace();
         }finally {
             try {


                 // 关闭 Statement
                 if (statement != null) {
                     statement.close();
                 }
                 // 关闭 Connection
                 if (connection != null) {
                     connection.close();
                 }
             } catch (SQLException e) {
                 // 处理关闭资源时的异常
                 e.printStackTrace();
             }
         }
    }
}
