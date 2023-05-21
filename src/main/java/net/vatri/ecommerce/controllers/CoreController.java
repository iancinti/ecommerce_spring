package net.vatri.ecommerce.controllers;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CoreController {
    protected Link createHateoasLink(long id){
        Link link = linkTo(methodOn(getClass()).findById(id)).withSelfRel();
        return link;
    }
}

