package com.app.agendai.services;

import com.app.agendai.dtos.EnterpriseRequest;
import com.app.agendai.dtos.EnterpriseResponse;
import com.app.agendai.entities.Enterprise;
import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.exceptions.custons.NotFoundException;
import com.app.agendai.repositories.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public EnterpriseResponse save(EnterpriseRequest enterpriseRequest){
        Enterprise enterprise = new Enterprise();
        enterprise.setName(enterpriseRequest.name());
        enterprise.setUser(enterpriseRequest.user());


        List<ServiceEntity> serviceEntities = new ArrayList<>();
        for (ServiceEntity serviceEntity : enterpriseRequest.services()){
            ServiceEntity s = new ServiceEntity();
            s.setEnterprise(enterprise);
            s.setName(serviceEntity.getName());
            s.setPrice(serviceEntity.getPrice());
            s.setOpen(serviceEntity.getOpen());
            s.setClosed(serviceEntity.getClosed());
            serviceEntities.add(s);
        }

        enterprise.setServiceEntities(serviceEntities);

         enterpriseRepository.save(enterprise);

         return new EnterpriseResponse(enterprise.getId(),enterprise.getName(),serviceEntities.stream().map(s-> s.getId()).collect(Collectors.toList()));
    }

    public List<EnterpriseResponse> findAll(){



        List<Enterprise> enterprises = enterpriseRepository.findAll();
        return enterprises.stream()
                .map(e-> new EnterpriseResponse(
                        e.getId(),
                        e.getName(),
                        e.getServiceEntities().stream()
                                .map(sId-> sId.getId())
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());

    }

    public EnterpriseResponse findById(UUID id){
        Enterprise enterprise = enterpriseRepository.findById(id).orElseThrow(()->new NotFoundException("empresa nao encontada"));

        EnterpriseResponse enterpriseResponse = new EnterpriseResponse(enterprise.getId(),enterprise.getName(), enterprise.getServiceEntities().stream()
                .map(s-> s.getId()).collect(Collectors.toList()));
        return enterpriseResponse;
    }
}
