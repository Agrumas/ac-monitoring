package kth.ID2212.ac.common;

import kth.ID2212.ac.common.entities.ProcessList;
import kth.ID2212.ac.common.entities.UserData;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote control object of Anticheat client.
 * In this system anticheat client is passive, server pulls data from client.
 */
public interface AnticheatClient extends Remote {
    /**
     * Takes screenshot of user screen. It is represented of array of bytes.
     * @return screenshot
     * @throws RemoteException when networks fails
     */
    byte[] screen() throws RemoteException;

    /**
     * Collects list of running processes and windows.
     * @return process list
     * @throws RemoteException when networks fails
     */
    ProcessList getProcesses() throws RemoteException;

    /**
     * Client callback to inform user about warning.
     * @param reason explanation why warning is given
     * @throws RemoteException when networks fails
     */
    void onWarning(String reason) throws RemoteException;

    /**
     * Client callback to inform user about ban.
     * @param reason explanation why ban is given
     * @throws RemoteException when networks fails
     */
    void onBan(String reason) throws RemoteException;

    /**
     * Client callback to inform user about changes of user details.
     * @param data user details
     * @throws RemoteException when networks fails
     */
    void onUserUpdate(UserData data) throws RemoteException;
}
