package info.kgeorgiy.ja.popov.rmi;

import java.rmi.RemoteException;

public class AbstractPerson implements Person {
    private final String name, surname, passport;

    public AbstractPerson(String name, String surname, String passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassport() {
        return passport;
    }

    @Override
    public boolean equals(Person person) throws RemoteException {
        return name.equals(person.getName()) &&
                surname.equals(person.getSurname()) &&
                passport.equals(person.getPassport());
    }
}
