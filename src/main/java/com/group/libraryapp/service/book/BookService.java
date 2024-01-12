package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.book.Book;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName())); //Insert sql이 자동으로 날라간다
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        //책 이름을 통해 책 객체를 가져온다, 책이 없다면 예외를 던진다
        Book book = bookRepository.findByName(request.getBookName());
        if (book == null) {
            throw new IllegalArgumentException();
        }
        //누가 책을 대출중인지 확인한다
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(request.getBookName(), false)){
            //누가 대출중
            throw new IllegalArgumentException("진작 대출되어 있는 책 입니다");
        }
        //유저 name을 통해 유저 id을 알아온다
        User user = userRepository.findByName(request.getUserName());
        if (user == null) {
            throw new IllegalArgumentException();
        }
        user.loanBook(book.getName()); //바뀐 코드
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName(), false)); //id, bookname, isreturn
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        //user 이름으로 user id을 가져온다
        User user = userRepository.findByName(request.getUserName());
        if (user == null) {
            throw new IllegalArgumentException();
        }

        user.returnBook(request.getBookName()); //바뀐 코드
        //UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
        //        .orElseThrow(IllegalAccessError::new);
        //history.doReturn(); //반납 처리
        //Transactional 어노테이션 덕분에 영속성 컨텍스트가 존재하고, 이는 변경 감지 기능이 있기에 is_return이 바뀐것에 대해 save()을 할 필요가 없다
    }
}
