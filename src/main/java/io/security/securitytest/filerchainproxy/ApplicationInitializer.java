package io.security.securitytest.filerchainproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationInitializer implements ApplicationRunner {

    @Autowired
    private SecurityFilterChain securityFilterChain1;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Filter> filters = securityFilterChain1.getFilters();
        Optional<Filter> filter = filters.stream()
                .filter(f -> f.getClass() == FilterSecurityInterceptor.class)
                .findFirst();
        FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor)filter.orElseGet(() -> new FilterSecurityInterceptor() {
        });
        AffirmativeBased accessDecisionManager = (AffirmativeBased)filterSecurityInterceptor.getAccessDecisionManager();
//        accessDecisionManager.getDecisionVoters().add(0,new IpAddressVoter());
    }
}
