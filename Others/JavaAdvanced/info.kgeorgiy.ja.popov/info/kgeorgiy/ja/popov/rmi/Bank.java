package info.kgeorgiy.ja.popov.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

public interface Bank extends Remote {
    /**
     * Creates a new account with specified identifier if it does not exist.
     * Identifier must be "passport:subId".
     * @param id account id
     * @return created or existing account. If Identifier is invalid {@code null} is returned
     */
    Account createAccount(String id) throws RemoteException;

    /**
     * Returns account by identifier.
     * @param subId account subId
     * @return account with specified identifier or {@code null} if such account does not exists.
     */
    Account getAccount(String subId) throws RemoteException;

    /**
     * Creates a new person with specified name, surname and passport if it does not exist.
     * @param name person name
     * @param surname person surname
     * @param passport person passport identifier
     * @return created or existing remote person. If a different person with such passport
     * already exists {@link null} is returned
     */
    Person createPerson(String name, String surname, String passport) throws RemoteException;

    /**
     * Returns person by identifier.
     * Returns specified instance of {@link RemotePerson} or {@link LocalPerson}
     * @param id person passport id
     * @param remote if person instance will be remote or local
     * @return person with specified identifier or {@code null} if such person does not exist.
     */
    Person getPerson(String id, boolean remote) throws RemoteException;

    /**
     * Returns person accounts.
     * If person is instance of {@link LocalPerson}, it's own accounts state is used.
     * Else actual bank state is used.
     * @param person whose accounts are retrieved
     * @return person accounts' identifiers or {@code emptySet} if there are no accounts
     */
    Map<String, Account> getPersonAccounts(Person person) throws RemoteException;

    /**
     * Returns person account by identifier.
     * If person is instance of {@link LocalPerson}, it's own accounts state is used.
     * Else actual bank state is used.
     * @param person whose account is retrieved
     * @return person accounts' identifiers or {@code null} if such account does not exist
     */
    Account getPersonAccount(Person person, String id) throws RemoteException;
}
