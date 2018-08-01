# DownListView

一个简单的下拉列表。
一个PopupWindow弹窗，布局控件RecyclerView通过接口BindListener将RecyclerView抛出来，RecyclerView的适配器需要自己去实现，不在这做解释
## Example
![images](./20180801_143605.gif)
##Download
```gradle
allprojects {
	repositories {
	    jcenter()
		maven { url "https://jitpack.io" }
	}
}
dependencies {
    compile 'com.lmoumou:lib_downlistview:1.0.0'
}
```
## Usage

1.在xml布局中
```xml
<com.lmoumou.lib_downlistview.DownListView
        android:id="@+id/mDownListView"
        android:background="@android:color/white"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="50dp"
        android:paddingBottom="12dp"
        android:paddingRight="24dp"
        android:paddingTop="12dp"
        app:dl_divisionSize="2dp"
        app:dl_frameColor="@android:color/holo_blue_bright"
        app:dl_frameSize="4dp"
        app:dl_maxHeight="50"
        app:dl_popupBg="@drawable/popup_bg_2"
        app:dl_text="显示的内容"
        app:dl_textColor="@android:color/holo_green_light"
        app:dl_textSize="20sp"
        app:dl_trigoColor="@android:color/holo_green_light"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
#Attrs
```xml
 <declare-styleable name="DownListView">
        <!--文本内容-->
        <attr name="dl_text" format="string|reference" />
        <!--文本大小-->
        <attr name="dl_textSize" format="dimension|reference" />
        <!--文本颜色-->
        <attr name="dl_textColor" format="color|reference" />
        <!--背景框线的粗细-->
        <attr name="dl_frameSize" format="dimension|reference" />
        <!--背景框颜色-->
        <attr name="dl_frameColor" format="reference|color" />
        <!--分隔线粗细-->
        <attr name="dl_divisionSize" format="dimension|reference" />
        <!--三角形颜色-->
        <attr name="dl_trigoColor" format="color|reference" />
        <!--弹窗所占屏幕的高度比例-->
        <attr name="dl_maxHeight" format="integer" />
        <!--弹窗背景-->
        <attr name="dl_popupBg" format="reference" />
    </declare-styleable>
```

