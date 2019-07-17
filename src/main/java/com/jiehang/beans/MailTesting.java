package com.jiehang.beans;

import com.jiehang.util.MailUtil;

import java.util.Collections;

/**
 * @ClassName MailTesting
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-14 00:35
 **/

public class MailTesting {

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setSubject("password");
        mail.setReceivers(Collections.singleton("cjh790880528@gmail.com"));
        mail.setMessage("hello world!");
        MailUtil.send(mail);
    }
}
