package net.vatri.ecommerce.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.vatri.ecommerce.models.GroupVariant;
import net.vatri.ecommerce.models.ProductGroup;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GroupResource extends ResourceSupport {
    @JsonProperty
    private long id;
    private String groupName;
    private String price;
    private List<GroupVariant> variants;

    public GroupResource(ProductGroup group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.price = group.getPrice();
        this.variants = group.getGroupVariants();
    }

    public long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPrice() {
        return price;
    }

    public List<GroupVariant> getVariants() {
        return variants;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setVariants(List<GroupVariant> variants) {
        this.variants = variants;
    }
}

