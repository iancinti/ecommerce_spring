# Java Spring E-commerce

This project is an E-commerce REST API based on Java Spring, Spring Boot, Hibernate ORM with MySQL, Spring HATEOAS, Spring Fox (Swagger API docs), JWT, and Redis.

## REST API Endpoints

All inputs and outputs use the JSON format.

**To access the interactive Swagger API documentation, navigate your browser to [YOUR-URL]/swagger-ui.html**

### Authentication
- `POST /login`: Login using the username `b` and password `b`.

### Products
- `GET /product`: Get a list of all products.
- `POST /product`: Add a new product. Required fields: `name`, `groupId`, `userId`.
- `GET /product/{id}`: View product details.
- `POST /product/{id}`: Update product details.
- `GET /product/{id}/images`: View product images.
- `GET /image/{id}`: View an image.
- `POST /product/{id}/uploadimage`: Upload an image for a product.

### Groups
- `GET /group`: Get a list of all groups.
- `POST /group`: Add a new group.
- `GET /group/{id}`: View group details.
- `POST /group/{id}`: Update group details.

### Orders
- `GET /order`: Get a list of all orders.
- `POST /order`: Add a new order.
- `GET /order/{id}`: View order details.
- `POST /order/{id}`: Update order details.

### Cart
- `POST /cart`: Create a new cart.
- `GET /cart/{id}`: Get items for the cart with ID `{id}`.
- `POST /cart/{id}`: Add a `CartItem` to the cart with ID `{id}`.
- `DELETE /cart/{id}/{product_id}`: Remove a product with ID `{product_id}` from the cart with ID `{id}`.
- `POST /cart/{id}/quantity`: Update the quantity of a cart item.
- `POST /cart/{id}/order`: Create an order from the cart.

