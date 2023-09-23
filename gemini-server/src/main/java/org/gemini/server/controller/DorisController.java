package org.gemini.server.controller;

import org.gemini.server.po.Person;
import org.gemini.server.service.DorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:42
 */
@RestController
public class DorisController {

    @Autowired
    DorisService dorisService;

    @GetMapping("/dorislist")
    public List<Person> getListDoris()
    {
        return  dorisService.listDoris();
    }
}
