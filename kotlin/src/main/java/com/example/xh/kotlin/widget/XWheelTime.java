package com.example.xh.kotlin.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.xh.kotlin.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class XWheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_seconds;
    private int gravity;

    private boolean[] type;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;
    private static final int DEFAULT_START_HOUR = 0;
    private static final int DEFAULT_END_HOUR = 23;
    private static final int DEFAULT_START_MIN = 0;
    private static final int DEFAULT_END_MIN = 59;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY; //表示31天的
    private int startHour = DEFAULT_START_HOUR;
    private int endHour = DEFAULT_END_HOUR;
    private int startMin = DEFAULT_START_MIN;
    private int endMin = DEFAULT_END_MIN;


    private int currentYear;
    protected int currentMonth;
    protected int currentDay;
    protected int currentHour;

    private int textSize;

    //文字的颜色和分割线的颜色
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;

    private float lineSpacingMultiplier;
    private WheelView.DividerType dividerType;
    private boolean isLunarCalendar = false;
    private ISelectTimeCallback mSelectChangeCallback;


    public XWheelTime(View view, PickerOptions options) {
        this.view = view;

        wv_year = view.findViewById(R.id.year);
        wv_month = view.findViewById(R.id.month);
        wv_day = view.findViewById(R.id.day);
        wv_hours = view.findViewById(R.id.hour);
        wv_minutes = view.findViewById(R.id.min);
        wv_seconds = view.findViewById(R.id.second);

        initWithOptions(options);
    }

    private void initWithOptions(PickerOptions mPickerOptions) {
        this.type = mPickerOptions.type;
        this.gravity = mPickerOptions.textGravity;
        this.textSize = mPickerOptions.textSizeContent;

        setLunarMode(mPickerOptions.isLunarCalendar);

        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0
                && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setStartYear(mPickerOptions.startYear);
            setEndYear(mPickerOptions.endYear);
        }

        //若手动设置了时间范围限制
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            if (mPickerOptions.startDate.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            } else {
                setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
                initDefaultSelectedDate(mPickerOptions);
            }
        } else if (mPickerOptions.startDate != null) {
            if (mPickerOptions.startDate.get(Calendar.YEAR) < 1900) {
                throw new IllegalArgumentException("The startDate can not as early as 1900");
            } else {
                setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
                initDefaultSelectedDate(mPickerOptions);
            }
        } else if (mPickerOptions.endDate != null) {
            if (mPickerOptions.endDate.get(Calendar.YEAR) > 2100) {
                throw new IllegalArgumentException("The endDate should not be later than 2100");
            } else {
                setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
                initDefaultSelectedDate(mPickerOptions);
            }
        } else {//没有设置时间范围限制，则会使用默认范围。
            setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
            initDefaultSelectedDate(mPickerOptions);
        }
        setTime(mPickerOptions);

        setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day
                , mPickerOptions.label_hours, mPickerOptions.label_minutes, mPickerOptions.label_seconds);
        setTextXOffset(mPickerOptions.x_offset_year, mPickerOptions.x_offset_month, mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_hours, mPickerOptions.x_offset_minutes, mPickerOptions.x_offset_seconds);
        setCyclic(mPickerOptions.cyclic);
        setDividerColor(mPickerOptions.dividerColor);
        setDividerType(mPickerOptions.dividerType);
        setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        setTextColorOut(mPickerOptions.textColorOut);
        setTextColorCenter(mPickerOptions.textColorCenter);
        isCenterLabel(mPickerOptions.isCenterLabel);
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime(PickerOptions mPickerOptions) {
        int year, month, day, hours, minute, seconds;
        Calendar calendar = Calendar.getInstance();

        if (mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = mPickerOptions.date.get(Calendar.YEAR);
            month = mPickerOptions.date.get(Calendar.MONTH);
            day = mPickerOptions.date.get(Calendar.DAY_OF_MONTH);
            hours = mPickerOptions.date.get(Calendar.HOUR_OF_DAY);
            minute = mPickerOptions.date.get(Calendar.MINUTE);
            seconds = mPickerOptions.date.get(Calendar.SECOND);
        }

        setPicker(year, month, day, hours, minute, seconds);
    }


    private void initDefaultSelectedDate(PickerOptions mPickerOptions) {
        //如果手动设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (mPickerOptions.date == null || mPickerOptions.date.getTimeInMillis() < mPickerOptions.startDate.getTimeInMillis()
                    || mPickerOptions.date.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                mPickerOptions.date = mPickerOptions.startDate;
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate;
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate;
        }
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public boolean isLunarMode() {
        return isLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m, int s) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    /**
     * 设置农历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setLunar(int year, final int month, int day, boolean isLeap, int h, int m, int s) {
        // 年
        wv_year.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(startYear, endYear)));// 设置"年"的显示数据
        wv_year.setLabel("");// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);

        // 月
        wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        wv_month.setLabel("");

        int leapMonth = ChinaDate.leapMonth(year);
        if (leapMonth != 0 && (month > leapMonth - 1 || isLeap)) { //选中月是闰月或大于闰月
            wv_month.setCurrentItem(month + 1);
        } else {
            wv_month.setCurrentItem(month);
        }

        wv_month.setGravity(gravity);

        // 日
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (ChinaDate.leapMonth(year) == 0) {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wv_day.setLabel("");
        wv_day.setCurrentItem(day - 1);
        wv_day.setGravity(gravity);

        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        //wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);

        wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_minutes.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_minutes.setCurrentItem(m);
        wv_minutes.setGravity(gravity);

        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_seconds.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_seconds.setCurrentItem(m);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断是不是闰年,来确定月和日的选择
                wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
                } else {
                    wv_month.setCurrentItem(wv_month.getCurrentItem());
                }

                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
                        maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index;
                int year_num = wv_year.getCurrentItem() + startYear;
                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
                        maxItem = ChinaDate.monthDays(year_num, month_num);
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
                    maxItem = ChinaDate.monthDays(year_num, month_num + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wv_day);
        setChangedListener(wv_hours);
        setChangedListener(wv_minutes);
        setChangedListener(wv_seconds);

        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 设置公历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setSolar(int year, final int month, int day, int h, int m, int s) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        currentYear = year;
        currentMonth = month + 1;
        currentDay = day;
        currentHour = h;

        // 年
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据


        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        if (startYear == endYear) {//开始年等于终止年
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }
        wv_month.setGravity(gravity);
        // 日

        if (startYear == endYear && startMonth == endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            wv_day.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            wv_day.setCurrentItem(day - 1);
        }

        wv_day.setGravity(gravity);

        //时
        wv_hours.setGravity(gravity);

        //分
        wv_minutes.setGravity(gravity);

        if (startYear == endYear && startMonth == endMonth && startDay == endDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(startHour, endHour));
            if (startHour == endHour) {
                wv_minutes.setAdapter(new NumericWheelAdapter(startMin, endMin));
            } else {
                wv_minutes.setAdapter(new NumericWheelAdapter(DEFAULT_START_MIN, DEFAULT_END_MIN));
            }
        } else if (startYear == year && startMonth == month + 1 && startDay == day) {
            wv_hours.setAdapter(new NumericWheelAdapter(startHour, DEFAULT_END_HOUR));
            if (startHour == h) {
                wv_minutes.setAdapter(new NumericWheelAdapter(startMin, DEFAULT_END_MIN));
            } else {
                wv_minutes.setAdapter(new NumericWheelAdapter(DEFAULT_START_MIN, DEFAULT_END_MIN));
            }
        } else if (endYear == year && endMonth == month + 1 && endDay == day) {
            wv_hours.setAdapter(new NumericWheelAdapter(DEFAULT_START_HOUR, endHour));
            if (endHour == h) {
                wv_minutes.setAdapter(new NumericWheelAdapter(DEFAULT_START_MIN, endMin));
            } else {
                wv_minutes.setAdapter(new NumericWheelAdapter(DEFAULT_START_MIN, DEFAULT_END_MIN));
            }
        } else {
            wv_hours.setAdapter(new NumericWheelAdapter(DEFAULT_START_HOUR, DEFAULT_END_HOUR));
            wv_minutes.setAdapter(new NumericWheelAdapter(DEFAULT_START_MIN, DEFAULT_END_MIN));
        }

        wv_hours.setCurrentItem(h - (int) wv_hours.getAdapter().getItem(0));
        wv_minutes.setCurrentItem(m - (int) wv_minutes.getAdapter().getItem(0));

        //秒
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));

        wv_seconds.setCurrentItem(s);
        wv_seconds.setGravity(gravity);


        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                currentYear = year_num;
                int currentMonthItem = wv_month.getCurrentItem();//记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    setWheelViewAdapter(wv_month, startMonth, endMonth);
                    currentMonth = startMonth;

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int monthNum = currentMonthItem + startMonth;

                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, startDay, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                        resetTimeAdapter();
                    } else if (monthNum == endMonth) {
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else {//重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }
                } else if (year_num == startYear) {//等于开始的年
                    //重新设置月份
                    setWheelViewAdapter(wv_month, startMonth, DEFAULT_END_MONTH);

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int month = currentMonthItem + startMonth;
                    currentMonth = month;
                    if (month == startMonth) {
                        //重新设置日
                        setReDay(year_num, month, startDay, 31, list_big, list_little);
                        resetTimeAdapter();
                    } else {
                        //重新设置日
                        setReDay(year_num, month, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }

                } else if (year_num == endYear) {
                    //重新设置月份
                    setWheelViewAdapter(wv_month, DEFAULT_START_MONTH, endMonth);
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;
                    currentMonth = monthNum;

                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }

                } else {
                    //重新设置月份
                    setWheelViewAdapter(wv_month, DEFAULT_START_MONTH, DEFAULT_END_MONTH);
                    currentMonth = currentMonthItem + 1;
                    //重新设置日
                    setReDay(year_num, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                    resetTimeAdapter();
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });


        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;

                if (startYear == endYear) {
                    month_num = month_num + startMonth - 1;
                    currentMonth = month_num;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else if (startMonth == month_num) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                        resetTimeAdapter();
                    } else if (endMonth == month_num) {
                        setReDay(currentYear, month_num, DEFAULT_START_DAY, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else {
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }
                } else if (currentYear == startYear) {
                    month_num = month_num + startMonth - 1;
                    currentMonth = month_num;
                    if (month_num == startMonth) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                        resetTimeAdapter();
                    } else {
                        //重新设置日
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }

                } else if (currentYear == endYear) {
                    currentMonth = month_num;
                    if (month_num == endMonth) {
                        //重新设置日
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, endDay, list_big, list_little);
                        resetTimeAdapter();
                    } else {
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                        resetTimeAdapter();
                    }
                } else {
                    currentMonth = month_num;
                    //重新设置日
                    setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    resetTimeAdapter();
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"日"监听
        wv_day.setOnItemSelectedListener(index -> {
            resetTimeAdapter();

            if (mSelectChangeCallback != null) {
                mSelectChangeCallback.onTimeSelectChanged();
            }
        });

        // 添加"时"监听
        wv_hours.setOnItemSelectedListener(index -> {
//            Log.d("", "");
//            String str = String.format("year = %d, %d, %d,\n month = %d, %d, %d,\n day=%d, %d, %d,\n hour=%d, %d, %d",
//                    startYear, endYear, currentYear,
//                    startMonth, endMonth, currentMonth,
//                    startDay, endDay, currentDay,
//                    startHour, endHour, currentHour);
//            Log.d("TAG", str);
            resetMinuteAdapter(index);


            if (mSelectChangeCallback != null) {
                mSelectChangeCallback.onTimeSelectChanged();
            }
        });


        wv_minutes.setOnItemSelectedListener(it -> {
            Log.d("", "");
        });

        wv_seconds.setOnItemSelectedListener(it -> {
            Log.d("", "");
        });

//        setChangedListener(wv_minutes);
//        setChangedListener(wv_seconds);

        if (type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    protected void resetMinuteAdapter(int index) {
        if (startYear == endYear && startMonth == endMonth && startDay == endDay && startHour == endHour) {
            currentHour = startHour + index;
            //重新设置分
            setWheelViewAdapter(wv_minutes, startMin, endMin);
        } else if (startYear == currentYear && startMonth == currentMonth && startDay == currentDay) {
            currentHour = startHour + index;
            //重新设置分
            if (startHour == currentHour) {
                setWheelViewAdapter(wv_minutes, startMin, DEFAULT_END_MIN);
            } else {
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
            }
        } else if (endYear == currentYear && endMonth == currentMonth && endDay == currentDay) {
            currentHour = index;

            //重新设置分
            if (endHour == currentHour) {
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, endMin);
            } else {
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
            }
        } else {
            currentHour = index;
            //重新设置分
            setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
        }
    }

    protected void setWheelViewAdapter(WheelView view, int start, int end) {
        WheelAdapter adapter = view.getAdapter();
        if (adapter != null && adapter.getItemsCount() != 0) {
            int aStart = (int) adapter.getItem(0);
            int aEnd = (int) adapter.getItem(adapter.getItemsCount() - 1);
            if (start == aStart && end == aEnd) {
                return;
            }
        }

        view.setAdapter(new NumericWheelAdapter(start, end));
    }

    /**
     * 重置wv_hours, wv_minutes的适配器
     */
    protected void resetTimeAdapter() {
        currentDay = (int) wv_day.getAdapter().getItem(wv_day.getCurrentItem());

        int currentHourItem = wv_hours.getCurrentItem();
        int currentMinItem = wv_minutes.getCurrentItem();

        if (startYear == endYear && startMonth == endMonth && startDay == endDay) {
            //重新设置时
            setWheelViewAdapter(wv_hours, startHour, endHour);
            currentHour = startHour;

            if (startHour == endHour) {
                //重新设置分
                setWheelViewAdapter(wv_minutes, startMin, endMin);
            } else {
                //重新设置分
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
                if (currentMinItem > wv_minutes.getAdapter().getItemsCount() - 1) {
                    currentMinItem = wv_minutes.getAdapter().getItemsCount() - 1;
                    wv_minutes.setCurrentItem(currentMinItem);
                }
            }
        } else if (startYear == currentYear && startMonth == currentMonth && startDay == currentDay) {
            //重新设置时
            setWheelViewAdapter(wv_hours, startHour, DEFAULT_END_HOUR);
            if (currentHourItem > wv_hours.getAdapter().getItemsCount() - 1) {
                currentHourItem = wv_hours.getAdapter().getItemsCount() - 1;
                wv_hours.setCurrentItem(currentHourItem);
            }
            currentHour = (int) wv_hours.getAdapter().getItem(wv_hours.getCurrentItem());

            if (startHour == currentHour) {
                //重新设置分
                setWheelViewAdapter(wv_minutes, startMin, DEFAULT_END_MIN);
            } else {
                //重新设置分
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
            }
            if (currentMinItem > wv_minutes.getAdapter().getItemsCount() - 1) {
                currentMinItem = wv_minutes.getAdapter().getItemsCount() - 1;
                wv_minutes.setCurrentItem(currentMinItem);
            }
        } else if (endYear == currentYear && endMonth == currentMonth && endDay == currentDay) {
            //重新设置时
            setWheelViewAdapter(wv_hours, DEFAULT_START_HOUR, endHour);
            if (currentHourItem > wv_hours.getAdapter().getItemsCount() - 1) {
                currentHourItem = wv_hours.getAdapter().getItemsCount() - 1;
                wv_hours.setCurrentItem(currentHourItem);
            }
            currentHour = (int) wv_hours.getAdapter().getItem(wv_hours.getCurrentItem());

            if (endHour == currentHour) {
                //重新设置分
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, endMin);
            } else {
                //重新设置分
                setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
            }
            if (currentMinItem > wv_minutes.getAdapter().getItemsCount() - 1) {
                currentMinItem = wv_minutes.getAdapter().getItemsCount() - 1;
                wv_minutes.setCurrentItem(currentMinItem);
            }
        } else {
            //重新设置时
            setWheelViewAdapter(wv_hours, DEFAULT_START_HOUR, DEFAULT_END_HOUR);
            if (currentHourItem > wv_hours.getAdapter().getItemsCount() - 1) {
                currentHourItem = wv_hours.getAdapter().getItemsCount() - 1;
                wv_hours.setCurrentItem(currentHourItem);
            }
            currentHour = (int) wv_hours.getAdapter().getItem(wv_hours.getCurrentItem());

            //重新设置分
            setWheelViewAdapter(wv_minutes, DEFAULT_START_MIN, DEFAULT_END_MIN);
            if (currentMinItem > wv_minutes.getAdapter().getItemsCount() - 1) {
                currentMinItem = wv_minutes.getAdapter().getItemsCount() - 1;
                wv_minutes.setCurrentItem(currentMinItem);
            }
        }

    }

    private void setChangedListener(WheelView wheelView) {
        if (mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }

    }


    private void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = wv_day.getCurrentItem();

