import java.util.ArrayList;

public abstract class MemoryAllocationAlgorithm {

    protected final int[] availableBlockSizes;

    protected int slotStart, slotEnd, blockStart, blockEnd;

    public MemoryAllocationAlgorithm(int[] availableBlockSizes) {
        this.availableBlockSizes = availableBlockSizes;
    }

    public abstract int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots);

    /** Method that returns the theoretical address, the start, of a memory Block from the availableBlockSizes table.
     * The method takes as input the index of a memory Block on the availableBlockSizes table and calculates its address
     * by adding up the sums of all the previous memory blocks on the table.
     * @param blockIndex The index of the block ,that we want to find the address of, on the availableBlockSizes table
     * @return An int number indicating the theoretical address of the block
     */
    protected int calcBlockStart(int blockIndex) {
        int blockStart = 0;
        for (int i = 0; i < blockIndex; i++) {
            blockStart += availableBlockSizes[i];
        }
        return blockStart;
    }

    /**
     * Create a new MemorySlot object and add it on the currentlyUsedMemorySlots arraylist. In case of
     * an exception coming from the MemorySlot constructor, the method will throw an exception as well.
     * @param currentlyUsedMemorySlots An arraylist with all the MemorySlots currently taking up space in our
     *                                 theoretical memory system
     * @return  The theoretical address of the Memory Slot we have added to the arraylist
     * @throws RuntimeException If slotStart < blockStart or slotEnd > blockEnd the MemorySlot constructor will throw an
     * exception. The method will throw it to the memory allocation algorithm
     */
    protected int createMemorySlot(ArrayList<MemorySlot> currentlyUsedMemorySlots) throws RuntimeException{
        MemorySlot newMemorySlot = new MemorySlot(slotStart, slotEnd, blockStart, blockEnd);
        currentlyUsedMemorySlots.add(newMemorySlot);
        return slotStart;
    }

    /**
     *This methods creates an arraylist that contains all the memory Slots in the desired memory block.
     * Given as input the start of the address Memory Block and an arraylist of all the currently Used Memory Slots,
     * the method starts by creating an empty arraylist. It searches through every MemorySlot object in the
     * currentlyUsedMemorySlots arraylist and if a memory slot has the same Block address as the one given as input then
     * the object is copied in the new arraylist. The method returns the arraylist with all the memorySlots in that particular block.
     * @param currentlyUsedMemorySlots An arraylist with all the MemorySlots currently taking up space in our
     *                                 theoretical memory system
     * @param tmpBlockStart The theoretical address of the Memory Block whose contained Memory Slots we want to find
     * @return An arraylist of all the Memory Slots contained in the given Memory Block
     */
    protected ArrayList<MemorySlot> findMemorySlotsInCurrentBlock(ArrayList<MemorySlot> currentlyUsedMemorySlots, int tmpBlockStart) {
        ArrayList<MemorySlot> memorySlotsInCurrentBlock = new ArrayList<>();

        for (MemorySlot m : currentlyUsedMemorySlots) {
            if (m.getBlockStart() == tmpBlockStart) {
                MemorySlot copyMemorySlot = new MemorySlot(m.getStart(), m.getEnd(), m.getBlockStart(), m.getBlockEnd());
                memorySlotsInCurrentBlock.add(copyMemorySlot);
            }
        }

        return memorySlotsInCurrentBlock;
    }

    /**
     * This method has an input of an arraylist of MemorySlots. It then sorts the arraylist in an ascending order
     * based on the start address of the Memory Slot. It uses a Bubblesort algorithm that utilizes the swapValuesFromArrayList
     * method.
     * @param memorySlots An arraylist of MemorySlot objects to be rearranged in an ascending order.
     */
    protected void arrangeMemorySlotsInSlotStartOrder(ArrayList<MemorySlot> memorySlots) {
        for (int i = 0; i < memorySlots.size() - 1; i++) {
            for (int j = i; j < memorySlots.size(); j++) {
                if (memorySlots.get(i).getStart() > memorySlots.get(j).getStart()) {
                    swapValuesFromArrayList(memorySlots, i, j);
                }
            }
        }
    }

    protected void swapValuesFromArrayList(ArrayList<MemorySlot> memorySlotsInCurrentBlock, int i, int j) {
        MemorySlot tmpMemorySlot;
        tmpMemorySlot = memorySlotsInCurrentBlock.get(i);
        memorySlotsInCurrentBlock.remove(j);
        memorySlotsInCurrentBlock.add(j, tmpMemorySlot);
        tmpMemorySlot = memorySlotsInCurrentBlock.get(j);
        memorySlotsInCurrentBlock.remove(i);
        memorySlotsInCurrentBlock.add(i, tmpMemorySlot);
    }
}
