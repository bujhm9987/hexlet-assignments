package exercise;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

class App {

    // BEGIN
    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = App.unionFiles("src/main/resources/file1.txt",
                "src/main/resources/file2.txt",
                "src/main/resources/file3.txt");
        System.out.println(result.get());

        CompletableFuture<Long> size = App.getDirectorySize("src/main/resources");
        System.out.println(size.get());
        // END
    }

    private static Path getPath(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }

    private static Long getSizeFiles(File path) {
        long size = 0L;
        for (File file: Objects.requireNonNull(path.listFiles())) {
            if (file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static CompletableFuture<Long> getDirectorySize(String dir) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return getSizeFiles(new File(dir));
        });
    }

    public static CompletableFuture<String> unionFiles(String pathIn1, String pathIn2, String pathOut) throws Exception {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            String fileData1 = "";
            try {
                fileData1 = Files.readString(getPath(pathIn1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return fileData1;
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            String fileData2 = "";
            try {
                fileData2 = Files.readString(getPath(pathIn2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return fileData2;
        });

        CompletableFuture<String> futureDensity = future1.thenCombine(future2, (string1, string2) -> {

            String result = string1 + string2;
            try {
                Files.write(getPath(pathOut), result.getBytes(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result;

        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return ex.getMessage();
        });

        return futureDensity;
    }
    // END
}

