package com.app.agendai.services;

import com.app.agendai.dtos.ClientRequest;
import com.app.agendai.dtos.ClientResponse;
import com.app.agendai.entities.Client;
import com.app.agendai.exceptions.custons.NotFoundException;
import com.app.agendai.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientResponse save(ClientRequest clientRequest){
        Client client = new Client();
        client.setName(clientRequest.name());
        client.setUser(clientRequest.user());
        clientRepository.save(client);
        return new ClientResponse(client.getId(), client.getName());
    }

    public List<ClientResponse> findAll(){


        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map( client -> new ClientResponse(
                        client.getId(),
                        client.getName()
                )).collect(Collectors.toList());


    }

    public ClientResponse findById(UUID id){
        Client client = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("cliente " + id + " nao encontrado"));

        return new ClientResponse(client.getId(),client.getName());
    }
}
