package com.nik35.smartstorehouse.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik35.smartstorehouse.MainActivity;
import com.nik35.smartstorehouse.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;

public class DemoBubblesView extends View {

    private String text = "";
    private String date = "";

    private final RectF bubbleRect = new RectF();
    private final Paint bubblePaint = new Paint();
    private TextRect textRect;

    private int outerPadding;
    private int outerPaddingBoth;
    private int bubblePadding;

    private Bitmap scaled;

    private Point displaySize;

    private Paint fontPaint;
    private Paint fontDatePaint;
    private float dp;

    private int paddingTop = 200;
    private int paddingStartEnd = 0;

    private Calendar calendar;
    private DateFormat dateFormat;

    public DemoBubblesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        dp = context.getResources().getDisplayMetrics().density;

        outerPadding = Math.round(16f * dp);
        outerPaddingBoth = (1 + 1) * outerPadding;
        bubblePadding = Math.round(8f * dp);

        // create text rect for this font
        {
            fontPaint = new Paint();
            fontPaint.setColor(Color.BLACK);
            fontPaint.setAntiAlias(true);
            fontPaint.setTextSize(10 * dp);
            fontPaint.setFakeBoldText(true);
            fontPaint.setTextAlign(Paint.Align.CENTER);

            textRect = new TextRect(fontPaint);

            fontDatePaint = new Paint();
            fontDatePaint.setColor(Color.BLACK);
            fontDatePaint.setAntiAlias(true);
            fontDatePaint.setTextSize(70 * dp);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.peddana_regular);
            fontDatePaint.setTypeface(typeface);
            fontDatePaint.setTextAlign(Paint.Align.RIGHT);
        }

        bubblePaint.setStyle(Paint.Style.FILL);
        bubblePaint.setColor(Color.BLUE);
        bubblePaint.setAntiAlias(true);

        setDrawingCacheEnabled(true);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault());

        date = dateFormat.format(calendar.getTime());
    }

    public void setDate(int d, TextView dateText){
        calendar.add(Calendar.DAY_OF_YEAR, d);
        date = dateFormat.format(calendar.getTime());

        dateText.setText(date);

        invalidate();
    }

    public void setText(String text){
        this.text = text;

        invalidate();
    }

    public void setPaddingStartEnd(int padding){
        paddingStartEnd = padding;

        invalidate();
    }

    public void setPaddingTop(int padding){
        paddingTop = padding + 200;

        invalidate();
    }

    public void setTextSize(float size){
        fontPaint.setTextSize(size * dp);
        textRect = new TextRect(fontPaint);

        invalidate();
    }

    public void setDisplaySize(Point displaySize) {
        this.displaySize = displaySize;

        ViewGroup.LayoutParams params = getLayoutParams();

        params.width = displaySize.x;
        params.height = displaySize.x;

        setLayoutParams(params);

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        float scale = (float)background.getWidth()/(float)displaySize.x;
        int newWidth = Math.round(background.getWidth()/scale);
        int newHeight = Math.round(background.getHeight()/scale);
        scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
    }


    @Override
    public void onDraw(Canvas canvas) {
        int bubbleWidth = (getWidth() - outerPaddingBoth);
        int bubbleHeight = (getHeight() - outerPaddingBoth);
        int x = outerPadding;
        int y = outerPadding;

        if(scaled != null)
            canvas.drawBitmap(scaled, (displaySize.x - scaled.getWidth()) / 2, 0, null);

            drawTextBubble(canvas, x + paddingStartEnd, paddingTop, bubbleWidth - paddingStartEnd * 2, bubbleHeight,
                    bubblePadding, text);

            /*if (++i % CELLS == 0) {
                x = outerPadding;
                y += bubbleHeight + outerPadding;
            } else {
                x += bubbleWidth + outerPadding;
            }*/

            canvas.drawText(date, displaySize.x - 135, 190, fontDatePaint);
    }

    public Bitmap get(){
        return this.getDrawingCache();
    }

    private void drawTextBubble(
            Canvas canvas,
            int x,
            int y,
            int width,
            int height,
            int padding,
            String text) {
        int paddingBoth = padding * 2;
        int h = textRect.prepare(
                text,
                width - paddingBoth,
                height - paddingBoth);

        bubbleRect.set(x, y, x + width, y + h + paddingBoth);
        //canvas.drawRoundRect(bubbleRect, padding, padding, bubblePaint);

        textRect.draw(canvas, x + width / 2, y + padding);
    }
}