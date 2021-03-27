package com.accountapp.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@Where(clause = "enabled=true")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class User extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true, updatable = false)
    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$", message = "wrong pattern")
    private String username;

    @Column(nullable = false, length = 50)
    @Email(message = "wrong format")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "wrong pattern")
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Pattern(regexp = "^[a-zA-Z]+([' ]?[a-zA-Z]+)*$", message = "wrong pattern")
    private String firstName;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Pattern(regexp = "^[a-zA-Z]+([' ]?[a-zA-Z]+)*$", message = "wrong pattern")
    private String lastName;

    @Column(nullable = false, length = 60)
    @NotNull
    @NotBlank
    @Size(min = 8, max = 60)
    // TODO find out why with regexp password is not saving to the user object.
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%])$", message = "wrong pattern")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, length = 20)
    @NotNull
    @NotBlank
    @Size(min = 10, max = 20)
    @Pattern(regexp = "(^((\\+\\d{1,2}|1)[\\s.-]?)?\\(?[2-9](?!11)\\d{2}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$|^$)",
            message = "wrong pattern")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

}
