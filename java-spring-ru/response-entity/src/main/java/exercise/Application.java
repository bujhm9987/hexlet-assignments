package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> show(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst();
        if (post.isPresent()) {
            return ResponseEntity.ok()
                    .body(post);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        Optional<Post> searchPost = posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst();
        if (searchPost.isPresent()) {
            var modifyPost = searchPost.get();
            modifyPost.setSlug(post.getSlug());
            modifyPost.setTitle(post.getTitle());
            modifyPost.setBody(post.getBody());
            return ResponseEntity.ok()
                    .body(post);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(post);
    }
    // END

    @DeleteMapping("/posts/{slug}")
    public void destroy(@PathVariable String slug) {
        posts.removeIf(p -> p.getSlug().equals(slug));
    }
}
