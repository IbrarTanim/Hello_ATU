package com.dtec.helloatu.manager;

import com.dtec.helloatu.dao.Crime;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface that provides methods for managing the database inside the Application.
 *
 * @author Octa
 */
public interface IDatabaseManager {

    /**
     * Closing available connections
     */
    void closeDbConnections();

    /**
     * Delete all tables and content from our database
     */
    void dropDatabase();

    /**
     * Insert a crime into the DB
     *
     * @param crime to be inserted
     */
    Crime insertCrime(Crime crime);

    /**
     * List all the Crime from the DB
     *
     * @return list of Crime
     */
    ArrayList<Crime> listCrime();

    /**
     * Update a Crime from the DB
     *
     * @param crime to be updated
     */
    void updateCrime(Crime crime);

    boolean isCrime(int crimeId);



    List<Crime> listCrimesByCategoryId(int categoryId);

    /**
     * Delete a Crime with a certain id from the DB
     *
     * @param crimeId of Crime to be deleted
     */
    boolean deleteCrimeById(Long crimeId);


    /**
     * @param crimeId - of the Crime we want to fetch
     * @return Return a Crime by its id
     */
    Crime getCrimeById(Long crimeId);

    /**
     * Delete all the Crime from the DB
     */
    void deleteAllCrime();


}
