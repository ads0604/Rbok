function api_call(type, search_text, pageNo) {

    if(type == "datagokr") {
        $.ajax({
            url: '/api/getApiDataDatagokr.do',
            type: 'GET',
            data: {
                callTp: "L",
                pageNo: pageNo,
                numOfRows: "10",
                srchKeyCode: "003",
                searchWrd: search_text
            },
            success: function(res) {
                let result = JSON.parse(res);
                let resultArr = result['wantedList']['servList'];
                let htmlStr_title = "검색결과 : " + search_text + "(" + result['wantedList'].totalCount + ")";

                // 검색 결과 리스트업
                let htmlStr_result = "<ol>";
                for(let idx in resultArr) {
                    let li_idx = (parseInt(pageNo)-1) * 10 + (parseInt(idx)+1);
                    // 관련 복지사이트로 이동ver
                    // htmlStr_result += "<li value=" + li_idx + "><a href='" + resultArr[idx].servDtlLink + "' title='" + resultArr[idx].servDgst +"' target='_blank'>[" + resultArr[idx].jurMnofNm + "] " + resultArr[idx].servNm + "</a></li>";

                    // 서비스ID로 세부사항 조회
                    htmlStr_result += "<li value=" + li_idx + "><a href='javascript:void(0);' title='" + resultArr[idx].servDgst +"' class='serviceitem'"
                        + "data-serviceid='"+ resultArr[idx].servId +"' data-servicetype='"+ type +"' data-link='"+ resultArr[idx].servDtlLink
                        + "'>[" + resultArr[idx].jurMnofNm + "] " + resultArr[idx].servNm + "</a></li>";
                }
                htmlStr_result += "</ol>";

                $("#search_result").html("");
                $('#search_title').text(htmlStr_title);
                $('#search_result').append(htmlStr_result);

                createPageNation(type, result['wantedList'].totalCount, result['wantedList'].numOfRows, result['wantedList'].pageNo);
            },
            error: function(error) {
                console.log(error);
            }
        });
    } else if(type == "youthcenter") {

        const DISPLAY = 10;
        $.ajax({
            url: '/api/getApiDataYouthcenter.do',
            type: 'GET',
            data: {
                display: DISPLAY,                  /*  출력건수  */
                pageIndex: pageNo,              /*  페이지 번호 */
                query: search_text,             /*  검색키워드 */
                srchPolyBizSecd: "003002001"    /*  지역코드 서울 */
            },
            success: function(res) {
                let result = JSON.parse(res);
                console.log(result);

                // let resultArr = result['empsInfo']['emp']; // API 구
                // let htmlStr_title = "검색결과 : " + search_text + "(" + result['empsInfo'].totalCnt + ")"; // API 구
                let resultArr = result['youthPolicyList']['youthPolicy'];
                let htmlStr_title = "검색결과 : " + search_text + "(" + result['youthPolicyList'].totalCnt + ")";


                let htmlStr_result = "<ol>";
                for(let idx in resultArr) {
                    let li_idx = (parseInt(pageNo)-1) * 10 + (parseInt(idx)+1);

                    //
                    // 서비스ID로 세부사항 조회
                    htmlStr_result += "<li value=" + li_idx + "><a href='javascript:void(0);' title='" + resultArr[idx].polyItcnCn +"' class='serviceitem'"
                        + "data-serviceid='"+ resultArr[idx].bizId +"' data-servicetype='"+ type +"' data-link='"+ (resultArr[idx].rfcSiteUrla1 == "null" ? "#" : resultArr[idx].rfcSiteUrla1)
                        + "'>[" + resultArr[idx].cnsgNmor + "] " + resultArr[idx].polyBizSjnm + "</a></li>";
                }
                htmlStr_result += "</ol>";

                $("#search_result").html("");
                $('#search_title').text(htmlStr_title);
                $('#search_result').append(htmlStr_result);

                // 검색 결과 리스트업
                // createPageNation(type, result['empsInfo'].totalCnt, DISPLAY, result['empsInfo'].pageIndex); // API 구
                createPageNation(type, result['youthPolicyList'].totalCnt, DISPLAY, result['youthPolicyList'].pageIndex);
            },
            error: function(error) {
                console.log(error);
            }
        });
    } else {
        console.log("[error]api call type error!");
    }
}

