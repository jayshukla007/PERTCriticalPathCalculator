package com.dm.domain;

import java.util.HashSet;

public class Task {
    private String name;
    private int cost;
    private  int criticalCost;
    private HashSet<Task> dependencies;

    public Task(String name, int criticalCost) {
        this.name = name;
        this.criticalCost = criticalCost;
    }

    public Task(String name, int cost, HashSet<Task> dependencies) {
        this.name = name;
        this.cost = cost;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCriticalCost() {
        return criticalCost;
    }

    public void setCriticalCost(int criticalCost) {
        this.criticalCost = criticalCost;
    }

    public HashSet<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(HashSet<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public boolean isDependent(Task task){
        if(dependencies.contains(task)){
            return true;
        }
        for(Task dep : dependencies){
            if(dep.isDependent(task)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name+": "+criticalCost;
    }
}
