package org.gemini.server.service.impl;

import org.gemini.server.mapper.DorisMapper;
import org.gemini.server.po.Person;
import org.gemini.server.service.DorisService;
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
