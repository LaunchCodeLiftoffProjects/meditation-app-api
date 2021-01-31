package com.launchcode.sunrise_meditation_java.controller;

import com.launchcode.sunrise_meditation_java.util.PasswordLink;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.launchcode.sunrise_meditation_java.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.launchcode.sunrise_meditation_java.model.Meditation;
import com.launchcode.sunrise_meditation_java.service.MeditationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.launchcode.sunrise_meditation_java.model.NewUser;
import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.model.UserDetails;
import com.launchcode.sunrise_meditation_java.service.UserService;
import com.launchcode.sunrise_meditation_java.util.CommonUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {
    @Autowired
    private  UserController userController;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private MeditationService meditationService;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordLink passwordLink;

//    @GetMapping("/forgotPassword")
//    public String revealForgottenPasswordForm(Model model) {
//        return "forgotPasswordForm";
//    }

    @PostMapping(path = "/forgotPassword")
        public String runForgotPassword(HttpServletRequest request)  {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        System.out.println("Email: " + email);
        System.out.println("Token: " + token);

        try {
        //sets User's RESET_PASSWORD_TOKEN to the token variable created on line 59
        userService.resetPasswordToken(token, email);

        String resetPasswordLink = PasswordLink.getSiteAddress(request) + "/reset_password?token=" + token;

        System.out.println(resetPasswordLink);
        //Send an email to the user

            sendEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return "/forgotPassword"; //definitely wrong

    }

    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String emailSubjectLine = "Reset password link";
        String emailBodyContent = "<p>Here is a link to reset your password: </p>" +
                "<p><b><a href=\"" + resetPasswordLink + "\">Link</a><b></p>";

        helper.setFrom("sunrisemeditationapp@gmail.com", "Sunrise Helper");
        helper.setTo(email);
        helper.setSubject(emailSubjectLine);
        helper.setText(emailBodyContent, true);
        //helper.setText(emailBodyContent);

        mailSender.send(message);

    }

    @GetMapping("/resetPassword")
    public String showPasswordResetScreen(@Param(value = "token") String token, Model model ){
        User user = userService.get(token);
        if(user == null) {
            return "/resetPassword";
        }


        return "/login"; //probably wrong
    }


    /** Still need to call updatePassword via the user's input on resetPassword.jsx */
}
