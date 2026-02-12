package com.tcs.e_commerce_back_end.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OneTimeCodeModel {
    @Id
    private String id;
    private String value;

    public OneTimeCodeModel(String id) {
        this.id = id;
    }
}
