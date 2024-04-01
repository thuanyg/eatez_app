package com.thuanht.eatez.view.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuanht.eatez.Adapter.PostLatestAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentLatestBinding;
import com.thuanht.eatez.model.Post;

import java.util.ArrayList;
import java.util.List;

public class LatestFragment extends Fragment {
    private FragmentLatestBinding bd;
    private PostLatestAdapter adapter;
    private List<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bd = FragmentLatestBinding.inflate(inflater, container, false);
        initPostLatest();
        return bd.getRoot();
    }

    private void initPostLatest() {
        bd.rcvLatest.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        posts = getPostList();
        adapter = new PostLatestAdapter(posts, requireContext());
        bd.rcvLatest.setAdapter(adapter);
        bd.shimmerHomeLatest.startShimmer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Thực hiện các công việc nặng ở đây
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Sau khi hoàn thành, sử dụng Handler để đưa công việc về giao diện người dùng (UI thread)
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        bd.shimmerHomeLatest.stopShimmer();
                        bd.shimmerHomeLatest.setVisibility(View.GONE);
                        bd.rcvLatest.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    public List<Post> getPostList(){
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("Bánh sầu riêng 9 sạch - Hoàng Diệu 2", "Bánh sầu riêng vỏ mỏng mềm, nhiều sầu riêng, vị béo thơm ngon ngất ngây."));
        postList.add(new Post("Bánh mì ngũ cốc - Quán Bánh Mì Tuấn", "Bánh mì ngũ cốc sơ sẩy hấp dẫn, với những hạt ngũ cốc tươi mới, kết hợp với sốt mayonnaise thơm ngon."));
        postList.add(new Post("Cà phê hạt rang - Cafe Sáng", "Cà phê hạt rang đắng mạnh, hương thơm đặc trưng, phù hợp cho những ai yêu thích cảm giác mạnh mẽ của cà phê."));
        postList.add(new Post("Phở bò tái - Quán Phở Hương", "Phở bò tái nóng hổi, nước dùng ngọt đậm, thịt bò mềm, dai, kèm theo rau sống, chanh và ớt tạo cảm giác thơm ngon đầy đặn."));
        postList.add(new Post("Gỏi cuốn tôm thịt - Quán Gỏi Cuốn Hương Sen", "Gỏi cuốn tôm thịt tươi ngon, bọc trong lớp bánh tráng mềm, kèm theo nước mắm chua ngọt và rau sống tươi mát."));
        postList.add(new Post("Bánh canh cua - Quán Bánh Canh 3 Người Anh Em", "Bánh canh cua đặc trưng với nước dùng đậm đà, cua tươi ngon, thêm chút hành phi và rau sống tạo nên hương vị đặc biệt."));
        postList.add(new Post("Sushi combo - Nhà Hàng Nhật Bản Sakura", "Sushi combo đa dạng với các loại cá, tôm, cua và..."));
        postList.add(new Post("Mỳ quảng gà - Quán Mỳ Quảng 4 Tư Thế", "Mỳ quảng gà nổi tiếng với nước dùng thơm ngon, mỳ dai và thịt gà thượng hạng."));
        postList.add(new Post("Gà nướng muối ớt - Gà Rán Chị Hảo", "Gà nướng muối ớt giòn thơm, vị cay nồng đặc trưng của ớt, phù hợp cho các tín đồ của ẩm thực miền Nam."));
        postList.add(new Post("Bún riêu cua - Quán Bún Riêu Tí Hon", "Bún riêu cua nổi tiếng với nước dùng đậm đà, cua tươi ngon và bún mềm, dai."));
        postList.add(new Post("Nước mía lúa mạch - Quán Nước Mía Bà Năm", "Nước mía lúa mạch tươi mát, hấp dẫn, không chỉ giải khát mà còn cung cấp nhiều dưỡng chất."));
        postList.add(new Post("Bánh tráng trộn - Bánh Tráng Tôm Trứng Minh Châu", "Bánh tráng trộn cay nồng, vị chua ngọt, hòa quyện cùng các loại gia vị và nguyên liệu độc đáo."));
        postList.add(new Post("Bò kho - Quán Bò Kho Ông Sáu", "Bò kho thơm ngon, thịt bò mềm, ngấm gia vị, kèm theo bánh mì nóng hổi."));
        postList.add(new Post("Cơm gà xối mỡ - Quán Cơm Gà Anh Tài", "Cơm gà xối mỡ giòn thơm, vị ngọt của gà và hòa quện cùng mỡ hành."));
        postList.add(new Post("Hủ tiếu Nam Vang - Quán Hủ Tiếu Ba Duy", "Hủ tiếu Nam Vang hấp dẫn với nước dùng đậm đà, thịt heo, tôm và các loại rau sống."));
        postList.add(new Post("Bánh xèo - Quán Bánh Xèo Anh Đạt", "Bánh xèo giòn rụm, nhân thịt, tôm, đậu xanh và rau sống, kèm nước mắm chua ngọt."));
        postList.add(new Post("Bánh flan - Quán Bánh Flan Gia Bảo", "Bánh flan mềm mịn, ngọt thanh, thơm bơ và vani."));
        postList.add(new Post("Chè thập cẩm - Quán Chè Ba Tiền", "Chè thập cẩm đa dạng với các loại đậu, ngô, bột lọc và trái cây tạo nên hương vị đặc biệt."));
        postList.add(new Post("Bún chả Hà Nội - Quán Bún Chả 30 Tư Thế", "Bún chả Hà Nội với nước chấm chua ngọt, thịt nướng thơm phức, bún mềm, dai."));
        postList.add(new Post("Xôi gà - Quán Xôi Gà Ông Văn", "Xôi gà ngon hấp dẫn, màu sắc bắt mắt, vị ngọt của xôi cùng vị béo của gà."));
        postList.add(new Post("Bánh bao nhân sầu riêng - Quán Bánh Bao Bà Năm", "Bánh bao nhân sầu riêng thơm ngon, vỏ mềm, nhân béo ngậy."));
        postList.add(new Post("Cơm tấm - Quán Cơm Tấm Ba Gia", "Cơm tấm với cơm nở hương, thịt heo ba chỉ, trứng hấp và dưa leo giòn."));
        postList.add(new Post("Trà sữa trân châu đen - Trà Sữa Táo Đỏ", "Trà sữa trân châu đen ngọt ngào, hòa quện với sữa và trân châu dai."));
        postList.add(new Post("Bún đậu mắm tôm - Quán Bún Đậu Ông Hùng", "Bún đậu mắm tôm hấp dẫn với đậu phụng, bún mềm và mắm tôm đậm đà."));
        postList.add(new Post("Bánh xèo mướp đắng - Quán Bánh Xèo Bà Lan", "Bánh xèo mướp đắng giòn rụm, nhân thịt và tôm."));
        postList.add(new Post("Bún ốc - Quán Bún Ốc Chị Tám", "Bún ốc với nước dùng đậm đà, ốc sạch và rau sống tươi mát."));
        postList.add(new Post("Bánh mì chảo - Quán Bánh Mì Chảo Bà Hòa", "Bánh mì chảo nóng hổi, thơm phức, béo ngậy."));
        postList.add(new Post("Chè bưởi - Quán Chè Bưởi 3 Ông Tướng", "Chè bưởi với bưởi tươi mát, dẻo dai, hòa quện cùng nước cốt dừa."));
        postList.add(new Post("Bò bía - Quán Bò Bía Bà Dưỡng", "Bò bía với thịt bò, trứng, dưa leo và rau sống, chấm nước mắm."));
        postList.add(new Post("Bánh tráng cuốn thịt heo - Quán Bánh Tráng Cuốn Anh Tú", "Bánh tráng cuốn thịt heo giòn thơm, ăn kèm rau sống và nước mắm."));
        postList.add(new Post("Bánh mì que - Quán Bánh Mì Que Bà Hòa", "Bánh mì que giòn tan, nhân thịt ngọt ngào, ăn kèm nước mắm."));
        postList.add(new Post("Cháo lòng - Quán Cháo Lòng Ông Bảy", "Cháo lòng thơm ngon, lòng heo mềm, kèm hành phi và tiêu."));
        postList.add(new Post("Bún mắm - Quán Bún Mắm Ba Xứ", "Bún mắm đậm đà với nước mắm, cá và rau sống tươi mát."));
        postList.add(new Post("Cà phê sữa đá - Quán Cà Phê Sữa Đá Anh Tuấn", "Cà phê sữa đá thơm ngon, đậm đà, đá xay nhuyễn."));
        postList.add(new Post("Bánh ướt lòng gà - Quán Bánh Ướt Lòng Gà 7 Tính", "Bánh ướt lòng gà mềm, nước mắm chấm thơm ngon, lòng gà giòn."));
        postList.add(new Post("Bánh canh cua - Quán Bánh Canh Cua Ông Chính", "Bánh canh cua với nước dùng đậm đà, cua tươi ngon và bánh canh dai."));
        postList.add(new Post("Ốc len xào dừa - Quán Ốc Len Xào Dừa 5 Sao", "Ốc len xào dừa với dừa thơm ngon, ốc len dai, béo ngậy."));
        postList.add(new Post("Mì xào hải sản - Quán Mì Xào Hải Sản Đại Dương", "Mì xào hải sản đa dạng với các loại tôm, mực và cá."));
        postList.add(new Post("Bánh bao nhân thịt - Quán Bánh Bao Bà Mười", "Bánh bao nhân thịt mềm, nhân thịt thơm ngon, ăn kèm nước mắm."));
        postList.add(new Post("Bún riêu cua - Quán Bún Riêu Cua Bà Tám", "Bún riêu cua đậm đà, cua tươi ngon, bún mềm và dưa leo giòn."));
        postList.add(new Post("Chè trôi nước - Quán Chè Trôi Nước 6 Góc Tính", "Chè trôi nước mềm, đường phèn thơm ngon, nước cốt dừa."));
        postList.add(new Post("Bánh bèo - Quán Bánh Bèo 9 Góc Tính", "Bánh bèo nhỏ xinh, nước mắm chấm đậm đà, chà bông thơm."));
        postList.add(new Post("Bánh flan trứng gà - Quán Bánh Flan Bà Lành", "Bánh flan trứng gà mềm mịn, hương vani thơm ngon."));
        postList.add(new Post("Bánh mì kẹp thịt - Quán Bánh Mì Kẹp Bà Hà", "Bánh mì kẹp thịt giòn, nhân thịt ngọt ngào, ăn kèm rau sống."));
        postList.add(new Post("Cháo gà - Quán Cháo Gà Ông Địa", "Cháo gà mềm, thơm ngon, ăn kèm hành phi và tiêu."));
        postList.add(new Post("Bún thang - Quán Bún Thang Ông Dương", "Bún thang đặc trưng với nước dùng thơm ngon, gà, trứng và nấm."));
        postList.add(new Post("Nước ép trái cây - Quán Nước Ép Trái Cây Ba Dũng", "Nước ép trái cây tươi mát, đa dạng với các loại trái cây."));
        postList.add(new Post("Bánh tráng trộn - Quán Bánh Tráng Trộn Bà Mười", "Bánh tráng trộn cay nồng, vị chua ngọt, hòa quyện cùng các loại gia vị."));
        postList.add(new Post("Bò lúc lắc - Quán Bò Lúc Lắc 8 Chân", "Bò lúc lắc thơm ngon, giòn tan, ăn kèm cơm trắng."));
        return postList;
    }
}