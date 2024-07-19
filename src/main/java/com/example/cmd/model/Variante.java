package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "variante")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Variante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @OneToMany(mappedBy = "variante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributValeur> attributValeurs;

    private double prix;
    private int stock;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation", nullable = false, updatable = false)
    @CreatedDate
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modification")
    @LastModifiedDate
    private Date dateModification;
}
