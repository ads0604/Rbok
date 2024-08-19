<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP Example</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/custom/custom.css" rel="stylesheet" type="text/css">

</head>
<body>
<%
    // 스크립틀릿
    String greeting = "알면 복지, 모르면 휴지! 알복";
%>

<div class="container mt-5">
    <div class="input-group mb-3">
        <input type="text" class="form-control" id="search-text" placeholder="복지명을 입력하세요.">
    </div>
    <form>
        <fieldset>
            <legend>OPEN API 목록</legend>
            <div class="input-group">
                <button type="button" id="datagokr_btn" class="btn btn-primary group-btn">공공데이터 검색</button>
                <!-- <button type="button" id="dataseoul_btn" class="btn btn-secondary group-btn" disabled>열린데이터 검색</button> -->
                <button type="button" id="youthcenter_btn" class="btn btn-success group-btn">온통청년 검색</button>
            </div>
        </fieldset>
    </form>

    <fieldset>
        <legend id="search_title">검색결과</legend>
        <div id="search_result"></div>
    </fieldset>

    <nav aria-label="Page navigation">
        <ul class="pagination"></ul>
    </nav>

    <!-- Modal창 -->
    <div class="modal fade" id="resultModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="resultTitle">title</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
                </div>
                <div class="modal-body">
                    <a id="modal-link" target="_blank">:: URL 링크</a>
                    <div id="modal-content">
                        <span id="modal-tgtrDtlCn"></span>
                        <span id="modal-slctCritCn"></span>
                        <span id="modal-alwServCn"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="js/jquery-3.7.1.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom/custom.js"></script>
<script type="text/javascript">
    $(function(){
        const datagokr_btn = document.querySelector("#datagokr_btn");
        const youthcenter_btn = document.querySelector("#youthcenter_btn");

        // 공공데이터 검색 버튼
        datagokr_btn.addEventListener("click", function () {
            $('#search_result').html("");

            let search_val = $("#search-text").val();
            api_call("datagokr", search_val, "1");
        });

        // 온통청년 검색 버튼
        youthcenter_btn.addEventListener("click", function () {
            $('#search_result').html("");

            let search_val = $("#search-text").val();
            api_call("youthcenter", search_val, "1");
        });

        // 페이지네이션 버튼 선택
        $(document).on("click",".page-ajax", function () {
            let pageNum = $(this).text();
            let search_val = $("#search-text").val();
            let type = $(this).data("type");

            if(pageNum.length === 0) return;

            $(".active").removeClass("active");
            $(this).addClass("active");


            api_call(type, search_val, pageNum);
        });

        // 아이템리스트 선택
        $(document).on("click",".serviceitem", function () {

            let serviceId = $(this).data("serviceid");
            let serviceType = $(this).data("servicetype");
            let serviceLink = $(this).data("link");
            let serviceNm = $(this).text();

            const Json_result = api_call_serviceDetail(serviceType, serviceId);
            createModalcontent(serviceType, serviceNm, serviceLink, Json_result);
            $("#resultModal").modal("show");
        });

    });

</script>
</body>
</html>