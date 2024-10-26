package org.example.queries.filters;

import org.example.model.Person;
import org.example.queries.search.SearchParameters;

import java.util.ArrayList;
import java.util.List;

public class ByNameFilter implements IFilterPeople{
    private SearchParameters searchParams;


    @Override
    public void setSearchParameters(SearchParameters searchParams) {
        this.searchParams = searchParams;
    }

    // Checks if filtering by Name is possible based on set parameters
    @Override
    public boolean canFilter() {
        return searchParams.getName() != null && !searchParams.getName().isEmpty();
    }

    @Override
    public List<Person> filter(List<Person> people) {
        return people.stream().filter(person -> person.getName().equals(searchParams.getName())).toList();
        //stream -> filter po person -> check if name is equal with search parametrs -> push to list
    }
}
