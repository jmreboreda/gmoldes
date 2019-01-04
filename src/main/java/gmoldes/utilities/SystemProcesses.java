package gmoldes.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemProcesses {

    public static Boolean isRunningInLinuxAndContains(String initialFileNameContent, String finalFileNameContent) {

        String line;
        try {

            Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "ps -aux | grep ".concat(initialFileNameContent)});

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if (line.contains(initialFileNameContent) && line.contains(finalFileNameContent)) {
                    return true;
                }
            }
        } catch (Exception err) {
            System.out.println(err);
        }

        return false;
    }

    public static Boolean isRunningInWindowsAndContains(String initialFileNameContent, String finalFileNameContent) {

        try {
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line); //<-- Parse data here.
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return Boolean.TRUE;
    }
}
