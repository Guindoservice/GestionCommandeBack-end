package com.example.cmd.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

public class ProduitDto {
    private String nom;
    private String description;
    private boolean aVariante;
    private double prix;
    private int stock;
    private List<VarianteDto> variantes;


    public boolean isAVariante() {
        return aVariante;
    }
    public void setAVariante(boolean aVariante) {
        this.aVariante = aVariante;
    }
}
