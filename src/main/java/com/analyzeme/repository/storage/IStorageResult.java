package com.analyzeme.repository.storage;


/**
 * Result of  some storage operation
 *
 * @param <T> type of result
 */
interface IStorageResult<T> {

    /**
     * Calls ifsome storage query was successful and stores result in {@code result} parameter
     *
     * @param result result of query
     */
    void onComplete(T result);

    /**
     * Calls if it was an error while processing storage query
     *
     * @param message error message
     */
    void onError(String message);
}
