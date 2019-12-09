<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>回顾考试</title>
    <link href="/home/exam/css/main.css" rel="stylesheet" type="text/css"/>
    <link href="/home/exam/css/iconfont.css" rel="stylesheet" type="text/css"/>
    <link href="/home/exam/css/test.css" rel="stylesheet" type="text/css"/>
    <#--    <style>-->
    <#--        .hasBeenAnswer {-->
    <#--            background: #5d9cec;-->
    <#--            color:#fff;-->
    <#--        }-->
    <#--    </style>-->

</head>
<body>
<div class="main">
    <!--nr start-->
    <div class="test_main">
        <div class="nr_left">
            <div class="test">
                <form action="" method="post">
                    <div class="test_title">
                        <p class="test_time">
                            <img style="float:left;margin-top:15px;margin-left:10px;"
                                 src="/static/home/exam/images/time.png" width="16px"><b class="alt-1">${hour }
                                :${minitute }:${second }</b>
                        </p>
                        <font><input type="button" onclick="" name="test_jiaojuan" value="交卷"></font>
                    </div>
                    <#if (singleQuestionNum !=0 )>
                        <div class="test_content">
                            <div class="test_content_title">
                                <h2>单选题</h2>
                                <p>
                                    <span>共</span><i
                                            class="content_lit">${singleQuestionNum }</i><span>题，</span><span>合计</span><i
                                            class="content_fs">${singleQuestionScore}</i><span>分</span>
                                </p>
                            </div>
                        </div>
                        <div class="test_content_nr">
                            <ul>
                                <#list singleQuestionList as singleQuestion>
                                    <li id="qu_${singleQuestion.questionType}_${singleQuestion.id}"
                                        data-key="${singleQuestion.id}">
                                        <div class="test_content_nr_tt">
                                            <i>${singleQuestion_index+1}</i><span>(${singleQuestion.score}分)</span><font>${singleQuestion.title }</font>
                                        </div>

                                        <div class="test_content_nr_main">
                                            <ul>

                                                <li class="option" data-type="single" data-value="A">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${singleQuestion.id}"
                                                           id="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_1"
                                                            <#if singleQuestion.answer =='A'>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_1">
                                                        A.
                                                        <p class="ue"
                                                           style="display: inline;">${singleQuestion.attrA }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="B">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${singleQuestion.id}"
                                                           id="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_2"

                                                            <#if singleQuestion.answer =='B'>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_2">
                                                        B.
                                                        <p class="ue"
                                                           style="display: inline;">${singleQuestion.attrB }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="C">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${singleQuestion.id}"
                                                           id="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_3"
                                                            <#if singleQuestion.answer =='C'>
                                                                checked
                                                            </#if>

                                                    />


                                                    <label for="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_3">
                                                        C.
                                                        <p class="ue"
                                                           style="display: inline;">${singleQuestion.attrC }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="D">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${singleQuestion.id}"
                                                           id="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_4"

                                                            <#if singleQuestion.answer =='D'>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${singleQuestion.questionType }_answer_${singleQuestion.id}_option_4">
                                                        D.
                                                        <p class="ue"
                                                           style="display: inline;">${singleQuestion.attrD }</p>
                                                    </label>

                                                </li>
                                                <li class="option" data-type="single" data-value="D">
                                                    <label style="color:red;">正确答案：${singleQuestion.trueAnswer }</label>
                                                </li>

                                            </ul>
                                        </div>
                                    </li>
                                </#list>
                            </ul>

                        </div>
                    </#if>
                    <#if (muiltQuestionNum !=0 )>
                        <div class="test_content">
                            <div class="test_content_title">
                                <h2>多选题</h2>
                                <p>
                                    <span>共</span><i
                                            class="content_lit">${muiltQuestionNum }</i><span>题，</span><span>合计</span><i
                                            class="content_fs">${muiltQuestionScore}</i><span>分</span>
                                </p>
                            </div>
                        </div>
                        <div class="test_content_nr">
                            <ul>
                                <#list muiltQuestionList as muiltQuestion>
                                    <li id="qu_${muiltQuestion.questionType}_${muiltQuestion.id}"
                                        data-key="${muiltQuestion.id}">
                                        <div class="test_content_nr_tt">
                                            <i>${muiltQuestion_index+1}</i><span>(${muiltQuestion.score}分)</span><font>${muiltQuestion.title }</font>
                                        </div>

                                        <div class="test_content_nr_main">
                                            <ul>

                                                <li class="option" data-type="single" data-value="A">

                                                    <input type="checkbox" class="radioOrCheck"
                                                           name="answer${muiltQuestion.id}"
                                                           id="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_1"
                                                            <#if muiltQuestion.answer?split(',')?seq_contains('A')>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_1">
                                                        A.
                                                        <p class="ue"
                                                           style="display: inline;">${muiltQuestion.attrA }</p>
                                                    </label>
                                                </li>
                                                <li class="option" data-type="single" data-value="B">
                                                    <input type="checkbox" class="radioOrCheck"
                                                           name="answer${muiltQuestion.id}"
                                                           id="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_2"
                                                            <#if muiltQuestion.answer?split(',')?seq_contains('B')>
                                                                checked
                                                            </#if>

                                                    />


                                                    <label for="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_2">
                                                        B.
                                                        <p class="ue"
                                                           style="display: inline;">${muiltQuestion.attrB }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="C">

                                                    <input type="checkbox" class="radioOrCheck"
                                                           name="answer${muiltQuestion.id}"
                                                           id="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_3"
                                                            <#if muiltQuestion.answer?split(',')?seq_contains('C')>
                                                                checked
                                                            </#if>

                                                    />


                                                    <label for="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_3">
                                                        C.
                                                        <p class="ue"
                                                           style="display: inline;">${muiltQuestion.attrC }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="D">

                                                    <input type="checkbox" class="radioOrCheck"
                                                           name="answer${muiltQuestion.id}"
                                                           id="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_4"
                                                            <#if muiltQuestion.answer?split(',')?seq_contains('D')>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${muiltQuestion.questionType }_answer_${muiltQuestion.id}_option_4">
                                                        D.
                                                        <p class="ue"
                                                           style="display: inline;">${muiltQuestion.attrD }</p>
                                                    </label>
                                                </li>
                                                <li class="option" data-type="single">
                                                    <label style="color:red;">正确答案：${muiltQuestion.trueAnswer}</label>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </#list>
                            </ul>

                        </div>
                    </#if>
                    <#if (chargeQuestionNum !=0 )>
                        <div class="test_content">
                            <div class="test_content_title">
                                <h2>判断题</h2>
                                <p>
                                    <span>共</span><i
                                            class="content_lit">${chargeQuestionNum }</i><span>题，</span><span>合计</span><i
                                            class="content_fs">${chargeQuestionScore}</i><span>分</span>
                                </p>
                            </div>
                        </div>
                        <div class="test_content_nr">
                            <ul>
                                <#list chargeQuestionList as chargeQuestion>
                                    <li id="qu_${chargeQuestion.questionType}_${chargeQuestion.id}"
                                        data-key="${chargeQuestion.id}">
                                        <div class="test_content_nr_tt">
                                            <i>${chargeQuestion_index+1}</i><span>(${chargeQuestion.score}分)</span><font>${chargeQuestion.title }</font>
                                        </div>

                                        <div class="test_content_nr_main">
                                            <ul>

                                                <li class="option" data-type="single" data-value="A">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${chargeQuestion.id}"
                                                           id="${chargeQuestion.questionType }_answer_${chargeQuestion.id}_option_1"

                                                            <#if chargeQuestion.answer =='A'>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${chargeQuestion.questionType }_answer_${chargeQuestion.id}_option_1">
                                                        A.
                                                        <p class="ue"
                                                           style="display: inline;">${chargeQuestion.attrA }</p>
                                                    </label>
                                                </li>

                                                <li class="option" data-type="single" data-value="B">

                                                    <input type="radio" class="radioOrCheck"
                                                           name="answer${chargeQuestion.id}"
                                                           id="${chargeQuestion.questionType }_answer_${chargeQuestion.id}_option_2"

                                                            <#if chargeQuestion.answer =='B'>
                                                                checked
                                                            </#if>
                                                    />


                                                    <label for="${chargeQuestion.questionType }_answer_${chargeQuestion.id}_option_2">
                                                        B.
                                                        <p class="ue"
                                                           style="display: inline;">${chargeQuestion.attrB }</p>
                                                    </label>
                                                </li>
                                                <li class="option" data-type="single" data-value="D">
                                                    <label style="color:red;">正确答案：${chargeQuestion.trueAnswer}</label>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </#list>
                            </ul>

                        </div>
                    </#if>
                </form>
            </div>

        </div>
        <div class="nr_right">
            <div class="nr_rt_main">
                <div class="rt_nr1">
                    <div class="rt_nr1_title">
                        <h1>
                            <span style="font-size:18px;">答题卡</span>
                        </h1>
                        <p class="test_time">
                            <img style="float:left;margin-top:15px;margin-left:10px;"
                                 src="/static/home/exam/images/time.png" width="16px">
                            <b class="alt-1">${hour}:${minitute}:${second}</b>
                        </p>
                    </div>
                    <#if (singleQuestionNum !=0 )>
                        <div class="rt_content">
                            <div class="rt_content_tt">
                                <h2>单选题</h2>
                                <p>
                                    <span>共</span><i class="content_lit">${singleQuestionNum }</i><span>题</span>
                                </p>
                            </div>
                            <div class="rt_content_nr answerSheet">
                                <ul>
                                    <#list singleQuestionList as singleQuestion>
                                        <li>
                                            <a href="#qu_${singleQuestion.questionType}_${singleQuestion.id}">${singleQuestion_index+1}</a>
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                        </div>
                    </#if>
                    <#if (muiltQuestionNum !=0 )>
                        <div class="rt_content">
                            <div class="rt_content_tt">
                                <h2>多选题</h2>
                                <p>
                                    <span>共</span><i class="content_lit">${muiltQuestionNum }</i><span>题</span>
                                </p>
                            </div>
                            <div class="rt_content_nr answerSheet">
                                <ul>
                                    <#list muiltQuestionList as muiltQuestion>
                                        <li>
                                            <a href="#qu_${muiltQuestion.questionType}_${muiltQuestion.id}">${muiltQuestion_index+1}</a>
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                        </div>
                    </#if>
                    <#if (chargeQuestionNum !=0 )>
                        <div class="rt_content">
                            <div class="rt_content_tt">
                                <h2>判断题</h2>
                                <p>
                                    <span>共</span><i class="content_lit">${chargeQuestionNum }</i><span>题</span>
                                </p>
                            </div>
                            <div class="rt_content_nr answerSheet">
                                <ul>
                                    <#list chargeQuestionList as chargeQuestion>
                                        <li>
                                            <a href="#qu_${chargeQuestion.questionType}_${chargeQuestion.id}">${chargeQuestion_index+1}</a>
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <!--nr end-->
    <div class="foot"></div>
</div>

<script src="/home/exam/js/jquery-1.11.3.min.js"></script>
<script src="/home/exam/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="/home/exam/js/jquery.countdown.js"></script>
<script>
    window.jQuery(function ($) {
        "use strict";

        $('time').countDown({
            with_separators: false
        });
        $('.alt-1').countDown({
            css_class: 'countdown-alt-1'
        });
        $('.alt-2').countDown({
            css_class: 'countdown-alt-2'
        });
        $('.alt-3').countDown({
            css_class: 'countdown-alt-3'
        });
    });


    $(function () {
        $('li.option input').click(function () {
            var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }

        });
    });

    function autoSubmitExam() {
    }
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>【looli.club】在线考试系统</p>
    <p><a href="#" target="_blank">【looli.club】</a></p>
</div>
</body>
</html>