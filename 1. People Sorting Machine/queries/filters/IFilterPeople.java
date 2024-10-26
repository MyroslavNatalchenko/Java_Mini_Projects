package org.example.queries.filters;

import org.example.model.Person;
import org.example.queries.search.SearchParameters;

import java.util.List;

public interface IFilterPeople {
    void setSearchParameters(SearchParameters searchParams);
    boolean canFilter();
    List<Person> filter(List<Person> people);
}
