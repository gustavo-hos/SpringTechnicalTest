package me.gustavo.springordermanager.event.listener;

import me.gustavo.springordermanager.event.event.OrderStatusChangedEvent;
import me.gustavo.springordermanager.model.Mail;
import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.User;
import me.gustavo.springordermanager.service.intf.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderStatusChangedEventListener implements ApplicationListener<OrderStatusChangedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final MailService mailService;

    public OrderStatusChangedEventListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OrderStatusChangedEvent event) {
        Order order = event.getChangedOrder();

        if (order.getStatus().equals(Order.Status.COMPLETED)) {
            User userCreatedOrder = order.getUser();

            Map<String, Object> data = new HashMap<>();

            data.put("name", userCreatedOrder.getName());
            data.put("order_id", order.getUuid());

            mailService.sendMail("order-completed", data, new Mail(userCreatedOrder.getEmail(), "Order Completed!"));

            LOGGER.info("Order #{} is now completed!", order.getUuid());
        }
    }
}
