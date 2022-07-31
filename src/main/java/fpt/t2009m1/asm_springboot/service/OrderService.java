package fpt.t2009m1.asm_springboot.service;

import fpt.t2009m1.asm_springboot.entity.Order;
import fpt.t2009m1.asm_springboot.entity.OrderDetail;
import fpt.t2009m1.asm_springboot.entity.Product;
import fpt.t2009m1.asm_springboot.repository.OrderRepository;
import fpt.t2009m1.asm_springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService{
    final OrderRepository orderRepository;
    final ProductRepository productRepository;

    public Order findShoppingCartByUserId(int userId){
       return orderRepository.getShoppingCart(userId);
    }

    public Order addShoppingCart(int userId, String productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            return null;
        }
        Order order = orderRepository.getShoppingCart(userId);
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        boolean exist = false;
        for(OrderDetail entry : orderDetails)

        if(!exist){
            OrderDetail orderDetail = new OrderDetail();
            orderDetails.add(orderDetail);
        }
         return order;
    }
}
