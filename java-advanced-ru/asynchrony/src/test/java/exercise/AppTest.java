package exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;

class AppTest {
    private String destPath;

    private static Path getFullPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        destPath = Files.createTempFile("test", "tmp").toString();
    }

    @Test
    void testUnion() throws Exception {
        CompletableFuture<String> result = App.unionFiles(
            "src/test/resources/file1.txt",
            "src/test/resources/file2.txt",
            destPath
        );
        result.get();

        String actual = Files.readString(getFullPath(destPath));
        assertThat(actual).contains("Test", "Message");
    }

    @Test
    void testUnionWithNonExistedFile() throws Exception {

        String result = tapSystemOut(() -> {
            App.unionFiles("nonExistingFile", "file", destPath).get();
        });

        assertThat(result.trim()).contains("NoSuchFileException");
    }

    // BEGIN
    @Test
    void testGetDirectorySize() throws Exception {
        String dir = "src/test/resources/dir";
        CompletableFuture<Long> result = App.getDirectorySize(dir);


        AtomicReference<Long> actual = new AtomicReference<>(0L);
        Files.list(Path.of(dir)).forEach(path -> actual.updateAndGet(v -> {
            try {
                return v + Files.size(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        assertThat(result.get().equals(actual.get()));
    }
    // END
}
