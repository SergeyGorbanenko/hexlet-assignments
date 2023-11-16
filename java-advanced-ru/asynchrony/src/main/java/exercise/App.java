package exercise;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String source1, String source2, String target) {

        CompletableFuture<String> futureReadFile1 = CompletableFuture.supplyAsync(() -> {
            String res;
            try {
                res = Files.readString(Path.of(source1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return res;
        });

        CompletableFuture<String> futureReadFile2 = CompletableFuture.supplyAsync(() -> {
            String res;
            try {
                res = Files.readString(Path.of(source2));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return res;
        });

        CompletableFuture<String> futureWriteFile = futureReadFile1.thenCombine(futureReadFile2, (readFile1, readFile2) -> {
            String res = readFile1 + readFile2;
            try {
                Files.writeString(Path.of(target), res);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return res;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        return futureWriteFile;
    }

    public static CompletableFuture<Long> getDirectorySize(String dir) {

        CompletableFuture<Long> futureTotalSize = CompletableFuture.supplyAsync(() -> {
            long totalSize = 0L;
            try {
                for (Path path : Files.newDirectoryStream(Path.of(dir))) {
                    //totalSize = totalSize + Files.size(path);
                    CompletableFuture<Long> futureSize = CompletableFuture.supplyAsync(() -> {
                            try {
                                return Files.size(path);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        try {
                            totalSize = totalSize + futureSize.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return totalSize;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        return futureTotalSize;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        String result = App.unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt", "src/main/resources/file3.txt")
           .get();

        System.out.println(App.getDirectorySize("src/main/resources/").get());
        // END
    }
}
