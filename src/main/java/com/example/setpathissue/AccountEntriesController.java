package com.example.setpathissue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
class AccountEntriesController {

    @GetMapping("accounts/{accountId}/entries")
    Flux<AccountEntry> getAccountEntries(@PathVariable("accountId") String accountId) {
        return Flux.just(
                new AccountEntry(new BigDecimal("10.57"), LocalDate.parse("2018-04-08")),
                new AccountEntry(new BigDecimal("2.31"), LocalDate.parse("2018-04-08")),
                new AccountEntry(new BigDecimal("0.05"), LocalDate.parse("2018-04-09"))
        );
    }
}
