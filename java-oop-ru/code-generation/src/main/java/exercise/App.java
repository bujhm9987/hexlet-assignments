package exercise;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;


// BEGIN
public class App {

    public static void save(Path filepath, Car car) throws IOException {
        Files.write(filepath, car.serialize().getBytes());
    }

    public static Car extract(Path filepath) throws IOException {
        String data = Files.readString(filepath);
        return Car.unserialize(data);
    }
}
// END
