package cornell.eickleapp;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.View;

public class WordleGraphics extends View {

	Context mContext;
	ArrayList<String[]> nestArrayDrinkList = new ArrayList<String[]>();
	ArrayList<String[]> nestArrayNoDrinkList = new ArrayList<String[]>();
	String word, title;
	int count;
	ArrayList<Rect> wordRectList = new ArrayList<Rect>();

	public WordleGraphics(Context context, ArrayList<String[]> d,
			ArrayList<String[]> nd, String t) {
		super(context);
		mContext = context;
		nestArrayDrinkList = d;
		nestArrayNoDrinkList = nd;
		title = t;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		float leftX = canvas.getWidth() / 4;
		float rightX = 3 * canvas.getWidth() / 4;

		// left/drink cloud
		setUpDrinkCloud(canvas, true);

		/*
		 * line to divide the cloud values Paint linePaint = new Paint();
		 * linePaint.setStrokeWidth(10); linePaint.setTextAlign(Align.CENTER);
		 * linePaint.setColor(Color.rgb(242, 147, 39));
		 * canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2,
		 * canvas.getHeight(), linePaint);
		 */
		// right/no drink cloud
		//setUpDrinkCloud(canvas, false);

	}

	private int getTotalCount(ArrayList<String[]> values) {
		int sum = 0;
		for (int i = 0; i < values.size(); i++) {
			sum += Integer.parseInt(values.get(i)[1]);
		}
		return sum;
	}

