package com.luxoft.bankapp.service.audit;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.audit.events.BalanceEvent;
import com.luxoft.bankapp.service.audit.events.DepositEvent;
import com.luxoft.bankapp.service.audit.events.WithdrawEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AuditAspect {
    @Autowired
    private Audit audit;

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("execution(* com.luxoft.bankapp.service.operations.*.deposit(..))")
    public void anyDeposit() {}

    @Pointcut("execution(* com.luxoft.bankapp.service.operations.*.withdraw(..))")
    public void anyWithdraw() {}

    @Pointcut("execution(* com.luxoft.bankapp.service.operations.*.getBalance(..))")
    public void anyBalance() {}

    private long getAccountId(Object[] methodArgs) {
        Account account;
        if (methodArgs[0] instanceof Client) {
            account = ((Client)  methodArgs[0])
                    .getActiveAccount();
        } else {
            account = (Account) methodArgs[0];
        }
        return account.getId();
    }

    @Before("anyDeposit()")
    public void logDeposit(JoinPoint joinPoint) {
        Object[] methodArgs = joinPoint.getArgs();
        long accountId = getAccountId(methodArgs);
        double amount = (double) methodArgs[1];

        // Create and publish DepositEvent
        applicationContext.publishEvent(
                new DepositEvent(accountId, amount)
        );
    }

    @Before("anyBalance()")
    public void logBalance(JoinPoint joinPoint) {
        Object[] methodArgs = joinPoint.getArgs();
        applicationContext.publishEvent(
                new BalanceEvent(getAccountId(methodArgs)));
    }


    @Around("anyWithdraw()")
    public Object logWithdrawal(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object[] methodArgs = thisJoinPoint.getArgs();
        long accountId = getAccountId(methodArgs);
        double amount = (double) methodArgs[1];

        // Publish event before proceeding
        applicationContext.publishEvent(new WithdrawEvent(accountId, amount));

        Object result;
        try {
            result = thisJoinPoint.proceed();

            // Publish event after successful result
            applicationContext.publishEvent(
                    new WithdrawEvent(accountId, amount, WithdrawEvent.State.SUCCESSFUL)
            );
        } catch (Exception e) {
            // Publish event after exception
            applicationContext.publishEvent(
                    new WithdrawEvent(accountId, amount, WithdrawEvent.State.FAILED)
            );
            throw e;
        }
        return result;
    }
}
