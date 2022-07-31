package com.example.springboot_assignment.repository;

import com.example.springboot_assignment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
