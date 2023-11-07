package Entities;

public interface Subject {
    public void addObservers(Observer obs);
    public void removeObservers(Observer obs);
    public void notifyObservers();
}
