package com.Mail.Service;

import com.Mail.Model.Admin;

import java.util.List;

public interface AdminService {

    Admin CreerAdmin(Admin admin);

    Admin ModifierAdmin(Long Id, Admin admin);

    List<Admin> ADMIN_LIST();

    String SupprimerAdmin(Long Id);
}
