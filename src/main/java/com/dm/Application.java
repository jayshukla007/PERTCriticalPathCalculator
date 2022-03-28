package com.dm;

import com.dm.domain.Task;
import com.dm.interfaces.CriticalPathCalculator;
import com.dm.processor.CriticalPathCalculatorImpl;

import java.util.Arrays;
import java.util.HashSet;

public class Application {
    public static void main(String[] args) {

        HashSet<Task> tasks = new HashSet<Task>();
        Task end = new Task("End", 0, new HashSet<>());
        Task task4 = new Task("Task4", 2, new HashSet<>(Arrays.asList(end)));
        Task task3 = new Task("Task3", 3, new HashSet<>(Arrays.asList(end)));
        Task task2 = new Task("Task2", 4, new HashSet<>(Arrays.asList(task3, task4)));
        Task task1 = new Task("Task1", 2, new HashSet<>(Arrays.asList(task2, task3)));
        Task start = new Task("Start", 0, new HashSet<>(Arrays.asList(task1)));
        tasks.add(end);
        tasks.add(task4);
        tasks.add(task3);
        tasks.add(task2);
        tasks.add(task1);
        tasks.add(start);

        CriticalPathCalculator calculator = new CriticalPathCalculatorImpl();

        System.out.println("Critical Path: "+ calculator.calculateCriticalPath(tasks).toString());
    }
}
