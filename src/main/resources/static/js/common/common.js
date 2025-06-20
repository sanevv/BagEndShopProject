window.onload = function() {
    const btnAllMenu = document.querySelector('#btnAllMenu');

    // 헤더 전체메뉴 버튼 클릭 이벤트
    btnAllMenu.addEventListener('click', function () {
        const header = document.querySelector("#header");

        const isActive = this.classList.contains('active');

        if (isActive) {
            // active → deactive로 전환
            header.classList.remove("expand");
            btnAllMenu.classList.remove('active');
            btnAllMenu.classList.add('deactive');

            // 애니메이션 끝난 뒤 deactive 제거
            setTimeout(() => {
                btnAllMenu.classList.remove('deactive');
            }, 750);
        }
        else {
            header.classList.add("expand");
            // deactive 상태 아닐 때만 active 추가
            btnAllMenu.classList.add('active');

        }
    });

    // 검색 버튼 클릭 이벤트
    const btnSearch = document.querySelector('.btn-search');
    const searchForm = document.querySelector('.search-form');
    const inpSearch = document.querySelector('.inp-search');

    //searchForm.classList.add("deactive");

    btnSearch.onclick = () => {
        searchForm.classList.add("active");
        searchForm.classList.remove("deactive");
        inpSearch.focus();
    }

    document.addEventListener('click', function(event) {

        // 검색창이 켜져있을 경우에 영역 외 클릭 시 구문 실행
        if(searchForm.classList.contains("active")) {
            // 버튼 자체를 클릭한 경우는 제외 (토글 유지용)
            if (!searchForm.contains(event.target) && !btnSearch.contains(event.target)) {
                searchForm.classList.remove("active");
                searchForm.classList.add("deactive");
                inpSearch.value = "";
            }
        }

    });

}