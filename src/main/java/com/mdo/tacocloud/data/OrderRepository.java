package com.mdo.tacocloud.data;

import com.mdo.tacocloud.Order;

public interface OrderRepository {

    Order save(Order order);
}
