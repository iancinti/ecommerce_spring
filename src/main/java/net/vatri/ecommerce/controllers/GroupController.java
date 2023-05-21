package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.hateoas.GroupResource;
import net.vatri.ecommerce.models.ProductGroup;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
public class GroupController extends CoreController {

    @Autowired
    EcommerceService ecommerceService;

    @Autowired
    Validator groupValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(groupValidator);
    }

    @GetMapping
    public List<GroupResource> index() {
        List<ProductGroup> list = ecommerceService.getGroups();

        return list.stream()
                .map(g -> {
                    GroupResource gr = new GroupResource(g);
                    gr.add(createHateoasLink(g.getId()));
                    return gr;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<ProductGroup> view(@PathVariable("id") long id) {
        ProductGroup group = ecommerceService.getGroup(id);

        if (group != null) {
            GroupResource gr = new GroupResource(group);
            gr.add(createHateoasLink(id));
            return EntityModel.of(group, gr.getLinks());
        }

        return EntityModel.of(group);
    }

    @PostMapping(value = "/{id}")
    public ProductGroup edit(@PathVariable(value = "id", required = false) long id, @RequestBody @Valid ProductGroup group) {
        ProductGroup updatedGroup = ecommerceService.getGroup(id);

        if (updatedGroup == null) {
            return null;
        }

        updatedGroup.setGroupName(group.getGroupName());
        updatedGroup.setPrice(group.getPrice());
        updatedGroup.setGroupVariants(group.getGroupVariants());

        if (updatedGroup.getGroupVariants() != null) {
            updatedGroup.getGroupVariants().forEach(gv -> gv.setGroup(updatedGroup));
        }

        return ecommerceService.saveGroup(updatedGroup);
    }

    @PostMapping
    public ProductGroup create(@RequestBody @Valid ProductGroup group) {
        if (group.getGroupVariants() != null) {
            group.getGroupVariants().forEach(gv -> gv.setGroup(group));
        }

        return ecommerceService.saveGroup(group);
    }

    // Todo: add delete method
}

