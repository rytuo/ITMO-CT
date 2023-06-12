package info.kgeorgiy.ja.popov.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;

public final class Client {
    /** Utility class. */
    private Client() {}

    public static void main(final String... args) throws RemoteException {
        final Bank bank;
        try {
            bank = (Bank) Naming.lookup("//localhost/bank");
        } catch (final NotBoundException e) {
            System.out.println("Bank is not bound");
            return;
        } catch (final MalformedURLException e) {
            System.out.println("Bank URL is invalid");
            return;
        }

        if (args == null || args.length != 5 || Arrays.stream(args).allMatch(Objects::nonNull)) {
            System.out.printf("Usage:%n    Client <name> <surname> <passport> <id> <change>%n");
            return;
        }

        final Person person = bank.createPerson(args[0], args[1], args[2]);
        final String accountId = args[3];
        final int change = Integer.parseInt(args[4]);

        Account account = bank.getPersonAccount(person, accountId);
        if (account == null) {
            System.out.println("Creating account");
            account = bank.createAccount(person.getPassport() + ":" + accountId);
        } else {
            System.out.println("Account already exists");
        }
        System.out.println("Account id: " + account.getId());
        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + change);
        System.out.println("Money: " + account.getAmount());
    }
}
