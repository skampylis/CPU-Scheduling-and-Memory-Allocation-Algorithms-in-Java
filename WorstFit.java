import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {

    private int memorySlotSize;

    public WorstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    /**
     * This method extends the fitProcess method of the MemoryAllocationAlgorithm class
     * and implements the Worst Fit memory allocation algorithm.
     * First it loops through all the availableBlockSizes and stops once a block with enough memory space is found.
     * It creates a temporary block start and end address. Then it takes all the memorySlots in that current block
     * and arranges them in order of their start address within an arraylist.Then it calculates the largest possible memorySlot in the
     * arraylist. The process is then inserted in that address if it meets the requirements.
     * @param p The process that the algorithm is called upon to try and fit into the System memory. It is mainly used
     *          to grab its memoryRequirements
     * @param currentlyUsedMemorySlots An arraylist of all the memory slots that already exist within the memory and are
     *                                 taking up space.
     * @return The start address of the memory slot that correlates to the process we were given. If fitting the process
     * in the memory proves unsuccessful we return an int value of -1.
     */
    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        memorySlotSize = -1;
        slotEnd = slotStart = blockStart = blockEnd = 0;
        int tmpBlockStart, tmpBlockEnd;

        for (int blockIndex = 0; blockIndex < availableBlockSizes.length; blockIndex++) {
            if (availableBlockSizes[blockIndex] < p.getMemoryRequirements()) continue;
            //Find where the Block starts
            tmpBlockStart = calcBlockStart(blockIndex);
            tmpBlockEnd = tmpBlockStart + availableBlockSizes[blockIndex] - 1;
            ArrayList<MemorySlot> memorySlotsInCurrentBlock = findMemorySlotsInCurrentBlock(currentlyUsedMemorySlots, tmpBlockStart);
            arrangeMemorySlotsInSlotStartOrder(memorySlotsInCurrentBlock);
            calcLargestMemorySlotParamsInCurrentBlock(memorySlotsInCurrentBlock, tmpBlockStart, tmpBlockEnd);
        }

        slotEnd = slotStart + p.getMemoryRequirements() - 1;
        try {
            address = createMemorySlot(currentlyUsedMemorySlots);
        } catch (RuntimeException ignored) { }
        return address;
    }

    private void calcLargestMemorySlotParamsInCurrentBlock(ArrayList<MemorySlot> memorySlotsInCurrentBlock, int tmpBlockStart, int tmpBlockEnd) {

        if (memorySlotsInCurrentBlock.isEmpty()) {
            if (memorySlotSize < tmpBlockEnd - tmpBlockStart) {
                slotStart = blockStart = tmpBlockStart;
                blockEnd = tmpBlockEnd;
                memorySlotSize = blockEnd - blockStart;
            }
            return;
        }

        int tmpSlotStart = tmpBlockStart;
        MemorySlot m;
        for (int i = 0; i < memorySlotsInCurrentBlock.size(); i++) {
            m = memorySlotsInCurrentBlock.get(i);
            if (!(tmpSlotStart == m.getStart())) {
                int end = m.getStart() - 1;
                compareCurrentMemorySlotSize(tmpSlotStart, end, tmpBlockStart, tmpBlockEnd);
            }
            tmpSlotStart = m.getEnd() + 1;
        }
        m = memorySlotsInCurrentBlock.get(memorySlotsInCurrentBlock.size() - 1);
        compareCurrentMemorySlotSize(m.getEnd() + 1, tmpBlockEnd, tmpBlockStart, tmpBlockEnd);
    }

    private void compareCurrentMemorySlotSize(int start, int end, int tmpBlockStart, int tmpBlockEnd) {
        int tmpMemorySlotSize = end - start;
        if (tmpMemorySlotSize > memorySlotSize) {
            memorySlotSize = tmpMemorySlotSize;
            slotStart = start;
            blockStart = tmpBlockStart;
            blockEnd = tmpBlockEnd;
        }
    }

}
