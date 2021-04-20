package com.power.usercmdsvc.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class EmailLookupEntity {

    @Id
    private String email;
    private String username;
}
