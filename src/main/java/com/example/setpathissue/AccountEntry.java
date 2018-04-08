package com.example.setpathissue;

import java.math.BigDecimal;
import java.time.LocalDate;

class AccountEntry {

    private BigDecimal amount;

    private LocalDate date;

    public AccountEntry() {
    }

    public AccountEntry(BigDecimal amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
