package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name="CLIENT")
@Entity
@NoArgsConstructor
@Data
public class Client extends Utilisateur {
    public Client(String username, String motDePasse, RoleType roletype){
        super(username, motDePasse, roletype);
    }


}

