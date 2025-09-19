package com.app.agendai.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;


    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<ServiceEntity> serviceEntities;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
