使用方式：

步骤一、 在project的 build.Gradle 中添加仓库

```cpp
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

步骤二、 添加依赖

```cpp
dependencies { implementation 'com.github.ytf12138:PictureSelector:v1.2' }
```

在代码中的调用：

只用一行代码搞定：

```cpp
PictureSelector.getInstance()
                        .setTitle("图片选择")//设置标题
                        .setMaxCount(9)//设置最大选择数量
                        .setShowImage(true)//是否显示图片，默认true
                        .setShowVideo(true)//是否显示视频，默认false
                        .setSelectType(false)//是否能同时选择图片和视频，默认false
                        .setShowCamera(false)//是否显示拍照item，默认false
                        .start(MainActivity.this, 1);//设置回调时 requestCode
```

选择结果的接收：
在 Activity 的回调方法中：

```cpp
@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
           ArrayList<String> arrayList = data.getStringArrayListExtra(PictureSelector.SELECT_ITEM);
        }
    }
```

arrayList 中保存着选中的图片路径
