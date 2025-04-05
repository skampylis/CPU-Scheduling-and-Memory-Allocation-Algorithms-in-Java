
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    private int address = -1;

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }

    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        burstTime--;
    }

    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        pcb.setState(ProcessState.READY, CPU.clock);

    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (pcb.getStartTimes().isEmpty() ||
                pcb.getStopTimes().isEmpty() ||
                pcb.getStopTimes().size() != pcb.getStartTimes().size())
            return -1;
        return recursiveCalcWaitingTime(0);
    }

    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (pcb.getStartTimes().isEmpty()) return -1;
        int firstRunningTime = pcb.getStartTimes().get(0);
        return firstRunningTime - arrivalTime;
    }

    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (pcb.getStartTimes().isEmpty() || pcb.getStopTimes().isEmpty()) return -1;
        int firstRunningTime = pcb.getStartTimes().get(0);
        int terminatedTime = pcb.getStopTimes().get(pcb.getStopTimes().size() - 1);
        return terminatedTime - firstRunningTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    private double recursiveCalcWaitingTime(int i) {
        if (i + 1 >= pcb.getStartTimes().size()) return 0;
        int stopWindow = pcb.getStartTimes().get(i + 1) - pcb.getStopTimes().get(i);
        return stopWindow + recursiveCalcWaitingTime(i + 1);
    }

    public int getMemoryRequirements() {
        return memoryRequirements;
    }
    public int getAddress() { return address; }

    public void setAddress(int address) { this.address = address; }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;

        if (!(o instanceof Process)) return false;

        if (((Process) o).arrivalTime == arrivalTime &&
            ((Process) o).address == address &&
                ((Process) o).pcb == pcb &&
                ((Process) o).memoryRequirements == memoryRequirements
        ) return true;

        return false;
    }
}
