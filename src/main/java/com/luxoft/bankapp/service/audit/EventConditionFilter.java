package com.luxoft.bankapp.service.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventConditionFilter {
    @Value("${audit.amount.deposit}")
    public double depositLimit;

    @Value("${audit.amount.withdrawal}")
    public double withdrawalLimit;
}
