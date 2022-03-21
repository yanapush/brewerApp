package com.yanapush.BrewerApp.user.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.user.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "authorities")
@JsonSerialize(using = CustomRoleSerializer.class)
@Data
public class Role extends BaseEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String authority;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
