package info.kgeorgiy.ja.popov.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Walk {

    private static final int BYTE_ARRAY_SIZE = 1024;
    private static final int BITS_SIZE = 64;
    private static final long LAST_BYTE = 0xff00000000000000L;
    private static final long FIRST_BYTE = 0xffL;
    private static final long INITIAL_HASH = 0;
    private static final long ERROR_HASH = 0;

    private static long getPjwHash(long h, byte[] b, int n) {
        long high;
        for (int i = 0; i < n; ++i) {
            h = (h << (BITS_SIZE / 8)) + (b[i] & FIRST_BYTE);
            if ((high = h & LAST_BYTE) != 0) {
                h = h ^ (high >> (BITS_SIZE * 3 / 4));
                h = h & ~high;
            }
        }
        return h;
    }

    private static long getFileHash(String filename) {
        long hash = INITIAL_HASH;
        try (InputStream is = new FileInputStream(filename)) {
            byte[] b = new byte[BYTE_ARRAY_SIZE];
            int n;
            while ((n = is.read(b)) >= 0) {
                hash = getPjwHash(hash, b, n);
            }
        } catch (IOException | SecurityException e) {
            hash = ERROR_HASH;
        }
        return hash;
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Please enter two valid arguments");
            return;
        }

        Path inputFilePath, outputFilePath;
        try {
            inputFilePath = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            System.out.println("Invalid input file name");
            return;
        }
        try {
            outputFilePath = Paths.get(args[1]);
            Path outputFileParentPath = outputFilePath.getParent();
            if (outputFileParentPath != null) {
                Files.createDirectories(outputFileParentPath);
            }
        } catch (InvalidPathException e) {
            System.out.println("Invalid output file name");
            return;
        } catch (IOException e) {
            System.out.println("I/O error while creating directories");
            return;
        } catch (SecurityException e) {
            System.out.println("Security error while creating directories");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(inputFilePath, StandardCharsets.UTF_8)) {
            try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8)) {
                String filename;
                while ((filename = br.readLine()) != null) {
                    long hash = getFileHash(filename);
                    bw.write(String.format("%016x %s\n", hash, filename));
                }
            } catch (IOException e) {
                System.out.println("I/O error while opening output file");
            } catch (SecurityException e) {
                System.out.println("Security error while opening output file");
            }
        } catch (IOException e) {
            System.out.println("I/O error while opening input file");
        } catch (SecurityException e) {
            System.out.println("Security error while opening input file");
        }
    }
}