function api_call_serviceDetail(type, serviceId) {
    let result;

    if(type == "datagokr") {
        $.ajax({
            url: '/api/getApiDataDetailDatagokr.do',
            type: 'GET',
            async: false,
            data: {
                serviceType: type,
                callTp: "D",
                servId: serviceId
            },
            success: function (res) {
                console.log(res);
                result = res;
            },
            error: function (error) {
                console.log(error);
                result = "";
            }
        });
    } else if(type == "youthcenter") {
        $.ajax({
            url: '/api/getApiDataDetailYouthcenter.do',
            type: 'GET',
            async: false,
            data: {
                srchPolicyId: serviceId
            },
            success: function (res) {
                console.log(res);
                result = res;
            },
            error: function (error) {
                console.log(error);
                result = "";
            }
        });
    } else {
        console.log("[error]api Detail call type error!");
    }
    return result;
}

function createModalcontent(serviceType, serviceNm, serviceLink, Json_result){
    $("#resultTitle").text(serviceNm);
    $("#modal-link").attr("href", serviceLink);

    let result = JSON.parse(Json_result);
    if(serviceType == "datagokr") {
        $("#modal-tgtrDtlCn").text("지원대상 : " + result["wantedDtl"]["tgtrDtlCn"]);
        $("#modal-slctCritCn").text("선정기준 : " + result["wantedDtl"]["slctCritCn"]);
        $("#modal-alwServCn").text("급여서비스 내용 : " + result["wantedDtl"]["alwServCn"]);
    } else if(serviceType == "youthcenter") {
        console.log(result);
        let tgtrDtlCn = "연령 :" + result["youthPolicyList"]["youthPolicy"].ageInfo;
            tgtrDtlCn += "<br>취업상태 :" + result["youthPolicyList"]["youthPolicy"].empmSttsCn;
            tgtrDtlCn += "<br>학력 :" + result["youthPolicyList"]["youthPolicy"].accrRqisCn;
            tgtrDtlCn += "<br>전공 :" + result["youthPolicyList"]["youthPolicy"].majrRqisCn;
            tgtrDtlCn += "<br>특화분야 :" + result["youthPolicyList"]["youthPolicy"].splzRlmRqisCn;

        $("#modal-tgtrDtlCn").html("지원대상 : " + tgtrDtlCn);
        $("#modal-slctCritCn").text("선정기준 : " + result["youthPolicyList"]["youthPolicy"].ageInfo);
        $("#modal-alwServCn").text("지원 내용 : " + result["youthPolicyList"]["youthPolicy"].sporCn);
    } else {
        console.log("[error]api Modal content type error!");
    }
}

function createPageNation(type, totalCount, numOfRows, pageNo) {

    let pageCnt = Math.ceil(totalCount / numOfRows);
    let htmlStr = "";
                        // "<li class='page-item '>"
                        // + "    <a class='page-link' href='#' aria-label='Previous'>"
                        // + "        <span aria-hidden='true'>&laquo;</span>"
                        // + "    </a>"
                        // + "</li>"
                        if(pageNo == "1") htmlStr = "<li id='first-item' class='page-item active'><a class='page-link page-ajax' href='#' data-type='" + type + "'>1</a></li>";
                        else htmlStr = "<li id='first-item' class='page-item'><a class='page-link page-ajax' href='#' data-type='" + type + "'>1</a></li>";

    for(let i=1; i < pageCnt; i++) {
        // 현재 선택한 페이지인 경우 class에 active를 넣어서 선택표시 처리를 해준다.
        if((i+1) == pageNo) htmlStr += "<li class='page-item active'><a class='page-link page-ajax' href='#' data-type='" + type + "'>"+ (i+1) +"</a></li>"
        // 선택하지 않은 페이지메뉴의 경우
        else htmlStr += "<li class='page-item'><a class='page-link page-ajax' href='#' data-type='" + type +"'>"+ (i+1) +"</a></li>"
    }

                   //htmlStr +=  "<li class='page-item'>"
                   // + "    <a class='page-link' href='#' aria-label='Next'>"
                   // + "        <span aria-hidden='true'>&raquo;</span>"
                   // + "    </a>"
                   // + "</li>";

    $(".pagination").html(htmlStr);
}



