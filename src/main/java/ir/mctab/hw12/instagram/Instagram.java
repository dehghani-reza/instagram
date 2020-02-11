package ir.mctab.hw12.instagram;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.Comment;
import ir.mctab.hw12.instagram.entities.Like;
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
            commandMenu(AuthenticationService.getInstance().getLoginUser());
            System.out.println("type command:");
            command = scanner.nextLine();
            try {
                commandValidation(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            if(command.equalsIgnoreCase("signUp")){
                System.out.println("write your username and password: ");
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                try {
                    remoteController.signUp(username,password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (command.equalsIgnoreCase("signin")) {
                try {
                    System.out.println("type your username and  pass:");
                    String u = scanner.nextLine();
                    String w = scanner.nextLine();
                    remoteController.signIn(u, w);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println(AuthenticationService.getInstance().getLoginUser().getUsername());
            }

            if (command.equalsIgnoreCase("delete")) {
                System.out.println("write your pass to delete your account:");
                String p = scanner.nextLine();
                try {
                    remoteController.deleteUser(p);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("Editpassword")) {
                System.out.println("write your oldPass and your new pass");
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
                postList.forEach(System.out::println);
//                postList.forEach(System.out::println);


            }

            if (command.equalsIgnoreCase("showfollowingpost")) {
                List<Post> postList = remoteController.showFollowingPost();
                System.out.println(postList.size());
                postList.forEach(System.out::println);

            }

            if (command.equalsIgnoreCase("addpost")) {
                System.out.println("write some thing: ....");
                String content = scanner.nextLine();
                Post post = remoteController.savePost(content);
                System.out.println(post);
            }

            if (command.equalsIgnoreCase("editpost")) {
                System.out.println(remoteController.showPosts(AuthenticationService.getInstance().getLoginUser()));
                System.out.println("id , content : ");
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
                System.out.println("which post do you want to delete write id: ");
                String id = scanner.nextLine();
                try {
                    remoteController.deletePost(Long.valueOf(id));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("finduser")) {
                System.out.println("write username: ");
                String username = scanner.nextLine();
                User user = remoteController.searchUserByUsername(username);
                System.out.println(user);
            }

            if (command.equalsIgnoreCase("follow")) {
                System.out.println("write username: ");
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
                System.out.println("write username: ");
                String username = scanner.nextLine();
                Set<User> following = null;
                try {
                    following = remoteController.unFollow(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(following);
            }

            if (command.equalsIgnoreCase("like")) {
                System.out.println("write post id that you want to like: ");
                String postId = scanner.nextLine();
                try {
                    remoteController.likePost(Long.valueOf(postId));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (command.equalsIgnoreCase("comment")) {
                System.out.println("id post and comment content");
                String postId = scanner.nextLine();
                String content = scanner.nextLine();
                try {
                    Post post =remoteController.addComment(content, Long.valueOf(postId));
                    System.out.println(post);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


            if (command.equalsIgnoreCase("max")) {
                System.out.println("username: ");
                String username = scanner.nextLine();
                try {
                    Post post =remoteController.findByMostLike(username);
                    System.out.println(post);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if(command.equalsIgnoreCase("signOut")){
                AuthenticationService.getInstance().setLoginUser(null);
                System.out.println("Bye");
            }


        }

    }//end of method main
    static void commandMenu(User user){
        if(user==null){
            System.out.println("here is your command list: " +
                    "\n signUp \t signIn");
            return;
        }
        System.out.println("here is your command list "+user.getUsername()+": " +
                "\n signOut \t delete \t editpassword " +
                "\n showpost \t showfollowingpost \t addpost \t editpost \t delete post" +
                "\n finduser \t follow \t unfollow \t like \t comment \t max(max Like) ");
    }

    static void commandValidation(String command) throws Exception {
        if(AuthenticationService.getInstance().getLoginUser()==null){
            if (!(command.equalsIgnoreCase("signUp")||command.equalsIgnoreCase("signin"))){
                throw new Exception("wrong command first sign in or sign up");
            }
        }
        if (AuthenticationService.getInstance().getLoginUser()!=null){
            if (command.equalsIgnoreCase("signUp")||command.equalsIgnoreCase("signin")){
                throw new Exception("wrong command your still log in");
            }
        }
    }
}
