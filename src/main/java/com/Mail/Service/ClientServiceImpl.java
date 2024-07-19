package com.Mail.Service;

import com.Mail.Model.Client;
import com.Mail.Repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;
    @Override
    public Client Creerclient(Client client) {
        return clientRepository.save(client);
    }




}
