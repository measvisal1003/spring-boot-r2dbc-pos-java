package backend.Dto;

import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;

import java.util.List;

public record OrderDetails(
        OrderItem orderItems,
        List<OrderDetail> orderDetails
) {
}
