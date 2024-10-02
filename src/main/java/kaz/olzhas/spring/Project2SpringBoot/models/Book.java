package kaz.olzhas.spring.Project2SpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nameofbook")
    @NotEmpty(message = "Имя книги не должно быть пустым.")
    private String nameOfBook;

    @Column(name = "author")
    @NotEmpty(message = "Имя автора не должно быть пустым.")
    @Size(min = 0, max = 100, message = "Имя должно быть минимум 0 и максимум 100 символов.")
    private String author;

    @Column(name = "publishedyear")
    @Min(value = 0, message = "Дата публикации должно быть не меньше 0.")
    @Max(value = 2024, message = "Дата публикации должно быть меньше 2024.")
    private int publishedYear;

    @Column(name = "borrow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrow_date;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Transient
    private boolean isExpired;

    public Book(){}

    public Book(String nameOfBook, String author, int publishedYear) {
        this.nameOfBook = nameOfBook;
        this.author = author;
        this.publishedYear = publishedYear;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Date getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", nameOfBook='" + nameOfBook + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", borrow_date=" + borrow_date +
                '}';
    }
}
