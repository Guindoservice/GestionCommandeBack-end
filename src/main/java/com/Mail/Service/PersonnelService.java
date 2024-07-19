package com.Mail.Service;

import com.Mail.Model.Personnel;

import java.util.List;

public interface PersonnelService {
    Personnel Creerpersonnel (Personnel personnel);

    Personnel Modifierpersonnel(Long Id, Personnel personnel);

    List<Personnel> PERSONNEL_LIST();

    String Supprimerpersonnel (Long Id);
}
