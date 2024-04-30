package com.vishwdeep.controller;

import com.vishwdeep.model.Cart;
import com.vishwdeep.model.CartItem;
import com.vishwdeep.model.User;
import com.vishwdeep.request.AddCartItemRequest;
import com.vishwdeep.request.UpdateCartItemRequest;
import com.vishwdeep.service.CartService;
import com.vishwdeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem>addItemToCart(@RequestBody AddCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
        System.err.println(req);
        CartItem cartItem = cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem>updateCartItemQuantity(@RequestBody UpdateCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart>removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
        Cart cartItem = cartService.removeItemFromCart(id,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart>clearCart(@RequestBody UpdateCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart((long) user.getId());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart>findUserCart( @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId((long) user.getId());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
}
