package com.hua.gentlemanx2.database;


import java.io.Serializable;

public class Member implements Serializable {
    private int member_id;
    private String name;
    private String password;
    private String email;
    private String sex;
    private String mobile;
    private Object registertime;
    private Object lastlogin;
    private String image;
    private Object memberAddresses;
}
