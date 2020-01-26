package ir.mctab.hw12.instagram.entities;

import antlr.collections.impl.LList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true , nullable = false , name = "username")
    private String username;

    @Column(nullable = false , name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "followers" ,joinColumns = @JoinColumn (name = "followingId") ,inverseJoinColumns = @JoinColumn(name = "followerId"))
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers" , fetch = FetchType.EAGER)
    private Set<User> following = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", postList=" + postList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId().equals(user.getUserId()) &&
                getUsername().equals(user.getUsername()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword());
    }
}
