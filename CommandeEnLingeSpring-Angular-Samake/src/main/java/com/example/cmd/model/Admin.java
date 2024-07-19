package com.example.cmd.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Table(name="ADMIN")
@Entity

public class Admin extends Utilisateur {
    @OneToMany(mappedBy = "admin")
    private Set<Personnel> personnel;

    public Admin() {}

    public Admin(String username, String motDePasse, RoleType roletype){
        super(username, motDePasse, roletype);

    }


}
