package org.apache.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.server.po.Person;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:46
 */
@Mapper
public interface DorisMapper {

    /**
     * 查询所有的doris 数据
     * @return
     */
    public List<Person> listDoris();

}
