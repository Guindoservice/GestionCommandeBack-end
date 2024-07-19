package com.Mail.Service;

import com.Mail.Model.Personnel;
import com.Mail.Repository.PersonnelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonnelServiceImpl implements PersonnelService{
    private PersonnelRepository personnelRepository;

    @Override
    public Personnel Creerpersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @Override
    public Personnel Modifierpersonnel(Long Id, Personnel personnel) {
        return personnelRepository.findById(Id)
                .map(a->{
                    personnel.setNom(personnel.getNom());
                    personnel.setPrenom(personnel.getPrenom());
                    personnel.setEmail(personnel.getEmail());
                    personnel.setPassword(personnel.getPassword());
                    return personnelRepository.save(personnel);
                }).orElseThrow(()->new RuntimeException("Administrateur non trouvable"));


    }

    @Override
    public List<Personnel> PERSONNEL_LIST() {
        return personnelRepository.findAll();
    }

    @Override
    public String Supprimerpersonnel(Long Id) {
        return "Le personnel a bien été supprimer";
    }
}
