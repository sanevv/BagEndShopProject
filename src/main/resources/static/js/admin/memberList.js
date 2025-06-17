const startParams = {
    page: 1,
    size: 10
};
let prevSearchType = '';
let prevSearchValue = '';
let prevSize = 10; // 이전에 선택한 페이지 크기
let prevPage = 1; // 이전에 선택한 페이지 번호

const searchTbody = document.getElementById('searchTbody');

function viewMemberList(params) {
    axios.get('/api/admin/member', {params: params})
        .then(response => {

            const memberList = response.data.success.responseData.items;
            searchTbody.innerHTML = '';
            if (memberList && memberList.length > 0) {
                memberList.forEach(member => {
                    const tr = document.createElement('tr');
                    tr.className = 'memberInfo';
                    tr.innerHTML = `
                    <td>${member.searchCount}</td>
                    <td id="userId">${member.userId}</td>
                    <td>${member.userName}</td>
                    <td>${member.email}</td>
                    <td>${member.gender}</td>
                `;
                    searchTbody.appendChild(tr);
                });
                // 페이지네이션 처리
                prevSize = response.data.success.responseData.size;
                prevPage = response.data.success.responseData.page;
                prevSearchValue = params.searchValue || '';
                prevSearchType = params.searchColumn || '';


                renderPagination(params.page, response.data.success.responseData.totalPages);

            } else {
                const tr = document.createElement('tr');
                tr.innerHTML = `<td colspan="5">데이터가 존재하지 않습니다.</td>`;
                searchTbody.appendChild(tr);
            }
        })
        .catch(error => {
            console.error(error);
        });
}

viewMemberList(startParams);

// 부모요소에 click 이벤트 리스너를 추가하여
// 자식 요소인 .memberInfo를 클릭했을 때 이벤트를 처리합니다.

searchTbody.addEventListener('click', function(event) {
    const tr = event.target.closest('.memberInfo');
    if (tr) {
        const userId = tr.querySelector('#userId').textContent.trim();

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/admin/memberDetail.up';

        const idInput = document.createElement('input');
        idInput.type = 'hidden';
        idInput.name = 'userId';
        idInput.value = userId;
        form.appendChild(idInput);

        const prevPageInput = document.createElement('input');
        prevPageInput.type = 'hidden';
        prevPageInput.name = 'prevPage';
        prevPageInput.value = prevPage;
        form.appendChild(prevPageInput);

        const prevSizeInput = document.createElement('input');
        prevSizeInput.type = 'hidden';
        prevSizeInput.name = 'prevSize';
        prevSizeInput.value = prevSize;
        form.appendChild(prevSizeInput);

        const prevSearchTypeInput = document.createElement('input');
        prevSearchTypeInput.type = 'hidden';
        prevSearchTypeInput.name = 'prevSearchType';
        prevSearchTypeInput.value = prevSearchType;
        form.appendChild(prevSearchTypeInput);

        const prevSearchValueInput = document.createElement('input');
        prevSearchValueInput.type = 'hidden';
        prevSearchValueInput.name = 'prevSearchValue';
        prevSearchValueInput.value = prevSearchValue;
        form.appendChild(prevSearchValueInput);



        document.body.appendChild(form);


        form.submit();
    }
});




const searchTypeSelect = document.getElementById('searchType');
const sizePerPageSelect = document.querySelector('select[name="sizePerPage"]');
const searchValueInput = document.getElementById('searchValue')

sizePerPageSelect.addEventListener('change', function () {

    const newSize = Number(this.value);
    const oldSize = Number(prevSize);
    const oldPage = Number(prevPage);

    // 현재 보고 있던 첫 번째 아이템의 전체 인덱스
    const firstItemIndex = (oldPage - 1) * oldSize;
    // 새 페이지 번호 계산
    const newPage = Math.floor(firstItemIndex / newSize) + 1;

    prevSize = newSize;
    prevPage = newPage;
    //
    // console.log(prevSize);
    // console.log(this.value);
    // alert(`페이지 크기가 ${prevSize}로 변경되었습니다.`);
    // alert(`요청할 페이지는 ${prevPage}입니다.`);
    const params = {
        size: prevSize,
        searchColumn: prevSearchType,
        searchValue: prevSearchValue,
        page: prevPage
    };
    viewMemberList(params);
});


searchValueInput.addEventListener("keyup", (event) => {
    if (event.key === "Enter")
        goSearch();
    if (event.key === "Escape")
        searchValueInput.value = '';
})


function goSearch() {
    prevSearchType = searchTypeSelect.value;
    if (prevSearchType === '') {
        alert("검색 유형을 선택해주세요.");
        return;
    }
    prevSearchValue = searchValueInput.value.trim();
    if (prevSearchValue === '') {
        alert("검색어를 입력해주세요.");
        return;
    }
    const params = {
        size: sizePerPageSelect.value,
        searchColumn: prevSearchType,
        searchValue: prevSearchValue,
        page: prevPage
    };
    viewMemberList(params);
}


function renderPagination(currentPage, totalPages) {
    const $bar = $('#paginationBar');
    $bar.empty();

    // 맨처음 버튼
    $bar.append(`
      <li class="page-item${currentPage === 1 ? ' disabled' : ''}">
        <a class="page-link" href="#" aria-label="First" data-page="1">
          <span aria-hidden="true">&laquo;&laquo;</span>
        </a>
      </li>
    `);

    // 이전 버튼
    $bar.append(`
      <li class="page-item${currentPage === 1 ? ' disabled' : ''}">
        <a class="page-link" href="#" aria-label="Previous" data-page="${currentPage - 1}">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
    `);

    // 페이지 번호 (최대 10개)
    let start = Math.floor((currentPage - 1) / 10) * 10 + 1;
    let end = Math.min(start + 9, totalPages);

    for (let i = start; i <= end; i++) {
        $bar.append(`
          <li class="page-item${i === currentPage ? ' active' : ''}">
            <a class="page-link" href="#" data-page="${i}">${i}</a>
          </li>
        `);
    }

    // 다음 버튼
    $bar.append(`
      <li class="page-item${currentPage === totalPages ? ' disabled' : ''}">
        <a class="page-link" href="#" aria-label="Next" data-page="${currentPage + 1}">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    `);

    // 맨끝 버튼
    $bar.append(`
      <li class="page-item${currentPage === totalPages ? ' disabled' : ''}">
        <a class="page-link" href="#" aria-label="Last" data-page="${totalPages}">
          <span aria-hidden="true">&raquo;&raquo;</span>
        </a>
      </li>
    `);
}


// 페이지 클릭 이벤트
$(document).on('click', '#paginationBar .page-link', function (e) {
    e.preventDefault();
    const page = Number($(this).data('page'));
    const params = {
        size: sizePerPageSelect.value,
        searchColumn: prevSearchType,
        searchValue: prevSearchValue,
        page: page
    };

    if (!isNaN(page) && page > 0) {
        viewMemberList(params);
        // 여기에 페이지 이동 또는 데이터 로딩 함수 호출
        renderPagination(page, 10); // 예시: 10페이지 기준
    }
});