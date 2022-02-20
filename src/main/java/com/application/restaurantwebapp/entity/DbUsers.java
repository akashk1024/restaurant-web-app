package com.application.restaurantwebapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USERS")
public class DbUsers {

    @Id
    private String username;

    private String password;
    private Boolean enabled;
}
