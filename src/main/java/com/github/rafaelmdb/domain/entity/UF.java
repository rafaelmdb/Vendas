package com.github.rafaelmdb.domain.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name="UF")
public class UF {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;
    private String sigla;

    public UF(){}
    public UF(String sigla){
        this();
        this.sigla=sigla;
    }

}
