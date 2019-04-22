package com.huzihan.web.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.huzihan.dto.User;
import com.huzihan.exception.UserNotExistException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> getUser(){
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id){
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping("/user")
    public User create(@Valid @RequestBody User user, BindingResult bindingResult){

        //获取校验没过的信息
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        System.out.println(user);
        user.setId(1);
        return user;
    }

    @PutMapping("/user/{id:\\d+}")
    public User update(@Valid @RequestBody User user,BindingResult bindingResult){
        //获取校验没过的信息
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError)error;
                String message = fieldError.getField() + " " + error.getDefaultMessage();
                System.out.println(message);
                System.out.println(error.getDefaultMessage());
            });
        }

        System.out.println(user);
        return user;
    }

    @DeleteMapping("/user/{id:\\d+}")
    public void delete(@PathVariable String id){
        //throw new UserNotExistException(id);
        System.out.println(id);
    }
}
