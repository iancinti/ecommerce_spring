package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.hateoas.OrderResource;
import net.vatri.ecommerce.models.Order;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController extends CoreController {

    @Autowired
    private EcommerceService ecommerceService;

    @Autowired
    Validator orderValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(orderValidator);
    }

    @GetMapping
    public List<OrderResource> index() {
        List<Order> orders = ecommerceService.getOrders();

        return orders.stream()
                .map(o -> {
                    OrderResource orderResource = new OrderResource(o);
                    orderResource.add(createHateoasLink(o.getId()));
                    return orderResource;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public Order create(@RequestBody @Valid Order order){
        if(order.getItems() != null){
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return ecommerceService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderResource view(@PathVariable("id") long id){
        Order order = ecommerceService.getOrder(id);
        if(order != null){
            OrderResource orderResource = new OrderResource(order);
            orderResource.add(createHateoasLink(id));
            return orderResource;
        }
        return null;
    }

    @PostMapping("/{id}")
    public Order edit(@PathVariable("id") long id, @RequestBody @Valid Order order){
        Order updatedOrder = ecommerceService.getOrder(id);
        if(updatedOrder == null){
            return null;
        }
        // Actualizar los campos necesarios de updatedOrder según la información en order
        return ecommerceService.saveOrder(updatedOrder);
    }
}

