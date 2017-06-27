package com.suncd.controller;

import com.suncd.entity.ContractDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：RestDocs Controller
 *
 * @author qust
 * @version 1.0  2017/5/17
 */
//@RequestMapping("/restDocs")
@RestController
public class RestDocsController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String restTest(){
        return "cplgx";
    }

    @RequestMapping(value = "/user/5",method = RequestMethod.GET)
    public ContractDto get(){
        return new ContractDto("c","cplgx");
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public ContractDto getUser(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "title1") String title1
//            @RequestParam(value = "title2") String title2
    ){
        return new ContractDto("c","cplgx"+title1+title);
    }

}
