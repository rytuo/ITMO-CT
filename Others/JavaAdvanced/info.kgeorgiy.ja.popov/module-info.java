/**
 * Homeworks for java-advanced-2021 course.
 *
 * @author Popov Aleksandr (popov.aleksandr@niuitmo.ru)
 */
module info.kgeorgiy.ja.popov {
    requires info.kgeorgiy.java.advanced.walk;
    requires info.kgeorgiy.java.advanced.arrayset;
    requires info.kgeorgiy.java.advanced.student;
    requires info.kgeorgiy.java.advanced.implementor;
    requires info.kgeorgiy.java.advanced.concurrent;
    requires info.kgeorgiy.java.advanced.mapper;
    requires info.kgeorgiy.java.advanced.crawler;
    requires info.kgeorgiy.java.advanced.hello;
    requires java.compiler;
    requires java.rmi;
    requires jdk.httpserver;
    requires java.desktop;

    exports info.kgeorgiy.ja.popov.walk;
    exports info.kgeorgiy.ja.popov.arrayset;
    exports info.kgeorgiy.ja.popov.student;
    exports info.kgeorgiy.ja.popov.implementor;
    exports info.kgeorgiy.ja.popov.concurrent;
    exports info.kgeorgiy.ja.popov.crawler;
    exports info.kgeorgiy.ja.popov.hello;

    opens info.kgeorgiy.ja.popov.rmi to junit;
}