package com.example.demo.domain.user;

import java.net.URL;

public class ProfileImageURL {

    private URL profileImageURL;

    //コンストラクタ生成時にデフォルト画像をセット
    public ProfileImageURL(URL url) {

        this.profileImageURL = url;

    }

    public URL getValue() {return this.profileImageURL;}
}
