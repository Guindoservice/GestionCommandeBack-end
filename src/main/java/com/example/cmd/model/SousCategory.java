package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sous_category")
@Data
public class SousCategory {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String name;
    private String descript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
