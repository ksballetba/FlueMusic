package com.ksballetba.fluemusic.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class CircleImageView:ImageView {

    private var mPaint:Paint? = null
    private var mRadius:Int? = null
    private var mScale:Float? = null
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredWidth,measuredHeight)
        mRadius = size/2
        setMeasuredDimension(size,size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint = Paint()
        val bitmap = drawableToBitmap(drawable)
        val bitmapShader = BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        mScale = (mRadius?.times(2.0f))?.div(Math.min(bitmap.width,bitmap.height))
        var matrix = Matrix()
        matrix.setScale(mScale!!,mScale!!)
        bitmapShader.setLocalMatrix(matrix)
        mPaint?.setShader(bitmapShader)
        canvas?.drawCircle(mRadius!!.toFloat(),mRadius!!.toFloat(),mRadius!!.toFloat(),mPaint)

    }

    private fun drawableToBitmap(drawable:Drawable):Bitmap{
        if(drawable is BitmapDrawable){
            val bd = drawable
            return bd.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,w,h)
        drawable.draw(canvas)
        return bitmap
    }

}