package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id //primary key로 간주한다
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //generatedValue : primarykey는 자동생성된다, IDENTITY : auto-increment
    private Long id = null; //user table에 있는 필드 추가 (bigInt => Long)

    @Column(nullable = false, length = 25, name = "name") //객체의 필드와 테이블의 필드를 매핑한다
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //필드 이름을 넣어준다
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    //Entity 객체에는 매개변수가 하나도 없는 기본생성자가 필요
    protected User() {
    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) { //대출, 도메인 계층에 비즈니스 로직이 들어감
        this.userLoanHistories.add(new UserLoanHistory(this, bookName, false));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .filter(history -> !history.isReturn())
                .findFirst().orElseThrow(IllegalAccessError::new);
        targetHistory.doReturn();
    }
}
