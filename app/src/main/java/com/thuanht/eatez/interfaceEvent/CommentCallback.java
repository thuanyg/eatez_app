package com.thuanht.eatez.interfaceEvent;

public interface CommentCallback {
    void onCommentSuccess();
    void onCommentFailure(String errorMessage);
}
