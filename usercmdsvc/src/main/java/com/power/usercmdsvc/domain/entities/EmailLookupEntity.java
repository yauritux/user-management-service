package com.power.usercmdsvc.domain.entities;

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
