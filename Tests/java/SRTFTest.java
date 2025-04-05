import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SRTFTest {

    SRTF srtf;
    int[] availableBlockSizes = {50, 20, 200};

    ArrayList<Process> createProcesses(){
        ArrayList<Process> expectedProcesses = new ArrayList<>() {
            {
                add (new Process(0, 5, 10));
                add (new Process(2, 2, 40));
                add(new Process(3, 1, 25));
                add(new Process(4, 3, 30));
            }
        };
        return  expectedProcesses;
    }

    @BeforeEach
    void setUp() {
        srtf = new SRTF();
    }

    @RepeatedTest(1)
    @DisplayName("Check if processes are added")
    void testAddProcesses() {
        ArrayList<Process> actualProcesses = new ArrayList<>();
        ArrayList<Process> expectedProcesses = createProcesses();
        for (Process p: expectedProcesses){
            srtf.addProcess(p);
        }
        assertEquals(expectedProcesses.size(), srtf.getProcesses().size());
        for (int i = 0; i < srtf.getProcesses().size(); i++) {
            assertEquals(expectedProcesses.get(i), srtf.getProcesses().get(i));
        }
    }

    @RepeatedTest(1)
    @DisplayName("Check if processes are removed")
    void testRemoveProcesses() {

        ArrayList<Process> actualProcesses = new ArrayList<>();
        ArrayList<Process> expectedProcesses = createProcesses();

        for (Process p: expectedProcesses){
            srtf.addProcess(p);
        }

        srtf.removeProcess(srtf.getProcesses().get(1));
        expectedProcesses.remove(expectedProcesses.get(1));

        assertEquals(expectedProcesses.size(), srtf.getProcesses().size());
        for (int i = 0; i < srtf.getProcesses().size(); i++) {
            assertEquals(expectedProcesses.get(i), srtf.getProcesses().get(i));
        }
    }

    @RepeatedTest(1)
    @DisplayName("Check if the getNextProcess returns the correct value")
    void testGetNextProcess() {

        ArrayList<Process> processes = createProcesses();

        for (Process p: processes){
            srtf.addProcess(p);
        }

        Process actualProcess = srtf.getNextProcess();
        Process expectedProcess = srtf.getProcesses().get(2);
        assertEquals(expectedProcess, actualProcess);

    }

    @RepeatedTest(1)
    @DisplayName("Check if the getNextProcess returns null when processes is empty")
    void testGetNextProcessWhenNull() {

        Process actualProcess = srtf.getNextProcess();
        assertEquals(null, actualProcess);

    }

}