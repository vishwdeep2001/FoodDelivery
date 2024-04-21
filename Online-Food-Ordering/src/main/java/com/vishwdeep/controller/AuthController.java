package com.vishwdeep.controller;

import com.vishwdeep.config.JwtProvider;
import com.vishwdeep.model.Cart;
import com.vishwdeep.model.USER_ROLE;
import com.vishwdeep.model.User;
import com.vishwdeep.repository.CartRepository;
import com.vishwdeep.repository.UserRepository;
import com.vishwdeep.request.LoginRequest;
import com.vishwdeep.response.AuthResponse;
import com.vishwdeep.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandles(@RequestBody User user) throws Exception {
        User isEmailExist =  userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null) {
          throw new Exception("Email already exists with another account");
        }
        User createdUser =  new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        System.err.println(user.getRole());
        System.err.println(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered successfully");
        authResponse.setRole(savedUser.getRole());
        System.out.println("Saved user role"+savedUser.getRole());

return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){

        String username = req.getEmail();
        String password = req.getPassword();
        Authentication authentication = authenticate(username,password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null :authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login successfully");

        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Password is invalid");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
