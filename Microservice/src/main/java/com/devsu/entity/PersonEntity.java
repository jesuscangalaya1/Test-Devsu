package com.devsu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personas")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false , unique = true)
    private Long id;

    @Column(name = "nombre", length = 100)
    private String name;

    @Column(name = "genero", length = 200)
    private String gender;

    @Column(name = "edad")
    private Integer age;

    @Column(name = "DNI", length = 20, nullable = false)
    private String identification;

    @Column(name = "direccion", length = 200)
    private String address;

    @Column(name = "telefono")
    private Integer phone;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private ClientEntity client;

}
