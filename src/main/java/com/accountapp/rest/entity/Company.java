package com.accountapp.rest.entity;

import com.accountapp.rest.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "company")
@Where(clause = "enabled=true")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class Company extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 100)
    private String address1;

    @Column(length = 100)
    private String address2;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private State state;

    @Column(nullable = false, length = 5)
    private String zip;

    @Column(nullable = false, length = 50)
    private String representative;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDateTime establishmentDate;

}
