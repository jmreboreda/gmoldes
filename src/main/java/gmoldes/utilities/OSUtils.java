package gmoldes.utilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;


public class OSUtils {

    enum OperatingSystem {
        WINDOWS,
        UNIX,
        MAC,
        SOLARIS,
        UNKNOWN
    }

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static final Map<String, OperatingSystem> osmap = new HashMap<>();
    static {
        osmap.put("win", OperatingSystem.WINDOWS);
        osmap.put("nix", OperatingSystem.UNIX);
        osmap.put("nux", OperatingSystem.UNIX);
        osmap.put("aix", OperatingSystem.UNIX);
        osmap.put("mac", OperatingSystem.MAC);
        osmap.put("sunos", OperatingSystem.SOLARIS);
    }

    private static OperatingSystem currentOS() {
        return osmap.entrySet().stream()
                .filter(e -> OS.contains(e.getKey()))
                .map(Entry::getValue)
                .findFirst()
                .orElse(OperatingSystem.UNKNOWN);
    }

    public static class TemporalFolderUtils {

        private static final Map<OperatingSystem,String> tempDirMap = new HashMap<>();
        static {
            tempDirMap.put(OperatingSystem.UNIX, "/Temp/Borrame");
            tempDirMap.put(OperatingSystem.WINDOWS, "/AppData/Local/Temp/Borrame");
        }

        public static Optional<Path> tempFolder() {
            final OperatingSystem os = OSUtils.currentOS();
            if(!tempDirMap.containsKey(os)) {
                return Optional.empty();
            }
            final Path path = Paths.get(tempDirMap.get(os));
            return Optional.of(path);
        }
    }

    public static String getOSName(){
        return OS;
    }
}