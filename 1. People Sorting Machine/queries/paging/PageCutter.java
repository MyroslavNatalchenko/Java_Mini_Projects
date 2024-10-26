package org.example.queries.paging;

import org.example.model.Person;
import org.example.queries.search.Page;

import java.util.ArrayList;
import java.util.List;

public class PageCutter implements ICutToPage {
    @Override
    public List<Person> cut(Page page, List<Person> data) {
        int size = page.getSize();
        int amount = data.size();
        return data.subList(0, Math.min(size,amount));
    }

}
