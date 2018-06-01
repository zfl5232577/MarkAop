# MarkAop
面向切面编程-使代码更加清晰————工具使用aspectj
  
AOP切面框架：

    buildscript {
       repositories {
          maven { url "https://raw.githubusercontent.com/zfl5232577/maven/master" }
      }
      dependencies {
          classpath 'com.mark:markaop-plugin:1.0.0'
      }
    }
    
app的build.gradle文件

    apply plugin: 'com.android.application'
    apply plugin: 'markaop-plugin'
  
  
