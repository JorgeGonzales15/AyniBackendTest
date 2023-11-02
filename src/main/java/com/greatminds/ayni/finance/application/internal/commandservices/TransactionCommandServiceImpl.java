package com.greatminds.ayni.finance.application.internal.commandservices;

import com.greatminds.ayni.finance.domain.model.aggregates.Transaction;
import com.greatminds.ayni.finance.domain.model.commands.CreateTransactionCommand;
import com.greatminds.ayni.finance.domain.services.TransactionCommandService;
import com.greatminds.ayni.finance.infrastructure.persistence.jpa.repositories.TransactionRepository;
import com.greatminds.ayni.finance.interfaces.rest.resources.UpdateTransactionResource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final TransactionRepository transactionRepository;
    public TransactionCommandServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long handle(CreateTransactionCommand command) {
        Date currentDate = new Date();
        var transaction = new Transaction(command.costName(), command.description(), command.type(), command.price(), command.quantity());
        transaction.updateDate(currentDate);
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    @Override
    public Long deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new IllegalArgumentException("Transaction with ID " + transactionId + " not found");
        }
         transactionRepository.deleteById(transactionId);
         return transactionId;
    }

    @Override
    public Long updateTransaction(Long transactionId, UpdateTransactionResource request) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + transactionId + " not found"));

        transaction.update(new Transaction(request.costName(), request.description(), request.type(), request.price(), request.quantity()));
        transactionRepository.save(transaction);
        return transaction.getId();
    }
}