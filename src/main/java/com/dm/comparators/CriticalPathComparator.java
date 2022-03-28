package com.dm.comparators;

import com.dm.domain.Task;

import java.util.Comparator;

public class CriticalPathComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if(o2.getCriticalCost()-o1.getCriticalCost() != 0)
            return o2.getCriticalCost()-o1.getCriticalCost();

        if(o1.isDependent(o2))
            return -1;
        if(o2.isDependent(o1))
            return 1;

        return 0;
    }
}
