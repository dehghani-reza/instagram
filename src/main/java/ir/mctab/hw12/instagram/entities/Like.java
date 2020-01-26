package ir.mctab.hw12.instagram.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId" , nullable = false)
    private Post post;

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                '}';
    }
}

