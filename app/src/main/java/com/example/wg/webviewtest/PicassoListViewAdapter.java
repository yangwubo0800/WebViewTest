package com.example.wg.webviewtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PicassoListViewAdapter extends BaseAdapter {

    private static final String[] IMAGES = new String[]{
            "http://www.sinaimg.cn/qc/photo_auto/photo/84/35/39698435/39698435_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/44/23/39674423/39674423_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/74/19/39657419/39657419_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/34/09/39653409/39653409_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/53/97/39645397/39645397_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/93/94/39629394/39629394_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/63/79/39616379/39616379_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/53/77/39615377/39615377_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/43/70/39594370/39594370_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/93/58/39579358/39579358_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/23/45/39572345/39572345_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/13/41/39571341/39571341_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/03/29/39550329/39550329_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/53/27/39525327/39525327_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/93/10/39399310/39399310_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/93/06/39389306/39389306_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/92/90/39329290/39329290_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/62/88/39326288/39326288_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/52/86/39325286/39325286_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/72/69/39277269/39277269_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/62/67/39276267/39276267_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/82/78/39308278/39308278_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/32/60/39273260/39273260_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/22/59/39272259/39272259_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/12/56/39271256/39271256_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/02/55/39270255/39270255_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/72/49/39267249/39267249_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/42/43/39254243/39254243_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/42/43/39254243/39254243_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/92/31/39239231/39239231_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/82/29/39238229/39238229_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/72/27/39237227/39237227_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/62/24/39226224/39226224_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/72/10/39197210/39197210_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/42/06/39194206/39194206_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/82/00/39188200/39188200_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/61/98/39186198/39186198_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/31/96/39183196/39183196_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/82/10/39198210/39198210_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/61/83/39176183/39176183_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/12/12/39201212/39201212_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/91/87/39179187/39179187_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/11/69/39171169/39171169_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/91/66/39169166/39169166_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/61/62/39166162/39166162_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/51/60/39165160/39165160_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/81/52/39158152/39158152_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/71/27/39127127/39127127_140.jpg",
            "http://www.sinaimg.cn/qc/photo_auto/photo/10/98/39111098/39111098_140.jpg"
    };
    private Context context;

    public PicassoListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //使用viewHolder 减少重复对象创建，减少了findviewById的调用和系统开销
        ViewHolder holder = null;
        if (convertView == null) {
            //通过xml加载来找view
            convertView = View.inflate(context, R.layout.picasso_listitem, null);
            holder =  new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.iv_picasso_item);
            holder.name = (TextView) convertView.findViewById(R.id.tv_picasso_name);
            convertView.setTag(holder);
        } else {
            //通过tag找到view
            holder = (ViewHolder) convertView.getTag();
        }

        //通过holder可以找到view布局，并对对象赋值操作
        holder.name.setText("itme" + position);
        Picasso.with(context)
                .load(IMAGES[position])
                .error(R.mipmap.ic_launcher)
                .into(holder.image);

        //返回填充内容的view， view的复用是adapter里面做了处理，和viewHolder无关
        return convertView;
    }


    class ViewHolder {
        ImageView image;
        TextView name;
    }
}