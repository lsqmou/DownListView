package com.lmoumou.lib_downlistview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow

/**
 * Author: Lmoumou
 * Date: 2018-07-27 10:44
 * 文件名: DownListView2
 * 描述:
 */
class DownListView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    /**
     * 控件宽，高
     * */
    private var abWith: Int = 0
    private var abHeight: Int = 0
    /**
     * 背景框画笔
     * */
    private val bgPaint: Paint by lazy { Paint() }
    /**
     * 背景圆角矩形大小
     * */
    private val rectf: RectF by lazy { RectF() }

    /**
     * 背景框线条粗细
     * */
    private var frameSize: Float = 4F

    /**
     * 背景框颜色
     * */
    private var frameColor = Color.parseColor("#000000")

    /**
     * 分隔线画笔
     * */
    private val divisionPaint: Paint by lazy { Paint() }

    /**
     * 分割线粗细
     * */
    private var divisionSize = 2F

    /**
     * 三角形画笔
     * */
    private val trigoPaint: Paint by lazy { Paint() }

    /**
     * 三角形颜色
     * */
    private var trigoColor = Color.parseColor("#000000")

    /**
     * 文字画笔
     * */
    private val textPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    /**
     * 文字绘制所在的矩形
     * */
    private val textRect: Rect by lazy { Rect() }


    /**
     * 文本大小
     * */
    private var textSize: Float = 40F

    /**
     * 文本颜色
     * */
    private var textColor = Color.parseColor("#000000")

    /**
     * 弹窗所占屏幕的高度比例
     * */
    private var maxHeight: Int = -1

    /**
     * 弹窗背景
     * */
    private var popupBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.popup_bg_2)

    /**
     * 文本内容
     * */
    var textStr: String = ""
        set(value) {
            field = value
            textPaint.getTextBounds(value, 0, value.length, textRect)
            invalidate()
        }


    private var popupWindow: PopupWindow? = null

    init {

        val t = context.obtainStyledAttributes(attrs, R.styleable.DownListView)
        textStr = t.getString(R.styleable.DownListView_dl_text)
        textSize = t.getDimension(R.styleable.DownListView_dl_textSize, 40F)
        textColor = t.getColor(R.styleable.DownListView_dl_textColor, Color.parseColor("#000000"))
        frameSize = t.getDimension(R.styleable.DownListView_dl_frameSize, 4F)
        frameColor = t.getColor(R.styleable.DownListView_dl_frameColor, Color.parseColor("#000000"))
        divisionSize = t.getDimension(R.styleable.DownListView_dl_divisionSize, 2F)
        trigoColor = t.getColor(R.styleable.DownListView_dl_trigoColor, Color.parseColor("#000000"))
        maxHeight = t.getInt(R.styleable.DownListView_dl_maxHeight, -1)
        popupBg = t.getDrawable(R.styleable.DownListView_dl_popupBg)

        initBgPaint()
        initDivisionPaint()
        initTrigoPaint()
        initTextPaint()

        t.recycle()

        this.setOnClickListener {
            if (popupWindow == null) {
                showPopWindow(it)
            } else {
                closePopWindow()
            }
        }
    }

    /**
     * 初始化背景框画笔
     * */
    private fun initBgPaint() {
        bgPaint.color = frameColor
        bgPaint.strokeWidth = frameSize
        bgPaint.style = Paint.Style.STROKE
        bgPaint.isAntiAlias = true
    }

    /**
     * 初始化分隔线画笔
     * */
    private fun initDivisionPaint() {
        divisionPaint.color = frameColor
        divisionPaint.style = Paint.Style.STROKE
        divisionPaint.strokeWidth = divisionSize
        divisionPaint.isAntiAlias = true
    }

    /**
     * 初始三角形画笔
     * */
    private fun initTrigoPaint() {
        trigoPaint.color = trigoColor
        trigoPaint.style = Paint.Style.FILL
        // trigoPaint.strokeWidth = 1F
        trigoPaint.isAntiAlias = true
    }

    /**
     * 初始化文字画笔
     * */
    private fun initTextPaint() {
        textPaint.textSize = textSize
        textPaint.color = textColor
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.getTextBounds(textStr, 0, textStr.length, textRect)
        //textPaint.getTextBounds(ellipsis, 0, ellipsis.length, ellipsisRect)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)



        abHeight = if (heightModel == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            val textHeight = textRect.height()//文本高度
            paddingTop + textHeight + paddingBottom
        }

        abWith = if (widthModel == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            val textWidth: Int = textRect.width()//文本宽度

            val withNow = paddingLeft + textWidth + paddingRight + abHeight

            //宽度超出屏幕宽度需要处理
            if (withNow > getWH(context)[0]) {
                getWH(context)[0]
            } else {
                withNow
            }

        }

        //文本宽度超出控件宽度，需要处理
        /*if (textRect.width() > abWith - abHeight - divisionPaint.strokeWidth) {
            Log.e("DownListView2", "文本超出长度")
            var l = textRect.width() - (abWith - abHeight - divisionPaint.strokeWidth) - ellipsisRect.width()

        }*/


        setMeasuredDimension(abWith, abHeight)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawBg(it)
            drawDivision(it)
            drawTrgio(it)
            drawText(it)
        }
    }

    /**
     * 画矩形背景框
     * */
    private fun drawBg(canvas: Canvas) {
        rectf.set(0F, 0F, abWith.toFloat(), abHeight.toFloat())
        canvas.drawRect(rectf, bgPaint)
    }

    /**
     * 画分割线
     * */
    private fun drawDivision(canvas: Canvas) {
        canvas.drawLine((abWith - abHeight).toFloat(), 0F, (abWith - abHeight).toFloat(), abWith.toFloat(), divisionPaint)
    }

    /**
     * 画三角形
     * */
    private fun drawTrgio(canvas: Canvas) {
        val path = Path()
        path.moveTo((abWith - (abHeight / 2)).toFloat(), (abHeight * 3 / 4).toFloat())
        path.lineTo((abWith - abHeight * 5 / 6).toFloat(), (abHeight / 4).toFloat())
        path.lineTo((abWith - abHeight / 6).toFloat(), (abHeight / 4).toFloat())
        path.close()
        canvas.drawPath(path, trigoPaint)
    }

    /**
     * 画文字
     * */
    private fun drawText(canvas: Canvas) {
        textRect.left = 0
        textRect.top = 0
        textRect.right = abWith - abHeight
        textRect.bottom = abHeight
        val fontMetrics = textPaint.fontMetricsInt
        val baseLine: Int = (textRect.top + textRect.bottom - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(textStr, textRect.centerX().toFloat(), baseLine.toFloat(), textPaint)
    }


    @SuppressLint("InflateParams")
    private fun showPopWindow(view: View) {
        val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView: View = mInflater.inflate(R.layout.popup_view, null, false)
        val mRecyclerView: RecyclerView = contentView.findViewById(R.id.mRecyclerView)
        listener?.bindAdapter(mRecyclerView)
        popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        popupWindow?.animationStyle = R.style.popupWinsowAnim
        popupWindow?.width = view.width
        if (maxHeight in 1..100) {
            popupWindow?.height = (getWH(context)[1] * maxHeight / 100)
        }
        popupWindow?.setBackgroundDrawable(popupBg)
        popupWindow?.isOutsideTouchable = true
        popupWindow?.showAsDropDown(view)

    }

    fun closePopWindow() {
        popupWindow?.dismiss()
        popupWindow = null
    }

    private var listener: BindListener? = null

    fun setBindListener(listener: BindListener) {
        this.listener = listener
    }

    interface BindListener {
        fun bindAdapter(recycler: RecyclerView)
    }


    private fun getWH(context: Context): IntArray {
        val whArray = IntArray(2)
        val dm: DisplayMetrics = context.resources.displayMetrics
        whArray[0] = dm.widthPixels
        whArray[1] = dm.heightPixels
        return whArray
    }
}