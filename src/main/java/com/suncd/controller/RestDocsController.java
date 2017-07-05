package com.suncd.controller;

import com.suncd.entity.ContractDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/user/str",method = RequestMethod.GET,produces = "text/plain")
    public String getStr(){
        return "hello happy!";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public ContractDto getUser(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "title1") String title1
//            @RequestParam(value = "title2") String title2
    ){
        return new ContractDto("c","cplgx"+title1+title);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public List<Map> testMap(@RequestParam Map<String,Object> map){
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map);
        return list;
    }

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String testStr(String map){
        return map;
    }

}
