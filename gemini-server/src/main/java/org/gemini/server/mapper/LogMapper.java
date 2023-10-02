package org.gemini.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gemini.server.entity.CommonLogMessage;
import org.gemini.server.entity.QueryParam;
import org.gemini.server.po.Person;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:46
 */
@Mapper
public interface LogMapper {

    /**
     * 查询所有的doris 数据
     * @return
     */
    public List<Person> listDoris();

    List<CommonLogMessage> getListByParam(QueryParam queryParam, int current, int size);
}
