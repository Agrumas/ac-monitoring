package kth.ID2212.ac.client;

import com.sun.deploy.util.StringUtils;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import kth.ID2212.ac.common.entities.Process;
import kth.ID2212.ac.common.entities.ProcessList;

import java.util.List;

/**
 * Class is responsible to get process list and titles of opened windows.
 */
public class ListProcesses {
    public static void main(String[] args) {

        ListProcesses processes = new ListProcesses();

        processes.get().forEach((s, strings) -> {
            System.out.println(s + "[" + StringUtils.join(strings.getWindows(), ", ") + "]");
        });
    }

    /**
     * Gathers process list using Java Native Access (JNA)
     * Some system windows are created without visible process, so path is not available.
     * @return process list
     */
    public ProcessList get() {
        ProcessList list = new ProcessList();

        final List<DesktopWindow> allWindows = WindowUtils.getAllWindows(false);
        for (final DesktopWindow dw : allWindows) {
            String title = dw.getTitle();
            String path = dw.getFilePath();

            Process process = list.insert(path);

            if (title.length() > 0) {
                process.addTitle(title);
            }
        }
        return list;
    }
}
