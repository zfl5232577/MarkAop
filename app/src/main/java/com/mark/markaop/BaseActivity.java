package com.mark.markaop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mark.aoplibrary.utils.MPermissionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/31
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class BaseActivity extends AppCompatActivity {
    private List<Method> mMethodList = new ArrayList<>();
    private HashMap<String, Object[]> mMethodArgs = new HashMap<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case LoginActivity.REQUEST_CODE:
                for (Method method : mMethodList) {
                    try {
                        method.setAccessible(true);
                        if (mMethodArgs.get(method.getName()) != null) {
                            method.invoke(this, mMethodArgs.get(method.getName()));
                        }else {
                            method.invoke(this);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                mMethodList.clear();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
