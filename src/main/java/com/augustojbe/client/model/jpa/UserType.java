package com.augustojbe.client.model.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user_type")
public class UserType implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_type_id")
    private Long id;

    private String name;

    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
