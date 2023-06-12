package info.kgeorgiy.ja.popov.rmi;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class LocalPerson extends AbstractPerson {
    private final Map<String, Account> accounts;

    public LocalPerson(String name, String surname, String passport, Map<String, Account> accounts) {
        super(name, surname, passport);
        this.accounts = accounts;
    }

    public Set<String> getAccountIds() {
        return accounts.keySet();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }
}
