package com.accountapp.rest.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
@Where(clause = "enabled=true")
public class Role extends BaseEntity {

    @Column(nullable = false, length = 20, updatable = false, unique = true)
    private String name;
}
