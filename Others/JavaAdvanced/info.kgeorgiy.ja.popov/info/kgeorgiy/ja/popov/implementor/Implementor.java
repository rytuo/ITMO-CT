package info.kgeorgiy.ja.popov.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileAttribute;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Tool for generating class implementations.
 *
 * @author Aleksandr Popov (popov.aleksandr@niuitmo.ru)
 */
public class Implementor implements JarImpler {

    /**
     * Entry point of program
     * <p>
     * "Usage:  <code>Implementor class.name [file.jar]</code>"
     * Currently active directory is {@code root} directory.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args.length > 2 || args[0] == null ||
                (args.length == 2 && args[1] == null)) {
            System.out.println("Usage:\n\tImplementor class.name [file.jar]");
            return;
        }
        String className = args[0];

        Path root = Path.of(System.getProperty("user.dir"));
        System.out.println(root.toString());

        JarImpler implementor = new Implementor();
        Class<?> token;
        try {
            token = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find class " + className);
            return;
        }

        try {
            if (args.length == 2) {
                Path jarPath = root.resolve(token.getName() + "Impl.jar");
                implementor.implementJar(token, jarPath);
            } else {
                implementor.implement(token, root);
            }
        } catch (ImplerException e) {
            System.out.println("Error while implementing class\n");
            e.printStackTrace();
        }
    }

    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        Path root = jarFile.toAbsolutePath().getParent();
        implement(token, root);
        compile(token, root);
        archive(token, root, jarFile);
    }

    /**
     * Compiles given class.
     * <p>
     * System java compiler is used, don't forget to specify it.
     * Class path is created from {@code root} and {@link #getClassPath(Class, Path, boolean)}
     *
     * @param token target class
     * @param root root path
     * @throws ImplerException if compiler is absent or error happened while compiling
     */
    private void compile(Class<?> token, final Path root) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Compiler is absent");
        }
        Path filePath = getClassPath(token, root, true);
        String[] args = {filePath.toString(), "-cp", root + File.pathSeparator + getClassPath(token)};
        final int exitCode = compiler.run(null, null, null, args);
        if (exitCode != 0) {
            throw new ImplerException("Error while compiling " + filePath);
        }
    }

    /**
     * Creates class path for compiling.
     * <p>
     * It is specified by {@link Class#getProtectionDomain()},
     * {@link ProtectionDomain#getCodeSource()} and
     * {@link CodeSource#getLocation()}
     *
     * @param token class
     * @return class path
     * @throws ImplerException if class path is invalid
     */
    private static String getClassPath(Class<?> token) throws ImplerException {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new ImplerException("Invalid class path");
        }
    }

    /**
     * Creates <var>.jar</var> archive.
     * <p>
     * Archive is created in root directory.
     * File path in archive is related and specified by {@link #getClassPath(Class, Path, boolean)}
     * Default manifest is generated
     *
     * @param token target class for archiving
     * @param root root directory
     * @param jarFile path for creating an archive
     * @throws ImplerException if an archive cannot be written
     */
    private void archive(Class<?> token, Path root, Path jarFile) throws ImplerException {
        Path classPath = getClassPath(token, root, false).resolveSibling(getImplName(token) + ".class");
        String relativeClassPath = classPath.toString().replace(File.separatorChar, '/');

        Manifest manifest = new Manifest();
        try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            jos.putNextEntry(new ZipEntry(relativeClassPath));
            Files.copy(root.resolve(classPath), jos);
            jos.closeEntry();
        } catch (IOException e) {
            throw new ImplerException("Cannot create archive file" + classPath, e);
        }
    }

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        validateToken(token);
        Path classPath = getClassPath(token, root, true);
        createDirectories(classPath);
        try (OutputStream os = Files.newOutputStream(classPath)) {
            os.write(getClassImplBytes(token));
        } catch (IOException e) {
            throw new ImplerException("Cannot write an implementation of " + token.getName(), e);
        }
    }

    /**
     * Interprets input string to unicode string and transforms it to byte array.
     * <p>
     * If a symbol is not default unicode it is transformed into {@code "\\u...."} form
     * Byte array is formed by {@link String#getBytes()}
     *
     * @param input string in default encoding
     * @return byte array with unicode transcription of a string
     */
    private byte[] toUnicode(String input) {
        StringBuilder b = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 128) {
                b.append(String.format("\\u%04X", (int) c));
            } else {
                b.append(c);
            }
        }
        return b.toString().getBytes();
    }

    /**
     * Checks if a type can be implemented.
     * <p>
     * This version supports only non-private interface implementation
     *
     * @param clazz type token
     * @throws ImplerException when type cannot be implemented
     */
    private void validateToken(Class<?> clazz) throws ImplerException {
        if (!clazz.isInterface() || Modifier.isPrivate(clazz.getModifiers())) {
            throw new ImplerException("Invalid class token");
        }
    }

    /**
     * Creates all non-existing directories on the way to target class
     * <p>
     * Creates directories if parent directory is not not null
     *
     * @see Files#createDirectories(Path, FileAttribute[])
     * @param classPath target class path
     * @throws ImplerException if cannot create directory
     */
    private void createDirectories(Path classPath) throws ImplerException {
        Path dirPath = classPath.toAbsolutePath().getParent();
        if (dirPath == null) {
            return;
        }
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new ImplerException("Cannot create parent directories", e);
        }
    }

    /**
     * Creates a class path
     * <p>
     * It is formed from {@link Class#getPackageName()} as directory path and {@link #getImplName(Class)} as file name
     *
     * @param clazz target class
     * @param root root directory
     * @param absolute is result path resolved relatively to root directory
     * @return class path
     */
    private Path getClassPath(Class<?> clazz, Path root, boolean absolute) {
        Path classPath = Path.of(clazz.getPackageName().replace('.', File.separatorChar))
                .resolve(getImplName(clazz) + ".java");
        if (absolute) {
            classPath = root.resolve(classPath);
        }
        return classPath;
    }

    /**
     * Creates a class implication of specified {@link Class} and transforms it to Unicode byte form.
     * <p>
     * Created implication:
     * <ul>
     *     <li>Has the same {@link Package} as specified class</li>
     *     <li>Inherits all non-abstract class fields</li>
     *     <li>Implements all abstract {@link Method} returning default value</li>
     * </ul>
     * Implication is byte interpreted in {@code Unicode} encoding by {@link #toUnicode(String)}.
     *
     * @see #getPackageDeclaration(Class)
     * @see #getClassDeclaration(Class)
     * @see #getMethodsImpl(Class)
     * @param token class type token
     * @return {@code byte} array with implementation
     */
    private byte[] getClassImplBytes(Class<?> token) {
        String impl = String.format("%s%s {%n%s%n}%n",
                getPackageDeclaration(token), getClassDeclaration(token), getMethodsImpl(token));
        return toUnicode(impl);
    }

    /**
     * Creates package declaration.
     * <p>
     * If package exists in specified class, it is specified by {@link Class#getPackage()}.
     *
     * @param clazz class type token
     * @return package declaration
     */
    private String getPackageDeclaration(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        if (!packageName.isEmpty()) {
            packageName = String.format("package %s;%n%n", packageName);
        }
        return packageName;
    }

    /**
     * Creates class declaration.
     * <p>
     * Class declaration is {@code public} and implements specified class type.
     * Class name is specified by {@link #getImplName}
     * Implemented class type is specified by {@link Class#getCanonicalName()}
     *
     * @param clazz class type token
     * @return class declaration
     */
    private String getClassDeclaration(Class<?> clazz) {
        return String.format("public class %s implements %s",
                getImplName(clazz), clazz.getCanonicalName());
    }

    /**
     * Creates an implementation of all abstract methods of specified {@link Class}.
     *
     * @see #getMethodImpl(Method)
     * @param token class type token for implementation
     * @return methods' implementation
     * @see #getMethodImpl
     */
    private String getMethodsImpl(Class<?> token) {
        return Arrays.stream(token.getMethods())
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .map(this::getMethodImpl)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Creates an implementation of specified {@link Method}.
     * <p>
     * Method implementation is public and returns default value specified by {@link #getMethodReturn}.
     * Method parameters are specified by {@link #getMethodParameters(Method)}
     * Method throws are specified by {@link #getMethodThrows(Method)}
     *
     * @param method abstract method for implementing
     * @return an implementation of given method
     */
    private String getMethodImpl(Method method) {
        return String.format("%n\tpublic %s %s(%s)%s {%n\t\t%s%n\t}",
                method.getReturnType().getCanonicalName(), method.getName(),
                getMethodParameters(method), getMethodThrows(method), getMethodReturn(method));
    }

    /**
     * Creates method parameters declaration.
     * <p>
     * If abstract method has parameters, they are copied.
     *
     * @param method abstract method token
     * @return method parameters
     */
    private String getMethodParameters(Method method) {
        return Arrays.stream(method.getParameters())
                .map(param -> String.format("%s %s", param.getType().getCanonicalName(), param.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates method throws declaration.
     * <p>
     * If abstract method has throws, they are copied.
     *
     * @param method abstract method token
     * @return method throws
     */
    private String getMethodThrows(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length == 0) {
            return "";
        }
        return " throws " + Arrays.stream(exceptionTypes)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates method return with default value.
     * <p>
     * If abstract method return type is not {@code void}, default value is returned.
     * Default value:
     * <ul>
     *     <li>{@code false}, if return type is {@code boolean}.</li>
     *     <li>{@code 0}, if return type is primitive and not {@code boolean}.</li>
     *     <li>{@code null}, in other cases, when return type is inherited from {@link Object}.</li>
     * </ul>
     *
     * @param method abstract method token
     * @return method return
     */
    private String getMethodReturn(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType == void.class) {
            return "";
        }
        String returnValue = "null";
        if (returnType == boolean.class) {
            returnValue = "false";
        } else if (returnType.isPrimitive()) {
            returnValue = "0";
        }
        return String.format("return %s;", returnValue);
    }

    /**
     * Get implicated class simple name.
     * <p>
     * Class name is generated from {@link Class#getSimpleName()} by adding {@code Impl} suffix.
     *
     * @param clazz parent class type token
     * @return Simple implication name
     */
    private String getImplName(Class<?> clazz) {
        return clazz.getSimpleName() + "Impl";
    }

}