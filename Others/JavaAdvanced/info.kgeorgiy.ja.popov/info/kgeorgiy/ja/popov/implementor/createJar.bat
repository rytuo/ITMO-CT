:: run from java-advanced/java-solutions

:: path to java-advanced-2021
set advanced=..\java-advanced-2021

:: creating directories for temp files
mkdir "info\kgeorgiy\java\advanced\implementor"

:: unzip .class files to temp directory
jar xf "%advanced%\artifacts\info.kgeorgiy.java.advanced.implementor.jar" "info\kgeorgiy\java\advanced\implementor\Impler.class" "info\kgeorgiy\java\advanced\implementor\JarImpler.class" "info\kgeorgiy\java\advanced\implementor\ImplerException.class"

:: compile
javac "info\kgeorgiy\ja\popov\implementor\Implementor.java"

:: create jar
jar cevf "info.kgeorgiy.ja.popov.implementor.Implementor" "info.kgeorgiy.ja.popov.implementor.Implementor.jar" "info\kgeorgiy\ja\popov\implementor\Implementor.class" "info\kgeorgiy\java\advanced\implementor\Impler.class" "info\kgeorgiy\java\advanced\implementor\JarImpler.class" "info\kgeorgiy\java\advanced\implementor\ImplerException.class"
