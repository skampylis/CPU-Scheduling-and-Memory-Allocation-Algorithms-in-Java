
public class SRTF extends Scheduler {

    public SRTF() {
        /* TODO: you _may_ need to add some code here */
        super();
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (processes.isEmpty()) return null;
        Process process = processes.get(0);
        for (Process p : processes) {
            if (p.getBurstTime() < process.getBurstTime())
                process = p;
        }
        return process;
    }
}
