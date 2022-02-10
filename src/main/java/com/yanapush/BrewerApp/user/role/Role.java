package com.yanapush.BrewerApp.user.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
@JsonSerialize(using = CustomRoleSerializer.class)
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String authority;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
