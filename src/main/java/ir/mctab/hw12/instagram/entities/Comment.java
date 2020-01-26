package ir.mctab.hw12.instagram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(nullable = false , name = "content")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name ="userId")
    private User user;


    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", user=" + user +
                '}';
    }
}
