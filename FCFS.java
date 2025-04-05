
public class FCFS extends Scheduler {

    public FCFS() {
        /* TODO: you _may_ need to add some code here */
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        /* Add the given process (p) to the end of the Arraylist */
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        /* If the Arraylist is Empty: return null, else return the first process of the Arraylist */
        if (processes.isEmpty()) {
            return null;
        } else {
            return processes.get(0);
        }
    }
}
