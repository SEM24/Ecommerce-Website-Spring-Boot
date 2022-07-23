//package com.khomsi.site_project.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "order_history")
//@RequiredArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
////source table
//public class OrderHistory {
//    //TODO Нужен тест для этого класса
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @NonNull
//    @Column(name = "price")
//    private int price;
//
//    @NonNull
//    @Column(name = "shippingType")
//    private int shippingType;
//
//    // @ManyToOne should annotate a field not a collection.
//    // For collection fields the right annotation is @OneToMany.
//    @ToString.Exclude
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userHistory_id")
//    private UserHistory userHistoryId;
//
//}
