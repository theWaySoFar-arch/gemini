package org.gemini.server.controller;

import org.gemini.server.common.Result;
import org.gemini.server.entity.QueryParam;
import org.gemini.server.po.Person;
import org.gemini.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:42
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/dorislist")
    public List<Person> getListDoris()
    {
        return  logService.listDoris();
    }


    /**
     * 根据查询条件查询日志
     * @param queryParam
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getListByParam")
    public Result getListByParam(@RequestBody QueryParam queryParam,int current,int size){
        return logService.getListByParam(queryParam,current,size);
    }

}
