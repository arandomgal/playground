package andrews.ying.process;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A simple example of calling a process from Java using ProcessBuilder
 */
public class ProcessByProcessBuilder {
    public static void main(String[] args) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        process.waitFor(60L, TimeUnit.SECONDS);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }
}
