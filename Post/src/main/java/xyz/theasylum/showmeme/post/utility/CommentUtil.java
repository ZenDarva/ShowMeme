package xyz.theasylum.showmeme.post.utility;

import xyz.theasylum.showmeme.post.domain.Comment;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class CommentUtil {
    public static List<Comment> organizeComments(List<Comment> baseComments){
        List<Comment> unsortedComments = new LinkedList<>();
        List<Comment> sortedComments = new LinkedList<>();

        unsortedComments.addAll(baseComments);

        baseComments.stream().filter(f->f.getParentId()==null).forEach(sortedComments::add);
        baseComments.removeAll(sortedComments);

        List<Comment> toRemove = new LinkedList<>();
        while (!baseComments.isEmpty()){
            for (Comment baseComment : baseComments) {
                for (Comment sortedComment : unsortedComments) {
                    if (sortedComment.getId().equals(baseComment.getParentId())){
                        if (sortedComment.getChildren()==null){
                            sortedComment.setChildren(new LinkedList<>());
                        }
                        sortedComment.getChildren().add(baseComment);
                        toRemove.add(baseComment);
                    }
                }
            }
            baseComments.removeAll(toRemove);
            toRemove.clear();
        }
        return sortedComments;
    }
    public static Comment getCommentsChildren(List<Comment> baseComments, Comment parent){
        List<Comment> unsortedComments = new LinkedList<>();
        unsortedComments.addAll(baseComments);
        baseComments = baseComments.stream().filter(f->f.getParentId()!=null).collect(Collectors.toList());
        Queue<Comment> parents = new LinkedList<>();
        parents.add(parent);
        List<Comment> toRemove = new LinkedList<>();
        while(!parents.isEmpty()){
            Comment targParent = parents.poll();
            for (Comment targComment : baseComments) {
                if (targComment.getParentId().equals(targParent.getId())){
                    targParent.getChildren().add(targComment);
                    toRemove.add(targComment);
                    parents.add(targComment);
                }
            }
            baseComments.removeAll(toRemove);
            toRemove.clear();
        }
        return parent;
    }
}
