package fpt.t2009m1.asm_springboot.restapi;

import fpt.t2009m1.asm_springboot.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserApi {

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "Hello user";
    }
}
