package ru.innopolis.attendance.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Database model contains information about roles
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 16)
    private String name;

    @Override
    public String toString() {
        return getName();
    }
}