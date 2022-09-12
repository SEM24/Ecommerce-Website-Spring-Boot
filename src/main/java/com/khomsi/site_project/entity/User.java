package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @ToString.Exclude
    private List<OrderBasket> orderBaskets;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;


}