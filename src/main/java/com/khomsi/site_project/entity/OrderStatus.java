package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class OrderStatus {
    //TODO Нужен тест для этого класса

    @Id
    @Column(name = "id")
    private int id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private OrderType orderStatus;

    //FIXME эти строки вроде даже не нужны
    //TODO я хз еще надо ли тут  @ToString.Exclude, типо нужна ли эта
    //строка тут
    //У одного пользователя может быть много заказов
//    {CascadeType.PERSIST, CascadeType.DETACH,
//            CascadeType.REFRESH,CascadeType.MERGE}
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStatusId")
//    private List<Orders> ordersListStatus;


//    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.DETACH,
//            CascadeType.REFRESH,CascadeType.MERGE}, mappedBy = "orderStatusId")
//    private List<Orders> ordersListStatus;
//
//
//    //Этот пользователь будет пользователем данного заказа
//    public void addOrderToStatus(Orders orders) {
//        if (ordersListStatus == null) ordersListStatus = new ArrayList<>();
//
//        ordersListStatus.add(orders);
//        orders.setOrderStatusId(this);
//    }
}
