package net.vatri.ecommerce.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.vatri.ecommerce.models.Order;
import net.vatri.ecommerce.models.OrderItem;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class OrderResource extends ResourceSupport {
    @JsonProperty
    private long id;
    private String name;
    private String address;
    private String city;
    private String zip;
    private String status;
    private String comment;
    private String totalPrice;
    private String type;
    private String created;
    private List<OrderItem> items;

    public OrderResource(Order o) {
        this.id = o.getId();
        this.name = o.getName();
        this.address = o.getAddress();
        this.city = o.getCity();
        this.zip = o.getZip();
        this.status = o.getStatus();
        this.comment = o.getComment();
        this.totalPrice = o.getTotalPrice();
        this.type = o.getType();
        this.created = o.getCreated();
        this.items = o.getItems();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getType() {
        return type;
    }

    public String getCreated() {
        return created;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}

