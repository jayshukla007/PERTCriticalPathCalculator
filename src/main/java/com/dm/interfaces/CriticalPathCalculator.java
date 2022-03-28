package com.dm.interfaces;

import com.dm.domain.Task;

import java.util.List;
import java.util.Set;

public interface CriticalPathCalculator {
    public List<Task> calculateCriticalPath(Set<Task> tasks);
}
