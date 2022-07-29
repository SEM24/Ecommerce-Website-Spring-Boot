package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetails {
    @Id
    @Column(name = "user_details_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDetailsId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_details_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

}