	private void setUpDrinkCloud(Canvas canvas, Boolean drink) {
		ArrayList<String[]> arrayList;
		if (drink)
			arrayList = nestArrayDrinkList;
		else
			arrayList = nestArrayNoDrinkList;

		Paint titlePaint = new Paint();
		titlePaint.setTextSize(60);
		titlePaint.setTextAlign(Align.LEFT);
		titlePaint.setColor(Color.rgb(0, 153, 204));
		if (drink)
			canvas.drawText("Drinking", canvas.getWidth() / 2, 50, titlePaint);
		else if (!drink)
			canvas.drawText("Non-Drinking", canvas.getWidth() / 2,
					canvas.getHeight() / 2+50, titlePaint);

		if (arrayList == null) {

			if (drink) {
				canvas.drawText("N/A", canvas.getWidth() / 2,
						canvas.getHeight() / 4, titlePaint);
			} else if (!drink) {
				canvas.drawText("N/A", canvas.getWidth() / 2,
						3 * canvas.getHeight() / 4, titlePaint);
			}
		}

		if (arrayList != null) {

			for (int n = 0; n < 1; n++) {
				word = arrayList.get(n)[0];
				count = Integer.valueOf(arrayList.get(n)[1]);

				Paint textPainter = new Paint();
				textPainter.setTextSize(16);
				if (drink)
					textPainter.setColor(Color.rgb(247, 144, 30));
				else if (!drink)
					textPainter.setColor(Color.rgb(14, 109, 97));

				// The sizes should be relative to the total amount
				int total_cnt = getTotalCount(arrayList);

				double size = 200 * (count / Double.valueOf(total_cnt));
				if ((int) size < 30) {
					size = 30.0;
				}

				// setup the text
				textPainter.setTextSize((int) size);
				textPainter.setTextAlign(Align.LEFT);
				Rect wordBound = new Rect();
				textPainter.getTextBounds(word, 0, word.length(), wordBound);

				// root of the branching
				if (n == 0) {
					if (drink) {
						canvas.drawText(word, 0,
								50, textPainter);

						wordBound.set(
								(0) ,
								0,
								(0) + (wordBound.width()),
								wordBound.height());

					} else if (!drink) {
						canvas.drawText(
								word,
								canvas.getWidth() / 2,
								3 * canvas.getHeight() / 2 - wordBound.height(),
								textPainter);

						wordBound
								.set((canvas.getWidth() / 2)
										- (wordBound.width()),
										3 * canvas.getHeight() / 4
												+ wordBound.height(),
										(canvas.getWidth() / 2)
												+ (wordBound.width()), 3
												* canvas.getHeight() / 4
												- wordBound.height());
					}
					wordRectList.add(wordBound);
				} else {
					boolean continueSearch = true;
					boolean intersection = false;
					while (continueSearch) {
						intersection = false;
						Random random = new Random();
						int randomNode = random.nextInt(wordRectList.size());

						int randomSide = random.nextInt(5 - 1) + 1;

						Rect parentRect = wordRectList.get(randomNode);
						switch (randomSide) {
						// <-------------------right side branching
						case 1:
							float parentRightSideX = parentRect.centerX()
									+ (parentRect.width() / 2)
									+ (wordBound.width() / 2);
							float parentRightSideY = parentRect.centerY();
							// checks if intersects anything
							wordBound.set(
									(int) parentRightSideX
											- (wordBound.width() / 2),
									(int) (parentRightSideY - wordBound
											.height()),
									(int) (parentRightSideX + (wordBound
											.width() / 2)),
									(int) (parentRightSideY));
							intersection = false;
							for (int i = 0; i < wordRectList.size(); i++) {
								if (Rect.intersects(wordBound,
										wordRectList.get(i))) {
									intersection = true;
								}
							}
							if (!intersection) {
								if (checkBoundary(wordBound, canvas, drink)) {
									canvas.drawText(word, parentRightSideX,
											parentRightSideY, textPainter);
									continueSearch = false;
								}

							}
							break;
						// <-------------------Left side branching
						case 3:
							float parentLeftSideX = parentRect.exactCenterX()
									- (parentRect.width() / 2)
									- (wordBound.width() / 2);
							float parentLeftSideY = parentRect.exactCenterY();
							// checks if intersects anything
							wordBound
									.set((int) parentLeftSideX
											- (wordBound.width() / 2),
											(int) (parentLeftSideY - wordBound
													.height()),
											(int) (parentLeftSideX + (wordBound
													.width() / 2)),
											(int) (parentLeftSideY));
							intersection = false;
							for (int i = 0; i < wordRectList.size(); i++) {
								if (Rect.intersects(wordBound,
										wordRectList.get(i))) {
									intersection = true;
								}
							}
							if (!intersection) {
								if (checkBoundary(wordBound, canvas, drink)) {
									canvas.drawText(word, parentLeftSideX,
											parentLeftSideY, textPainter);
									continueSearch = false;
								}

							}
							break;
						// <-------------------Top side branching
						case 4:
							float parentTopSideX = parentRect.exactCenterX();
							float parentTopSideY = parentRect.exactCenterY()
									- (parentRect.height() / 2);
							// checks if intersects anything
							wordBound
									.set((int) parentTopSideX
											- (wordBound.width() / 2),
											(int) (parentTopSideY - wordBound
													.height()),
											(int) (parentTopSideX + (wordBound
													.width() / 2)),
											(int) (parentTopSideY));
							intersection = false;
							for (int i = 0; i < wordRectList.size(); i++) {
								if (Rect.intersects(wordBound,
										wordRectList.get(i))) {
									intersection = true;
								}
							}
							if (!intersection) {
								if (checkBoundary(wordBound, canvas, drink)) {
									canvas.drawText(word, parentTopSideX,
											parentTopSideY, textPainter);
									continueSearch = false;
								}

							}
							break;
						}

					}
				}

				// saves so we can pull up the corner to slap in more words
				wordRectList.add(wordBound);

			}
		}
	}

	private Boolean checkBoundary(Rect bound, Canvas canvas, Boolean drink) {
		if (drink) {
			if (bound.left > 0 && bound.right < canvas.getWidth()
					&& bound.top > 60 && bound.bottom < canvas.getHeight() / 2) {
				return true;
			} else {
				return false;
			}
		} else {
			if (bound.left > canvas.getWidth()
					&& bound.right < canvas.getWidth()
					&& bound.top > ((canvas.getWidth() / 2) + 60)
					&& bound.bottom < canvas.getHeight()) {
				return true;
			} else {
				return false;
			}
		}
	}
}
