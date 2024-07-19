package com.example.cmd.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Table(name="attribut_valeur")
@Entity
@Getter
@Setter

@EntityListeners(AuditingEntityListener.class)
public class AttributValeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attribut_id", nullable = false)
    private Attribut attribut;

    @ManyToOne
    @JoinColumn(name = "valeur_id", nullable = false)
    private Valeur valeur;

    @ManyToOne
    @JoinColumn(name = "variante_id", nullable = false)
    private Variante variante;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation", nullable = false, updatable = false)
    @CreatedDate
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modification")
    @LastModifiedDate
    private Date dateModification;
}
