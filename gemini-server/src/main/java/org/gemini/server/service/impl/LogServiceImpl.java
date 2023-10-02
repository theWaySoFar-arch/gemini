package org.gemini.server.service.impl;

import org.gemini.server.common.Result;
import org.gemini.server.entity.CommonLogMessage;
import org.gemini.server.entity.QueryParam;
import org.gemini.server.mapper.LogMapper;
import org.gemini.server.po.Person;
import org.gemini.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:45
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;
    @Override
    public List<Person> listDoris() {
        return logMapper.listDoris();
    }

    @Override
    public Result getListByParam(QueryParam queryParam, int current, int size) {
       List<CommonLogMessage>list =logMapper.getListByParam(queryParam,current,size);
        return null;
    }
}
