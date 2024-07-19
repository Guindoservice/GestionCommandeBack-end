package com.Mail.Service;

import com.Mail.Model.Admin;
import com.Mail.Repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{


    private final AdminRepository adminRepository;

    @Override
    public Admin CreerAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin ModifierAdmin(Long Id, Admin admin) {
        return adminRepository.findById(Id)
                .map(a->{
                    admin.setNom(admin.getNom());
                    admin.setPrenom(admin.getPrenom());
                    admin.setEmail(admin.getEmail());
                    admin.setPassword(admin.getPassword());
                    return adminRepository.save(admin);
                }).orElseThrow(()->new RuntimeException("Administrateur non trouvable"));
    }

    @Override
    public List<Admin> ADMIN_LIST() {
        return adminRepository.findAll();
    }

    @Override
    public String SupprimerAdmin(Long Id) {
        return "L'admin a bien été supprimer";
    }
}
