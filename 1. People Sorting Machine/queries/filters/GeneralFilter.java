package org.example.queries.filters;

import org.example.model.Person;
import org.example.queries.search.SearchParameters;

import java.util.List;
import java.util.function.Predicate;

public class GeneralFilter implements IFilterPeople {
    private SearchParameters searchParams;
    private Predicate<SearchParameters> canFilterPredicate; //Predicate to determine if the filter can be applied
    private DualPredicate filterDualPredicate; //Custom predicate for filtering based on SearchParameters and Person objects

    // Constructor to initialize predicates for determining filter applicability and filtering logic
    public GeneralFilter(Predicate<SearchParameters> canFilterPredicate, DualPredicate filterDualPredicate) {
        this.canFilterPredicate = canFilterPredicate;
        this.filterDualPredicate = filterDualPredicate;
    }

    @Override
    public void setSearchParameters(SearchParameters searchParams) {
        this.searchParams = searchParams;
    }

    @Override
    public boolean canFilter() {
        // Checks if the search parameters are set and if the filter can be applied according to `canFilterPredicate`
        return searchParams != null && canFilterPredicate.test(searchParams);
    }

    @Override
    public List<Person> filter(List<Person> people) {
        // Filters the list of people according to `filterDualPredicate` which uses both `searchParams` and each `Person`
        return people.stream().filter(person -> filterDualPredicate.check(searchParams, person)).toList();
    }
}
