package com.Mail.Controller;


import com.Mail.Model.Admin;
import com.Mail.Model.Email;
import com.Mail.Model.Personnel;
import com.Mail.Service.AdminService;
import com.Mail.Service.MailService;
import com.Mail.Service.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Admin")


@AllArgsConstructor

public class AdminController {
    private AdminService adminService;
    private MailService mailService;
    private MailSender mailSender;
    private PersonnelService personnelService;

    @PostMapping("/CreationAdmin")
    public Admin CreateAdmin(@RequestBody Admin admin) {
        Admin createdmin = adminService.CreerAdmin(admin);

        // Envoi de l'e-mail avec le mot de passe
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(createdmin.getEmail());
        message.setSubject("Veuillez vérifier votre compte");
        message.setText("Bonjour, voici votre mot de passe  : " + createdmin.getPassword()+ "et votre username est :" + createdmin.getUsername());
        mailSender.send(message);

        return adminService.CreerAdmin(admin);
    }

    @PostMapping("/Creerpersonnel")
    public Personnel Creerpersonnel(@RequestBody Personnel personnel ) {
       // String email = (String) request.getParameter("email");
     //  String password = (String) request.getParameter("password");
       // String email = requestBody.get("email");
      //  String password = requestBody.get("password");

        //personnel = new Personnel();
        //personnel.setEmail(email);
        // Attribuez le mot de passe à partir de la requête
        //personnel.setPassword(password); // Assurez-vous que c'est sécurisé

        Personnel createdPersonnel = personnelService.Creerpersonnel(personnel);

        // Envoi de l'e-mail avec le mot de passe
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(createdPersonnel.getEmail());
        message.setSubject("Veuillez vérifier votre compte");
        message.setText("Bonjour, voici votre mot de passe  : " + createdPersonnel.getPassword()+ "et votre username est :" + createdPersonnel.getUsername());
        mailSender.send(message);




        //mailService.Creermail(createdPersonnel.getEmail(),"Veuillez vérifier votre compte", "Bonjour, voici votre mot de passe : " + createdPersonnel.getPassword());
        return personnelService.Creerpersonnel(personnel);

    }

}
