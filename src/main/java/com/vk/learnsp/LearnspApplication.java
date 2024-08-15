package com.vk.learnsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class LearnspApplication {
    @Autowired
    private final CustomerRepository customerRepository;

    public LearnspApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(LearnspApplication.class, args);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getcustomers")
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age){}

    @PostMapping("/addcustomer")
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(String.valueOf(request.age));
        customerRepository.save(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerRepository.deleteById(id);
    }


    @PutMapping("/update/{id}")
    public void updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setAge(updatedCustomer.getAge());
            customerRepository.save(existingCustomer);
        }
    }

}