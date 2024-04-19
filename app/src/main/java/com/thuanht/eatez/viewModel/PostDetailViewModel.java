package com.thuanht.eatez.viewModel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.interfaceEvent.CommentCallback;
import com.thuanht.eatez.jsonResponse.CommentResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.jsonResponse.StatusResponse;
import com.thuanht.eatez.model.Comment;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.retrofit.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostDetailViewModel extends ViewModel {
    private Disposable disposable_post, disposable_cmt, disposable_addCmt;
    private MutableLiveData<Post> post = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();
    private CommentCallback commentCallback;
    private MutableLiveData<String> contentCommentError = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPageComment = new MutableLiveData<>();

    public void setCommentCallback(CommentCallback callback) {
        this.commentCallback = callback;
    }

    public void fetchPostDetail(int postid) {
        ApiService.ApiService.getDetailPost(postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable_post = d;
                    }

                    @Override
                    public void onNext(@NonNull PostResponse postResponse) {
                        if (postResponse != null) {
                            if (postResponse.getStatus()) {
                                post.setValue(postResponse.getData().get(0));
                            } else {
                                post.setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_post.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_post.dispose();
                    }
                });
    }


    public void fetchComments(int postId, int pageNumber) {
        ApiService.ApiService.getComments(postId, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<CommentResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable_cmt = d;
                    }
                    @Override
                    public void onNext(@NonNull CommentResponse commentResponse) {
                        if (commentResponse != null) {
                            if (commentResponse.getStatus()) {
                                comments.setValue(commentResponse.getData());
                                if (commentResponse.getPagination().getCurrentPage() == commentResponse.getPagination().getTotalPage()){
                                    isLastPageComment.setValue(true);
                                } else {
                                    isLastPageComment.setValue(false);
                                }
                            } else {
                                comments.setValue(null);
                            }
                        } else comments.setValue(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_cmt.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_cmt.dispose();
                    }
                });
    }

    public Boolean varidate(String content) {
        contentCommentError.setValue(null);
        if (TextUtils.isEmpty(content)) {
            contentCommentError.setValue("Please enter your comment!");
            return false;
        }
        return true;
    }

    public void addComment(int userID, int postID, String content, int rating) {
        ApiService.ApiService.setComment(userID, postID, content, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable_addCmt = d;
                    }

                    @Override
                    public void onNext(@NonNull StatusResponse statusResponse) {
                        if (statusResponse.getStatus()) {
                            commentCallback.onCommentSuccess();
                        } else {
                            commentCallback.onCommentFailure("Error");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_addCmt.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_addCmt.dispose();
                    }
                });
    }

    public MutableLiveData<List<Comment>> getComments() {
        return comments;
    }

    public MutableLiveData<String> getContentCommentError() {
        return contentCommentError;
    }

    public MutableLiveData<Post> getPost() {
        return post;
    }

    public MutableLiveData<Boolean> getIsLastPageComment() {
        return isLastPageComment;
    }
}
