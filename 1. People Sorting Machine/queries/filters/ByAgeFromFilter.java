package org.example.queries.filters;

import org.example.model.Person;
import org.example.queries.search.SearchParameters;

import java.util.List;

public class ByAgeFromFilter implements IFilterPeople {
    private SearchParameters searchParams;

    // Sets search parameters for filtering
    @Override
    public void setSearchParameters(SearchParameters searchParams) {
        this.searchParams = searchParams;
    }

    // Checks if filtering by age is possible based on set parameters
    @Override
    public boolean canFilter() {
        return searchParams.getAgeFrom() > 0 && searchParams.getAgeFrom() < searchParams.getAgeTo();
    }

    // Filters people based on the minimum age criteria
    @Override
    public List<Person> filter(List<Person> people) {
        return people.stream().filter(person -> person.getAge() >= searchParams.getAgeFrom()).toList();
    }
}
