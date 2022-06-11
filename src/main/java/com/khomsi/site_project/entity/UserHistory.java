package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_history")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class UserHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NonNull
    private String name;
    @Column(name = "phone")
    @NonNull
    private String phone;
    @Column(name = "email")
    @NonNull
    private String email;
    @Column(name = "city")
    @NonNull
    private String city;
    @Column(name = "qty")
    @NonNull
    private int qty;
}
