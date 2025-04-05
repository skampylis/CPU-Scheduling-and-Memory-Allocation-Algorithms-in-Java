import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {

    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    /**
     * This method extends the fitProcess method of the MemoryAllocationAlgorithm class
     * and implements the First Fit memory allocation algorithm. The method is called to insert a process into the
     * memory of available memory blocks and return the  address of the created memory slot that it occupies.
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

        //Look through all the blocks but assume that they are empty first
        for (int blockIndex = 0; blockIndex < availableBlockSizes.length; blockIndex++) {
            if (availableBlockSizes[blockIndex] < p.getMemoryRequirements()) continue;
            //Find where the Block starts
            blockStart = calcBlockStart(blockIndex);
            blockEnd = blockStart + availableBlockSizes[blockIndex] - 1;

            calcSlotStartEnd(p, currentlyUsedMemorySlots);

            try {
                address = createMemorySlot(currentlyUsedMemorySlots);
                break;
            } catch (Exception ignored) { }
            //If an exception is encountered we just keep looking for a spot to fit the process
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
                //Skip if the slot we took from the array is not in this block
                if (blockStart != m.getBlockStart()) {
                    slotStartOrEndOverlaps = false;
                    continue;
                }
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

}
