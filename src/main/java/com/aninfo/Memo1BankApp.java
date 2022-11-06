package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Collection> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}

	/*
	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.withdraw(cbu, sum);
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.deposit(cbu, sum);
	}
	*/

	@PostMapping("/accounts/{cbu}/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
		Optional<Account> accountOptional = accountService.findById(transaction.getAccountCBU());
		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity(transactionService.createTransaction(transaction), HttpStatus.CREATED);
	}

	@GetMapping("/accounts/{cbu}/transactions")
	public ResponseEntity<Collection<Transaction>> getAccountTransactions(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.of(transactionService.findByCBU(cbu));
	}

	@GetMapping("/transactions/{transactionID}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionID) {
		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		return ResponseEntity.of(transactionOptional);
	}

	@GetMapping("/transactions")
	public Collection<Transaction> getTransactions() {
		return transactionService.getTransactions();
	}

	@DeleteMapping("/transactions/{transactionID}")
	public void deleteTransaction(@PathVariable Long transactionID) {
		transactionService.deleteById(transactionID);
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
