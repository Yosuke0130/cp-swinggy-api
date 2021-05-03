package com.example.demo.domain.user;

import java.net.URL;

public class ProfileImageURL {

    private URL profileImageURL;

    //コンストラクタ生成時にデフォルト画像をセット
    public ProfileImageURL(URL defaultURL) {

        this.profileImageURL = defaultURL;

    }

    public URL getValue() {return profileImageURL;}

    public void setValue(URL profileImageURL) {this.profileImageURL = profileImageURL;}

}
