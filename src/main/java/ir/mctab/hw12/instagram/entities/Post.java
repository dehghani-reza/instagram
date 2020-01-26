package ir.mctab.hw12.instagram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER , mappedBy = "post")
    private List<Comment> comment = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", comment=" + comment +
                '}';
    }
}
