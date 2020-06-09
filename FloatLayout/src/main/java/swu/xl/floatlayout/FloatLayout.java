package swu.xl.floatlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FloatLayout extends ViewGroup {

    //水平间距
    private int horizontalSpace = 10;
    //垂直间距
    private int verticalSpace = 10;

    /**
     * 构造方法：Java代码初始化
     * @param context
     */
    public FloatLayout(Context context) {
        super(context);
    }

    /**
     * 构造方法：Xml代码初始化
     * @param context
     * @param attrs
     */
    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //解析属性
        if (attrs != null){
            //获取所有属性值的集合
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatLayout);

            //解析每一个属性
            horizontalSpace = typedArray.getInteger(R.styleable.FloatLayout_horizontalSpace,horizontalSpace);
            verticalSpace = typedArray.getInteger(R.styleable.FloatLayout_verticalSpace,verticalSpace);

            //释放资源
            typedArray.recycle();
        }
    }

    //设置水平间距
    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    //设置垂直间距
    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    //因为我们需要支持margin,所以需要重写generateLayoutParams方法并创建MarginLayoutParams对象
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
    /*@Override
    protected ViewGroup.LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }*/
    /*@Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }*/

    //存储每一行的最大高度
    private List<Integer> heights = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获得测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //wrap_content模式下，存储宽和高
        int wrapWidth = 0;
        int wrapHeight = 0;

        //padding的宽度
        int widthUsed = getPaddingLeft() + getPaddingRight();

        //存储当前行的宽度
        int lineWidth = widthUsed;
        //存储一行的最大高度
        int lineHeight = 0;

        //遍历子View进行测量
        for (int i = 0; i < getChildCount(); i++) {
            //获取子View
            View child = getChildAt(i);

            //子View为GONE则跳过
            if (child.getVisibility() == View.GONE){
                continue;
            }

            //获得一个支持margin的布局参数
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //测量每个child的宽高，每个child可用的最大宽高为 widthSize-padding-margin
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);

            //child实际占据的宽高
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //判断这一行还能否装得下这个child
            if (lineWidth + childWidth <= widthSize) {
                //装得下,累加宽度,并记录最大高度
                lineWidth += childWidth + horizontalSpace;

                //最大高度
                lineHeight = Math.max(lineHeight,childHeight);
           }else {
                //装不下,需要换行,记录这一行的宽度,高度,下一行的初始宽度，高度

                //比较当前行宽度和下一行宽度，取最大值
                wrapWidth = Math.max(lineWidth - horizontalSpace, widthUsed + childWidth);

                //换行，记录新行的初始宽度
                lineWidth = widthUsed + childWidth + horizontalSpace;

                //累计当前高度
                wrapHeight += lineHeight + verticalSpace;
                //保存每一行的最大高度
                heights.add(lineHeight);

                //记录下一行的初始高度
                lineHeight = childHeight;
            }

            //如果是最后一个child,则将当前记录的最大宽度和当前lineWidth做比较
            if (i == getChildCount() - 1){
                wrapWidth = Math.max(wrapWidth,lineWidth - horizontalSpace);

                //累加高度
                wrapHeight += lineHeight;
            }
        }

        //根据测量模式去保存相应的测量宽度
        //即如果是MeasureSpec.EXACTLY直接使用父ViewGroup传入的宽和高
        //否则设置为自己计算的宽和高，即为warp_content时
        setMeasuredDimension(
                (widthMode == MeasureSpec.EXACTLY) ? widthSize : wrapWidth,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize : wrapHeight
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取视图宽度
        int width = getWidth();
        //记录当前行号
        int line = 0;
        //存储padding的宽度
        int widthUsed = getPaddingLeft() + getPaddingRight();
        //记录当前行的宽度
        int lineWidth = widthUsed;
        //开始的横坐标
        int left = getPaddingLeft();
        //开始的纵坐标
        int top = getPaddingTop();

        //遍历所有的子View
        for (int i = 0; i < getChildCount(); i++) {
            //获取子View
            View child = getChildAt(i);

            //子View为GONE则跳过
            if (child.getVisibility() == View.GONE){
                continue;
            }

            //获得一个支持margin的布局参数
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //child测量的宽
            int childWidth = child.getMeasuredWidth();

            //判断这一行是否装得下这个child，需要把margin加上
            if (lineWidth + childWidth + layoutParams.leftMargin + layoutParams.rightMargin <= width) {
                //装得下，累加这一行的宽度
                lineWidth += childWidth + horizontalSpace;
            }else {
                //装不下,需要换行，记录新行的宽度，并设置新的left，right的位置

                //重置left
                left = getPaddingLeft();

                //top累加当前行的最大高度和行间距
                top += heights.get(line++) + verticalSpace;

                //开始新行，记录宽度
                lineWidth = widthUsed + childWidth + horizontalSpace;
            }

            //计算child的left，top，right，bottom
            int lc = left + layoutParams.leftMargin;
            int tc = top + layoutParams.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            //计算child的位置
            child.layout(lc,tc,rc,bc);

            //left向右移动一个间距
            left = layoutParams.rightMargin + rc + horizontalSpace;
        }
    }
}
