package com.thuanht.eatez.interfaceEvent;

import com.thuanht.eatez.model.Comment;

public interface CommentCallback {
    void onCommentSuccess(Comment comment);
    void onCommentFailure(String errorMessage);
}
