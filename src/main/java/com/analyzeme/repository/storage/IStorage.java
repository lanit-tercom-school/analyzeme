package com.analyzeme.repository.storage;

import java.util.List;

/**
 * Basic interface for all entry storage
 */
public interface IStorage {
    /**
     * Connects to server using coniguration
     *
     * @param configuration
     */
    void connect(Object configuration);

    /**
     * Saves given entry using random name and returns this name
     *
     * @param collection name of collection to store entry
     * @param entry      entry to store in collection
     * @return unique randomly generated name of this entry in given collection
     */
    String saveEntry(String collection, Object entry);

    /**
     * Saves given entry using given name and returns this name
     *
     * @param collection name of collection to store entry
     * @param entry      entry to store in collection
     * @param entryName  name to save entry(should be equal otherwise it will overwrite existing entry)
     * @return {@code entryName}
     */
    String saveEntry(String collection, Object entry, String entryName);

    /**
     * Gets entry from given collection by given name
     *
     * @param collection collection of the entry to be taken from
     * @param entryName  name of entry
     * @param entryClass java class of the entry
     * @param result     callback
     * @param <T>        type of entry
     */
    <T> void getEntry(String collection, String entryName, Class<T> entryClass, IStorageResult<T> result);

    /**
     * Gets all entry rom given collection
     *
     * @param collection name of collection to list
     * @param entryClass java class of the entries in this collection
     * @param result     callback
     * @param <T>        type of the entry ifn this collection
     */
    <T> void getAllFromCollection(String collection, Class<T> entryClass, IStorageResult<List<T>> result);
}
