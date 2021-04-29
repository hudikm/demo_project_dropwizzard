package sk.fri.uniza.auth;

import java.util.List;
import java.util.Optional;

public interface BasicDao<T,I> {

    Optional<T> get(I id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}