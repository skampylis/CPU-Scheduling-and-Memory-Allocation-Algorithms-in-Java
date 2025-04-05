
public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;

    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */
        currentProcess = -1; // initially no current process is running
        /*
         * The CPU will keep running until the processes are finished or can't be loaded
         */
        while (!existingProcessesFinishedOrCantBeLoaded()) {
            tick();
        }

    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        /*
         * Checks if a new process is to be loaded. If such is the case the process is loaded and the clock is
         * incremented by invoking the processNewToReady method, thus the CPU can't anything else.
         */
        if (isNewProcessToBeLoaded()) return;

        /*
         * If we don't have anything to load then we fetch the next process from the scheduler.
         */
        Process p = scheduler.getNextProcess();

        /*
         * The scheduler can return null if there are no processes added. The clock is incremented
         */
        if (p == null) {
            clock++;
            return;
        }

        /*
         * currentProcess hold the index id of the current process. If its -1 then no process was running before
         */
        if (currentProcess == -1)
            processReadyToRunning(p); // case no process running before. Clock is incremented by 2
        else if (currentProcess == findProcessIndex(p))
            processRunning(p); // case same process running. Clock is incremented
        else
            processChanged(p); // case process running before swaps with another. Clock is incremented by 2

    }

    private boolean isNewProcessToBeLoaded() {
        boolean processAdded = false;
        for (Process p : processes) {
            if (isProcessToBeAdded(p))
                processAdded = addProcessIfItFits(p);
            if (processAdded) return true;
        }
        return false;
    }

    private boolean addProcessIfItFits(Process p) {
        if (mmu.loadProcessIntoRAM(p)) {
            scheduler.addProcess(p);
            processNewToReady(p);
            return true;
        }
        return false;
    }

    private boolean isProcessToBeAdded(Process p) {
        return p.getArrivalTime() <= clock && p.getPCB().getState() == ProcessState.NEW;
    }

    private boolean existingProcessesFinishedOrCantBeLoaded() {
        for (Process p : processes) {
            if (processNotArrivedOrLoaded(p)) return false;
        }
        return true;
    }

    private boolean processNotArrivedOrLoaded(Process p) {
        return p.getArrivalTime() > clock || p.getPCB().getState() == ProcessState.READY || p.getPCB().getState() == ProcessState.RUNNING;
    }

    private int findProcessIndex(Process process) {
        for (int i = 0; i < processes.length; i++) {
            if (processes[i] == process) return i;
        }
        return -1;
    }

    private void processChanged(Process p) {
        int previousRunningProcess = currentProcess;
        currentProcess = findProcessIndex(p);
        processes[previousRunningProcess].waitInBackground();
        p.getPCB().setState(ProcessState.RUNNING, clock);
        clock += 2;
    }

    private void processNewToReady(Process p) {
        p.getPCB().setState(ProcessState.READY, clock);
        clock++;
    }

    private void processReadyToRunning(Process p) {
        p.getPCB().setState(ProcessState.RUNNING, clock);
        currentProcess = findProcessIndex(p);
        clock += 2;
    }

    private void processRunning(Process p) {
        p.run();
        clock++;
        if (p.getBurstTime() == 0)
            processFinished(p); // case process is finished. The clock is not incremented
    }

    private void processFinished(Process p) {
        p.getPCB().setState(ProcessState.TERMINATED, clock);
        mmu.loadProcessIntoRAM(p);
        scheduler.removeProcess(p);
        currentProcess = -1;
    }

}
