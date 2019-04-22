package com.huzihan.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.huzihan.validator.MyConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class User {

    public interface UserSimpleView{}
    public interface UserDetailView extends UserSimpleView{}

    @JsonView(UserSimpleView.class)
    private String username;

    @JsonView(UserDetailView.class)
    @NotNull(message = "密码不能为空")//校验password不能为空
    private String password;

    @MyConstraint(message = "测试用例")
    private Integer id;

    @Past   //传进来的时间必须是过去时间
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
