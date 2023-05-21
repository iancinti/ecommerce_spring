package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.hateoas.ProductResource;
import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.models.ProductImage;
import net.vatri.ecommerce.services.EcommerceService;
import net.vatri.ecommerce.storage.StorageService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController extends CoreController {

    @Autowired
    private EcommerceService ecommerceService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    Validator productValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productValidator);
    }

    @GetMapping
    public List<ProductResource> index() {
        List<Product> products = ecommerceService.getProducts();

        return products.stream()
                .map(p -> {
                    ProductResource pr = new ProductResource(p);
                    pr.add(createHateoasLink(p.getId()));
                    return pr;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product) {
        return ecommerceService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ProductResource view(@PathVariable("id") long id) {
        Product product = ecommerceService.getProduct(id);
        if (product != null) {
            ProductResource pr = new ProductResource(product);
            pr.add(createHateoasLink(id));
            return pr;
        }
        return null;
    }

    @PostMapping("/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product) {
        Product updatedProduct = ecommerceService.getProduct(id);
        if (updatedProduct == null) {
            return null;
        }
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        return ecommerceService.saveProduct(updatedProduct);
    }

    @GetMapping("/{id}/images")
    public List<ProductImage> viewImages(@PathVariable("id") long productId) {
        Session session = sessionFactory.openSession();
        List<ProductImage> images = session.createQuery("FROM ProductImage WHERE product_id = :product_id")
                .setParameter("product_id", productId)
                .list();
        session.close();
        return images;
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> serveFile(@PathVariable("id") long id) {
        Session session = sessionFactory.openSession();
        ProductImage image = session.get(ProductImage.class, id);
        session.close();

        if (image != null) {
            String path = "product-images/" + image.getProductId() + "/";
            Resource file = storageService.loadAsResource(path + image.getPath());
            String mimeType = "image/png";
            try {
                mimeType = file.getURL().openConnection().getContentType();
            } catch (IOException e) {
                System.out.println("Can't get file mimeType. " + e.getMessage());
            }
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, mimeType)
                    .body(file);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/uploadimage")
    public String handleFileUpload(@PathVariable("

