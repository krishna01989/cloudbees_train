package com.cloudbees.test.ticket.ticket.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Train {
    @Id
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private List<Section> sections;
}

