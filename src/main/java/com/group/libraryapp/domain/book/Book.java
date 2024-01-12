package com.group.libraryapp.domain.book;

import javax.persistence.*;

@Entity
public class Book {
    @Id //primary key로 간주
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id = null;

    @Column(nullable = false, length = 255, name="name") //객체 필드와 테이블 필드 매핑
    private String name;

    //기본 생성자 - Entity 객체에는 항상 필요
    protected Book() {
    }

    public Book(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
