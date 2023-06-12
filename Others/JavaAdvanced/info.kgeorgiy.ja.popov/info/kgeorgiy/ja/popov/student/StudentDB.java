package info.kgeorgiy.ja.popov.student;

import info.kgeorgiy.java.advanced.student.*;


import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {

    private <T> Stream<T> get(Collection<Student> students, Function<Student, T> f) {
        return students.stream().map(f);
    }

    private <T> List<T> toList(Stream<T> items) {
        return items.collect(Collectors.toList());
    }

    private <T> Set<T> toSet(Stream<T> items) {
        return items.collect(Collectors.toCollection(TreeSet::new));
    }

    private <T> List<T> getToList(Collection<Student> students, Function<Student, T> f) {
        return toList(get(students, f));
    }

    private <T> Set<T> getToSet(Collection<Student> students, Function<Student, T> f) {
        return toSet(get(students, f));
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getToList(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getToList(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return getToList(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getToList(students, student ->
                String.format("%s %s", student.getFirstName(), student.getLastName()));
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return getToSet(students, Student::getFirstName);
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students.stream()
                .max(Student::compareTo)
                .map(Student::getFirstName)
                .orElse("");
    }

    private List<Student> sortStreamByComparator(Stream<Student> students, Comparator<Student> comparator) {
        return toList(students.sorted(comparator));
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStreamByComparator(students.stream(), Student::compareTo);
    }

    private final Comparator<Student> nameComparator = Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .reversed()
            .thenComparing(Student::getId);

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStreamByComparator(students.stream(), nameComparator);
    }

    private List<Student> findStudentsBy(Collection<Student> students, Predicate<Student> f) {
        return sortStreamByComparator(students.stream().filter(f), nameComparator);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudentsBy(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudentsBy(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return findStudentsBy(students, student -> student.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return students.stream().filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(String::compareTo)));
    }
}
