package dev.codescreen.service;

import dev.codescreen.exeption.EventStoreException;
import dev.codescreen.model.*;
import dev.codescreen.repository.InMemoryEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    /*
        Every load or authorization PUT should return the updated balance following the transaction.
        Authorization declines should be saved, even if they do not impact balance calculation.
     */

    private final InMemoryEventStore eventStore;
    private final BalanceProjection projection;

    @Autowired
    public TransactionServiceImpl(InMemoryEventStore eventStore, BalanceProjection projection) {
        this.eventStore = eventStore;
        this.projection = projection;
    }

    /**
     * Authorizes a transaction based on user balance and saves the transaction event.
     * This method determines if the transaction can be approved based on the available balance and
     * records the decision in an immutable event store.
     *
     * @param request the authorization request details
     * @return an AuthorizationResponse containing the outcome of the transaction
     */
    @Override
    public AuthorizationResponse authorize(AuthorizationRequest request) {
        Amount requestAmount = request.getTransactionAmount();
        BigDecimal amount = new BigDecimal(requestAmount.getAmount());

        // approve if user's sufficient funds are available.
        boolean isApproved = projection.isSufficientBalance(request.getUserId(), amount);

        // Creating the Authorization event
        TransactionEvent event = new TransactionEvent(
                request,
                TransactionType.AUTHORIZATION,
                LocalDateTime.now(),
                isApproved ? ResponseCode.APPROVED : ResponseCode.DECLINED
        );

        try {
            // Storing the event
            eventStore.saveEvent(event);

            if (isApproved) {
                // Update the projection only if approved
                projection.project(event);
            }
        }  catch (Exception e) {
            throw new EventStoreException(e.getMessage());
        }


        // Updated balance
        Amount updatedBalance = new Amount(
                projection.getBalance(request.getUserId()).toString(),
                requestAmount.getCurrency(),
                requestAmount.getDebitOrCredit()
        );

        // Constructing response with updated balance
        return new AuthorizationResponse(
                request.getUserId(),
                request.getMessageId(),
                isApproved ? ResponseCode.APPROVED : ResponseCode.DECLINED,
                updatedBalance
        );
    }


    /**
     * Processes a load transaction, updating the user's balance.
     * Assumes load transactions are always approved.
     *
     * @param request the load request details
     * @return a LoadResponse containing the updated balance after the transaction
     */
    @Override
    public LoadResponse load(LoadRequest request) {
        Amount requestAmount = request.getTransactionAmount();

        // Creating the Load event
        TransactionEvent event = new TransactionEvent(
                request,
                TransactionType.LOAD,
                LocalDateTime.now(),
                ResponseCode.APPROVED // Assume load operations are always approved
        );

        try {
            // Storing the event
            eventStore.saveEvent(event);

            // Updating the projection
            projection.project(event);
        } catch (Exception e) {
            throw new EventStoreException(e.getMessage());
        }

        // Updated balance
        Amount updatedBalance = new Amount(
                projection.getBalance(request.getUserId()).toString(),
                requestAmount.getCurrency(),
                requestAmount.getDebitOrCredit()
        );

        // Returning the updated balance
        return new LoadResponse(
                request.getUserId(),
                request.getMessageId(),
                updatedBalance
        );
    }
}

