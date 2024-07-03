package com.api.biblioteca.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private LibraryCard libraryCard;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Loan> loans;

    public Member(){}

    public Member(String name) {
        this.name = name;
    }
}
