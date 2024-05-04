package com.vishwdeep.service;

import com.vishwdeep.model.Cart;
import com.vishwdeep.model.CartItem;
import com.vishwdeep.model.Food;
import com.vishwdeep.model.User;
import com.vishwdeep.repository.CartItemRepository;
import com.vishwdeep.repository.CartRepository;
import com.vishwdeep.repository.FoodRepository;
import com.vishwdeep.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId((long) user.getId());

// Check if the item already exists in the cart
        boolean itemExists = false;
        for (CartItem cartItem : cart.getItem()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = (int) (cartItem.getQuantity() + req.getQuantity());
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

// If the item does not exist in the cart, create a new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(Math.toIntExact(req.getQuantity()));
        cartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        cartItem.setIngredients(req.getIngredients());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
       Optional <CartItem> cartItem = cartItemRepository.findById(cartItemId);
       if (cartItem.isEmpty()){
         throw new Exception("Cart Item not found");
       }
       CartItem item = cartItem.get();
       item.setQuantity(quantity);
       item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId((long) user.getId());
        Optional <CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()){
            throw new Exception("Cart Item not found");
        }
        CartItem item = cartItem.get();
        cart.getItem().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total =0L;
        for (CartItem cartItem: cart.getItem()) {
            total +=(long) (cartItem.getFood().getPrice()*(long)cartItem.getQuantity());

        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
     Optional<Cart> optionalCart = cartRepository.findById(id);
     if (optionalCart.isEmpty()){
         throw new Exception("Cart not found");
     }
     return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
         Cart cart=cartRepository.findByCustomerId(userId);
         cart.setTotal(calculateCartTotals(cart));
         return cart;

    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart=findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
