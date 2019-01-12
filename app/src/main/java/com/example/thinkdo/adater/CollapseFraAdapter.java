package com.example.thinkdo.adater;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.CollapseBean;
import com.example.thinkdo.view.CollapseTextView;

import java.util.ArrayList;

/**
 * Created by xh on 2018/4/14.
 */

public class CollapseFraAdapter extends RecyclerView.Adapter {
    ArrayList<CollapseBean> data = new ArrayList<>();


    public CollapseFraAdapter() {
        generateData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyHolder holder = new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collapse_text, parent, false));

//        LifecycleOwner
        LiveData<String>a;
//        a.ob
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder tHolder, final int position) {
        final CollapseBean bean = data.get(position);

        final MyHolder holder = (MyHolder) tHolder;
        holder.tvContent.setText(bean.getText());
        holder.setBean(bean, position);


        Log.d("TAG", "position = " + position + "  state=" + bean.getState());
        if (bean.getState() == CollapseBean.CollapseState.Init) {

            holder.tvContent.setCollapseState(bean.getState());
            holder.tvContent.setCollapseStateChange(new CollapseTextView.CollapseStateChange() {
                @Override
                public void onStateChange(int state) {
                    bean.setState(state);
                    if (state == CollapseBean.CollapseState.Less) {
                        holder.tvCollapse.setVisibility(View.GONE);
                    } else {
                        holder.tvCollapse.setVisibility(View.VISIBLE);
                        holder.tvCollapse.setText(R.string.more);
                    }
                }
            });

        } else if (bean.getState() == CollapseBean.CollapseState.Less) {
            holder.tvCollapse.setVisibility(View.GONE);
            holder.tvContent.setCollapseState(bean.getState());
        } else if (bean.getState() == CollapseBean.CollapseState.More) {
            holder.tvCollapse.setVisibility(View.VISIBLE);
            holder.tvCollapse.setText(R.string.more);
            holder.tvContent.setCollapseState(bean.getState());

        } else {
            holder.tvCollapse.setVisibility(View.VISIBLE);
            holder.tvCollapse.setText(R.string.collapse);
            holder.tvContent.setCollapseState(bean.getState());

        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCollapse;
        CollapseTextView tvContent;

        private CollapseBean bean;
        private int pos;

        public void setBean(CollapseBean bean, int pos) {
            this.bean = bean;
            this.pos = pos;
        }

        public MyHolder(View itemView) {
            super(itemView);

            tvContent = (CollapseTextView) itemView.findViewById(R.id.tv);
            tvCollapse = (TextView) itemView.findViewById(R.id.tv_collapse);


            tvCollapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick");
                    if (bean.getState() == CollapseBean.CollapseState.Collapse) {
                        bean.setState(CollapseBean.CollapseState.More);
                        tvCollapse.setVisibility(View.VISIBLE);
                        tvCollapse.setText("更多");
                        tvContent.setCollapseState(CollapseBean.CollapseState.More);


                    } else {
                        bean.setState(CollapseBean.CollapseState.Collapse);
                        tvCollapse.setVisibility(View.VISIBLE);
                        tvCollapse.setText("收起");
                        tvContent.setCollapseState(CollapseBean.CollapseState.Collapse);

                    }
                }
            });
        }
    }


    private void generateData() {
        String[] array = {"潜艇部队组建于1954年，是海军主要突击兵力之一，主要用于水下突袭敌重要目标、反潜和实施侦察，是维护国家主权和海洋权益，维护战略通道和海外利益安全的坚强盾牌。",
                "中国之行将为菲律宾人创造一万个工作机会.",
                "海关总署新闻发言人黄颂平13日在国新办举行的一季度进出口情况新闻发布会上表示，今年以来，世界经济延续复苏态势，国内经济稳中向好，推动一季度我国外贸进出口较快增长。黄颂平说，一季度我国货物贸易进出口总值6.75万亿元人民币，比去年同期增长9.4%。具体来看，一般贸易进出口快速增长，比重上升。一季度，我国一般贸易进出口3.93万亿元，增长13.2%，占我国进出口总值的58.3%，比去年同期提升2个百分点。贸易方式结构进一步优化.",
                "此外，中西部进口增速高于全国整体，机电产品、传统劳动密集型产品仍为出口主力。铁矿砂进口量微减，原油、大豆等商品进口量增加，进口均价涨跌互现.",
                "厄瓜多尔3名被武装绑架记者遇害 将重启军事打击",
                "构建完善国家安全教育内容体系。小学生应了解国家安全基本常识，增强爱国主义情感；中学生应掌握国家安全基础知识，增强国家安全意识；大学生应接受国家安全系统化学习训练，增强维护国家安全的责任感和能力.",
                "多地发生小孩玩食品干燥剂致伤事件 入眼可致失明.",
                "据中国之声《新闻纵横》报道，近日，北京产权交易所官网挂出一则产权转让公告，动车网络科技有限公司拟转让49%的股权，这被视为中铁总启动混合所有制改革迈出的重要一步。动车网络成立于2017年12月20日，中国铁路总公司完全控股的中国铁路投资有限公司是动车网络的100%控股股东。值得注意的是，动车网络也是中铁总确定的下属企业中唯一经营动车组WiFi的企业.",
                "54岁男子考科四作弊 场外2人涉嫌组织考试作弊被刑拘.54岁男子考科四作弊 场外2人涉嫌组织考试作弊被刑拘.54岁男子考科四作弊 场外2人涉嫌组织考试作弊被刑拘.",
                "早在几年前，一个19岁的男孩在火车上卖WiFi的事情曾广为流传，据说他把火车车厢变成网吧，在半小时内赚取了500块。坐着动车连着WiFi，是不少朋友的期待，背后也蕴含着巨大的商机.",
                "鸡汤文，是一些所谓励志、情感类文章的统称。其实，此类文章最早被称作“心灵鸡汤”，其中倒真有一些思考人生、励志的文章。不过，随着移动互联网的覆盖和智能手机的普及，移动阅读用户越来越多，“心灵鸡汤”的写手也不断“扩容”，“鸡汤”不再营养丰富，甚至还出现了“馊鸡汤”“毒鸡汤”，大量虚构编造甚至完全违背逻辑常理的故事不断出现。如今，“鸡汤文”成了一个用来调侃略带贬义的词。"

        };

        for (int i = 0; i < 4; i++) {
            for (String s : array) {
                data.add(new CollapseBean(s));
            }
        }
    }
}
