package kth.ID2212.ac.common;

import kth.ID2212.ac.common.entities.UserData;
import kth.ID2212.ac.common.exceptions.BannedClientException;
import kth.ID2212.ac.common.exceptions.DuplicateNameException;
import kth.ID2212.ac.common.exceptions.InvalidCredentialsException;
import kth.ID2212.ac.common.exceptions.InvalidParametersException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Algirdas on 12/5/2016.
 */
public interface AnticheatServer extends Remote {
    String NAME = "ac-server";

    /**
     * Pings the server
     *
     * @return always true :)
     * @throws RemoteException when network issues occurs
     */
    boolean ping() throws RemoteException;

    /**
     * Creates a new user in AntiCheat server.
     *
     * @param username unique user name
     * @param password password
     * @return status of operation
     * @throws RemoteException            when network issues occurs
     * @throws DuplicateNameException     when username is taken
     * @throws InvalidParametersException when format of username or password is invalid
     */
    boolean register(String username, String password) throws RemoteException, DuplicateNameException, InvalidParametersException;

    /**
     * Connects client to anticheat server. This performs authorization and authentication
     * also checks if client is not banned.
     *
     * @param username Username of the client
     * @param password Password of the client
     * @param client   Control object of the client, in this system anticheat client is passive, server pulls data from client.
     * @return returns True if successfully connected
     * @throws RemoteException             when network issues occurs
     * @throws InvalidCredentialsException when username not found or password is not correct
     * @throws BannedClientException       when client is banned
     */
    UserData connect(String username, String password, AnticheatClient client) throws RemoteException, InvalidCredentialsException, BannedClientException;

    /**
     * Gracefully disconnects user
     *
     * @param username Username
     * @param client   Control object of the client
     * @throws RemoteException when network issues occurs
     */
    void disconnect(String username, AnticheatClient client) throws RemoteException;
}
