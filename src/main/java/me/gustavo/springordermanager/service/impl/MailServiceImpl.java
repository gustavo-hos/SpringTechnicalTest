package me.gustavo.springordermanager.service.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import me.gustavo.springordermanager.model.Mail;
import me.gustavo.springordermanager.service.intf.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String templateName, Map<String, Object> variables, Mail mail) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("classpath:/email/" + templateName + ".mustache");
        Writer writer = new StringWriter();
        mustache.execute(writer, variables);

        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "utf-8");

        try {
            helper.setTo("gustavo@terato.me");
            helper.setSubject(mail.getSubject());
            helper.setText(writer.toString(), true);

//            mailSender.send(mimeMailMessage); // only for tests

            // a more sophisticated way is calling an external mail service
            // running on a spring cloud instance or a Kafka Broker :)
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> mailSender.send(mimeMailMessage));

            future.thenRun(() -> LOGGER.info("Email {} sent to {}", templateName, mail.getTo()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
