package com.app.agendai.services;

import com.app.agendai.dtos.ServiceRequest;
import com.app.agendai.dtos.ServiceResponse;
import com.app.agendai.entities.Enterprise;
import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.exceptions.custons.NotFoundException;
import com.app.agendai.repositories.EnterpriseRepository;
import com.app.agendai.repositories.ServiceRepository;
import com.app.agendai.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;
@Autowired
private UserRepository userRepository;

    public ServiceResponse save(ServiceRequest serviceRequest, UserDetails userDetails){
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setPrice(serviceRequest.price());
        serviceEntity.setName(serviceRequest.name());
        serviceEntity.setClosed(serviceRequest.closed());
        serviceEntity.setOpen(serviceRequest.open());



        UserDetails user = userRepository.findByLogin(userDetails.getUsername());
        Enterprise enterprise = enterpriseRepository.findByUser(user).orElseThrow(()->new NotFoundException("nao encontrado empresa"));

        serviceEntity.setEnterprise(enterprise);
        serviceEntity.setEnterprise(enterprise);

         serviceRepository.save(serviceEntity);
         return new ServiceResponse(serviceEntity.getId(),serviceEntity.getName(),serviceEntity.getPrice(),serviceEntity.getOpen(),serviceEntity.getClosed());
    }

    public List<ServiceResponse> findByEnterpriseId(UUID id){
        boolean exist = enterpriseRepository.existsById(id);
        if (!exist){
            throw new NotFoundException("enterprise de id "+ id + "nao encontrada");
        }
         List<ServiceEntity> serviceEntities = serviceRepository.findByEnterpriseId(id);
        return serviceToResponse(serviceEntities);
    }

    public ServiceResponse findById(UUID id){

        ServiceEntity serviceEntitie = serviceRepository.findById(id).orElseThrow(()-> new NotFoundException("servico nao encontrado"));
        return new ServiceResponse(serviceEntitie.getId(),serviceEntitie.getName(),serviceEntitie.getPrice(),serviceEntitie.getOpen(),serviceEntitie.getClosed());
    }

    private List<ServiceResponse> serviceToResponse(List<ServiceEntity> serviceEntities){
        return serviceEntities.stream()
                .map(s-> new ServiceResponse(
                        s.getId(),
                        s.getName(),
                        s.getPrice(),
                        s.getOpen(),
                        s.getClosed()
                )).collect(Collectors.toList());
    }


    public String atualizar(UUID id, ServiceRequest serviceRequest, UserDetails userDetails){
        ServiceEntity serviceEntity = serviceRepository.findById(id).orElseThrow(()->new NotFoundException("servico nao encontrado"));


        System.out.println(userDetails.getUsername());
        Enterprise enterprise = enterpriseRepository.findByName(userDetails.getUsername()).orElseThrow(()->new NotFoundException("empressa nao encontrada"));

        if (serviceEntity.getEnterprise().getId().equals(enterprise.getId())){
            return "empresa autorizada";
        }else {return "empresa nao autorizada";}

    }
}
