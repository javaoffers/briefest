package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.base.modelhelper.sample.constant.ManLevel;
import com.javaoffers.base.modelhelper.sample.constant.Sex;
import com.javaoffers.base.modelhelper.sample.constant.Work;
import com.javaoffers.base.modelhelper.sample.json.ExtraInfo;
import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.anno.ColName;
import com.javaoffers.brief.modelhelper.anno.derive.Blur;
import com.javaoffers.brief.modelhelper.anno.derive.StringBlur;
import com.javaoffers.brief.modelhelper.anno.derive.flag.IsDel;
import com.javaoffers.brief.modelhelper.anno.derive.flag.Version;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.Now;
import com.javaoffers.brief.modelhelper.anno.fun.params.CaseWhen;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfGt;
import com.javaoffers.brief.modelhelper.anno.fun.params.Left;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Abs;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Ceil;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Floor;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Mod;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Round;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Truncate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.DateFormat;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Dayname;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.FromDays;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Hour;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Makedate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Maketime;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Minute;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Month;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Monthname;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.SecToTime;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.StrToDate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.TimeFormat;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.TimeToSec;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.ToDays;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Week;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Weekday;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Year;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.CharLength;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Concat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.ConcatWs;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.GroupConcat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.LTrim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Length;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Lower;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.RTrim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Repeat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Replace;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Strcmp;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.SubString;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Trim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Upper;
import com.javaoffers.brief.modelhelper.core.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@BaseModel
@Data
@Builder
@AllArgsConstructor
public class User {

    /**
     * You can use id to do count(id). Use this object to get the result of count.
     */
    @ColName(value = "id", excludeColAll = true)
    private Long countId;

    @ColName("id")
    private Id IdImpl;

    @BaseUnique
    private Long id;

    private String name;

    private String birthday;

    @ColName("money")
    @StringBlur(blur = Blur.POST_BLUR, percent = 0.5)
    private String moneyBlur;

    @ColName("birthday")
    @Left(10)
    @Trim
    @StringBlur
    private String birthdayDateBlur;

    @ColName("birthday")
    private Date birthdayDate;

    private String createTime;

    @ColName("create_time")
    private Date createTimeDate;

    private String money;

    @ColName(value = "sum(money)", excludeColAll = true)
    private String sumMoney;

    private Sex sex;

    private com.javaoffers.base.modelhelper.sample.constant.Month  month;

    private Work work;

    private IsDel isDel;

    private Version version;

    @ColName("name")
    @ConcatWs(separator = "':'", value = "id", position = 1)
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "id",sort = GroupConcat.Sort.DESC))
    private String groupConcat;

    @ColName("'poor'")
    private ManLevel manLevel;

    @Now
    private String now;

    @ColName("money")
    @IfGt(gt = "10000",ep1 = "'very rich'", ep2 = "'generally rich'")
    private String moneyDesc;

    @ColName("-1")
    @Abs
    private String absCN;

    @ColName("3.2")
    @Ceil
    private String ceilCN;

    @ColName("3.2")
    @Floor
    private String floorCN;

    @ColName("3")
    @Mod( 2 )
    private String modCN;

    @ColName("1.6")
    @Round
    private String roundCN;

    @ColName("7.536432")
    @Truncate(2)
    private String truncateCN;

    @Now
    @DateFormat("'%m-%d-%Y'")
    private String dateFormatCN;

    @Now
    @Dayname
    private String daynameCN;

    @ColName("'780500'")
    @FromDays
    private String fromDaysCN;

    @Now
    @Hour
    private String hourCN;

    @ColName("2022")
    @Makedate(45)
    private String makedateCN;

    @ColName("12")
    @Maketime(ep1 = 10, ep2 = 20)
    private String maketimeCN;

    @Now
    @Minute
    private String minuteCN;

    @Now
    @Month
    private String monthCN;

    @Now
    @Monthname
    private String monthnameCN;

    @ColName("1000")
    @SecToTime
    private String secToTimeCN;

    @ColName("'202205251130'")
    @StrToDate("'%Y%m%d%h%i'")
    private String strToDateCN;

    @Now
    @TimeFormat("'%Y/%m/%d %H:%i:%s'")
    private String timeFormatCN;

    @ColName("'15:15:15'")
    @TimeToSec
    private String timeToSecCN;


    @Now
    @ToDays
    private String toDaysCN;

    @Now
    @Week
    private String weekCN;

    @Now
    @Weekday
    private String weekdayCN;

    @Now
    @Year
    private String yearCN;

    @ColName("'123456789'")
    @CharLength
    private String charLengthCN;

    @ColName("'123456789'")
    @Concat( {"'xx'"})
    private String concatCN;

    @ColName("'123456789'")
    @Length
    private String lengthCN;

    @ColName("'ABCD'")
    @Lower
    private String lowerCN;

    @ColName("' 123456789'")
    @LTrim
    private String ltrimCN;

    @ColName("'123456789'")
    @Repeat(2)
    private String repeatCN;

    @ColName("'123456789'")
    @Replace(ep1 = "'123'", ep2 = "'ABC'")
    private String replaceCN;

    @ColName("'123456789 '")
    @RTrim
    private String rTrimCN;

    @ColName("'123456789'")
    @Strcmp("'123'")
    private String strcmpCN;

    @ColName("'123456789'")
    @SubString(ep1 = "2", ep2 = "4")
    private String subStringCN;

    @ColName("' 123456789 '")
    @Trim
    private String trimCN;

    @ColName("'abcd'")
    @Upper
    private String upperCN;

    @CaseWhen(whens = {
            @CaseWhen.When(when = "money < 10", then = "'pool'"),
            @CaseWhen.When(when = "money > 10000", then = "'rich'")},
            elseEnd = @CaseWhen.Else("'civilian'")
    )
    private String moneyDes;

//    @ColName("'{\"nickName\":\"mingJie\",\"age\":30}'")
//    private ExtraInfo extraInfo;

    private List<UserOrder> orders;

    private List<Teacher> teachers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<UserOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrder> orders) {
        this.orders = orders;
    }

    public User() {
    }

}
