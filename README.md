# MarkAop
面向切面编程-使代码更加清晰————工具使用aspectj

基于aspectj的AOP，无需使用耗费性能的反射.不过,需要在build.gradle中配置一下aspectj
  
## AOP切面框架：

    buildscript {
       repositories {
          maven { url "https://raw.githubusercontent.com/zfl5232577/maven/master" }
      }
      dependencies {
          classpath 'com.mark:markaop-plugin:1.3.3'
      }
    }
    
    allprojects {
      repositories {
          maven { url "https://raw.githubusercontent.com/zfl5232577/maven/master" }
      }
    }
    
Module的build.gradle文件

    apply plugin: 'markaop-plugin'

## 混淆规则:
    -keep class com.mark.** { *; }
    -dontwarn com.mark.**
    -keepclassmembers class ** {
        @com.mark.aoplibrary.annotation.* <methods>;
    }
    
## 框架初始化:
------
```Java在Appliction的onCreate

	MarkAOPHelper.getInstance().init(context);//初始化
  	MarkAOPHelper.getInstance().getOptions().setDebug(BuildConfig.DEBUG);//日志打印Logger和Timelog的开关
  	MarkAOPHelper.getInstance().getOptions().setLoginActivity(LoginActivity.class
		,LoginActivity.REQUEST_CODE);//设置登陆界面和跳转登陆界面的申请码
  
```

登陆成功后一定记得调用：

    SPUtils.getInstance().put("isLogin",true);

检查登陆和检查权限配置需要麻烦点，因为登陆会完成你登陆前的操作，检查权限会自己去申请权限或者弹出提示框。

## 配置如下：
BaseActivity添加下面函数和变量：

	
	
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


| 注解名称         | 作用          | 备注          |
| -------------   |:-------------:| :-------------:|
| @Async          |借助rxjava,异步执行app中的方法| 尽量使用在静态方法上，否则很容易内存泄漏，线程会持有Activity的引用|
| @CheckLogin     |检查是否登陆，设置了登陆界面直接登陆界面，登陆完成后自己执行上此操作|
| @Logger         |将方法的入参和出参都打印出来,可以用于调试|       |
| @CheckNet       |可以在调用某个方法之前，检查网络连接状况，没有连接，注解不带方法名参数则不操作并弹出Toast，假如有方法名参数则执行该方法|
| @CheckPermission|适配6.0动态权限，没有权限并且申请权限|
| @SingleClick    |防止按钮被连点，执行重复操作，点击间隔为600ms|支持追踪匿名内部类中的方法    |
| @TimeLog        |用于追踪某个方法花费的时间,可以用于性能调优的评判|支持追踪匿名内部类中的方法       |
| @NotOpen        |用于拦截某个方法，功能暂未完成，弹出Toast提示      |
  
  
