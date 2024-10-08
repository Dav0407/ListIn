package com.igriss.ListIn.services;

import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveService {

    private CategoryRepo repo;

    public void save(Category category) {
        repo.save(category);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
