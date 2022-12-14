package fpt.t2009m1.asm_springboot.restapi;

import fpt.t2009m1.asm_springboot.entity.Account;
import fpt.t2009m1.asm_springboot.entity.Product;
import fpt.t2009m1.asm_springboot.entity.myenum.AccountStatus;
import fpt.t2009m1.asm_springboot.service.AccountService;
import fpt.t2009m1.asm_springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(path = "api/v1/admins")
@RestController
@CrossOrigin("*")
public class AdminApi {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, path = "profile/{id}")
    public Optional<Account> findAccById(@PathVariable String id){
        return accountService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "accounts")
    public Page<Account> findAllAcc(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "limit", defaultValue = "10") int limit){
        return accountService.findAll(page, limit);
    }

    @RequestMapping(method = RequestMethod.POST, path = "accounts")
    public Page<Account> searchAcc(@RequestParam(name = "search", defaultValue = "") String search,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "limit", defaultValue = "10") int limit){
        return accountService.search(search, page, limit);
    }

    @RequestMapping(method = RequestMethod.POST, path = "accounts")
    public Page<Account> findAllByStatus(@RequestParam(name = "status", defaultValue = "") AccountStatus status,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "limit", defaultValue = "10") int limit){
        return accountService.findByStatus(status, page, limit);
    }

    @RequestMapping(method = RequestMethod.POST, path = "accounts")
    public Page<Account> findAllByRole(@RequestParam(name = "role", defaultValue = "") String role,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "limit", defaultValue = "10") int limit){
        return accountService.findByRole(role, page, limit);
    }
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, path = "product")
    public Page<Product> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.findAll(page, limit);
    }

    @RequestMapping(method = RequestMethod.GET, path = "product/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id){
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> saveProduct(@RequestBody Product Product){
        return ResponseEntity.ok(productService.save(Product));
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "product/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id){
        if (!productService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(method = RequestMethod.PUT, path = "product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updateProduct) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();

        existProduct.setDescription(updateProduct.getDescription());
        existProduct.setCategory(updateProduct.getCategory());
        existProduct.setDetail(updateProduct.getDetail());
        existProduct.setName(updateProduct.getName());
        existProduct.setPrice(updateProduct.getPrice());
        existProduct.setStatus(updateProduct.getStatus());

        return ResponseEntity.ok(productService.save(existProduct));
    }
}
