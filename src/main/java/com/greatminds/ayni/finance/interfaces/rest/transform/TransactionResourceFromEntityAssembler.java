package com.greatminds.ayni.finance.interfaces.rest.transform;

import com.greatminds.ayni.finance.domain.model.aggregates.Transaction;
import com.greatminds.ayni.finance.interfaces.rest.resources.TransactionResource;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionResourceFromEntityAssembler {

    public static TransactionResource toResourceFromEntity(Transaction entity) {
        return new TransactionResource(entity.getId(), entity.getCostName(), entity.getDescription(), entity.getType(), entity.getPrice(), entity.getQuantity());
    }

    public static List<TransactionResource> toResourceListFromEntities(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
