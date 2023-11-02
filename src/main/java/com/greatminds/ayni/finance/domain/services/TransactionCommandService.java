package com.greatminds.ayni.finance.domain.services;

import com.greatminds.ayni.finance.domain.model.commands.CreateTransactionCommand;
import com.greatminds.ayni.finance.interfaces.rest.resources.UpdateTransactionResource;

public interface TransactionCommandService {
    Long handle(CreateTransactionCommand command);


    Long deleteTransaction(Long transactionId);
    Long updateTransaction(Long transactionId, UpdateTransactionResource request);
}
