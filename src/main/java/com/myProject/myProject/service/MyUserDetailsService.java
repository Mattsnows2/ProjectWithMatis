package com.myProject.myProject.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myProject.myProject.models.User;

import java.util.Objects;



public class MyUserDetailsService implements UserDetails{

    private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	
	private String password;



	private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetailsService(Long id,  String username, String password,
    Collection<? extends GrantedAuthority> authorities) {

        this.id = id;
	
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		
    }

    public static MyUserDetailsService build(User user) {
		List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

                return new MyUserDetailsService(
                    user.getId(),
               
                    user.getEmail(),
                    user.getPassword(),
                    authorities
                  
                );

    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}



	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}





	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MyUserDetailsService user = (MyUserDetailsService) o;
		return Objects.equals(id, user.id);
	}

    @Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


  /*   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return new User("foo","foo",new ArrayList<>());
    }*/
    
}
