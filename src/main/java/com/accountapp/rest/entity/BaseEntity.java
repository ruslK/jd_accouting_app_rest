package com.accountapp.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private Long createdBy;

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @JsonIgnore
    private Long updatedBy;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDateTime updatedTime;

    @JsonIgnore
    private Boolean enabled;
}
