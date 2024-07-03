package com.api.biblioteca.controller;

import com.api.biblioteca.domain.model.Book;
import com.api.biblioteca.domain.model.Genre;
import com.api.biblioteca.domain.repository.BookRepository;
import com.api.biblioteca.domain.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;


    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Book createBook(@RequestBody Book bookRequest) {
        Genre existingGenre = null;

        if (bookRequest.getGenre() != null && bookRequest.getGenre().getId() != null) {
            existingGenre = genreRepository.findById(bookRequest.getGenre().getId()).orElse(null);
        }

        if (existingGenre == null) {
            Genre newGenre = new Genre();
            newGenre.setName(bookRequest.getGenre().getName());
            newGenre.setDescription(bookRequest.getGenre().getDescription());
            existingGenre = genreRepository.save(newGenre);
        }

        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setGenre(existingGenre);

        return bookRepository.save(book);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails){
            Optional<Book> book = bookRepository.findById(id);
            if(book.isPresent()){
                Book updatedBook = book.get();
                updatedBook.setTitle(bookDetails.getTitle());
                updatedBook.setIsbn(bookDetails.getIsbn());
                updatedBook.setAuthor(bookDetails.getAuthor());
                updatedBook.setGenre(bookDetails.getGenre());
                return ResponseEntity.ok(bookRepository.save(updatedBook));
            }else{
                return ResponseEntity.notFound().build();
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
