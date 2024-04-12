package com.thuanht.eatez.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetworkUtils {

    /**
     * Kiểm tra xem thiết bị có kết nối mạng không
     *
     * @param context Context của ứng dụng
     * @return True nếu có kết nối mạng, False nếu không có kết nối mạng
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // Lấy tất cả các mạng đang hoạt động
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))) {
                    return true; // Trả về true nếu có một trong những loại kết nối mạng đáp ứng
                }
            }
        }
        return false; // Trả về false nếu không có kết nối mạng nào đáp ứng
    }
}
