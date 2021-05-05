package com.power.usercore.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private boolean gender;
    private String username;
    private String password;
    private String email;
    private String role;
}
