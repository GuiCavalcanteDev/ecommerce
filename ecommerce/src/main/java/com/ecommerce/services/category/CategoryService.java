package com.ecommerce.services.category;

import com.ecommerce.dtos.category.*;
import com.ecommerce.dtos.product.RegisterProductResponseDTO;
import com.ecommerce.dtos.product.SearchProductResponseDTO;
import com.ecommerce.entities.category.CategoryModel;
import com.ecommerce.entities.user.UserModel;
import com.ecommerce.exceptions.EventBadRequestException;
import com.ecommerce.exceptions.EventInternalServerErrorException;
import com.ecommerce.exceptions.EventNotFoundException;
import com.ecommerce.repositories.category.ICategoryRepository;
import com.ecommerce.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ICategoryRepository repository;

    private final UserService userService;

    public CategoryService(ICategoryRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    CategoryModel category = new CategoryModel();
    UserModel user = new UserModel();

    private CategoryModel validateCategory(String id) {
        return repository.findById(id).orElseThrow(() -> new EventNotFoundException("Categoria não encontrada."));
    }

    public ResponseEntity<CategoryResponseDTO> findById(String id) {
        try {
            category = validateCategory(id);

            String createdBy = category.getCreatedBy().getId();

            CategoryModelResponseDTO response = new CategoryModelResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.isStatus(),
                    createdBy,
                    category.getCreatedOn()
            );

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (JpaSystemException ex) {
            throw new EventInternalServerErrorException(ex.getMessage());
        }
    }

    public ResponseEntity<List<CategoryModelResponseDTO>> getAllCategory() {
        try {
            List<CategoryModel> categorys = repository.findAll();
            List<CategoryModelResponseDTO> response = new ArrayList<>();
            for (CategoryModel categoryModel : categorys) {
                CategoryModelResponseDTO responseDTO = new CategoryModelResponseDTO(
                        categoryModel.getId(),
                        categoryModel.getName(),
                        categoryModel.getDescription(),
                        categoryModel.isStatus(),
                        categoryModel.getCreatedBy().getId(),
                        categoryModel.getCreatedOn()
                );
                response.add(responseDTO);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public ResponseEntity registerCategory(RegisterCategoryRequestDTO request) {
        try {
            if (repository.findByName(request.name()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new CategoryResponseDTO(HttpStatus.CONFLICT, "Categoria já existe com esse nome."));
            }

            category.setName(request.name());
            category.setDescription(request.description());
            category.setStatus(true);
            user = userService.getAuthenticatedUser();
            category.setCreatedBy(user);

            repository.save(category);

            return new ResponseEntity(new RegisterProductResponseDTO(HttpStatus.CREATED, "Categoria criada com sucesso."), HttpStatus.CREATED);
        } catch (JpaSystemException ex) {
            throw new EventInternalServerErrorException(ex.getMessage());
        }
    }

    public ResponseEntity updateCategory(String id, UpdateCategoryRequestDTO request) {
        try {
            category = validateCategory(id);

            if (!category.getName().equals(request.name())) {
                Optional<CategoryModel> nameConflict = repository.findByName(request.name());
                if (nameConflict.isPresent()) {
                    return new ResponseEntity(new CategoryResponseDTO(HttpStatus.CONFLICT, "Categoria fornecido já esta em uso por outra categoria. Insira um nome unico. "), HttpStatus.CONFLICT);
                }
            }

            category.setName(request.name());
            category.setDescription(request.description());

            repository.save(category);

            return new ResponseEntity(new CategoryResponseDTO(HttpStatus.OK, "Categoria atualizada com sucesso."), HttpStatus.OK);
        } catch (EventBadRequestException ex) {
            throw new EventBadRequestException(ex.getMessage());
        }
    }

    public ResponseEntity deactivateCategory(String id) {
        try {
            category = validateCategory(id);

            category.setStatus(false);
            repository.save(category);

            return new ResponseEntity("Categoria deativada com sucesso", HttpStatus.OK);
        } catch (JpaSystemException ex) {
            throw new EventInternalServerErrorException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new EventBadRequestException(ex.getMessage());
        }
    }

    public ResponseEntity activeCategory(String id) {
        try {
            category = validateCategory(id);
            category.setStatus(true);
            repository.save(category);
            return new ResponseEntity("Categoria ativada com sucesso", HttpStatus.OK);
        } catch (JpaSystemException ex) {
            throw new EventInternalServerErrorException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new EventBadRequestException(ex.getMessage());
        }
    }

    public ResponseEntity deleteCategory(String id){
        try {
            category = validateCategory(id);
            repository.deleteById(id);
            return new ResponseEntity<>("Categoria deletada com sucesso", HttpStatus.OK);
        }catch (JpaSystemException ex){
            throw new EventInternalServerErrorException(ex.getMessage());
        }
    }

    public ResponseEntity<List<SearchCategoryResponseDTO>> searchCategory(String name){
        try{
            Optional<CategoryModel> category = repository.findByName(name);

            if(category.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonList(new SearchCategoryResponseDTO("Categoria não encontrada", null)));
            }

            List<SearchCategoryResponseDTO> response = category.stream()
                    .map(categoryModel -> new SearchCategoryResponseDTO(
                            categoryModel.getName(),
                            categoryModel.getDescription()
                    ))
                    .toList();

            return ResponseEntity.ok(response);
        }catch (JpaSystemException ex){
            throw new EventInternalServerErrorException(ex.getMessage());
        }catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}