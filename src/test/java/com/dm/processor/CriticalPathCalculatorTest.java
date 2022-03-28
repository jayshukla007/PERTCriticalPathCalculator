package com.dm.processor;

import com.dm.domain.Task;
import com.dm.interfaces.CriticalPathCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CriticalPathCalculatorTest {
    private static HashSet<Task> allTasks;
    private CriticalPathCalculator criticalPathCalculator = new CriticalPathCalculatorImpl();

    @BeforeAll
    static void setUP(){
        allTasks = new HashSet<Task>();
        Task end = new Task("End", 0, new HashSet<>());
        Task task4 = new Task("Task4", 2, new HashSet<>(Arrays.asList(end)));
        Task task3 = new Task("Task3", 3, new HashSet<>(Arrays.asList(end)));
        Task task2 = new Task("Task2", 4, new HashSet<>(Arrays.asList(task3, task4)));
        Task task1 = new Task("Task1", 2, new HashSet<>(Arrays.asList(task2, task3)));
        Task start = new Task("Start", 0, new HashSet<>(Arrays.asList(task1)));
        allTasks.add(end);
        allTasks.add(task4);
        allTasks.add(task3);
        allTasks.add(task2);
        allTasks.add(task1);
        allTasks.add(start);
    }

    @Test
    void shouldReturnEmptyListIfNoTasksArePresentToProcess(){
        List<Task> taskWithCriticalCost = criticalPathCalculator.calculateCriticalPath(new HashSet<>());

        assertTrue(taskWithCriticalCost.isEmpty());
    }

    @Test
    void shouldReturnCriticalCostForEachStep(){
        List<Task> expectedResult = Arrays.asList(new Task("Start",9), new Task("Task1",9),
                new Task("Task2",7), new Task("Task3",3), new Task("Task4",2),
                new Task("End",0));

        List<Task> taskWithCriticalCost = criticalPathCalculator.calculateCriticalPath(allTasks);

        assertTrue(!taskWithCriticalCost.isEmpty());
        assertEquals(expectedResult.toString(), taskWithCriticalCost.toString());
    }

    @Test
    void shouldThrowRuntimeExceptionIfCyclicDependencyIsPresent(){
        HashSet<Task> tasks = new HashSet<>();
        Task task1 = new Task("Start", 0, new HashSet<>(Arrays.asList()));
        Task task2 = new Task("Start", 0, new HashSet<>(Arrays.asList(task1)));
        task1.setDependencies(new HashSet<>(Arrays.asList(task2)));
        tasks.add(task1);
        tasks.add(task2);

        String expectedExceptionMessage = "There is cyclic dependency in input. Please check and run again.";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            criticalPathCalculator.calculateCriticalPath(tasks);
        });

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

}