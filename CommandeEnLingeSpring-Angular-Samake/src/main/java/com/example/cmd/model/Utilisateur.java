package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "UTILISATEUR")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
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

    public BigDecimal getSolde() {
        return getSolde();
    }

    public void setSolde(BigDecimal subtract) {
    }

    public void setAdmin(Admin admin) {
    }
}