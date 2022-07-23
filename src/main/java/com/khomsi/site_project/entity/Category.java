package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @Id
    @Column(name = "id")
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
   // @NonNull
    private String title;
}
