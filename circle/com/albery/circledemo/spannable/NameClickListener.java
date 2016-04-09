package com.albery.circledemo.spannable;

import android.text.SpannableString;
import android.widget.Toast;

import com.qipilang.lvyouplatform.application.MyApplication;

/**
 *
 * @ClassName: NameClickListener
 * @Description: 点赞或评论中人名的点击事件
 * @author yiw
 *
 */
public class NameClickListener implements ISpanClick {
    private SpannableString userName;
    private String userId;

    public NameClickListener(SpannableString name, String userid) {
        this.userName = name;
        this.userId = userid;
    }

    @Override
    public void onClick(int position) {
        
    }
}
