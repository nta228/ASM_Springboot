package fpt.t2009m1.asm_springboot.repository;

import fpt.t2009m1.asm_springboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
