package com.project.app.services.transaction;

import com.project.app.businesslogic.transaction.DefaultTransactionBL;
import com.project.app.businesslogic.transaction.TransactionBL;
import com.project.app.entities.transaction.Transaction;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        Transaction tx = transactionBL.getTransaction(id);
        return tx;
    }

    @GET
    @Path("/all")
    public List<Transaction> getAllTransactions() {
        List<Transaction> txs = transactionBL.getAllTransactions();
        return txs;
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
