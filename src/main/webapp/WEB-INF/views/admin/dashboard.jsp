<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../include/header.jsp"/>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<script src="https://code.highcharts.com/themes/adaptive.js"></script>
<style>

    .highcharts-figure,
    .highcharts-data-table table {
        min-width: 310px;
        max-width: 800px;
        margin: 1em auto;
    }

    #chartDashBoardContainer {
        max-width: 1024px;
        height: 600px;
        margin: 120px auto;
    }

    .highcharts-data-table table {
        font-family: Verdana, sans-serif;
        border-collapse: collapse;
        border: 1px solid #ebebeb;
        margin: 10px auto;
        text-align: center;
        width: 100%;
        max-width: 500px;
    }

    .highcharts-data-table caption {
        padding: 1em 0;
        font-size: 1.2em;
        color: #555;
    }

    .highcharts-data-table th {
        font-weight: 600;
        padding: 0.5em;
    }

    .highcharts-data-table td,
    .highcharts-data-table th,
    .highcharts-data-table caption {
        padding: 0.5em;
    }

    .highcharts-data-table thead tr,
    .highcharts-data-table tr:nth-child(even) {
        background: #f8f8f8;
    }

    .highcharts-data-table tr:hover {
        background: #f1f7ff;
    }

    #selectMonths {
        display: flex;
        border: solid 1px #ddd;
        padding:6px 10px 6px 6px;
        margin-bottom: 34px;
        margin-left: auto;
    }

</style>
<main id="main">
    <div id="chartDashBoardContainer">
        <select id="selectMonths" onchange="adminDashboard(this.value)">
            <option value="3" selected>최근 3개월</option>
            <option value="6">최근 6개월</option>
            <option value="12">최근 12개월</option>
        </select>
        <div id="chartDashBoard"></div>
    </div>

</main>

<jsp:include page="../include/footer.jsp"/>

<script>

    document.addEventListener("DOMContentLoaded", function () {
        const month = new URLSearchParams(window.location.search).get("months");
        adminDashboard(month);
    })
    ///api/admin/3months

    adminDashboard = (month) => {

        if(month == null) month = 3;

        console.log(month);
        fetch("/api/admin/3months?months="+month)
            .then(response => {
                if (!response.ok) {
                    alert("뭔가 잘못 됐다..!!");
                    return;
                }
                return response.json();
            })
            .then(res => {
                //console.log("전체 응답 확인:", res);

                // 응답 받은 res가 성공적으로 들어오면 responseData 사용헐거임
                const responArr = res.success?.responseData;

                if (Array.isArray(responArr)) {

                    //console.log(responArr.length);

                    const dashboardArr = [];

                    for(let i=0; i < responArr.length; i++){

                        let amountDiscountArr = [];

                        amountDiscountArr.push(Number(responArr[i].usageAmountDiscount));
                        // console.log(responArr[i].month);
                        // console.log(responArr[i].usageAmountDiscount);


                        const obj = {name: responArr[i].month+"월",
                                     data: amountDiscountArr};

                        dashboardArr.push(obj);
                    }

                    const nowMonth = new Date().getMonth() + 1;

                    Highcharts.chart('chartDashBoard', {
                        chart: {
                            type: 'column'
                        },
                        title: {
                            text:  nowMonth+'월 기준 최근 '+month+'개월 주문금액 통계 리스트'
                        },

                        yAxis: {
                            title: {
                                text: '최근 3개월 주문금액'
                            }
                        },
                        tooltip: {
                            valueSuffix: ' (원)'
                        },
                        plotOptions: {
                            column: {
                                pointPadding: 0.2,
                                borderWidth: 0
                            }
                        },
                        series: dashboardArr
                    });

                }

            })
            .catch(error => {
                console.error("API 호출 실패:", error);
            });

    }

</script>