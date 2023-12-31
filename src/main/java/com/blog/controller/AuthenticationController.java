package com.blog.controller;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payloaddto.JWTAuthResponse;
import com.blog.payloaddto.LoginDto;
import com.blog.payloaddto.SignUpDto;
import com.blog.repositry.RoleRepository;
import com.blog.repositry.UserRepository;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; //this take the loginDto data and give to loadUserByUsername method that is prasent in CustumUserDetailsServive


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email id exists:"+signUpDto.getEmail(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("username exists:"+signUpDto.getUsername(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user=new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));//coverting role object to Set


        User savedUser = userRepository.save(user);

        return new ResponseEntity<>("User Registered succesfully", HttpStatus.OK);

    }


//    searching the records in database based on email or username
//    this method is automaticall called by springboot
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){

//UsernamePasswordAuthenticationToken this object is expected value or data
        UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());

//        .authenticate(UsernamePasswordAuthenticationToken);this will automatically call the loadUserByUsername
//        and here the actual data that prasent in database and compare
        Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken);

//the context is set the right details eligible to use application
//      if invalid we cannot use the application acess forbidden
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

}
