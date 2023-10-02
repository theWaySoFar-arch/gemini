package org.gemini.server.service;

import org.gemini.server.common.Result;
import org.gemini.server.entity.QueryParam;
import org.gemini.server.po.Person;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:44
 */

public interface LogService {
    public List<Person>listDoris();

    Result getListByParam(QueryParam queryParam, int current, int size);
}
