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

@Table(name="attribut")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Attribut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation", nullable = false, updatable = false)
    @CreatedDate
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modification")
    @LastModifiedDate
    private Date dateModification;

    @OneToMany(mappedBy = "attribut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributValeur> attributValeurs;
}
