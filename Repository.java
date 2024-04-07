package repo;

import domain.Identifiable;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;

public interface Repository<T extends Identifiable<U>, U> {
    public void addItem(T item) throws DuplicateItemException;
    public void removeItem(U id) throws ItemNotFound;
    public T findItem(U id) throws ItemNotFound;
    public void updateItem(U id, T newItem) throws ItemNotFound;
    public Iterable<T> getAllItems();
}
