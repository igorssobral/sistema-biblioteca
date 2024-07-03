package com.api.biblioteca.controller;

import com.api.biblioteca.domain.model.Loan;
import com.api.biblioteca.domain.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanRepository.save(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isPresent()) {
            Loan updatedLoan = loan.get();
            updatedLoan.setLoanDate(loanDetails.getLoanDate());
            updatedLoan.setReturnDate(loanDetails.getReturnDate());
            updatedLoan.setMember(loanDetails.getMember());
            updatedLoan.setBook(loanDetails.getBook());
            return ResponseEntity.ok(loanRepository.save(updatedLoan));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
