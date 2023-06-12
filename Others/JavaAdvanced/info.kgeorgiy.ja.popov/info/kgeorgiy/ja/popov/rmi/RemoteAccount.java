package info.kgeorgiy.ja.popov.rmi;

import java.rmi.RemoteException;

public class RemoteAccount implements Account {
    private final String id;
    private int amount;

    public RemoteAccount(final String id) {
        this.id = id;
        amount = 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public synchronized int getAmount() {
        System.out.println("Getting amount of money for account " + id);
        return amount;
    }

    @Override
    public synchronized void setAmount(final int amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount = amount;
    }

    @Override
    public boolean equals(Account account) throws RemoteException {
        return id.equals(account.getId());
    }
}
