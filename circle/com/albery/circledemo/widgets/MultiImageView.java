package com.albery.circledemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.albery.circledemo.utils.DensityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.common.Constants;

import java.util.List;

/**
 * @ClassName MultiImageView.java
 * @author shoyu
 * @version 
 * @Description: ��ʾ1~N��ͼƬ��View
 */

public class MultiImageView extends LinearLayout {
	public static int MAX_WIDTH = 0;

	// ��Ƭ��Url�б�
	private List<String> imagesList;

	/** ���� ��λΪPixel **/
	private int pxOneMaxWandH;  // ����ͼ���������
	private int pxMoreWandH = 0;// ����ͼ�Ŀ��
	private int pxImagePadding = DensityUtil.dip2px(getContext(), 3);// ͼƬ��ļ��

	private int MAX_PER_ROW_COUNT = 3;// ÿ����ʾ�����

	private LayoutParams onePicPara;
	private LayoutParams morePara, moreParaColumnFirst;
	private LayoutParams rowPara;

	private OnItemClickListener mOnItemClickListener;
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		mOnItemClickListener = onItemClickListener;
	}

	public MultiImageView(Context context) {
		super(context);
	}

	public MultiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setList(List<String> lists) throws IllegalArgumentException{
		if(lists==null){
			throw new IllegalArgumentException("imageList is null...");
		}
		imagesList = lists;
		
		if(MAX_WIDTH > 0){
			pxMoreWandH = (MAX_WIDTH - pxImagePadding*2 )/3; //����Ҳ�ͼƬ�����ݶԲ�������
			pxOneMaxWandH = MAX_WIDTH * 2 / 3;
			initImageLayoutParams();
		}

		initView();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(MAX_WIDTH == 0){
			int width = measureWidth(widthMeasureSpec);
			if(width>0){
				MAX_WIDTH = width;
				if(imagesList!=null && imagesList.size()>0){
					setList(imagesList);
				}
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			// result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
			// + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private void initImageLayoutParams() {
		int wrap = LayoutParams.WRAP_CONTENT;
		int match = LayoutParams.MATCH_PARENT;

		onePicPara = new LayoutParams(pxOneMaxWandH, wrap);

		moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
		morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
		morePara.setMargins(pxImagePadding, 0, 0, 0);

		rowPara = new LayoutParams(match, wrap);
	}

	// ����imageView��������ʼ����ͬ��View����,��ҪΪÿһ��View�����Ч��
	private void initView() {
		this.setOrientation(VERTICAL);
		this.removeAllViews();
		if(MAX_WIDTH == 0){
			//Ϊ�˴���onMeasure()������MultiImageView������ȣ�MultiImageView�Ŀ�����Ϊmatch_parent
			addView(new View(getContext()));
			return;
		}
		
		if (imagesList == null || imagesList.size() == 0) {
			return;
		}

		if (imagesList.size() == 1) {
				addView(createImageView(0, false));
		} else {
			int allCount = imagesList.size();
			if(allCount == 4){
				MAX_PER_ROW_COUNT = 2;
			}else{
				MAX_PER_ROW_COUNT = 3;
			}
			int rowCount = allCount / MAX_PER_ROW_COUNT
					+ (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// ����
			for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
				LinearLayout rowLayout = new LinearLayout(getContext());
				rowLayout.setOrientation(LinearLayout.HORIZONTAL);

				rowLayout.setLayoutParams(rowPara);
				if (rowCursor != 0) {
					rowLayout.setPadding(0, pxImagePadding, 0, 0);
				}

				int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT
						: allCount % MAX_PER_ROW_COUNT;//ÿ�е�����
				if (rowCursor != rowCount - 1) {
					columnCount = MAX_PER_ROW_COUNT;
				}
				addView(rowLayout);

				int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// ��ƫ��
				for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
					int position = columnCursor + rowOffset;
					rowLayout.addView(createImageView(position, true));
				}
			}
		}
	}

	private ImageView createImageView(int position, final boolean isMultiImage) {
		String url = imagesList.get(position);
		ImageView imageView = new ColorFilterImageView(getContext());
		if(isMultiImage){
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ?moreParaColumnFirst : morePara);
		}else {
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ScaleType.FIT_START);
			imageView.setMaxHeight(pxOneMaxWandH);
			imageView.setLayoutParams(onePicPara);
		}

		imageView.setTag(position);
		imageView.setId(url.hashCode());
		imageView.setOnClickListener(mImageViewOnClickListener);
		//��̬����ʾͼƬ
		ImageLoader.getInstance().displayImage(Constants.BASE_URL + url, imageView);
		return imageView;
	}

	// ͼƬ����¼�
	private View.OnClickListener mImageViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			if(mOnItemClickListener != null){
				mOnItemClickListener.onItemClick(view, (Integer)view.getTag());
			}
		}
	};

	public interface OnItemClickListener{
		public void onItemClick(View view, int position);
	}
}