package com.Mail.Repository;

import com.Mail.Model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Email, Long> {
}
