package com.polyclinic.basemoudle;

import android.content.Context;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

/**
 * @author Lxg
 * @create 2020/2/13
 * @Describe
 */
public class CityPicker {
    private CityPickerView cityPickerView;
    private onChoiceListener listener;

    public void setListener(onChoiceListener listener) {
        this.listener = listener;
    }

    public CityPicker(Context context, String provice, String city, String town) {
        cityPickerView = new CityPickerView();
        cityPickerView.init(context);
        //配置
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#ffffff")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("确认")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .province(provice)//默认显示的省份
                .city(city)//默认显示省份下面的城市认显示省市下面的区县数据
                .district(town)//默
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .districtCyclic(false)//区县滚轮是否循环滚动
                .setCustomItemLayout(R.layout.basemoudle_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(false)//滚轮不显示模糊效果
                .setLineColor("#f5f5f5")//中间横线的颜色
                .setLineHeigh(2)//中间横线的高度
                .setShowGAT(false)//是否显示港澳台数据，默认不显示
                .build();
        cityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                if (listener != null)
                    listener.choice(province.getName(), city.getName(), district.getName(),
                            province.getId());
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });

        //添加配置
        cityPickerView.setConfig(cityConfig);
        //显示

        //监听选择点击事件及返回结果

    }

    public void show() {
        cityPickerView.showCityPicker();
    }

    public CityPickerView getCityPickerView() {
        return cityPickerView;
    }

    public interface onChoiceListener {
        void choice(String pro, String city, String town, String proCode);
    }


}
