package com.huzihan.web.controller;

import org.junit.Test;

import java.time.LocalDateTime;

public class demotest {

    @Test
    public void test(){
        String str = "/purchase-order/6e34027d8fbb46e4826a28baddc95326";
        str = str.substring(0,str.lastIndexOf("/"));
        System.out.println(str);
    }

    @Test
    public void test2(){
        LocalDateTime of = LocalDateTime.of(2015, 12, 01, 0, 59, 59).plusDays(1).minusHours(1);
        System.out.println(of.toString());
    }

}
