package org.apache.server.service.impl;

import org.apache.server.mapper.DorisMapper;
import org.apache.server.po.Person;
import org.apache.server.service.DorisService;
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
public class DorisServiceImpl implements DorisService {
    @Autowired
    DorisMapper dorisMapper;
    @Override
    public List<Person> listDoris() {
        return dorisMapper.listDoris();
    }
}
