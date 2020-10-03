package com.nik35.smartstorehouse.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nik35.smartstorehouse.MainActivity;
import com.nik35.smartstorehouse.R;

public class DemoBubblesView extends View {
    private static final String[] TEXTS = new String[]{
            "And now for something completely different. According to Wikipedia, the origin of this phrase \"is credited to Christopher Trace, founding presenter of the children's television programme Blue Peter, who used it (in all seriousness) as a link between segments\". Interesting, isn't it?"
    };
    private static final int CELLS = (int) Math.ceil(Math.sqrt(TEXTS.length));

    private final RectF bubbleRect = new RectF();
    private final Paint bubblePaint = new Paint();
    private final TextRect textRect;

    private int outerPadding;
    private int outerPaddingBoth;
    private int bubblePadding;

    private Bitmap scaled;

    private Point displaySize;

    public DemoBubblesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        float dp = context.getResources().getDisplayMetrics().density;

        outerPadding = Math.round(16f * dp);
        outerPaddingBoth = (CELLS + 1) * outerPadding;
        bubblePadding = Math.round(8f * dp);

        // create text rect for this font
        {
            Paint fontPaint = new Paint();
            fontPaint.setColor(Color.WHITE);
            fontPaint.setAntiAlias(true);
            fontPaint.setTextSize(14 * dp);
            fontPaint.setTextAlign(Paint.Align.CENTER);

            textRect = new TextRect(fontPaint);
        }

        bubblePaint.setStyle(Paint.Style.FILL);
        bubblePaint.setColor(Color.BLUE);
        bubblePaint.setAntiAlias(true);

        setDrawingCacheEnabled(true);
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
        int bubbleWidth = (getWidth() - outerPaddingBoth) / CELLS;
        int bubbleHeight = (getHeight() - outerPaddingBoth) / CELLS;
        int x = outerPadding;
        int y = outerPadding;

        if(scaled != null)
            canvas.drawBitmap(scaled, (displaySize.x - scaled.getWidth()) / 2, 0, null);

        for (int i = 0, l = TEXTS.length; i < l; ) {
            drawTextBubble(canvas, x, y, bubbleWidth, bubbleHeight,
                    bubblePadding, TEXTS[i]);

            if (++i % CELLS == 0) {
                x = outerPadding;
                y += bubbleHeight + outerPadding;
            } else {
                x += bubbleWidth + outerPadding;
            }
        }
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
        canvas.drawRoundRect(bubbleRect, padding, padding, bubblePaint);

        textRect.draw(canvas, x + width / 2, y + padding);
    }
}