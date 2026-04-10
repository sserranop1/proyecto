package com.proyecto.backend.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.backend.account.dto.AccountRegisterRequest;
import com.proyecto.backend.account.dto.AccountRegisterResponse;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountRegisterResponse register(AccountRegisterRequest request) {
        AccountProvider provider = AccountProvider.from(request.getProvider());

        Account account = new Account();
        account.setName(request.getName().trim());
        account.setProvider(provider.name());

        Account saved = accountRepository.save(account);
        return new AccountRegisterResponse(saved.getId(), saved.getName(), saved.getProvider(), "REGISTERED");
    }

    @Transactional(readOnly = true)
    public List<Account> list() {
        return accountRepository.findAll();
    }
}
