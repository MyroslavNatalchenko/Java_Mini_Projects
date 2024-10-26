package org.example.queries.filters;

import org.example.model.Person;
import org.example.queries.search.SearchParameters;

public interface DualPredicate {
    boolean check(SearchParameters params, Person person);
}
