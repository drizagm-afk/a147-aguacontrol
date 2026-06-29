//>>>>>>>>> DzSQL GENERATED FILE <<<<<<<<<//
package com.example.aguacontrol.generic;

import java.util.*;

public interface CrudService<T, ID> {
	T create(T t);
	T update(T t);
	Optional<T> read(ID id);
	void delete(ID id);
	Iterable<T> readAll();
}
