package com.example.tmpproject.Utill;


import com.example.tmpproject.Model.PaginationModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 * @author henil
 */
public class CommonUtill {


    public static ResponseModel create(String message, Object t, HttpStatus httpStatus, int httpStatusCode) {
        ResponseModel rs = new ResponseModel();
        rs.setMessage(message);
        rs.setData(t);
        rs.setStatus(httpStatus);
        rs.setStatusCode(httpStatusCode);
        return rs;
    }

    public static Calendar convertStringToCalendarDateAndTime(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Pageable getPaginationDetails(PaginationModel paginationModel) {
        /*creating page detail
        page : PageNo: 0 ,
        Limit: 20,
        Sort : Sort.Direction.ASC ,
        property :"CreatedAt"*/
        return PageRequest.of(paginationModel.getPage(), paginationModel.getLimit(), (paginationModel.getSortOrder().equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
    }

    public static java.sql.Date findCurrentDate()
    {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return java.sql.Date.valueOf(dateObj.format(formatter));
    }
    public static void sendMail(String toEmail, String subject, String message)
    {
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        var mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("johndoe@example.com");
        javaMailSender.send(mailMessage);
    }

    public static String generateRandomPassword() {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

}
