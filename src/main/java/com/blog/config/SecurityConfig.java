package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//using this we can use preAthorize annotation in contoller
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Your security configuration code goes here

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http 
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll() //is a method where we define wich url who can access
//                permitall() everyone can acces does not required username and password
//                .antMatchers(HttpMethod.POST,"/api/**").hasRole("ROLE_ADMIN") // only admin can access the Post requests

                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


//    in memory authentication
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService(){
////        objects consisting of username and password
//        UserDetails user = User.builder().username("vaseem").password(passwordEncoder.encode("test")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getEncodedPassword());
    }

    //telling springioc to geneate the object and inject it into @autowired in securityconfig class
    @Bean
    public PasswordEncoder getEncodedPassword(){
        return new BCryptPasswordEncoder();
    }
}

