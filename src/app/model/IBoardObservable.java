package app.model;

import app.view.IBoardObserver;

public interface IBoardObservable {
    void registerObserver(IBoardObserver o);
    void removeObserver(IBoardObserver o);
    void notifyObservers();
}
