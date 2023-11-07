package Entities;

public interface Subject {
    void addObservers(Observer obs);
    void removeObservers(Observer obs);
    void notifyObservers();
}
