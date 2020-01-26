package ir.mctab.hw12.instagram;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.Comment;
import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import ir.mctab.hw12.instagram.repositories.CrudDAO;
import ir.mctab.hw12.instagram.repositories.UserRepository;
import ir.mctab.hw12.instagram.share.AuthenticationService;
import ir.mctab.hw12.instagram.view.RemoteController;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

public class Instagram {
    public static void main(String[] args) {

        RemoteController remoteController = new RemoteController();
        Scanner scanner = new Scanner(System.in);

        String command = "hello";

        while (!command.equalsIgnoreCase("EXIT")) {
            System.out.println("type command:");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("signin")) {
                try {
                    System.out.println("type user pass");
                    String u = scanner.nextLine();
                    String w = scanner.nextLine();
                    remoteController.signIn(u, w);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(AuthenticationService.getInstance().getLoginUser().getUsername());
            }

            if (command.equalsIgnoreCase("delete")) {
                String p = scanner.nextLine();
                try {
                    remoteController.deleteUser(p);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("Editpassword")) {
                String p = scanner.nextLine();
                String pp = scanner.nextLine();
                try {
                    remoteController.editPassword(p, pp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("showpost")) {

                List<Post> postList = remoteController.showPosts(AuthenticationService.getInstance().getLoginUser());
                System.out.println(postList.size());
//                postList.forEach(System.out::println);


            }

            if (command.equalsIgnoreCase("showfollowingpost")) {
                List<Post> postList = remoteController.showFollowingPost();
                System.out.println(postList.size());

            }

            if (command.equalsIgnoreCase("addpost")) {
                String content = scanner.nextLine();
                Post post = remoteController.savePost(content);
                System.out.println(post);
            }

            if (command.equalsIgnoreCase("editpost")) {
                System.out.println(remoteController.showPosts(AuthenticationService.getInstance().getLoginUser()));
                System.out.println("id , content");
                String id = scanner.nextLine();
                String content = scanner.nextLine();
                Post post = null;
                try {
                    post = remoteController.editPost(content, Long.valueOf(id));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(post);
            }

            if (command.equalsIgnoreCase("deletePost")) {
                System.out.println(remoteController.showPosts(AuthenticationService.getInstance().getLoginUser()));
                String id = scanner.nextLine();
                try {
                    remoteController.deletePost(Long.valueOf(id));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("finduser")) {
                String username = scanner.nextLine();
                User user = remoteController.searchUserByUsername(username);
                System.out.println(user);
            }

            if (command.equalsIgnoreCase("follow")) {
                String username = scanner.nextLine();
                Set<User> following = null;
                try {
                    following = remoteController.follow(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(following);
            }

            if (command.equalsIgnoreCase("unfollow")) {
                String username = scanner.nextLine();
                Set<User> following = null;
                try {
                    following = remoteController.unFollow(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(following);
            }


        }

    }//end of method main
}
