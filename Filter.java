package filters;

import domain.Identifiable;

public interface Filter<T extends Identifiable> {
    boolean accept(T d);
}
