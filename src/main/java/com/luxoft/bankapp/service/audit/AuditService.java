package com.luxoft.bankapp.service.audit;

import com.luxoft.bankapp.service.audit.events.AccountEvent;
import com.luxoft.bankapp.service.audit.events.BalanceEvent;
import com.luxoft.bankapp.service.audit.events.DepositEvent;
import com.luxoft.bankapp.service.audit.events.WithdrawEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService implements Audit {

    private final List<AccountEvent> events;
    public AuditService() {
        this.events = new ArrayList<>(100);
    }

    @Override
    @EventListener(condition = "#event.amount >= @eventConditionFilter.depositLimit")
    public void auditOperation(DepositEvent event) {
        events.add(event);
        System.out.println("ACCOUNT ID: "
                + event.getAccountId() + " "
                + event.getSource() +
                ": " + event.getAmount());
    }


    @Override
    @EventListener(condition = "#event.amount >= @eventConditionFilter.withdrawalLimit")
    public void auditOperation(WithdrawEvent event) {
        events.add(event);
        System.out.println("ACCOUNT ID: "
                + event.getAccountId() + " "
                + event.getState() + " "
                + event.getSource() + ": "
                + event.getAmount());
    }


    @Override
    @EventListener
    public void auditOperation(BalanceEvent event) {
        events.add(event);
        System.out.println("ACCOUNT ID: "
                + event.getAccountId() + " "
                + event.getSource());
    }

    public List<AccountEvent> getEvents() {
        return new ArrayList<>(events);
    }
}