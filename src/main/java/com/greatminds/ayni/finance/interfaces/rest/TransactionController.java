package com.greatminds.ayni.finance.interfaces.rest;

import com.greatminds.ayni.finance.domain.model.aggregates.Transaction;
import com.greatminds.ayni.finance.domain.model.commands.CreateTransactionCommand;
import com.greatminds.ayni.finance.domain.model.queries.GetTransactionByIdQuery;
import com.greatminds.ayni.finance.domain.services.TransactionCommandService;
import com.greatminds.ayni.finance.domain.services.TransactionQueryService;
import com.greatminds.ayni.finance.interfaces.rest.resources.CreateTransactionResource;
import com.greatminds.ayni.finance.interfaces.rest.resources.TransactionResource;
import com.greatminds.ayni.finance.interfaces.rest.resources.UpdateTransactionResource;
import com.greatminds.ayni.finance.interfaces.rest.transform.CreateTransactionCommandFromResourceAssembler;
import com.greatminds.ayni.finance.interfaces.rest.transform.TransactionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transactions", description = "Financial Transactions Management Endpoints")
public class TransactionController {
    private final TransactionQueryService transactionQueryService;
    private final TransactionCommandService transactionCommandService;

    public TransactionController(TransactionQueryService transactionQueryService, TransactionCommandService transactionCommandService) {
        this.transactionQueryService = transactionQueryService;
        this.transactionCommandService = transactionCommandService;
    }

    @PostMapping
    public ResponseEntity<TransactionResource> createTransaction(@RequestBody CreateTransactionResource resource){
        var createTransactionCommand = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);

        var transactionId = transactionCommandService.handle(createTransactionCommand);
        if(transactionId == 0L){return ResponseEntity.badRequest().build();}
        var getTransactionIdByQuery = new GetTransactionByIdQuery(transactionId);
        var transaction = transactionQueryService.handle(getTransactionIdByQuery);
        if(transaction.isEmpty()){ return ResponseEntity.badRequest().build();}
        var transactionResource = TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return new ResponseEntity<>(transactionResource, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
        public ResponseEntity<TransactionResource> getTransactionById(@PathVariable Long transactionId) {
        var getTransactionIdByQuery = new GetTransactionByIdQuery(transactionId);
        var transaction = transactionQueryService.handle(getTransactionIdByQuery);
        if (transaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var transactionResource = TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return ResponseEntity.ok(transactionResource);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResource>> getAllTransactions() {
        List<Transaction> transactions = transactionQueryService.getAllTransactions();

        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TransactionResource> transactionResources = TransactionResourceFromEntityAssembler.toResourceListFromEntities(transactions);
        return ResponseEntity.ok(transactionResources);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResource> updateTransaction(@PathVariable Long transactionId, @RequestBody UpdateTransactionResource resource) {
        try {
            Long updatedTransactionId = transactionCommandService.updateTransaction(transactionId, resource);
            Transaction updatedTransaction = transactionQueryService.handle(new GetTransactionByIdQuery(updatedTransactionId))
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
            return ResponseEntity.ok(TransactionResourceFromEntityAssembler.toResourceFromEntity(updatedTransaction));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        var getTransactionIdByQuery = new GetTransactionByIdQuery(transactionId);
        var existingTransaction = transactionQueryService.handle(getTransactionIdByQuery);

        if (existingTransaction.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        transactionCommandService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

}
