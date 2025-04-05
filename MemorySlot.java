
public class MemorySlot {

    private int start; // the address where the memory slot starts
    private int end;   // the address where the memory slot ends
    private final int blockStart; // address of where the block starts
    private final int blockEnd;   // address of where the block ends
    /* The following should always hold true:
     * start >= blockStart
     * end <= blockEnd */
    
    public MemorySlot(int start, int end, int blockStart, int blockEnd) {
        if ((start < blockStart) || (end > blockEnd)) {
            throw new java.lang.RuntimeException("Memory access out of bounds.");
        }
        this.start = start;
        this.end = end;
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }

    public int getBlockStart() {
        return blockStart;
    }

    public int getBlockEnd() {
        return blockEnd;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;

        if (!(o instanceof MemorySlot)) return false;

        if (((MemorySlot) o).blockStart == blockStart &&
            ((MemorySlot) o).blockEnd == blockEnd &&
                ((MemorySlot) o).start == start &&
                ((MemorySlot) o).end == end
        ) return true;

        return false;
    }

}
