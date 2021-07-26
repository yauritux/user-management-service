package com.power.userquerysvc.domain.projections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * In terms of DDD, this will be our Value Object for Default User View.
 * As from the CQRS-ES perspective, this is our default projection to represent user data.
 * @author Yauri Attamimi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "default_user_view")
public class DefaultUserView {
    @Id
    @Column(nullable = false)
    private String id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
