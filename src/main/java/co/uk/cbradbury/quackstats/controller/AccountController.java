package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.BadRequestException;
import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/setPassword")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> setPassword(@RequestBody Map<String, String> payload) {
        var userName = Optional.ofNullable(payload.get("userName")).orElseThrow(
                () -> new BadRequestException("No username provided"));

        var oldPassword = Optional.ofNullable(payload.get("oldPassword")).orElseThrow(
                () -> new BadRequestException("No current password provided"));

        var newPassword = Optional.ofNullable(payload.get("newPassword")).orElseThrow(
                () -> new BadRequestException("No new password provided"));

        var account = accountService.findAccountByUsernameAndPassword(userName, oldPassword).orElseThrow(
                () -> new NotFoundException("No account found with the provided username and password"));

        accountService.setPassword(account, newPassword);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
