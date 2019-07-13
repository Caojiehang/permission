package com.jiehang.util;

import com.jiehang.beans.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

        String from = "jiehang.cao@gmail.com";
        int port = 465;
        String host = "smtp.gmail.com";
        String pass = "vnauwgxfzedguhhf";
        String nickname = "jiehang";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setSSLOnConnect(true);
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} send email to  {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "send email to " + StringUtils.join(mail.getReceivers(), ",") + "failed", e);
            return false;
        }
    }

}

