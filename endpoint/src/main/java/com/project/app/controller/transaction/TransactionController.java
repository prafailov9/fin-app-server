package com.project.app.controller.transaction;

import com.project.app.entities.transaction.Transaction;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.transaction.TransactionService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 *
 * @author p.rafailov
 */
@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController() {
        this.transactionService = ServiceInstanceHolder.get(TransactionService.class);
    }

    @GET
    @Path("/{transactionId}")
    public Transaction getTransaction(@PathParam("transactionId") Long id) {
        return transactionService.getTransaction(id);
    }

    @GET
    @Path("/all")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @POST
    @Path("/add")
    public Transaction addTransaction(Transaction transaction) {
        transactionService.insertTransaction(transaction);
        return transaction;
    }

    @PUT
    @Path("/update")
    public Transaction updateTransaction(Transaction transaction) {
        transactionService.updateTransaction(transaction);
        return transaction;
    }

    @DELETE
    @Path("/delete/{id}")
    public Transaction deleteTransaction(@PathParam("id") Long id) {
        Transaction transaction = transactionService.getTransaction(id);
        transactionService.deleteTransaction(transaction);
        return transaction;
    }

}
