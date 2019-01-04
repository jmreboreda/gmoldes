package gmoldes.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

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

        initialFileNameContent = initialFileNameContent.replaceAll("[^\\dA-Za-z]", "");
        finalFileNameContent = finalFileNameContent.replaceAll("[^\\dA-Za-z]", "");

        System.out.println("initialFileNameContent: " + initialFileNameContent);
        System.out.println("finalFileNameContent: " + finalFileNameContent);

        String line;
        try {
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe /V");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(),  "Cp1252"));
            while ((line = input.readLine()) != null) {
                line = line.replaceAll("[^\\dA-Za-z]", "");
                System.out.println(line);
                if (line.contains(initialFileNameContent) && line.contains(finalFileNameContent)) {
                    return true;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return false;
    }
}
