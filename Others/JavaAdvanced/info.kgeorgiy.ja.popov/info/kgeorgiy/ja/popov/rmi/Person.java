package info.kgeorgiy.ja.popov.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Person extends Remote, Serializable {
    /** Returns person name */
    String getName() throws RemoteException;

    /** Returns person surname */
    String getSurname() throws RemoteException;

    /** Returns person passport identifier */
    String getPassport() throws RemoteException;

    boolean equals(Person parson) throws RemoteException;
}
