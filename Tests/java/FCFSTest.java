import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FCFSTest {

    FCFS fcfs;
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
        fcfs = new FCFS();
    }

    @RepeatedTest(1)
    @DisplayName("Check if processes are added")
    void testAddProcesses() {
        ArrayList<Process> actualProcesses = new ArrayList<>();
        ArrayList<Process> expectedProcesses = createProcesses();
        for (Process p: expectedProcesses){
            fcfs.addProcess(p);
        }
        assertEquals(expectedProcesses.size(), fcfs.getProcesses().size());
        for (int i = 0; i < fcfs.getProcesses().size(); i++) {
            assertEquals(expectedProcesses.get(i), fcfs.getProcesses().get(i));
        }
    }

    @RepeatedTest(1)
    @DisplayName("Check if processes are removed")
    void testRemoveProcesses() {

        ArrayList<Process> actualProcesses = new ArrayList<>();
        ArrayList<Process> expectedProcesses = createProcesses();

        for (Process p: expectedProcesses){
            fcfs.addProcess(p);
        }

        fcfs.removeProcess(fcfs.getProcesses().get(1));
        expectedProcesses.remove(expectedProcesses.get(1));

        assertEquals(expectedProcesses.size(), fcfs.getProcesses().size());
        for (int i = 0; i < fcfs.getProcesses().size(); i++) {
            assertEquals(expectedProcesses.get(i), fcfs.getProcesses().get(i));
        }
    }

    @RepeatedTest(1)
    @DisplayName("Check if the getNextProcess returns the correct value")
    void testGetNextProcess() {

        ArrayList<Process> processes = createProcesses();

        for (Process p: processes){
            fcfs.addProcess(p);
        }

        Process actualProcess = fcfs.getNextProcess();
        Process expectedProcess = fcfs.getProcesses().get(0);
        assertEquals(expectedProcess, actualProcess);

    }

    @RepeatedTest(1)
    @DisplayName("Check if the getNextProcess returns null when processes is empty")
    void testGetNextProcessWhenNull() {

        Process actualProcess = fcfs.getNextProcess();
        assertEquals(null, actualProcess);

    }

}