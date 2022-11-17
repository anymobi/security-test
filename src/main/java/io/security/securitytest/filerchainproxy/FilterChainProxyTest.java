package io.security.securitytest.filerchainproxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
public class FilterChainProxyTest {

    @Autowired
    private Filter springSecurityFilterChain;

    @GetMapping("/index")
    public String index(){

        List<SecurityFilterChain> filterChains = ((FilterChainProxy)springSecurityFilterChain).getFilterChains();

        System.out.println("filterChains = " + filterChains);

        List<Filter> filters1 = ((FilterChainProxy)springSecurityFilterChain).getFilters("/admin");
        List<Filter> filters2 = ((FilterChainProxy)springSecurityFilterChain).getFilters("/");

        Optional<Filter> first1 = filters1.stream()
                .filter(filter -> filter.getClass() == UsernamePasswordAuthenticationFilter.class)
                .findFirst();

        Optional<Filter> first2 = filters2.stream()
                .filter(filter -> filter.getClass() == UsernamePasswordAuthenticationFilter.class)
                .findFirst();

        System.out.println("first1 = " + first1);
        System.out.println("first2 = " + first2);


        return "index";
    }
}
