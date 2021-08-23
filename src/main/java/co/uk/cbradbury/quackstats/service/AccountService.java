package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.enums.AccountRole;
import co.uk.cbradbury.quackstats.model.entity.Account;
import co.uk.cbradbury.quackstats.model.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        if(accountRepository.findByUsername("admin").isEmpty()) {
            save(new Account("admin", "password", AccountRole.ADMIN.name()));
        }
    }

    @Transactional
    void save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    public Optional<Account> findAccountByUsernameAndPassword(String userName, String password) {
        Optional<Account> account = accountRepository.findByUsername(userName);
        return account.map(a -> passwordEncoder.matches(password, a.getPassword()) ? a : null);
    }

    public void setPassword(Account account, String password) {
        account.setPassword(password);
        save(account);
    }
}
