package com.devsu.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false , unique = true)
    private Long id;

    @Column(name = "numero_cuenta")
    private Long accountNumber;
    @Column(name = "tipo_cuenta")
    private String accountType;
    @Column(name = "saldo_inicial")
    private double initialBalance;
    @Column(name = "estado")
    private boolean state = true;
    @Column(name = "cliente_id")
    private Long clientId;

    @JsonManagedReference
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<MotionEntity> movimientos;

}
