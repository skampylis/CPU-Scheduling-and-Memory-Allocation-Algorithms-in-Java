import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NextFitTest {

    NextFit nextFit;
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
        nextFit = new NextFit(availableBlockSizes);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process fits and the memory slots are empty, returns correct address and creates correct MemorySlot")
    void testCaseEmptyMemorySlots(){
        Process p = new Process(0, 0, 15);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = new ArrayList<>();

        int actualAddress = nextFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 0;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 14, 0, 14);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(0);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process fits, returns correct address and creates correct MemorySlot")
    void testCaseItFits(){
        Process p = new Process(0, 0, 15);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();

        int actualAddress = nextFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 36;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 50, 15, 54);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-1);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process does not fits, returns address -1 and the array does not change")
    void testCaseItNotFits(){
        Process p = new Process(0, 0, 21);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();


        int actualAddress = nextFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = -1;

        ArrayList<MemorySlot> expectedMemorySlots = memorySlotsTestSet();

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlots.size(), currentlyUsedMemorySlots.size());
        for (int i = 0; i < expectedMemorySlots.size(); i++){
            assertEquals(expectedMemorySlots.get(i), currentlyUsedMemorySlots.get(i));
        }
    }

    @RepeatedTest(1)
    @DisplayName("Case 2 processes fit and the algorithm places them where it should")
    void testCaseTwoProcessesThatFit(){
        Process p1 = new Process(0, 0, 15);
        Process p2 = new Process(0, 0, 4);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();


        int actualAddress1 = nextFit.fitProcess(p1, currentlyUsedMemorySlots);
        int actualAddress2 = nextFit.fitProcess(p2, currentlyUsedMemorySlots);
        int expectedAddress1 = 36;
        int expectedAddress2 = 51;

        MemorySlot expectedMemorySlot1 = new MemorySlot(expectedAddress1, 50, 15, 54);
        MemorySlot actualMemorySlot1 = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-2);
        MemorySlot expectedMemorySlot2 = new MemorySlot(expectedAddress2, 54, 15, 54);
        MemorySlot actualMemorySlot2 = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-1);

        assertEquals(expectedAddress1, actualAddress1);
        assertEquals(expectedMemorySlot1, actualMemorySlot1);
        assertEquals(expectedAddress2, actualAddress2);
        assertEquals(expectedMemorySlot2, actualMemorySlot2);
    }

}