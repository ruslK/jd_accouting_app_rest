package com.accountapp.rest.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "is required, getting")
    @NotBlank(message = "is required, getting")
    private String name;
}
