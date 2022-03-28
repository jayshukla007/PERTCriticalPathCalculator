package com.dm.processor;

import com.dm.comparators.CriticalPathComparator;
import com.dm.domain.Task;
import com.dm.interfaces.CriticalPathCalculator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CriticalPathCalculatorImpl implements CriticalPathCalculator {

    @Override
    public List<Task> calculateCriticalPath(Set<Task> tasks) {
        {
            HashSet<Task> completedTasks = new HashSet<Task>();
            HashSet<Task> remainingTasks = new HashSet<Task>(tasks);

            while (!remainingTasks.isEmpty()) {
                AtomicBoolean progress = new AtomicBoolean(false);

                for (Iterator<Task> it = remainingTasks.iterator(); it.hasNext(); ) {
                    Task task = it.next();
                    if (completedTasks.containsAll(task.getDependencies())) {
                        AtomicInteger critical = new AtomicInteger(0);
                        task.getDependencies().stream().forEach(t -> {
                            if (t.getCriticalCost() > critical.get()) {
                                critical.set(t.getCriticalCost());
                            }
                        });
                        task.setCriticalCost(critical.addAndGet(task.getCost()));
                        completedTasks.add(task);
                        it.remove();
                        progress.set(true);
                    }

                }
                if (!progress.get()) throw new RuntimeException("There is cyclic dependency in input. Please check and run again.");
            }
            return completedTasks.stream().sorted(new CriticalPathComparator()).collect(Collectors.toList());
        }
    }
}
