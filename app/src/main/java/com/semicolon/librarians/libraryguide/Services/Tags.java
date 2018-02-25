package com.semicolon.librarians.libraryguide.Services;

/**
 * Created by Delta on 21/12/2017.
 */

public class Tags {
    public static final String image_path="http://librarians.liboasis.com/public/uploads/images/";
    public static final String email_Regex="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String url_Regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";
    public static final String username_Regex="^[A-Za-z]([A-Za-z0-9]+(_|-|\\.)?[A-Za-z0-9]*)*[A-Za-z0-9]+$";
    public static final String name_Regex="[^0-9@!#$%\\^&()-\\*\\+<>/\\+~\\s]+$";
    public static final String pass_Regex="^[A-Za-z0-9_.-]+$";
    public static final String phone_Regex="[0-9()/\\.N,;#\\*\\+\\-\\s]+$";
    public static final String font = "fonts/kacst_title.ttf";
    //http://librarians.liboasis.com/api/service/library_news
    //http://librarians.liboasis.com/api/service/publishers
    //http://librarians.liboasis.com/api/service/jobs

}
