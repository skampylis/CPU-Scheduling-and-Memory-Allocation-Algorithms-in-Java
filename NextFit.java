import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {

    private int previousAddress = 0;

    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    /**
     * This method extends the fitProcess method of the MemoryAllocationAlgorithm class
     * and implements the Next Fit memory allocation algorithm. The method is called to insert a process into the
     * memory of available memory blocks and return the  address of the created memory slot that it occupies. This
     * algorithm functions similarly to the FirstFit algorithm but instead of starting from the top of the array we start
     * from the index of the previous fitting process.
     * First it loops through all the availableBlockSizes and stops once a block with enough memory space is found.
     * It calculates the theoretical addresses of the start and the end of the memory block and then calculates
     * the theoretical address of the start of the memory slot. Using those it will attempt to create a new memory slot.
     * If successful the method will return the start address of the memory slot.
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

        slotStart = slotEnd = blockStart = blockEnd = 0;
        //We will start iterating from the previous address
        int blockIndex = calcCurrentBlock();
        /*Because we start from a different index than the start of the array
        When iterationCount becomes equal to the number of availableBlockSizes.length it means we have
        cycled through every existing block
         */
        int iterationCount = 0;
        while (iterationCount < availableBlockSizes.length) {

            if (availableBlockSizes[blockIndex] < p.getMemoryRequirements()) {
                iterationCount++;
                continue;
            }
            //Find where the Block starts
            blockStart = calcBlockStart(blockIndex);
            blockEnd = blockStart + availableBlockSizes[blockIndex] - 1;

            ArrayList<MemorySlot> memorySlotsInCurrentBlock = findMemorySlotsInCurrentBlock(currentlyUsedMemorySlots, blockStart);
            arrangeMemorySlotsInSlotStartOrder(memorySlotsInCurrentBlock);
            calcSlotStartEnd(p, memorySlotsInCurrentBlock);

            try {
                address = createMemorySlot(currentlyUsedMemorySlots);
                previousAddress = address;
                break;
            } catch (RuntimeException ignored) { }

            iterationCount++;
            blockIndex = (blockIndex + 1) % availableBlockSizes.length;
        }
        return address;
    }

    /**
     * This method calculates the possible addresses of the start and end of a memory slot given its memory block
     * and the currently existing slots. If the currentlyUsedMemorySlots is empty it immediately returns the blockStart
     * as the slotStart. Otherwise it will loop through the existing slots in this memory block and try to find a
     * slot start and end that doesn't overlap with the existing slots.
     * @param p The process that the algorithm is called upon to try and fit into the System memory. It is mainly used
     *          to grab its memoryRequirements
     * @param currentlyUsedMemorySlots An arraylist of all the memory slots that already exist within the memory and are
     *                                 taking up space.
     */
    private void calcSlotStartEnd(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        slotStart = blockStart;
        slotEnd = slotStart + p.getMemoryRequirements() - 1;

        if (currentlyUsedMemorySlots.isEmpty()) return;

        boolean slotStartOrEndOverlaps = true;
        // loop till we find a non-overlapping slotStart and slotEnd
        while (slotStartOrEndOverlaps) {
            //Iterate through currently used slots
            for (MemorySlot m : currentlyUsedMemorySlots) {

                //If our slot overlaps with an existing one, put the start of our slot after the end of the existing one
                slotStartOrEndOverlaps = (m.getStart() >= slotStart && m.getStart() <= slotEnd) ||
                        (m.getEnd() >= slotStart && m.getEnd() <= slotEnd);
                if (slotStartOrEndOverlaps) {
                    slotStart = m.getEnd() + 1;
                    slotEnd = slotStart + p.getMemoryRequirements() - 1;
                    break;
                }
            }
        }
    }

    private int calcCurrentBlock(){
        int blockEnd = 0;
        int blockIndex = -1;
        for (int i = 0; i < availableBlockSizes.length; i++){
            blockEnd += availableBlockSizes[i];
            if (previousAddress < blockEnd){
                blockIndex = i;
                break;
            }
        }
        return blockIndex;
    }

}
