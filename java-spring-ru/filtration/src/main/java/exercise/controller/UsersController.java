package exercise.controller;
import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.service.SearchCriteria;
import exercise.service.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    private final UserRepository userRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<User> getUser(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {

        List<Specification> specifications = new ArrayList<>();
        if (firstName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("firstName", firstName)));
        }
        if (lastName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("lastName", lastName)));
        }

        if (specifications.isEmpty()) {
            return userRepository.findAll();
        }

        return userRepository.findAll(specifications.stream()
                .reduce(null, (specificationResult, specification) -> {
                    if (specificationResult == null) {
                        return specification;
                    }
                    return specificationResult.and(specification);
                }));
    }
    // END
}

