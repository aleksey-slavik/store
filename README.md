# store
Shopping cart 
<h2>Store Rest Service</h2>
User rest service:
<table class="table table-marked">
    <thead>
        <tr>
            <th width="100">Method</th>
            <th width="225">URL</th>
            <th>Description</th>
            <th>Response Statuses</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>users/</td>
            <td>Return list of all users</td>
            <td>200 — user data list return in response body</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>users/{id}/</td>
            <td>Return user with given id</td>
            <td>200 — user data return in response body,<br>
                404 — user with given id not found</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>users/</td>
            <td>Create new user</td>
            <td>200 — user data return in response body,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>users/{id}/</td>
            <td>Update user data with given id</td>
            <td>200 — user data return in response body,<br>
                404 — user with given id not found,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>users/{id}/</td>
            <td>Delete user with given id</td>
            <td>200 — deleted user data return in response body,<br>
                404 — user with given id not found</td>
        </tr>
    </tbody>
</table>
Product rest service:
<table class="table table-marked">
    <thead>
        <tr>
            <th width="100">Method</th>
            <th width="225">URL</th>
            <th>Description</th>
            <th>Response Statuses</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>products/</td>
            <td>Return list of all products</td>
            <td>200 — product data list return in response body</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>products/{id}/</td>
            <td>Return product with given id</td>
            <td>200 — product data return in response body,<br>
                404 — product with given id not found</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>products/</td>
            <td>Create new product</td>
            <td>200 — product data return in response body,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>products/{id}/</td>
            <td>Update product data with given id</td>
            <td>200 — product data return in response body,<br>
                404 — product with given id not found,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>products/{id}/</td>
            <td>Delete user with given id</td>
            <td>200 — deleted product data return in response body,<br>
                404 — product with given id not found</td>
        </tr>
    </tbody>
</table>
Order rest service:
<table class="table table-marked">
    <thead>
        <tr>
            <th width="100">Method</th>
            <th width="225">URL</th>
            <th>Description</th>
            <th>Response Statuses</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>orders/</td>
            <td>Return list of all orders</td>
            <td>200 — order data list return in response body</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>orders/{id}/</td>
            <td>Return order with given id</td>
            <td>200 — order data return in response body,<br>
                404 — order with given id not found</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>orders/{id}/items/</td>
            <td>Return list of all order items of order with given id</td>
            <td>200 — order item data list return in response body,<br>
                404 — order with given id not found</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>orders/{id}/items/{itemId}/</td>
            <td>Return order item with given itemId of order with given id</td>
            <td>200 — order item data return in response body,<br>
                404 — order with given id or order item with with given itemId not found</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>orders/</td>
            <td>Create new order</td>
            <td>200 — order data return in response body,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>orders/{id}/items/</td>
            <td>Create new order item for order with given id</td>
            <td>200 — order data return in response body,<br>
                404 — order with given id not found,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>orders/{id}/</td>
            <td>Update order data with given id</td>
            <td>200 — order data return in response body,<br>
                404 — order with given id not found,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>orders/{id}/items/{itemId}/</td>
            <td>Update order item data with given itemId of order with given id</td>
            <td>200 — order data return in response body,<br>
                404 — order with given id or order item with with given itemId not found,<br>
                406 — error in input data, explanations in response body</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>orders/{id}/</td>
            <td>Delete order with given id</td>
            <td>200 — deleted order data return in response body,<br>
                404 — product with given id not found</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>orders/{id}/tems/{itemId}/</td>
            <td>Delete order item with given itemId of order with given id</td>
            <td>200 — deleted order data return in response body,<br>
                404 — order with given id or order item with with given itemId not found</td>
        </tr>
    </tbody>
</table>
