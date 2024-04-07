package repo;

import domain.Identifiable;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;

public abstract class FileRepo<T extends Identifiable<U>,U> extends repo.MemoryRepo<T,U> {
    protected String fileName;

    public FileRepo(String fileName) {
        this.fileName = fileName;
        readFromFile();
    }

    protected abstract void readFromFile();
    protected abstract void writeToFile();
    @Override
    public void addItem(T item) throws DuplicateItemException {
        super.addItem(item);
        writeToFile();
    }
    @Override
    public void removeItem(U id) throws ItemNotFound {
        super.removeItem(id);
        writeToFile();
    }

    @Override
    public T findItem(U id) throws ItemNotFound {
        return super.findItem(id);
    }

    @Override
    public void updateItem(U id, T newItem) throws ItemNotFound {
        super.updateItem(id, newItem);
        writeToFile();
    }
}
