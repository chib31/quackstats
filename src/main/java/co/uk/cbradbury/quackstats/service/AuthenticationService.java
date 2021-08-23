package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.model.entity.Account;
import co.uk.cbradbury.quackstats.model.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        var account = accountRepository.findByUsername(userName).orElseThrow(
                () -> new UsernameNotFoundException("Username not found: " + userName));

        return createUser(account);
    }

    private User createUser(Account u) {
        return new User(u.getUsername(), u.getPassword(), createAuthorities(u));
    }

    private Collection<GrantedAuthority> createAuthorities(Account u) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+u.getRole()));
        return  authorities;
    }
}
