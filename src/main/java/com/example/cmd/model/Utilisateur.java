package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UTILISATEUR")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String motDePasse;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleType roleType;

    public Utilisateur(String username, String motDePasse, RoleType roleType) {
        this.username = username;
        this.motDePasse = motDePasse;
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
