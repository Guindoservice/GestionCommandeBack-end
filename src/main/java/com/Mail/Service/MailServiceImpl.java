package com.Mail.Service;

import com.Mail.Model.Email;
import com.Mail.Repository.MailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
    public class MailServiceImpl implements MailService{

    private MailRepository mailRepository;

    @Override
    public Email Creermail(Email email) {
        return mailRepository.save(email);
    }


}
