import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
    }

    /**
     * This method simulates a MemoryAllocationUnit. It is called when a process must be inserted or removed from the
     * memory. If a process is marked to be terminated the method removes its memory slot from the memory. This is done
     * by removing it from the currentlyUsedMemorySlots arraylist.
     * Otherwise if a process is to be inserted we call upon the fitProcess method of the used MemoryAllocationAlgorithm
     * and return a boolean value depending on if we managed to fit the process in the memory.
     * @param p Process the method is called to either insert or remove from the memory
     * @return A boolean value depending on if we managed to fit the process in the memory. Where removing a process the
     * result is always false.
     */
    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */
        /*Homer: Trying a different approach. We will reanalyze the MemoryAllocationAlgorithms to handle currentlyUsedMemorySlots internally
         */

        if (p.getPCB().getState() == ProcessState.TERMINATED){
            for (MemorySlot m: currentlyUsedMemorySlots){
                if (m.getStart() == p.getAddress()){
                    currentlyUsedMemorySlots.remove(m);
                    break;
                }
            }
        } else{
            int address = algorithm.fitProcess(p,currentlyUsedMemorySlots);
            if(address != -1){
                p.setAddress(address);
                fit = true;
            }
        }



        return fit;
    }
}
