package com.myProject.myProject.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.management.BadAttributeValueExpException;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.myProject.models.ERoles;
import com.myProject.myProject.models.Roles;
import com.myProject.myProject.models.User;
import com.myProject.myProject.repository.RoleRepository;
import com.myProject.myProject.repository.UserRepository;
import com.myProject.myProject.security.JwtUtil;
import com.myProject.myProject.security.payload.AuthRequest;
import com.myProject.myProject.security.payload.RegisterRequest;
import com.myProject.myProject.security.payload.Response.AuthResponse;
import com.myProject.myProject.security.payload.Response.MessageResponse;
import com.myProject.myProject.service.MyUserDetailsService;
import com.myProject.myProject.service.UserDetailServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required=false)
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;


    @Autowired
	PasswordEncoder encoder;

    @Autowired
	RoleRepository roleRepository;

    @Autowired (required=false)
    private JwtUtil jwtUtil;

   @PostMapping(value = "/signin")
   public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception{
    try{
         authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
    );

        }catch (BadCredentialsException e){
            throw new Exception("Incorrect credentials",e);
        }

        final UserDetails userDetails =userDetailServiceImpl
            .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt =jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
    if(userRepository.existsByEmail(registerRequest.getUsername())){
        return ResponseEntity
        .badRequest()
        .body(new MessageResponse("error already exist"));
    }

    User user = new User (registerRequest.getUsername(),
                        encoder.encode(registerRequest.getPassword()));

                        Set<String> strRoles = registerRequest.getRole();
                        Set<Roles> roles = new HashSet<>();
                        if (strRoles == null) {
                            Roles userRole = roleRepository.findByName(ERoles.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                        } else {
                            strRoles.forEach(role -> {
                                switch (role) {
                                case "admin":
                                    Roles adminRole = roleRepository.findByName(ERoles.ROLE_ADMIN)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(adminRole);
                
                                    break;
                                default:
                                    Roles userRole = roleRepository.findByName(ERoles.ROLE_USER)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(userRole);
                                }
                            });
                        }
                
                        user.setRoles(roles);
                        userRepository.save(user);
                
                        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

}

   
}

    

