package com.suncd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author qust
 * @version 1.0  2017/7/18
 */
public class ToolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolService.class);

    public void emptyTest(){
        //1.字符串非空判断
        String str1 = null;
        String str2 = "";
        String str3 = "ok";
        StringUtils.isEmpty(str1); //<1>
        StringUtils.isEmpty(str2); //<2>
        StringUtils.isEmpty(str3); //<3>

        //2.集合List、Map非空判断
        List list1 = null;
        List list2 = new ArrayList();
        Map map1 = null;
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        map3.put("1","2");
        CollectionUtils.isEmpty(list1); //<4>
        CollectionUtils.isEmpty(list2); //<5>
        CollectionUtils.isEmpty(map1); //<6>
        CollectionUtils.isEmpty(map2); //<7>
        CollectionUtils.isEmpty(map3); //<8>

        //3.数组及其它对象非空判断
        String[] strs = new String[]{};
        ToolService t = null;
        ObjectUtils.isEmpty(strs); //<9>
        ObjectUtils.isEmpty(t); //<10>
    }

    public static boolean testList(List list){
        return CollectionUtils.isEmpty(list);
    }

    public static boolean testObject(Object[] object){
        return ObjectUtils.isEmpty(object);
    }

    public static void main(String[] args){
        String kk = null;
        String[] s = new String[]{};
        List list = null;
        List list2 = new ArrayList();
        Integer i1 = 1000;
        Integer i2 = 1000;
        Map map1 = null;
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        ToolService i = null;
        map3.put("1","2");
        System.out.println("Integer : " + (i1.equals(i2)));
        System.out.println("Integer2 : " + (i1 == 1000));
        System.out.println(ObjectUtils.isEmpty(kk));
        System.out.println("Object : " + ToolService.testObject(s));
        System.out.println("list : " + ToolService.testList(list));
        System.out.println("list2 : " + ToolService.testList(list2));
        System.out.println("map1 : " + CollectionUtils.isEmpty(map1));
        System.out.println("map2 : " + CollectionUtils.isEmpty(map2));
        System.out.println("map3 : " + CollectionUtils.isEmpty(map3));
        System.out.println("map3 : " + ObjectUtils.isEmpty(i));
    }

}
