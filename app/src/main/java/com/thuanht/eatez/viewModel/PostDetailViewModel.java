package com.thuanht.eatez.viewModel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.interfaceEvent.CommentCallback;
import com.thuanht.eatez.interfaceEvent.RegisterCallback;
import com.thuanht.eatez.jsonResponse.CommentResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
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
    private Disposable disposable;
    private MutableLiveData<Post> post = new MutableLiveData<>();

    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    private CommentCallback commentCallback;

    private MutableLiveData<String> contentCommentError = new MutableLiveData<>();

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
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull PostResponse postResponse) {
                        if(postResponse != null){
                            post.setValue(postResponse.getData().get(0));

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    public MutableLiveData<Post> getPost() {
        return post;
    }

    public void fetchComments(int postId) {
        ApiService.ApiService.getComments(postId)
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
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull CommentResponse commentResponse) {
                        if (commentResponse != null) {
                            comments.setValue(commentResponse.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }
    public Boolean varidate(String content){
        contentCommentError.setValue(null);
        if(TextUtils.isEmpty(content)){
            contentCommentError.setValue("Please enter your comment!");
            return false;
        }
        return true;
    }
    public void addComment(int userID, int postID, String content, float rating ){
        ApiService.ApiService.setComment(userID, postID, content, rating)
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
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull CommentResponse commentResponse) {
                        if (commentResponse.getStatus()) {
                            commentCallback.onCommentSuccess(commentResponse.getComment());
                        }else{
                            commentCallback.onCommentFailure(commentResponse.getMessage());

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();                    }
                });
    }
    public MutableLiveData<List<Comment>> getComments() {return comments;}
    public MutableLiveData<String> getContentCommentError(){return contentCommentError;}
}
