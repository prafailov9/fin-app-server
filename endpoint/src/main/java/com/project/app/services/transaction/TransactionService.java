package com.project.app.services.transaction;

import com.project.app.businesslogic.transaction.DefaultTransactionBL;
import com.project.app.businesslogic.transaction.TransactionBL;
import com.project.app.entities.transaction.Transaction;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author p.rafailov
 */
@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {

    private final TransactionBL transactionBL;

    public TransactionService() {
        this.transactionBL = new DefaultTransactionBL();
    }

    @GET
    @Path("/{transactionId}")
    public Transaction getTransaction(@PathParam("transactionId") Long id) {
        return transactionBL.getTransaction(id);
    }

    @GET
    @Path("/all")
    public List<Transaction> getAllTransactions() {
        return transactionBL.getAllTransactions();
    }

    @POST
    @Path("/add")
    public Transaction addTransaction(Transaction transaction) {
        transactionBL.insertTransaction(transaction);
        return transaction;
    }

    @PUT
    @Path("/update")
    public Transaction updateTransaction(Transaction transaction) {
        transactionBL.updateTransaction(transaction);
        return transaction;
    }

    @DELETE
    @Path("/delete/{id}")
    public Transaction deleteTransaction(@PathParam("id") Long id) {
        Transaction transaction = transactionBL.getTransaction(id);
        transactionBL.deleteTransaction(transaction);
        return transaction;
    }

}
