package Task1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
public class FileTest {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("src/test/java/Task1/testDirectory");
        String extension = ".txt";
        List<String> result = new ArrayList<>();
        //поиск
        List<Path> files = Files.list(dir).filter(data -> data.getFileName().toString().contains(extension)).collect(Collectors.toList());
        if (files.size() != 0) {
            //последний созданный файл
            Path lastCreated = files.stream().max((f1, f2) -> Long.compare(f1.toFile().lastModified(), f2.toFile().lastModified())).get();
            Long lastCreatedSeconds = Files.readAttributes(lastCreated, BasicFileAttributes.class).creationTime().to(TimeUnit.SECONDS);
            result.add(lastCreated.toFile().getName());
            files.remove(lastCreated);
            //Проверяю файл созданные не более 10 сек назад
            for (Path file : files) {
                try {

                    Long fileCreationSeconds = Files.readAttributes(file, BasicFileAttributes.class).creationTime().to(TimeUnit.SECONDS);
                    if (lastCreatedSeconds - fileCreationSeconds <= 10) {
                        result.add(file.getFileName().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //
        System.out.println(String.join(", ", result));
    }
}


