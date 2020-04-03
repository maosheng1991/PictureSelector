package pictureselect.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @ClassName SquareImageView
 * @Description 正方形imageView
 * @Author ytf
 * CreateDate    2020/4/1 14:10
 */
public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //关键  用测量出来的宽代替高
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
