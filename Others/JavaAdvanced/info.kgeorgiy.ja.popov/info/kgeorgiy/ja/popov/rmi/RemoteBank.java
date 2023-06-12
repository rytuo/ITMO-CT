package info.kgeorgiy.ja.popov.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteBank implements Bank {

    private final int port;
    // passport -> person
    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();
    // passport -> subId
    private final ConcurrentMap<String, Set<String>> personsAccounts = new ConcurrentHashMap<>();
    // id == passport:subId -> account
    private final ConcurrentMap<String, Account> bankAccounts = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public Account createAccount(final String id) throws RemoteException {
        String[] parts = id.split(":");
        if (parts.length != 2 || !persons.containsKey(parts[0])) {
            System.err.println("Invalid account id");
            return null;
        }
        System.out.println("Creating account " + id);

        personsAccounts.get(parts[0]).add(parts[1]);
        final Account account = new RemoteAccount(parts[1]);
        if (bankAccounts.putIfAbsent(parts[1], account) == null) {
            UnicastRemoteObject.exportObject(account, port);
            return account;
        } else {
            return getAccount(parts[1]);
        }
    }

    @Override
    public Account getAccount(final String subId) {
        System.out.println("Retrieving account " + subId);
        return bankAccounts.get(subId);
    }

    @Override
    public Person createPerson(String name, String surname, String passport) throws RemoteException {
        if (persons.containsKey(passport) && (!persons.get(passport).getName().equals(name) ||
                !persons.get(passport).getSurname().equals(surname))) {
            System.err.println("Person with such passport identifier already exists");
            return null;
        }
        System.out.printf("Creating person %s %s %s%n", name, surname, passport);
        final Person person = new RemotePerson(name, surname, passport);
        personsAccounts.putIfAbsent(passport, ConcurrentHashMap.newKeySet());
        if (persons.putIfAbsent(passport, person) == null) {
            UnicastRemoteObject.exportObject(person, port);
            return person;
        } else {
            return getPerson(passport, true);
        }
    }

    @Override
    public Person getPerson(String passport, boolean remote) throws RemoteException {
        System.out.printf("Retrieving %s person with passport %s%n", remote ? "remote" : "local", passport);
        Person person = persons.get(passport);
        if (remote) {
            return person;
        } else {
            return new LocalPerson(person.getName(), person.getSurname(), person.getPassport(), copyPersonAccounts(passport));
        }
    }

    @Override
    public Map<String, Account> getPersonAccounts(Person person) throws RemoteException {
        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccounts();
        }
        return copyPersonAccounts(person.getPassport());
    }

    @Override
    public Account getPersonAccount(Person person, String id) throws RemoteException {
        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccount(id);
        }
        return bankAccounts.get(person.getPassport() + ":" + id);
    }

    private Map<String, Account> copyPersonAccounts(String passport) {
        Map<String, Account> personAccounts = new ConcurrentHashMap<>();
        personsAccounts.get(passport).forEach(subId -> {
            String bankAccountId = passport + ':' + subId;
            personAccounts.put(bankAccountId, bankAccounts.get(bankAccountId));
        });
        return personAccounts;
    }
}