//        int maxItem;
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            setWheelViewAdapter(wv_day, startD, endD);
//            maxItem = endD;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            setWheelViewAdapter(wv_day, startD, endD);
//            maxItem = endD;
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0) {
                if (endD > 29) {
                    endD = 29;
                }
                setWheelViewAdapter(wv_day, startD, endD);
//                maxItem = endD;
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                setWheelViewAdapter(wv_day, startD, endD);
//                maxItem = endD;
            }
        }

        if (currentItem > wv_day.getAdapter().getItemsCount() - 1) {
            currentItem = wv_day.getAdapter().getItemsCount() - 1;
            wv_day.setCurrentItem(currentItem);
        }
    }


    private void setContentTextSize() {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_minutes.setTextSize(textSize);
        wv_seconds.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wv_day.setTextColorOut(textColorOut);
        wv_month.setTextColorOut(textColorOut);
        wv_year.setTextColorOut(textColorOut);
        wv_hours.setTextColorOut(textColorOut);
        wv_minutes.setTextColorOut(textColorOut);
        wv_seconds.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wv_day.setTextColorCenter(textColorCenter);
        wv_month.setTextColorCenter(textColorCenter);
        wv_year.setTextColorCenter(textColorCenter);
        wv_hours.setTextColorCenter(textColorCenter);
        wv_minutes.setTextColorCenter(textColorCenter);
        wv_seconds.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wv_day.setDividerColor(dividerColor);
        wv_month.setDividerColor(dividerColor);
        wv_year.setDividerColor(dividerColor);
        wv_hours.setDividerColor(dividerColor);
        wv_minutes.setDividerColor(dividerColor);
        wv_seconds.setDividerColor(dividerColor);
    }

    private void setDividerType() {

        wv_day.setDividerType(dividerType);
        wv_month.setDividerType(dividerType);
        wv_year.setDividerType(dividerType);
        wv_hours.setDividerType(dividerType);
        wv_minutes.setDividerType(dividerType);
        wv_seconds.setDividerType(dividerType);

    }

    private void setLineSpacingMultiplier() {
        wv_day.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_month.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_year.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_hours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_minutes.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_seconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (isLunarCalendar) {
            return;
        }

        if (label_year != null) {
            wv_year.setLabel(label_year);
        } else {
            wv_year.setLabel(view.getContext().getString(R.string.pickerview_year));
        }
        if (label_month != null) {
            wv_month.setLabel(label_month);
        } else {
            wv_month.setLabel(view.getContext().getString(R.string.pickerview_month));
        }
        if (label_day != null) {
            wv_day.setLabel(label_day);
        } else {
            wv_day.setLabel(view.getContext().getString(R.string.pickerview_day));
        }
        if (label_hours != null) {
            wv_hours.setLabel(label_hours);
        } else {
            wv_hours.setLabel(view.getContext().getString(R.string.pickerview_hours));
        }
        if (label_mins != null) {
            wv_minutes.setLabel(label_mins);
        } else {
            wv_minutes.setLabel(view.getContext().getString(R.string.pickerview_minutes));
        }
        if (label_seconds != null) {
            wv_seconds.setLabel(label_seconds);
        } else {
            wv_seconds.setLabel(view.getContext().getString(R.string.pickerview_seconds));
        }

    }

    public void setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day,
                               int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        wv_day.setTextXOffset(x_offset_year);
        wv_month.setTextXOffset(x_offset_month);
        wv_year.setTextXOffset(x_offset_day);
        wv_hours.setTextXOffset(x_offset_hours);
        wv_minutes.setTextXOffset(x_offset_minutes);
        wv_seconds.setTextXOffset(x_offset_seconds);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_minutes.setCyclic(cyclic);
        wv_seconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (isLunarCalendar) {
            //如果是农历 返回对应的公历时间
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear) {
           /* int i = wv_month.getCurrentItem() + startMonth;
            System.out.println("i:" + i);*/
            if ((wv_month.getCurrentItem() + startMonth) == startMonth) {
                if (wv_day.getCurrentItem() + startDay == startDay) {
                    if (wv_hours.getCurrentItem() + startHour == startHour) {
                        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                                .append((wv_month.getCurrentItem() + startMonth)).append("-")
                                .append((wv_day.getCurrentItem() + startDay)).append(" ")
                                .append(wv_hours.getCurrentItem() + startHour).append(":")
                                .append(wv_minutes.getCurrentItem() + startMin).append(":")
                                .append(wv_seconds.getCurrentItem());
                    } else {
                        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                                .append((wv_month.getCurrentItem() + startMonth)).append("-")
                                .append((wv_day.getCurrentItem() + startDay)).append(" ")
                                .append(wv_hours.getCurrentItem() + startHour).append(":")
                                .append(wv_minutes.getCurrentItem()).append(":")
                                .append(wv_seconds.getCurrentItem());
                    }
                } else {
                    sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                            .append((wv_month.getCurrentItem() + startMonth)).append("-")
                            .append((wv_day.getCurrentItem() + startDay)).append(" ")
                            .append(wv_hours.getCurrentItem()).append(":")
                            .append(wv_minutes.getCurrentItem()).append(":")
                            .append(wv_seconds.getCurrentItem());
                }

            } else {
                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                        .append((wv_month.getCurrentItem() + startMonth)).append("-")
                        .append((wv_day.getCurrentItem() + 1)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_minutes.getCurrentItem()).append(":")
                        .append(wv_seconds.getCurrentItem());
            }

        } else {
            sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                    .append((wv_day.getCurrentItem() + 1)).append(" ")
                    .append(wv_hours.getCurrentItem()).append(":")
                    .append(wv_minutes.getCurrentItem()).append(":")
                    .append(wv_seconds.getCurrentItem());
        }

        return sb.toString();
    }


    /**
     * 农历返回对应的公历时间
     *
     * @return
     */
    private String getLunarTime() {
        StringBuilder sb = new StringBuilder();
        int year = wv_year.getCurrentItem() + startYear;
        int month = 1;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = wv_month.getCurrentItem() + 1;
        } else {
            if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                month = wv_month.getCurrentItem() + 1;
            } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                month = wv_month.getCurrentItem();
                isLeapMonth = true;
            } else {
                month = wv_month.getCurrentItem();
            }
        }
        int day = wv_day.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

        sb.append(solar[0]).append("-")
                .append(solar[1]).append("-")
                .append(solar[2]).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_minutes.getCurrentItem()).append(":")
                .append(wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }


    public void setRangDate(Calendar startDate, Calendar endDate) {

        if (startDate == null && endDate != null) {
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH) + 1;
            int day = endDate.get(Calendar.DAY_OF_MONTH);
            int hour = endDate.get(Calendar.HOUR_OF_DAY);
            int minute = endDate.get(Calendar.MINUTE);

            boolean isValidDate = false;
            if (year > startYear) {
                isValidDate = true;
            } else if (year == startYear) {
                if (month > startMonth) {
                    isValidDate = true;
                } else if (month == startMonth) {
                    if (day > startDay) {
                        isValidDate = true;
                    } else if (day == startDay) {
                        if (hour > startHour) {
                            isValidDate = true;
                        } else if (hour == startHour) {
                            if (minute >= startMin) {
                                isValidDate = true;
                            }
                        }

                    }
                }
            }

            if (isValidDate) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
                this.endHour = hour;
                this.endMin = minute;
            }

        } else if (startDate != null && endDate == null) {
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH) + 1;
            int day = startDate.get(Calendar.DAY_OF_MONTH);
            int hour = startDate.get(Calendar.HOUR_OF_DAY);
            int minute = startDate.get(Calendar.MINUTE);

            boolean isValidDate = false;

            if (year < endYear) {
                isValidDate = true;
            } else if (year == endYear) {
                if (month < endMonth) {
                    isValidDate = true;
                } else if (month == endMonth) {
                    if (day < endDay) {
                        isValidDate = true;
                    } else if (day == endDay) {
                        if (hour < endHour) {
                            isValidDate = true;
                        } else if (hour == endHour) {
                            if (minute <= endMin) {
                                isValidDate = true;
                            }
                        }
                    }
                }
            }

            if (isValidDate) {
                this.startMonth = month;
                this.startDay = day;
                this.startYear = year;
                this.startHour = hour;
                this.startMin = minute;
            }

        } else if (startDate != null && endDate != null) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
            this.startHour = startDate.get(Calendar.HOUR_OF_DAY);
            this.endHour = endDate.get(Calendar.HOUR_OF_DAY);
            this.startMin = startDate.get(Calendar.MINUTE);
            this.endMin = endDate.get(Calendar.MINUTE);
        }

    }

    /**
     * 设置间距倍数,但是只能在1.0-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * @param isCenterLabel 是否只显示中间选中项的
     */
    public void isCenterLabel(boolean isCenterLabel) {
        wv_day.isCenterLabel(isCenterLabel);
        wv_month.isCenterLabel(isCenterLabel);
        wv_year.isCenterLabel(isCenterLabel);
        wv_hours.isCenterLabel(isCenterLabel);
        wv_minutes.isCenterLabel(isCenterLabel);
        wv_seconds.isCenterLabel(isCenterLabel);
    }

    public void setSelectChangeCallback(ISelectTimeCallback mSelectChangeCallback) {
        this.mSelectChangeCallback = mSelectChangeCallback;
    }


}
