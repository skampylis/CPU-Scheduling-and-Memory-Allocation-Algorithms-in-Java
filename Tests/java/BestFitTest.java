import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFitTest {

    BestFit bestFit;
    int[] availableBlockSizes = {50, 20, 200};

    ArrayList<MemorySlot> memorySlotsTestSet(){
        ArrayList<MemorySlot> currentlyUsedMemorySlots = new ArrayList<>(){
            {
                // block 2
                add(new MemorySlot(52,54,50,69));
                add(new MemorySlot(56,60,50,69));
                add(new MemorySlot(63,67,50,69));

                // block 3
                add(new MemorySlot(70,100,70,269));
                add(new MemorySlot(110,130,70,269));
                add(new MemorySlot(101,102,70,269));
                add(new MemorySlot(200,269,70,269));

                // block 1
                add(new MemorySlot(0,5,0,49));
                add(new MemorySlot(31,36,0,49));
            }
        };

        return currentlyUsedMemorySlots;
    }


    @BeforeEach
    void setUp(){
        bestFit = new BestFit(availableBlockSizes);
    }


    @RepeatedTest(1)
    @DisplayName("Case the process fits and the memory slots are empty, returns correct address and creates correct MemorySlot")
    void testCaseEmptyMemorySlots(){
        Process p = new Process(0, 0, 15);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = new ArrayList<>();

        int actualAddress = bestFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 50;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 64, 50, 69);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(0);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process fits, returns correct address and creates correct MemorySlot")
    void testCaseItFits(){
        Process p = new Process(0, 0, 1);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();

        int actualAddress = bestFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = 55;

        MemorySlot expectedMemorySlot = new MemorySlot(expectedAddress, 55, 50, 69);
        MemorySlot actualMemorySlot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-1);

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlot, actualMemorySlot);
    }

    @RepeatedTest(1)
    @DisplayName("Case the process does not fits, returns address -1 and the array does not change")
    void testCaseItNotFits(){
        Process p = new Process(0, 0, 70);
        ArrayList<MemorySlot> currentlyUsedMemorySlots = memorySlotsTestSet();


        int actualAddress = bestFit.fitProcess(p, currentlyUsedMemorySlots);
        int expectedAddress = -1;

        ArrayList<MemorySlot> expectedMemorySlots = memorySlotsTestSet();

        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedMemorySlots.size(), currentlyUsedMemorySlots.size());
        for (int i = 0; i < expectedMemorySlots.size(); i++){
            assertEquals(expectedMemorySlots.get(i), currentlyUsedMemorySlots.get(i));
        }
    }

}