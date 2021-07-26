package com.power.userquerysvc.projections;

import javax.persistence.Column;
import javax.persistence.Id;

public class FinanceUserView {
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
    private String accountNumber;
    private String accountName;
}
