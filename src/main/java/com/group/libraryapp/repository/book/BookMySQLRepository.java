package com.group.libraryapp.repository.book;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class BookMySQLRepository implements BookRepository{

    @Override
    public void saveBook() {

    }
}
