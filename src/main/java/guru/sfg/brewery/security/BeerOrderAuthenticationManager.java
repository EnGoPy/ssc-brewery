package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManager {

    public boolean customerIdMatches(Authentication authentication, UUID customerId){
        User autheticatedUser = (User) authentication.getPrincipal();
        log.debug("Auth User Customer Id: "+ autheticatedUser.getCustomer().getId() +" Customer Id: "+ customerId);

        return autheticatedUser.getCustomer().getId().equals(customerId);
    }

}
