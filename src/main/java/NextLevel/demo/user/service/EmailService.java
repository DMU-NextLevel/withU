package NextLevel.demo.user.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final LoginService loginService;
    @Value("${email.EMAIL_TIME}")
    private Long emailTime;
    @Value("${email.EMAIL}")
    private String email;
    @Value("${email.EMAIL_PASSWORD}")
    private String emailPassword;

    private static Map<String, Pair> map = new HashMap<>();
    private static Random random = new Random();

    public void sendEmail(String toEmail){
        Pair pair = map.getOrDefault(toEmail, new Pair()).generate();

        // send email
        // SMTP 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 설정
        // props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 최신 TLS 버전 지정
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 세션 생성
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, emailPassword);
            }
        });

        try {
            // 이메일 메시지 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Login Email Subject");
            message.setText("인증 코드 5자리 : " + pair.key);

            // 이메일 전송
            Transport.send(message);
        } catch (MessagingException e) {
            log.info("send email fail " + toEmail);
            e.printStackTrace();
            throw new CustomException(ErrorCode.SEND_EMAIL_ERROR);
        } catch (Exception e) {
            log.info("send email fail " + toEmail);
            e.printStackTrace();
            throw new CustomException(ErrorCode.SEND_EMAIL_ERROR);
        }

        log.info("send email success " + toEmail +", key : " + pair.key);

        map.put(toEmail, pair);
    }

    public boolean checkEmailKey(String email, String key) {
        deleteOldMap();

        Pair pair = map.get(email);
        if(pair == null || !pair.key.equals(key)) {
            log.info("check email key fail " + email + ", input key : " + key +", server key : " + pair.key);
            return false;
        }
        pair.isChecked = true;
        return true;
    }

    public void checkEmailKeyAtRegister(String email, String key){
        if(!checkEmailKey(email, key))
            throw new CustomException(ErrorCode.NOT_CORRECT_EMAIL_KEY);
        map.remove(email);
    }

    private void deleteOldMap(){
        Date now = new Date(System.currentTimeMillis() + emailTime);
        map.keySet().stream().filter(k->now.before(map.get(k).created)).forEach(k->{log.info("erase old email : " + k); map.remove(k);});
    }

    class Pair{
        String key;
        Date created;
        boolean isChecked = false;
        Pair() {
            created = new Date();
        }
        private String generateRandomKey() {
            String number = String.format("%05d", random.nextInt(100000));
            if(map.values().stream().filter(v->v.equals(number)).findFirst().isPresent())
                return generateRandomKey();
            return number;
        }
        Pair generate(){
            key= generateRandomKey();
            return this;
        }
    }
}

