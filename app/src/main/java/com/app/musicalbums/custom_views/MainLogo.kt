package com.app.musicalbums.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.app.musicalbums.R


//ffcab1
//06D6A0
//8E4162
//41393E
//191716

class MainLogo(context: Context,attrs: AttributeSet) : View(context, attrs){
    private var animator: ValueAnimator? = null
    private var oppositeAnimator: ValueAnimator? = null
    var shader: Shader = LinearGradient(0f, 0f, 0f, 200f,
        intArrayOf(
              ContextCompat.getColor(context,R.color.purple)
            , ContextCompat.getColor(context,R.color.gradient_shade_1)
            , ContextCompat.getColor(context,R.color.gradient_shade_2)
            , ContextCompat.getColor(context,R.color.gradient_shade_3)
            , ContextCompat.getColor(context,R.color.gradient_shade_4)
            , ContextCompat.getColor(context,R.color.gradient_shade_5)
            , ContextCompat.getColor(context,R.color.gradient_shade_6)
            , ContextCompat.getColor(context,R.color.gradient_shade_7)
        ),
        null

        , Shader.TileMode.CLAMP)

    var screenWidth = context.resources.displayMetrics.widthPixels
    var screenHeight = context.resources.displayMetrics.heightPixels
    var strokeWidth = screenWidth/10f

    var startPointX = screenWidth/2f
    var startPointY = 0f
    var endPointX = screenWidth/2f
    var endPointY =0f
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }
    var stop = screenHeight/5f
    private var oppositeStartPointX = screenWidth/2f - strokeWidth
    var oppositeStartPointY = stop
    private var oppositeEndPointX = screenWidth/2f - strokeWidth
    var oppositeEndPointY = stop
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }
    val paint = Paint().also {
        it.color = Color.RED
        it.strokeWidth = this.strokeWidth
        it.shader = this.shader
    }
    val paintAlternateLines = Paint().also {
        it.color = Color.BLUE
        it.strokeWidth = this.strokeWidth
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        oppositeAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator = ValueAnimator.ofFloat(startPointY, stop).apply {
            addUpdateListener {
                endPointY = it.animatedValue as Float
            }
            duration = 1500L
            //repeatMode = ValueAnimator.RESTART
            //repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        oppositeAnimator = ValueAnimator.ofFloat(stop, 0f).apply {
            addUpdateListener {
                oppositeEndPointY = it.animatedValue as Float
            }
            duration = 1500L
            //repeatMode = ValueAnimator.RESTART
            //repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
//         if(endPointY < stop && oppositeStartPointY > 0){
//             endPointY += 5
//             oppositeEndPointY -= 5
//             //invalidate()
//            postInvalidateDelayed(10)
//
//         }

        canvas.drawLine(startPointX, startPointY, endPointX,endPointY,paint)
        canvas.drawLine(startPointX - strokeWidth*2, startPointY , endPointX - strokeWidth*2, endPointY,paint)
        canvas.drawLine(startPointX + strokeWidth*2, startPointY , endPointX + strokeWidth*2, endPointY,paint)
        //canvas.drawLine(startPointX + strokeWidth*6, startPointY , endPointX + strokeWidth*6, endPointY,paint)

        canvas.drawLine(oppositeStartPointX, oppositeStartPointY, oppositeEndPointX,oppositeEndPointY,paintAlternateLines)
        canvas.drawLine(oppositeStartPointX + strokeWidth*2, oppositeStartPointY , oppositeEndPointX + strokeWidth*2, oppositeEndPointY,paintAlternateLines)
//        canvas.drawLine(oppositeStartPointX + strokeWidth*4, oppositeStartPointY , oppositeEndPointX + strokeWidth*4, oppositeEndPointY,paintAlternateLines)
       // canvas.drawLine(oppositeStartPointX + strokeWidth*5, oppositeStartPointY , oppositeEndPointX + strokeWidth*5, oppositeEndPointY,paintAlternateLines)

    //draw circles separated by a space the size of waveGap
//        var currentRadius = initialRadius
//        while (currentRadius < maxRadius) {
//            canvas.drawCircle(center.x, center.y, currentRadius, wavePaint)
//            currentRadius += waveGap
//        }
    }
}