package dev.codescreen.repository;

import com.google.common.collect.ImmutableList;
import dev.codescreen.model.TransactionEvent;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEventStore {
    /*
     * The event list where all TransactionEvents are stored.
     * The use of ImmutableList ensures that the collection is immutable after creation.
     * ref: https://guava.dev/releases/28.0-jre/api/docs/com/google/common/collect/ImmutableList.html
     */
    private ImmutableList<TransactionEvent> events = ImmutableList.of();

    /**
     * Saves a transaction event to the in-memory store.
     * The method is synchronized to ensure thread safety when updating the ImmutableList.
     * If the event is null, it throws an IllegalArgumentException to prevent null entries.
     *
     * @param event the transaction event to save
     */
    public synchronized void saveEvent(TransactionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Cannot save a null event");
        }

        // Rebuild the immutable list with the new event added
        events = ImmutableList.<TransactionEvent>builder()
                .addAll(events)
                .add(event)
                .build();
    }

    /**
     * Retrieves all transaction events for a specific user.
     * Filters the immutable list of events to return only those that match the given user ID.
     * Throws IllegalArgumentException if the user ID is null or empty to ensure valid input.
     *
     * @param userId the user ID for which to retrieve transaction events
     * @return an ImmutableList containing the filtered transaction events
     */
    public ImmutableList<TransactionEvent> getEventsForUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        // Filter the list of events by user ID and collect the results in an ImmutableList
        return events.stream()
                .filter(event -> event.getTransactionRequest().getUserId().equals(userId))
                .collect(ImmutableList.toImmutableList());
    }
}
