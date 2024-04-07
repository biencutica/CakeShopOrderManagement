package repo;

import domain.Identifiable;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepo<T extends Identifiable<U>, U> implements repo.Repository<T,U> {
    protected Map<U, T> listOfItems = new HashMap<>();

    @Override
    public void addItem(T item) throws DuplicateItemException {
        if(listOfItems.containsKey(item.getId()))
            throw new DuplicateItemException("An item with " + item.getId() + " already exists.");
        listOfItems.put(item.getId(), item);
    }

    @Override
    public void removeItem(U id) throws ItemNotFound{
        if(listOfItems.containsKey(id))
        {
            listOfItems.remove(id);
            return;
        }
        throw new ItemNotFound("Item " + id + " not found");
    }

    @Override
    public T findItem(U id) throws ItemNotFound{
        if(!listOfItems.containsKey(id))
        {
            throw new ItemNotFound("Item with id " + id + " not found");
        }
        return listOfItems.get(id);
    }

    @Override
    public void updateItem(U id, T newItem) throws ItemNotFound{
        if(listOfItems.containsKey(id))
        {
            listOfItems.replace(id, newItem);
        }
        else
            throw new ItemNotFound("Item with id " + id + " not found");
    }

    @Override
    public Iterable<T> getAllItems() {
        return listOfItems.values();
    }

}
