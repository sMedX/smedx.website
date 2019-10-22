package app.web.GraphQL.Mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by anton_arakcheyev on 16/12/2018.
 */
@Component("gqlRootMutationResolver")
@PropertySource(value = {
        "classpath:resources/app.properties"
})
public class GraphQLMessageMutation implements GraphQLMutationResolver {
    protected static Logger logger = Logger.getLogger(GraphQLMessageMutation.class.getName());

    public GraphQLMessageMutation() {
    }

    @Autowired
    private Environment env;

    public Boolean sendMessage(String name, String email, String body) {
        System.out.println("String name [" + name + "], String email [" + email + "], String body [" + body + "]");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                env.getRequiredProperty("messaging.user"),
                                env.getRequiredProperty("messaging.pwd")
                        );
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(env.getRequiredProperty("messaging.to")));
            message.setSubject("Request from site");
            StringBuilder bodyTxt = new StringBuilder();
            bodyTxt.append("Requester: ").append(name).append("\r\n")
                    .append("Email: ").append(email)
                    .append("\r\n\r\n")
                    .append("*************************[ MESSAGE ]*************************")
                    .append("\r\n\r\n")
                    .append(body);

            message.setText(bodyTxt.toString());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            System.out.println("name = [" + name + "], email = [" + email + "], body = [" + body + "]");
            System.out.println("e = " + e.getMessage());
            //throw new RuntimeException(e);
            return false;
        }
        return true;
    }
}
