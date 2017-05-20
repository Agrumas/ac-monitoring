package kth.ID2212.ac.common.entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class representing the list of system processes, it is implemented inheriting HashMap.
 */
public class ProcessList extends HashMap<String, Process> implements Serializable {

    /**
     * Inserts process if needed ands returns it.
     * @param path path of executable
     * @return process object
     */
    public Process insert(String path) {
        if (path.length() == 0) {
            path = "<Unknown>";
        }
        Process process = get(path);
        if (process == null) {
            process = new Process(path);
            put(path, process);
        }
        return process;
    }
}
