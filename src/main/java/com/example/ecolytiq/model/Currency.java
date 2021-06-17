package com.example.ecolytiq.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name="tblCurrency")
@IdClass(Currency.class)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Serializable {

    @Id
    private String name;

    @Id
    @JsonFormat(pattern = "dd MMMM yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @Column(precision = 19, scale = 2)
    private BigDecimal rate;

}
