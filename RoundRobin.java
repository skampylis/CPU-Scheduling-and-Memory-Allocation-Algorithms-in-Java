
public class RoundRobin extends Scheduler {

    private int quantum;
    private int timestamp;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
        this.timestamp = -1;
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        /* Add the given process (p) to the end of the Arraylist */
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        //Initiate timestamp variable only the first time that the method getNextProcess is accessed. If the Arraylist
        //is Empty: return null, else return the first process of the Arraylist
        if (timestamp == -1) {
            timestamp = quantum;
        }
        if (processes.isEmpty()) {
            return null;
        } else {
            if (processes.get(0).getPCB().getState() == ProcessState.RUNNING) {
                if (timestamp == 0) {
                    processes.add(processes.get(0));
                    processes.remove(0);
                    timestamp = quantum;
                } else {
                    timestamp--;
                }
            }
            return processes.get(0);
        }
    }
}
