package kth.ID2212.ac.common.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * System process information
 */
public class Process implements Serializable {
    /**
     * Executable of process
     */
    public String path;
    /**
     * Titles of windows created by process.
     */
    public Set<String> windows;

    public Process(String path) {
        this.path = path;
        windows = new HashSet<>();
    }

    public String getPath() {
        return path;
    }

    public Set<String> getWindows() {
        return windows;
    }

    public void addTitle(String title) {
        windows.add(title);
    }
}
