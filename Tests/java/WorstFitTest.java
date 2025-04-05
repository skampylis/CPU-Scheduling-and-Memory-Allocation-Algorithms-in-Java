import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorstFitTest {

    WorstFit worstFit;
    int[] availableBlockSizes = {15, 40, 10, 20};

    ArrayList<MemorySlot> memorySlotsTestSet(){
        ArrayList<MemorySlot> currentlyUsedMemorySlots = new ArrayList<>(){
            {
                // block 3
                add(new MemorySlot(55,59,55,64));

                // block 1
                add(new MemorySlot(4,10,0,14));

                // block 4
                add(new MemorySlot(65,70,65,84));
                add(new MemorySlot(71,75,65,84));
                add(new MemorySlot(80,84,65,84));

                // block 2
                add(new MemorySlot(15,20,15,54));
                add(new MemorySlot(21,35,15,54));
            }
        };

        return currentlyUsedMemorySlots;
    }

    @BeforeEach
    void setUp(){
        worstFit = new WorstFit(availableBlockSizes);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process fits and the memory slots are empty, returns correct address and creates correct MemorySlot")
    void testCaseEmptyMemorySlots(){
        Process p = new Process(0, 0, 20);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = new ArrayList<>();

        int actualAddress = worstFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 15;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 34, 15, 54);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(0);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process fits, returns correct address and creates correct MemorySlot")
    void testCaseItFits(){
        Process p = new Process(0, 0, 1);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();

        int actualAddress = worstFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 36;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 36, 15, 54);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-1);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process does not fits, returns address -1 and the array does not change")
    void testCaseItNotFits(){
        Process p = new Process(0, 0, 70);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();


        int actualAddress = worstFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = -1;

        ArrayList<MemorySlot> expectedMemorySlots = memorySlotsTestSet();

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlots.size(), currentlyUsedMemorySlots.size());
        for (int i = 0; i < expectedMemorySlots.size(); i++){
            assertEquals(expectedMemorySlots.get(i), currentlyUsedMemorySlots.get(i));
        }
    }

}