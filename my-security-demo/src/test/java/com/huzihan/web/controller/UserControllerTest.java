package com.huzihan.web.controller;

import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    //伪造的MVC的环境
    private MockMvc mockMvc;

    @Before
    public void setup(){
        //构建一个伪造的MVC的环境
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/user")//模拟一个get请求
                .param("username", "jojo")//请求参数
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())//对请求的结果的一个期望
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString(); //获取返回值
        System.out.println(contentAsString);
    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
        String str = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
                .andReturn().getResponse().getContentAsString();//获取返回值
        System.out.println(str);
    }

    @Test
    public void whenGenInfoFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        Date date = new Date();
        String content = "{\"username\" : \"tom\",\"password\" : null,\"birthday\" : "+date.getTime()+"}";
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        Date date = new Date();
        String content = "{\"username\" : \"tom\",\"password\" : null,\"birthday\" : "+date.getTime()+"}";
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenUploadSueccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
        .file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
