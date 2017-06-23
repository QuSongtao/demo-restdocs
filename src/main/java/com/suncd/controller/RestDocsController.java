package com.suncd.controller;

import com.suncd.entity.ContractDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        return new ContractDto("c","plgx");
    }

}
