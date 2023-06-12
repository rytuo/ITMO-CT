package info.kgeorgiy.ja.popov.rmi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Tests {

    private static Bank bank;
    private static final int PORT = 8888;
    private static final String URL = "//localhost:" + PORT + "/bank";
    private static final boolean REMOTE = true;
    private static final boolean LOCAL = false;
    private static final String passport = "12345";
    private static final String accountId = "54321";
    private static final int threads = 10;


    @BeforeClass
    public static void beforeClass() throws Exception {
        bank = new RemoteBank(PORT);
        LocateRegistry.createRegistry(PORT);
        UnicastRemoteObject.exportObject(bank, PORT);
        Naming.rebind(URL, bank);
    }

    @org.junit.Test
    public void test01_create_person() throws RemoteException {
        final String name = "Вася",
                surname = "Пупкин",
                strangerName = "Иван";

        Assert.assertNull(bank.getPerson(passport, REMOTE));
        Assert.assertNull(bank.getPerson(passport, LOCAL));
        Person person = bank.createPerson(name, surname, passport);
        Assert.assertNotNull(person);
        Assert.assertNull(bank.createPerson(strangerName, surname, passport));
        Assert.assertEquals(person, bank.getPerson(passport, LOCAL));

        Assert.assertNotNull(person.getName());
        Assert.assertNotNull(person.getSurname());
        Assert.assertNotNull(person.getPassport());
    }

    @org.junit.Test
    public void test02_create_local_person() throws RemoteException {
        Person remotePerson = bank.getPerson(passport, REMOTE);
        Person localPerson = bank.getPerson(passport, LOCAL);

        Assert.assertEquals(remotePerson.getName(), localPerson.getName());
        Assert.assertEquals(remotePerson.getSurname(), localPerson.getSurname());
        Assert.assertEquals(remotePerson.getPassport(), localPerson.getPassport());
    }

    @org.junit.Test
    public void test03_create_account() throws RemoteException {
        Person remotePerson = bank.getPerson(passport, REMOTE);
        Person localPerson = bank.getPerson(passport, LOCAL);

        Assert.assertNotNull(bank.getPersonAccount(remotePerson, accountId));
        Assert.assertEquals(0, bank.getPersonAccounts(remotePerson).size());

        Assert.assertNull(bank.createAccount(passport + ":" + accountId + ":"));
        Assert.assertNotNull(bank.createAccount(passport + ":" + accountId));

        Assert.assertNotNull(bank.getPersonAccount(remotePerson, accountId));
        Assert.assertEquals(1, bank.getPersonAccounts(remotePerson).size());
        Assert.assertNull(bank.getPersonAccount(localPerson, accountId));
        Assert.assertEquals(0, bank.getPersonAccounts(localPerson).size());
    }

    @org.junit.Test
    public void test04_remote_account_changes() throws RemoteException {
        final int initMoney = 100;

        Person remotePerson = bank.getPerson(passport, REMOTE);
        Account remoteAccount = bank.getPersonAccount(remotePerson, accountId);
        Person localPerson = bank.getPerson(passport, LOCAL);
        Account localAccount = bank.getPersonAccount(localPerson, accountId);
        Assert.assertEquals(remoteAccount.getAmount(), localAccount.getAmount());

        remoteAccount.setAmount(initMoney);
        Assert.assertNotEquals(remoteAccount.getAmount(), localAccount.getAmount());

        remotePerson = bank.getPerson(passport, REMOTE);
        remoteAccount = bank.getPersonAccount(remotePerson, accountId);
        localPerson = bank.getPerson(passport, LOCAL);
        localAccount = bank.getPersonAccount(localPerson, accountId);
        Assert.assertEquals(remoteAccount.getAmount(), localAccount.getAmount());
        Assert.assertEquals(remoteAccount.getAmount(), initMoney);
    }

    @org.junit.Test
    public void test04_local_account_changes() throws RemoteException {
        final int newMoney = 200;

        Person remotePerson = bank.getPerson(passport, REMOTE);
        Account remoteAccount = bank.getPersonAccount(remotePerson, accountId);
        Person localPerson = bank.getPerson(passport, LOCAL);
        Account localAccount = bank.getPersonAccount(localPerson, accountId);
        Assert.assertEquals(remoteAccount.getAmount(), localAccount.getAmount());

        final int initMoney = localAccount.getAmount();

        localAccount.setAmount(newMoney);
        Assert.assertNotEquals(remoteAccount.getAmount(), localAccount.getAmount());

        remotePerson = bank.getPerson(passport, REMOTE);
        remoteAccount = bank.getPersonAccount(remotePerson, accountId);
        localPerson = bank.getPerson(passport, LOCAL);
        localAccount = bank.getPersonAccount(localPerson, accountId);
        Assert.assertEquals(remoteAccount.getAmount(), localAccount.getAmount());
        Assert.assertEquals(remoteAccount.getAmount(), initMoney);
    }

    @org.junit.Test
    public void test05_multithread_account() throws RemoteException {
        for (int i = 100; i <= threads * 100; i++) {
            new Thread(() -> {
                try {
                    Client.main();
                } catch (RemoteException e) {
                    System.err.println(e.getMessage());
                }
            });
        }
    }
}