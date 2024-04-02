package andrews.ying.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * A simple example of calling a process from Java using Runtime
 */
public class ProcessByRuntime {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        String trustedAppsDir = "C:\\TrustedApps";
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        Process process;
        if (isWindows) {
            process = Runtime.getRuntime()
                    .exec(String.format("cmd.exe /c dir %s | findstr \"GXP\"", trustedAppsDir));
        } else {
            process = Runtime.getRuntime()
                    .exec(String.format("/bin/sh -c ls %s | grep \"GXP\"", trustedAppsDir));
        }

        new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
                .forEach(System.out::println);
        int exitCode = process.waitFor();
        System.out.println("exitCode=" + exitCode);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }
}
