package com.Mail.Model;


import com.Mail.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

     @jakarta.persistence.Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)

     private Long Id;
     private String Nom;
     private String Prenom;
     private String Email;
     private String username;
     private String Password;
     @Enumerated(EnumType.STRING)
     private Role role;

}
