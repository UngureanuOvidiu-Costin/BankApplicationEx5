package com.luxoft.bankapp.service.audit;

import com.luxoft.bankapp.service.audit.events.BalanceEvent;
import com.luxoft.bankapp.service.audit.events.DepositEvent;
import com.luxoft.bankapp.service.audit.events.WithdrawEvent;

public interface Audit {

    void auditOperation(DepositEvent event);
    void auditOperation(WithdrawEvent event);
    void auditOperation(BalanceEvent event);
}

