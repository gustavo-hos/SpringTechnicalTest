package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.Mail;

import java.util.Map;

public interface MailService {

    void sendMail(String templateName, Map<String, Object> variables, Mail mail);

}
