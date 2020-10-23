package com.mark.markaop;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.utils.MPermissionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case LoginActivity.REQUEST_CODE:
                List<Method> mMethodList = MarkAOPHelper.getInstance().getMethodList();
                HashMap<String, Object[]> mMethodArgs = MarkAOPHelper.getInstance().getMethodArgs();
                HashMap<String, Object> mTargets = MarkAOPHelper.getInstance().getTargets();
                for (Method method : mMethodList) {
                    try {
                        method.setAccessible(true);
                        Object[] args = mMethodArgs.get(method.getName());
                        Object target = mTargets.get(method.getName());
                        if (target != null) {
                            if (args != null) {
                                method.invoke(target, args);
                            } else {
                                method.invoke(target);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                MarkAOPHelper.getInstance().clear();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
