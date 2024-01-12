package com.group.libraryapp.domain.user.loanhistory;

import javax.persistence.*;
import com.group.libraryapp.domain.user.User;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne //[N : 1 관계] 내가 N 너가 1 -> 여기선 UserLoanHistory가 N User가 1
    private User user;
    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private boolean isReturn;

    protected UserLoanHistory() {
    }

    public UserLoanHistory(User user, String bookName, boolean isReturn) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = isReturn;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void doReturn() {
        this.isReturn = true;
    }
}
