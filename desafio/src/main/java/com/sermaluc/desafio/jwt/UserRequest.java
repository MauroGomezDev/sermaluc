package com.sermaluc.desafio.jwt;

import com.sermaluc.desafio.model.Phone;
import com.sermaluc.desafio.model.User;

import java.util.List;

public class UserRequest {
   // private User user;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
  //  private String token;

    public UserRequest() {
    }

    public UserRequest(String juan_perez, String s, String password12, List<Phone> asList) {
    }

    // public User getUser() {
   //     return user;
   // }

    //public void setUser(User user) {
    //    this.user = user;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
}

